
package ca.uqam.projet.repositories;

import ca.uqam.projet.resources.Arceau;
import java.sql.PreparedStatement;
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
           " INSERT INTO arceau (id, coordinates)"
         + " VALUES (?, ST_SetSRID(ST_MakePoint(?, ?), 4326))"
         + " ON conflict do nothing"
         ;
   
   public void clearArceau()
   {
      jdbcTemplate.update("DELETE FROM arceau");
   }
   
   public int insert(Arceau arceau)
   {
      return jdbcTemplate.update(conn -> 
      {
         PreparedStatement ps = conn.prepareStatement(INSERT_STMT);
         ps.setInt   (1, arceau.getId());
         ps.setDouble(2, arceau.getLongitude());
         ps.setDouble(3, arceau.getLatitude());
         return ps;
      });
   }
   
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