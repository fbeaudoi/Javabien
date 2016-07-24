
package ca.uqam.projet.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author fbeaudoin
 */
public class Arceau
{
   private int id;
   private double longitude;
   private double latitude;
   
   public Arceau(int id, double longitude, double latitude)
   {
      this.id           = id;
      this.longitude    = longitude;
      this.latitude     = latitude;
   }
   
   @JsonProperty public int getId()             { return id; }
   @JsonProperty public double getLongitude()   { return longitude; }
   @JsonProperty public double getLatitude()    { return latitude; }
   
   @Override public String toString()
   {
      return ("id:" + id + ", longitude:" + longitude + ", latitude:" + latitude);
   }
}
