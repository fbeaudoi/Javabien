
package ca.uqam.projet.tasks;

import ca.uqam.projet.repositories.ArceauRepository;
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
public class FetchArceauTask
{
   private static final Logger log = LoggerFactory.getLogger(FetchArceauTask.class);
   private static final String URL = "http://donnees.ville.montreal.qc.ca/dataset/c4dfdeb1-cdb7-44f4-8068-247755a56cc6/resource/78dd2f91-2e68-4b8b-bb4a-44c1ab5b79b6/download/supportvelosigs.csv";
   
   @Autowired private ArceauRepository repository;
   
   @Scheduled(cron="0 0 0 1 * *")
   public void execute()
   {
      
   }
}
