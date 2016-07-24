
package ca.uqam.projet.repositories;

import ca.uqam.projet.resources.Arceau;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 *
 * @author fbeaudoin
 */
@Component
public class ArceauRepository
{
   @Autowired private JdbcTemplate jdbcTemplate;
   
   private static final String INSERT_STMT = 
           " INSERT INTO bixi (id, coordinates)"
         + " VALUES (?, ST_SetSRID(ST_MakePoint(?, ?), 4326)"
         + " ON conflict do nothing"
         ;
}

class ArceauRowMapper implements RowMapper<Arceau>
{
   public Arceau mapRow(ResultSet rs, int rowNum) throws SQLException
   {
      return new Arceau (
         rs.getInt("id")
       , rs.getDouble("longitude")
       , rs.getDouble("latitude")
      );
   }
}