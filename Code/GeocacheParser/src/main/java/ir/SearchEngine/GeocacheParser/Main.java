package ir.SearchEngine.GeocacheParser;

import java.util.List;

import org.json.JSONObject;

/**
 * Main class for testing purposes
 * @author Christian Schlecht
 *
 */
public class Main {

	public static void main(String[] args) {
		 String testDirectory = "C:\\Users\\chris\\Desktop\\test";
		
		 List<String> filesInTestDirectory = FileIO.listAllFiles(testDirectory);
		 for(String file : filesInTestDirectory) {
			 System.out.println(file);
		 }
		 
		 String data = FileIO.readFile(filesInTestDirectory.get(0));
		 System.out.println(data);
		 
		 Geocache testCache = Parser.parse(filesInTestDirectory.get(0));
		 System.out.println(testCache.toString());

		 JSONObject json = testCache.toJSON();
		 System.out.println(json.toString());

	}

}
