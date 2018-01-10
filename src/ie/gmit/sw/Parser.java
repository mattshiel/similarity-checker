package ie.gmit.sw;

public class Parser {
	 //Schemes for URL validation
	private static final String SCHEME1 = "http://";
	private static final String SCHEME2 = "https://"; 
	
	// Default constructor
	public Parser() {
		
	}
	
	public static void parseLocation(String location) {
		// Create a new instance of URLParser and parse as a URL
		if(location.startsWith(SCHEME1) || location.startsWith(SCHEME2)) //If input is a URL
		{		
			URLParser u1 = new URLParser();
			u1.parse(location);
		}
		
		// Create a new instance of FileParser and parse as a File
		else //If input is a file
		{
			FileParser f1 = new FileParser();
			f1.parse(location);
		}
	}
	
}
