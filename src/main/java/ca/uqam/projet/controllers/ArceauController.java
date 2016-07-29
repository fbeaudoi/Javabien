
package ca.uqam.projet.controllers;

import ca.uqam.projet.repositories.ArceauRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author fbeaudoin
 */
@RestController
public class ArceauController
{
   @Autowired ArceauRepository repository;
}
