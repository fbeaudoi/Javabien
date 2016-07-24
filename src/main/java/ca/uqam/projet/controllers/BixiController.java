
package ca.uqam.projet.controllers;

import ca.uqam.projet.repositories.BixiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author fbeaudoin
 */
@RestController
public class BixiController
{
   @Autowired BixiRepository repository;
}
