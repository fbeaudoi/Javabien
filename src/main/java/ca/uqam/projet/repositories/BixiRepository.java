
package ca.uqam.projet.repositories;

import ca.uqam.projet.resources.Bixi;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
         + " VALUES (?, ST_SetSRID(ST_MakePoint(?, ?), 4326), ?, ?, ?)"
         + " ON conflict do nothing"
         ;
   
   private static final String FIND_ALL_AT_RANGE = 
           " SELECT"
           + "    nb_empty_docks,"
           + "    id,"
           + "    ST_X(coordinates) as longitude,"
           + "    ST_Y(coordinates) as latitude,"
           + "    name,"
           + "    nb_bikes"
           + " FROM"
           + "    bixi"
           + " WHERE"
           + "    ST_DWithin(coordinates, ST_SetSRID(ST_MakePoint(?, ?), 4326), 200.0)"
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
   
   public List<Bixi> findAllAtRange(Double longitude, Double latitude)
   {
      return jdbcTemplate.query(FIND_ALL_AT_RANGE, new Object[] {longitude, latitude}, new BixiRowMapper());
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
