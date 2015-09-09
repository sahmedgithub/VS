package polyline;


import java.io.Serializable;

public class Location implements Serializable {

 private float latitude;

 private float longitude;

 public Location() {

 }

 public Location(Float latitude, Float longitude) {
  this.latitude = latitude;
  this.longitude = longitude;
 }

    Location(float lat2d, float lon2d) {
        this.latitude = lat2d;
        this.longitude = lon2d;
    }
 
 /**
  * @return the latitude
  */
 public double getLatitude() {
  return latitude;
 }

 /**
  * @return the longitude
  */
 public double getLongitude() {
  return longitude;
 }
}