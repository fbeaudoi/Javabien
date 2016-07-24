
package ca.uqam.projet.tasks;

import ca.uqam.projet.repositories.ArceauRepository;
import ca.uqam.projet.resources.Arceau;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.csvreader.CsvReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 *
 * @author fbeaudoin
 */
@Component
public class FetchArceauTask
{
   private static final String URL = "http://donnees.ville.montreal.qc.ca/dataset/c4dfdeb1-cdb7-44f4-8068-247755a56cc6/resource/78dd2f91-2e68-4b8b-bb4a-44c1ab5b79b6/download/supportvelosigs.csv";
   
   @Autowired private ArceauRepository repository;
   
   // initialise la BD au demarrage de l'application
   @Scheduled(initialDelay = 8000, fixedRate = Long.MAX_VALUE)
   public void initialize()
   {
      execute();
   }
   
   @Scheduled(cron="0 0 0 1 * *")
   public void execute()
   {
      try
      {
         URL arceauUrl = new URL(URL);
         BufferedReader buffer = new BufferedReader(new InputStreamReader(arceauUrl.openStream()));
         CsvReader csvReader = new CsvReader(buffer);
         csvReader.readHeaders();
         
         repository.clearArceau();
         
         while (csvReader.readRecord())
         {
            repository.insert(new Arceau(Integer.parseInt(csvReader.get("INV_ID"))
                                       , Double.parseDouble(csvReader.get("LONG"))
                                       , Double.parseDouble(csvReader.get("LAT")))
                              );
         }
         csvReader.close();
         
      } catch (FileNotFoundException e)
      {
         e.printStackTrace();
      } catch (IOException e)
      {
         e.printStackTrace();
      }
   }
}
