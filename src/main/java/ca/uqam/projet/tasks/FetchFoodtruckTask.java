
package ca.uqam.projet.tasks;

import ca.uqam.projet.repositories.FoodtruckRepository;
import ca.uqam.projet.resources.Foodtruck;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author fbeaudoin
 */
@Component
public class FetchFoodtruckTask
{
   private static final String URL = "http://camionderue.com/donneesouvertes/geojson";
   
   @Autowired private FoodtruckRepository repository;
   
   // initialise la BD au demarrage de l application
   @Scheduled(initialDelay = 1000, fixedRate = Long.MAX_VALUE)
   public void initialize()
   {
      execute();
   }
   
   @Scheduled(cron="0 0 0,12 * * ?")
   public void execute()
   {
      List<FoodtruckCollection> collection;
      collection = Arrays.asList(new RestTemplate().getForObject(URL, FoodtruckCollection.class));
      
      // Modification de la BD seulement si URL a retournee des donnees
      if (collection != null && ! collection.isEmpty())
      {
         repository.clearFoodtruck();
         
         for (Feature f : collection.get(0).features)
         {
            repository.insert(asFoodtruck(f));
         }
      }         
   }
   
   private Foodtruck asFoodtruck(Feature f)
   {
      Date date = null;
      try
      {
         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
         date = format.parse(f.properties.date);
      } catch (ParseException ex)
      {
         Logger.getLogger(FetchFoodtruckTask.class.getName()).log(Level.SEVERE, null, ex);
      }
      return new Foodtruck (f.properties.id, f.properties.name, f.properties.description, f.properties.camion, 
                            date, f.properties.heureDebut, f.properties.heureFin, 
                            f.geometry.coordinates[0], f.geometry.coordinates[1], f.properties.lieu);
   }
}

/*{ Json file structure 
  "type": "FeatureCollection",
  "features": [
    {
      "type": "Feature",
      "geometry": {
        "type": "Point",
        "coordinates": [
          -73.568347,
          45.503578
        ]
      },
      "properties": {
        "name": "",
        "description": "",
        "Date": "2016-07-02",
        "Heure_debut": "11:00",
        "Heure_fin": "16:00",
        "Lieu": "Square Philipps",
        "Camion": "Winneburger (Nouveau Palais)",
        "Truckid": "T00240014"
      }
    }*/
class FoodtruckCollection
{
   @JsonProperty("features") Feature[] features;
}

class Feature
{
   @JsonProperty("geometry") Geometry geometry;
   @JsonProperty("properties") Properties properties;
}

class Geometry
{
   @JsonProperty("coordinates") double coordinates[];
}

class Properties
{
   @JsonProperty("name")         String name;
   @JsonProperty("description")  String description;
   @JsonProperty("Date")         String date;
   @JsonProperty("Heure_debut")  String heureDebut;
   @JsonProperty("Heure_fin")    String heureFin;
   @JsonProperty("Lieu")         String lieu;
   @JsonProperty("Camion")       String camion;
   @JsonProperty("Truckid")      String id;
}
