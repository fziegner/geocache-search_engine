package ir.SearchEngine.GeocacheParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;

public class Parser {
	private List<String> fileNames; //holds the filepaths that are read from
	private List<Geocache> caches;  //parsed caches land in here
	private List<JSONObject> cachesJSON; //
	
	/**
	 * Constructor of the Parser.
	 * By giving a Path to a Folder/Directory the Parser will do the following:
	 * 1. Read all Files in the directory
	 * 2. parse them into GeoCache Objects
	 * 3. parse them into JSON Objects
	 * 4. output the JSON of the caches into separate .json files
	 * @param folderPath path to the directory/folder
	 */
	public Parser(String folderPath) {
		fileNames = listAllFiles(folderPath);
		caches = new ArrayList<Geocache>();
		cachesJSON = new ArrayList<JSONObject>();
		for(String file : fileNames) {
			caches.add(parse(file));
		}
		for(Geocache geocache : caches) {
			cachesJSON.add(geocache.toJSON());
		}
	}
	
	/**
	 * collects all file paths from a given DIRECTORY path
	 * @param path the directoy to search for files in
	 * @return List of filePaths
	 */
	public static List<String> listAllFiles(String path) {
		List<String> fileNames = new ArrayList<String>();
		
		File directory = new File(path);
		for(File file : directory.listFiles()) {
			if(file.isFile()) {
				fileNames.add(file.getAbsolutePath());
			}
		}
		return fileNames;
	}
	
	/**
	 * parses a given file (by filepath) to a GeoCache Object
	 * @param path filepath to be read from
	 * @return geocache parsed from the file
	 */
	public static Geocache parse(String path) {
		//TODO parse
		
		return null; //for now just return null
	}
	
}
