
package ca.uqam.projet.repositories;

import ca.uqam.projet.resources.Foodtruck;
import java.sql.Date;
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
public class FoodtruckRepository
{
   @Autowired private JdbcTemplate jdbcTemplate;
   
   private static final String INSERT_FOODTRUCK_INFO_STMT = 
           " INSERT INTO foodtruck (id, name, description, camion)"
         + " VALUES (?, ?, ?, ?)"
         + " on conflict do nothing"
         ;
   
   private static final String INSERT_SCHEDULE_STMT = 
           " INSERT INTO schedule (id, date, heure_debut, heure_fin, coordinates, lieu)"
         + " VALUES (?, ?, ?, ?, ST_SetSRID(ST_MakePoint(?, ?), 4326), ?)"
         + " ON conflict do nothing"
         ;
   
   private static final String FIND_BY_DATE_RANGE_STMT =
           " SELECT"
           + "    foodtruck.id,"
           + "    name,"
           + "    description,"
           + "    camion,"
           + "    date,"
           + "    heure_debut,"
           + "    heure_fin,"
           + "    ST_X(coordinates) as longitude,"
           + "    ST_Y(coordinates) as latitude,"
           + "    lieu"
           + " FROM"
           + "    foodtruck"
           + "    INNER JOIN"
           + "       schedule"
           + "    ON"
           + "       foodtruck.id = schedule.id"
           + " WHERE schedule.date_debut >= ? AND schedule.date_fin <= ?"
           ;

   public void clearFoodtruck()
   {
      jdbcTemplate.update("DELETE FROM schedule");
      jdbcTemplate.update("DELETE FROM foodtruck");
   }
   
   public List<Foodtruck> findByDateRange(Date debut, Date fin)
   {
      return jdbcTemplate.query(FIND_BY_DATE_RANGE_STMT, new Object[]{debut, fin}, new FoodtruckRowMapper());
   }

   public void insert(Foodtruck foodtruck)
   {
      _insertFoodtruckInfo(foodtruck);
      _insertFoodtruckSchedule(foodtruck);
   }

   private int _insertFoodtruckInfo(Foodtruck foodtruck)
   {
      return jdbcTemplate.update(conn -> 
      {
         PreparedStatement ps = conn.prepareStatement(INSERT_FOODTRUCK_INFO_STMT);
         ps.setString(1, foodtruck.getId());
         ps.setString(2, foodtruck.getName());
         ps.setString(3, foodtruck.getDescription());
         ps.setString(4, foodtruck.getCamion());
         return ps;
      });
   }

   private int _insertFoodtruckSchedule(Foodtruck foodtruck)
   {
      return jdbcTemplate.update(conn ->
      {
         PreparedStatement ps = conn.prepareStatement(INSERT_SCHEDULE_STMT);
         ps.setString(1, foodtruck.getId());
         ps.setDate  (2, new java.sql.Date(foodtruck.getDate().getTime()));
         ps.setString(3, foodtruck.getHeureDebut());
         ps.setString(4, foodtruck.getHeureFin());
         ps.setDouble(5, foodtruck.getLongitude());
         ps.setDouble(6, foodtruck.getLatitude());
         ps.setString(7, foodtruck.getLieu());
         return ps;
      });
   }
}

class FoodtruckRowMapper implements RowMapper<Foodtruck>
{
   public Foodtruck mapRow(ResultSet rs, int rowNum) throws SQLException
   {
      return new Foodtruck (
         rs.getString("id")
       , rs.getString("name")
       , rs.getString("description")
       , rs.getString("camion")
       , rs.getDate  ("date")
       , rs.getString("heure_debut")
       , rs.getString("heure_fin")
       , rs.getDouble("longitude")
       , rs.getDouble("latitude")
       , rs.getString("lieu")
      );
   }
}