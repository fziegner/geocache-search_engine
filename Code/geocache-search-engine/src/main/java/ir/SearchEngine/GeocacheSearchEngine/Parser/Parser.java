package ir.SearchEngine.GeocacheSearchEngine.Parser;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import ir.SearchEngine.GeocacheSearchEngine.Model.Geocache;
import ir.SearchEngine.GeocacheSearchEngine.Model.Log;

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
	public Parser(String folderPathIn, String folderPathOut) {
		fileNames = FileIO.listAllFiles(folderPathIn);
		caches = new ArrayList<Geocache>();
		cachesJSON = new ArrayList<JSONObject>();
		for(String file : fileNames) {
			caches.add(parse(file));
		}
		for(Geocache geocache : caches) {
			FileIO.writeFile(folderPathOut, geocache);
		}
	}
	
	/**
	 * parses a given file (by filepath) to a GeoCache Object
	 * @param path filepath to be read from
	 * @return geocache parsed from the file
	 */
	public static Geocache parse(String path) {
		Geocache geocache = new Geocache();
		String cacheString = "";
		try {
			cacheString = new String(FileIO.readFile(path).getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new AssertionError("UTF-8 is unknown");
		}
		String[] lines = cacheString.split("\n"); //separate every line
		boolean[] flags = new boolean[14];
		for(int i = 0; i < lines.length; i++) {
			try {
				if(lines[i].contains("Name: ") && flags[0] == false) { //check if the key "Name: " is in a line
					flags[0] = true;
					String name = lines[i].replace("Name: ",  ""); //delete the key
					geocache.setName(name); //and set the name of the cache
				}
				if(lines[i].contains("Koordinaten: ") && flags[1] == false) {
					flags[1] = true;
					String koordinaten = lines[i].replace("Koordinaten: ",  "");
					geocache.setCoordinates(koordinaten);
				}
				if(lines[i].contains("Status: ") && flags[2] == false) {
					flags[2] = true;
					String status = lines[i].replace("Status: ",  "");
					geocache.setStatus(status);
				}
				if(lines[i].contains("Zustand: ") && flags[3] == false) {
					flags[3] = true;
					String condition = lines[i].replace("Zustand: ",  "");
					geocache.setCondition(condition);
				}
				if(lines[i].contains("Versteckt am: ") && flags[4] == false) {
					flags[4] = true;
					String hiddenAt = lines[i].replace("Versteckt am: ",  "");
					geocache.setHiddenAt(hiddenAt);
				}
				if(lines[i].contains("Wegpunkt: ") && flags[5] == false) {
					flags[5] = true;
					String waypoint = lines[i].replace("Wegpunkt: ",  "");
					geocache.setWaypoint(waypoint);
				}
				if(lines[i].contains("Cacheart: ") && flags[6] == false) {
					flags[6] = true;
					String cacheType = lines[i].replace("Cacheart: ",  "");
					geocache.setCacheType(cacheType);
				}
				if(lines[i].contains("Behälter: ") && flags[7] == false) {
					flags[7] = true;
					String caseType = lines[i].replace("BehÃ¤lter: ",  "");
					geocache.setCaseType(caseType);
				}
				if(lines[i].contains("D/T: ") && flags[8] == false) {
					flags[8] = true;
					String caseType = lines[i];
					caseType = caseType.replace("D/T:",  "");
					String[] strings = caseType.split("/");
					geocache.setDifficulty(Float.parseFloat(strings[0]));
					geocache.setTerrain(Float.parseFloat(strings[1]));
				}
				if(lines[i].contains("Online: ") && flags[9] == false) {
					flags[9] = true;
					String link = lines[i].replace("Online: ",  "");
					geocache.setLink(link);
				}
				if(lines[i].contains("Kurzbeschreibung: ") && flags[10] == false) {
					flags[10] = true;
					String descriptionSnippet = lines[i].replace("Kurzbeschreibung: ",  "");
					geocache.setDescriptionSnippet(descriptionSnippet);
				}
				if(lines[i].contains("Beschreibung:") || lines[i].contains("Beschreibung (aus HTML konvertiert):") && flags[11] == false) {
					flags[11] = true;
					StringBuilder stringBuilder = new StringBuilder();
					int x = i+2; //skip the line with the first <===================>
					while(!lines[x].contains("<===================>")) { //add all lines until the next separator is reached
						stringBuilder.append(lines[x]);
						x++;
					}
					geocache.setDescription(stringBuilder.toString());
					//System.out.println(stringBuilder);
				}
				if(lines[i].contains("Zusätzliche Hinweise:") && flags[12] == false) {
					flags[12] = true;
					StringBuilder stringBuilder = new StringBuilder();
					int x = i+2; //skip the line with the first <===================>
					while(!lines[x].contains("<===================>")) { //add all lines until the next separator is reached
						stringBuilder.append(lines[x]);
						x++;
					}
					geocache.setTips(stringBuilder.toString());
				}
				if(lines[i].contains("Logeinträge:") && flags[13] == false) {
					flags[13] = true;
					StringBuilder stringBuilder = new StringBuilder();
					int documentLength = lines.length-1;
					int x = i+2; //skip the line with the first <===================>
					while(x < documentLength) {
						while(x <= documentLength && !lines[x].contains("<===================>")) { //add all lines until the next separator or EOF is reached
							stringBuilder.append(lines[x]);
							x++;
						}
						String logString = stringBuilder.toString(); //all lines of a log entry
					    String[] splitter = logString.split("/"); //split them on /
					    String person = splitter[0]; //first entry is the person
					    String date = splitter[1]; //second the date
					    String foundAndMsg = splitter[2]; //last one is found and msg without separator directly appended, so we need to split again:
					    boolean found;
					    if(foundAndMsg.contains("nicht gefunden")) { //cache was not found, so we set the bool and simply "delete" the found status from the string
					    	found = false;
					    	foundAndMsg = foundAndMsg.replace("nicht gefunden", "");
					    }
					    else { //same thing applies here
					    	found = true;
					    	foundAndMsg = foundAndMsg.replace("gefunden", "");
					    }
					    Log log = new Log(person, date, foundAndMsg, found); //create a new log
					    geocache.appendLog(log); //append it to the cache
						
						stringBuilder.setLength(0);
						x++;
					}		
				}
			}
			catch(ArrayIndexOutOfBoundsException ex) {
				continue;
			}
		}
		return geocache;
	}
}