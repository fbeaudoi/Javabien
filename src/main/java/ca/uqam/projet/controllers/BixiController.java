
package ca.uqam.projet.controllers;

import ca.uqam.projet.repositories.BixiRepository;
import ca.uqam.projet.resources.Bixi;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author fbeaudoin
 */
@RestController
public class BixiController
{
   @Autowired BixiRepository repository;
   
   @RequestMapping("/bixi")
   public List<Bixi> findAllAtRange(@RequestParam(value = "lat") Double latitude, @RequestParam(value = "lon") Double longitude)
   {
      return repository.findAllAtRange(latitude, longitude);
   }
}
