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

/**
 * parser Class to transform the geocache's .txt file into a json file
 * @author Christian Schlecht
 *
 */
public class Parser {
	private List<String> fileNames; //holds the filepaths that are read from
	private List<Geocache> caches;  //parsed caches land in here
	private List<JSONObject> cachesJSON; //hold the jsons of the parsed caches
	
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
		fileNames = FileIO.listAllFiles(folderPath);
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
	 * parses a given file (by filepath) to a GeoCache Object
	 * @param path filepath to be read from
	 * @return geocache parsed from the file
	 */
	public static Geocache parse(String path) {
		Geocache geocache = new Geocache();
		String cacheString = FileIO.readFile(path);
		String[] lines = cacheString.split("\n"); //separate every line
		for(int i = 0; i < lines.length; i++) {
			if(lines[i].contains("Name: ")) { //check if the key "Name: " is in a line
				String name = lines[i].replace("Name: ",  ""); //delete the key
				geocache.setName(name); //and set the name of the cache
			}
		}
		//TODO continue parsing
		
		return geocache;
	}
	
}
