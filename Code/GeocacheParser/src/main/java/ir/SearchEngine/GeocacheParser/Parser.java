package ir.SearchEngine.GeocacheParser;

import java.util.ArrayList;
import java.util.List;

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
			if(lines[i].contains("Koordinaten: ")) {
				String koordinaten = lines[i].replace("Koordinaten: ",  "");
				geocache.setCoordinates(koordinaten);
			}
			if(lines[i].contains("Status: ")) {
				String status = lines[i].replace("Status: ",  "");
				geocache.setStatus(status);
			}
			if(lines[i].contains("Zustand: ")) {
				String condition = lines[i].replace("Zustand: ",  "");
				geocache.setCondition(condition);
			}
			if(lines[i].contains("Versteckt am: ")) {
				String hiddenAt = lines[i].replace("Versteckt am: ",  "");
				geocache.setHiddenAt(hiddenAt);
			}
			if(lines[i].contains("Wegpunkt: ")) {
				String waypoint = lines[i].replace("Wegpunkt: ",  "");
				geocache.setWaypoint(waypoint);
			}
			if(lines[i].contains("Cacheart: ")) {
				String cacheType = lines[i].replace("Cacheart: ",  "");
				geocache.setCacheType(cacheType);
			}
			if(lines[i].contains("Behälter: ")) {
				String caseType = lines[i].replace("Behälter: ",  "");
				geocache.setCaseType(caseType);
			}
			if(lines[i].contains("D/T: ")) {
				String caseType = lines[i];
				caseType = caseType.replace("D/T:",  "");
				String[] strings = caseType.split("/");
				geocache.setDifficulty(Float.parseFloat(strings[0]));
				geocache.setTerrain(Float.parseFloat(strings[1]));
			}
			if(lines[i].contains("Online: ")) {
				String link = lines[i].replace("Online: ",  "");
				geocache.setLink(link);
			}
			if(lines[i].contains("Kurzbeschreibung: ")) {
				String descriptionSnippet = lines[i].replace("Kurzbeschreibung: ",  "");
				geocache.setDescriptionSnippet(descriptionSnippet);
			}
			if(lines[i].contains("Beschreibung:") || lines[i].contains("Beschreibung (aus HTML konvertiert):")) {
				StringBuilder stringBuilder = new StringBuilder();
				int x = i+2; //skip the line with the first <===================>
				while(!lines[x].contains("<===================>")) { //add all lines until the next separator is reached
					stringBuilder.append(lines[x]  + "\n");
					x++;
				}
				geocache.setDescription(stringBuilder.toString());
				System.out.println(stringBuilder);
			}
			if(lines[i].contains("Zusätzliche Hinweise:")) {
				StringBuilder stringBuilder = new StringBuilder();
				int x = i+2; //skip the line with the first <===================>
				while(!lines[x].contains("<===================>")) { //add all lines until the next separator is reached
					stringBuilder.append(lines[x] + "\n");
					x++;
				}
				geocache.setTips(stringBuilder.toString());
			}
		}
		//TODO continue parsing
		
		return geocache;
	}
}