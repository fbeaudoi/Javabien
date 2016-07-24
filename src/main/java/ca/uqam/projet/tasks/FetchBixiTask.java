
package ca.uqam.projet.tasks;

import ca.uqam.projet.repositories.BixiRepository;
import ca.uqam.projet.resources.Bixi;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author fbeaudoin
 */
@Component
public class FetchBixiTask
{
   private static final String URL = "https://secure.bixi.com/data/stations.json";
   
   @Autowired private BixiRepository repository;
   
   // initialise la BD au demarrage de l'application
   @Scheduled(initialDelay = 3000, fixedRate = Long.MAX_VALUE)
   public void initialize()
   {
      execute();
   }
   
   @Scheduled(cron="0 */10 * * * ?")
   public void execute()
   {
      List<StationCollection> collection;
      collection = Arrays.asList(new RestTemplate().getForObject(URL, StationCollection.class));
      
      // Modification de la BD seulement si URL a retournee des donnees
      if (collection != null && ! collection.isEmpty())
      {
         repository.clearBixi();
         
         for (Station s : collection.get(0).stations)
         {
            repository.insert(_asBixi(s));
         }
      }
   }
   
   private Bixi _asBixi(Station s)
   {
      return new Bixi(s.id, s.longitude, s.latitude, s.name, s.nbBikes, s.nbEmptyDocks);
   }
}

class StationCollection
{
   @JsonProperty("stations") Station[] stations;
}

class Station
{
   @JsonProperty("id") int id;
   @JsonProperty("s") String name;
   @JsonProperty("lo") double longitude;
   @JsonProperty("la") double latitude;
   @JsonProperty("ba") int nbBikes;
   @JsonProperty("dx") int nbEmptyDocks;
}