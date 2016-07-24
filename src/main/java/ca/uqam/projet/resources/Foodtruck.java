
package ca.uqam.projet.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

/**
 *
 * @author fbeaudoin
 */
public class Foodtruck
{
   // Foodtruck info
   private String id;
   private String name;
   private String description;
   private String camion;
   
   // Schedule info
   private Date date;
   private String heureDebut;
   private String heureFin;
   private double longitude;
   private double latitude;
   private String lieu;

   public Foodtruck(String id, String name, String description, String camion, 
           Date date, String heureDebut, String heureFin, double longitude, double latitude, String lieu)
   {
      this.id           = id;
      this.name         = name;
      this.description  = description;
      this.camion       = camion;
      this.date         = date;
      this.heureDebut   = heureDebut;
      this.heureFin     = heureFin;
      this.longitude    = longitude;
      this.latitude     = latitude;
      this.lieu         = lieu;
   }
   
   @JsonProperty public String getId()             { return id; }
   @JsonProperty public String getName()           { return name; }
   @JsonProperty public String getDescription()    { return description; }
   @JsonProperty public String getCamion()         { return camion; }
   @JsonProperty public Date getDate()             { return date; }
   @JsonProperty public String getHeureDebut()     { return heureDebut; }
   @JsonProperty public String getHeureFin()       { return heureFin; }
   @JsonProperty public double getLongitude()      { return longitude; }
   @JsonProperty public double getLatitude()       { return latitude; }
   @JsonProperty public String getLieu()           { return lieu; }
   
   @Override public String toString()
   {
      return ("id:"                 + id
              + ", name:"           + name
              + ", description:"    + description
              + ", camion:"         + camion
              + ", date:"           + date
              + ", heureDebut:"     + heureDebut
              + ", heureFin:"       + heureFin
              + ", longitude:"      + longitude
              + ", latitude:"       + latitude
              + ", lieu:"           + lieu
              );
   }
   
}
