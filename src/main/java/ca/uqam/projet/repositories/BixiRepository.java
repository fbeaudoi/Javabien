
package ca.uqam.projet.repositories;

import ca.uqam.projet.resources.Bixi;
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
public class BixiRepository
{
   @Autowired private JdbcTemplate jdbcTemplate;;
   
   private static final String INSERT_STMT = 
           " INSERT INTO bixi (id, coordinates, name, nb_bikes, nb_empty_docks)"
         + " VALUES (?, ST_SetSRID(ST_MakePoint(?, ?), 4326), ?, ?, ?"
         + " ON conflict do nothing"
         ;
   
   public void clearBixi()
   {
      jdbcTemplate.update("DELETE FROM bixi");
   }
   
   public int insert(Bixi bixi)
   {
      return jdbcTemplate.update(conn -> 
      {
         PreparedStatement ps = conn.prepareStatement(INSERT_STMT);
         ps.setInt   (1, bixi.getId());
         ps.setDouble(2, bixi.getLongitude());
         ps.setDouble(3, bixi.getLatitude());
         ps.setString(4, bixi.getName());
         ps.setInt   (5, bixi.getNbBikes());
         ps.setInt   (6, bixi.getNbEmptyDocks());
         return ps;
      });
   }
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
