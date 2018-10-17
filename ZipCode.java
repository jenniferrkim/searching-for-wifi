/**
 * This class represents a single zip code.
 * It stores information about the longitude and latitude that can be used for distance computations. 
 * @author YunSeo
 * @version May 3, 2016
 */
public class ZipCode {
	int zipcode; //zipcode of zipcode object
	
	double latitude; //latitude of zipcode
	double longitude; //longitude of zipcode
	
	/**
	 * This constructor creates a zipcode object with the passed parameters.
	 * @param zipcode, zipcode of zipcode object
	 * @param latitude, latitude of zipcode
	 * @param longitude, longitude of zipcode
	 */
	ZipCode(int zipcode, double latitude, double longitude) {
		this.zipcode = zipcode;
		
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
