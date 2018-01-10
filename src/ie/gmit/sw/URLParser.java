package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class URLParser implements Parserator{
	
	private URL url;
	private BufferedReader br;
	private String line = "";
	// ArrayList of strings that contain the words from the text file
	private List<String> words = new ArrayList<String>();
	
	public List<String> parse(String location) {
		try {
			this.url = new URL(location);
			br = new BufferedReader(new InputStreamReader(url.openStream()));
			
			try {
				// If the user enters this block the file has been read successfully
				System.out.println("URL successfully read");
				
				while((line = br.readLine()) != null) {
					String[] split = line.split(" ");
					// Add split words to the array list
					for (int i = 0; i < split.length; i++) {
						words.add(split[i]);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			System.out.println("Error URL " + url +" not found");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return words;
	}
}
