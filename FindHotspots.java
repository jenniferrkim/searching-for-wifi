/**
 * This class reads the input from the input files, provides the user interface, 
 * performs the computations, and prints the results. 
 * @author YunSeo (Jennifer) Kim
 * @version May 3, 2016
 *
 */
import java.io.*;
import java.util.*;

//throw exception when type of input from in the file does not match what is expected
//make something that allows user to get the next 5 hotspots
public class FindHotspots {
	public static void main(String[] args) {
		//try block to catch exceptions
		try {
			Scanner input = new Scanner(System.in);
			
			System.out.println("This program finds the nearest 5 hotspots to the entered zipcode, and prints information about them.");
			System.out.print("Enter your zipcode. Enter 0 to quit: ");
			
			int userZipCode = input.nextInt(); //read the zipcode that the user enters
			
			//exit if user wants to quit
			if (userZipCode == 0) {
				System.out.println("");
				System.out.println("Thank you for using this program. Goodbye.");
				System.exit(0);
			}
			
			//if user doesn't enter 0
			while (userZipCode != 0) {
				int count = 0; //to count how many times user enters a 1 to get the next 5 hotspots
				
				//read zipcode csv
				File zipCodeFile = new java.io.File("zip_codes_NYC.csv");
				Scanner readZipCode = new Scanner(zipCodeFile);
				//read hotspot csv
				File hotspotFile = new java.io.File("NYC_Wi-Fi_Hotspot_Locations.csv");
				Scanner readHotspots = new Scanner(hotspotFile); 
				
				if (!zipCodeFile.exists()) { //giving feedback if zipcode file does not exist where program expects it
					System.err.println("No such file based on using File class.");
					System.err.println("You tried to open " + zipCodeFile.getAbsolutePath());
					System.exit(0);
				}
				
				if (!hotspotFile.exists()) { //giving feedback if zipcode file does not exist where program expects it
					System.err.println("No such file based on using File class.");
					System.err.println("You tried to open " + hotspotFile.getAbsolutePath());
					System.exit(0);
				}
				
				//create the zipcode array
				ArrayList<ZipCode> zipcodeArray = createZipcodeArray (readZipCode, userZipCode);

				//create hotspot array
				ArrayList<Hotspot> hotspotArray = createHotspotArray (readHotspots, zipcodeArray);
				
				//create sorted distance array
				double [] distanceArray = createDistanceArray(hotspotArray);
				
				//print the first five hotspots the first time
				System.out.println("Your five nearest hotspots are: ");
				for (int i = 0; i < 5; i++) {
					for (int j = 0; j < hotspotArray.size(); j++) {
						if (distanceArray[i] == hotspotArray.get(j).distance) {
							System.out.println(hotspotArray.get(j).toString());
						}
					}
				}
				
				//reentering the user zip code, or choosing to get more hotspots or quitting
				System.out.println("");
				System.out.print("Enter your zipcode. Enter 1 to get 5 more hotspots. Or, enter 0 to quit: ");
				userZipCode = input.nextInt();
				
				//if the user wants to quit now
				if (userZipCode == 0) {
					System.out.println("");
					System.out.println("Thank you for using this program. Goodbye.");
					System.exit(0);
				}
				
				//if the user wants to continue printing out next 5 hotspots
				while (userZipCode == 1) {
					count++; //increase count to keep track of where to get hotspots
					System.out.println("Your five nearest hotspots are: "); //print the next five hotspots
					for (int i = count * 5; i < (count * 5) + 5; i++) {
						for (int j = 0; j < hotspotArray.size(); j++) {
							if (distanceArray[i] == hotspotArray.get(j).distance) {
								System.out.println(hotspotArray.get(j).toString());
							}
						}
					}
					
					System.out.println("");
					System.out.print("Enter your zipcode. Enter 1 to get 5 more hotspots. Or, enter 0 to quit: ");
					userZipCode = input.nextInt(); //have user enter their zipcode again
					
					//if user wants to quit
					if (userZipCode == 0) {
						System.out.println("");
						System.out.println("Thank you for using this program. Goodbye.");
						System.exit(0);
					}
				}
			}
		}

		//when the file does not exist
		catch (FileNotFoundException e) {
			System.err.println("No such file.");
			System.exit(0);
		}
		
		//for if the user enters a zipcode that doesn't exist in the file, or if 
		//the user wants to get the next 5 hotspots and there are no more
		catch (IndexOutOfBoundsException e) {
			System.out.println("");
			System.err.println("There are no hotspots. Goodbye.");
		}
		
		//when the input isn't an integer
		catch (InputMismatchException e) {
			System.out.println("");
			System.err.println("Please enter a valid integer zipcode.");
		}
		
		//for catching anything else
		catch (Exception e) {
			System.err.println("There is an exception.");
			System.exit(0);
		}
	}
	

	/**
	 * This method creates an arrayList of zipcode objects with one zipcode object that matches the 
	 * zipcode the user entered.
	 * @param readZipCode scanner that reads the file of zipcodes to pull the information for the one the user entered
	 * @param userZipCode the user entered zipcode
	 * @return ArrayList of the one zipcode object of the user entered zip code
	 */
	public static ArrayList <ZipCode> createZipcodeArray (Scanner readZipCode, int userZipCode){ 
		ArrayList<ZipCode> zipcodeArray = new ArrayList<ZipCode>(); //create arraylist of the zipcode that's found
		readZipCode.nextLine(); //skip the first label line
		
		//while the zipcode file has another line, look through it
		while (readZipCode.hasNextLine()){
			//split the line into strings separated by commas
			ArrayList<String> zipCodeInfo = split(readZipCode.nextLine()); 
			//get the zipcode in the csv file
			int zipCode = Integer.parseInt(zipCodeInfo.get(0));
			
			//if there's a zipcode match 
			if (userZipCode == zipCode) {
				double zipCodeLat = Double.parseDouble(zipCodeInfo.get(1)); //get latitude of zipcode
				double zipCodeLong = Double.parseDouble(zipCodeInfo.get(2)); //get longitude of zipcode

				//create zipcode object with the zipcode, latitude, and longitude
				ZipCode zipCodeObject = new ZipCode(zipCode, zipCodeLat, zipCodeLong); 
				zipcodeArray.add(zipCodeObject); //add the zipcode object to the array
			}
		}
		return zipcodeArray;
	}
	
	/**
	 * This method creates an arrayList of hotspots. The hotspot object includes the 
	 * distances from the user entered zipcode to each hotspot.
	 * @param readHotspots Scanner to read the csv file of hotspots
	 * @param zipcodeArray an ArrayList of the zipcode the user entered
	 * @return ArrayList of hotspots with distances
	 */
	public static ArrayList<Hotspot> createHotspotArray (Scanner readHotspots, ArrayList<ZipCode> zipcodeArray) {
		ArrayList <Hotspot> hotspotArray = new ArrayList<Hotspot>(); //make an array of hotspot objects
		readHotspots.nextLine(); //skip over first label line
		
		while (readHotspots.hasNextLine()) { //do this while there are still lines to read
			ArrayList<String> hotspotInfo = split(readHotspots.nextLine()); //create an array of strings for each line

			if (hotspotInfo.size() >= 4) { //has to be greater than 4 to proceed
				//some csv lines do not show up correctly; Professor Klukowska told me to skip over them
				
				//System.out.println(hotspotInfo.size());
				//System.out.println(hotspotInfo);
				double hotspotLat = Double.parseDouble(hotspotInfo.get(6)); //get the latitude of the hotspot
				double hotspotLong = Double.parseDouble(hotspotInfo.get(7)); //get longitude of the hotspot

				String borough = hotspotInfo.get(1); //find borough of the hotspot
				String locationName = hotspotInfo.get(4); //find location name of the hotspot
				String locationAddress = hotspotInfo.get(5); //find location address of the hotspot
				String freeOrNot = hotspotInfo.get(2); //find free status of the hotspot

				//calculate distance
				double distance = haversine(zipcodeArray.get(0).latitude, zipcodeArray.get(0).longitude, hotspotLat, hotspotLong);
				
				//create hotspot object with all info and distance 
				Hotspot hotspot = new Hotspot(hotspotLat, hotspotLong, borough, locationName, locationAddress, freeOrNot, distance);
				hotspotArray.add(hotspot);
			}
		}
		return hotspotArray;
	}
	
	/**
	 * This method takes the distances from the hotspotArray objects and puts it into a new distance array.
	 * It then sorts the distance array.
	 * @param hotspotArray the arraylist of hotspot objects to pull the distances from
	 * @return distanceArray with the distances sorted
	 */
	public static double [] createDistanceArray (ArrayList<Hotspot> hotspotArray) {
		//create a new array of distances 
		double [] distanceArray = new double [hotspotArray.size()];
		//run through the hotspotArray to get the distance from each hotspot object
		//and put it into the distanceArray
		for (int i = 0; i < hotspotArray.size(); i++) {
			distanceArray[i] = hotspotArray.get(i).distance;
		}
		//sort the distance array from shortest to longest distance
		Arrays.sort(distanceArray);
		
		return distanceArray;
	}
	
	/**
	 * Splits a given line according to commas (commas within entries are ignored)
	 * @param textLine line of text to be parsed
	 * @return an ArrayList object containing all individual entries / tokens
	 * found on the line.
	 */
	public static ArrayList<String> split (String textLine ) {
		ArrayList<String> entries = new ArrayList<String>();
		int lineLength = textLine.length();
		StringBuffer nextWord = new StringBuffer();
		char nextChar;
		boolean insideQuotes = false;

		for(int i = 0; i < lineLength; i++ ) {
			nextChar = textLine.charAt(i);
			//add character to the current entry
			if ( nextChar != ',' && nextChar != '"') {
				nextWord.append(nextChar);
			}
			//double quote found, decide if it is opening or closing one
			else if (nextChar == '"' ) {
				if ( insideQuotes ) {
					insideQuotes = false;
				}
				else {
					insideQuotes = true;
				}
			}

			//found comma inside double quotes, just add it to the string
			else if (nextChar == ',' && insideQuotes) {
				nextWord.append( nextChar );
			}

			//end of the current entry reached, add it to the list of entries
			//and reset the nextWord to empty string
			else if (nextChar == ',' && !insideQuotes) {
				//trim the white space before adding to the list
				entries.add(nextWord.toString().trim());

				nextWord = new StringBuffer();
			}

			else {
				System.err.println("This should never be printed.\n");
			}
		}

		//add the last word
		//trim the white space before adding to the list
		entries.add( nextWord.toString().trim() );

		return entries;
	}

	/**
	 * This program finds the distance between two points on Earth given their longitude and latitude
	 * provided by Haversite formula.
	 * @param lat1 latitude of point 1
	 * @param lon1 longitude of point 1
	 * @param lat2 latitude of point 2
	 * @param lon2 longitude of point 2
	 * @return
	 */
	static double haversine (double lat1, double lon1, double lat2, double lon2) {
		final double R = 6372.8; //in kilometers
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2)
		* Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.asin(Math.sqrt(a));
		return R * c;
	}
}
