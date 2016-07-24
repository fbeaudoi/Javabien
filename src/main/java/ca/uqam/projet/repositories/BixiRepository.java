
package ca.uqam.projet.repositories;

import ca.uqam.projet.resources.Bixi;
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
public class BixiRepository
{
   @Autowired private JdbcTemplate jdbcTemplate;
   
   private static final String INSERT_STMT = 
           " INSERT INTO bixi (id, coordinates, name, nb_bikes, nb_empty_docks)"
         + " VALUES (?, ST_SetSRID(ST_MakePoint(?, ?), 4326), ?, ?, ?"
         + " ON conflict do nothing"
         ;
   
   private static final
}

class BixiRowMapper implements RowMapper<Bixi>
{
   public Bixi mapRow(ResultSet rs, int rowNum) throws SQLException
   {
      return new Bixi (
         rs.getInt("id")
       , rs.getDouble("longitude")
       , rs.getDouble("latitude")
       , rs.getString("name")
       , rs.getInt("nb_bikes")
       , rs.getInt("nb_empty_docks")
      );
   }
}
