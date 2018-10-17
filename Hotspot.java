/**
 * This class represents a single wi-fi hotspot. 
 *
 * @author YunSeo (Jennifer) Kim
 * @version May 3, 2016
 *
 */
public class Hotspot {
	double latitude; //latitude of hotspot
	double longitude; //longitude of hotspot
	
	String borough; //borough of hotspot
	String locationName; //location name of hotspot
	String locationAddress; //location address of hotspot
	String freeOrNot; //free or not status of hotspot
	
	double distance; //distance of hotspot from zipcode
	
	/**
	 * This hotspot constructor creates a hotspot object with the passed parameters.
	 * @param latitude, latitude of hotspot
	 * @param longitude, longitude of hotspot
	 * @param borough, borough of hotspot
	 * @param locationName, location name of hotspot
	 * @param locationAddress, location address of hotspot
	 * @param freeOrNot, free or not status of hotspot
	 * @param distance, distance of hotspot form zipcode
	 */
	Hotspot (double latitude, double longitude, String borough, String locationName, String locationAddress, String freeOrNot, double distance) {
		this.latitude = latitude;
		this.longitude = longitude;
		
		this.borough = borough;
		this.locationName = locationName;
		this.locationAddress = locationAddress;
		this.freeOrNot = freeOrNot;
		
		this.distance = distance;
	}
	
	/**
	 * This method returns a string version of the important information on the hotspot to be printed.
	 * @return string of information to be printed
	 */
	public String toString() {
		return (borough + ", " + locationName + ", " + locationAddress + ", " + freeOrNot);
	}
}
