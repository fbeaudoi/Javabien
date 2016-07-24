
package ca.uqam.projet.tasks;

import ca.uqam.projet.repositories.BixiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author fbeaudoin
 */
@Component
public class FetchBixiTask
{
   private static final Logger log = LoggerFactory.getLogger(FetchBixiTask.class);
   private static final String URL = "https://secure.bixi.com/data/stations.json";
   
   @Autowired private BixiRepository repository;
   
   @Scheduled(cron="0 */10 * * * ?")
   public void execute()
   {
      
   }
}
