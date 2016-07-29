
package ca.uqam.projet.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author fbeaudoin
 */
public class Bixi
{
   private int id;
   private double longitude;
   private double latitude;
   private String name;
   private int nbBikes;
   private int nbEmptyDocks;
   
   public Bixi(int id, double longitude, double latitude, String name, int nbBikes, int nbEmptyDocks)
   {
      this.id              = id;
      this.longitude       = longitude;
      this.latitude        = latitude;
      this.name            = name;
      this.nbBikes         = nbBikes;
      this.nbEmptyDocks    = nbEmptyDocks;
   }
   
   @JsonProperty public int getId()             { return id; }
   @JsonProperty public double getLongitude()   { return longitude; }
   @JsonProperty public double getLatitude()    { return latitude; }
   @JsonProperty public String getName()        { return name; }
   @JsonProperty public int getNbBikes()        { return nbBikes; }
   @JsonProperty public int getNbEmptyDocks()   { return nbEmptyDocks; }
   
   @Override public String toString()
   {
      return ("id:"                 + id
              + ", longitude:"      + longitude
              + ", latitude:"       + latitude
              + ", name:"           + name
              + ", nbBikes:"        + nbBikes
              + ", nbEmptyDocks:"   + nbEmptyDocks
              );
   }

   
}
