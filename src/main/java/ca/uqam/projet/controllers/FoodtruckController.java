
package ca.uqam.projet.controllers;

import ca.uqam.projet.repositories.FoodtruckRepository;
import ca.uqam.projet.resources.Foodtruck;
import java.util.List;
import ognl.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author fbeaudoin
 */
@RestController
public class FoodtruckController
{
   @Autowired FoodtruckRepository repository;
   
   @RequestMapping("/horaires-camions/{Debut}/{Fin}")
   public List<Foodtruck> findByDateRange(@PathVariable("Debut") String debut, @PathVariable("Fin") String fin) throws ParseException
   {
      assert false : "Find by date range : NOT_YET_IMPLEMENTED";
      return null; //repository.findByDateRange(debut, fin);
   }
   
}
