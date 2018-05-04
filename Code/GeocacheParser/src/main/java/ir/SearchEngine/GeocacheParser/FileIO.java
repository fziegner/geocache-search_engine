package ir.SearchEngine.GeocacheParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

/**
 * File-Access Handler
 * @author Christian Schlecht
 *
 */
public class FileIO {
	
	/**
	 * collects all file paths from a given DIRECTORY path
	 * @param path the directoy to search for files in
	 * @return List of filePaths
	 */
	public static List<String> listAllFiles(String path) {
		List<String> fileNames = new ArrayList<String>();
		
		File directory = new File(path);
		for(File file : directory.listFiles()) { //list all files in the given directory
			if(file.isFile()) {
				fileNames.add(file.getAbsolutePath()); //if it is a file, add its path to return
			}
		}
		return fileNames;
	}
	
	/**
	 * Reads a File from the given path and stores it in a String
	 * @param path String containing the path of the file
	 * @return String content of the file
	 */
	public static String readFile(String path) {
		StringBuilder data = new StringBuilder(); //use Stringbuilder because it is mutable, e.g. appending is faster than += on Strings
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(path))); //open file
			String line;
			while((line = br.readLine()) != null) {
				data.append(line).append("\n"); //read and store the whole file
			}
			br.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data.toString();
	}
	
	/**
	 * outputs the JSON to a file, should potentially be a .json file (creates it if is not already present)
	 * @param path String containing the path of the file that should be written to
	 * @param data the JSON that should be written
	 */
	public static void writeFile(String path, JSONObject data) {
		
	}

}
