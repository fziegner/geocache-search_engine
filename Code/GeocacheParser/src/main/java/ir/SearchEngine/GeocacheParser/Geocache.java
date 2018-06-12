package ir.SearchEngine.GeocacheParser;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
/**
 * Model Class for Geocache
 * @author Christian Schlecht
 *
 */
public class Geocache {
	
	private String name;
	private String coordinates;
	private String status;
	private String condition;
	private String hiddenAt;
	private String waypoint;
	private String cacheType;
	private String caseType;
	private float difficulty;
	private float terrain;
	private String link;
	private String descriptionSnippet;
	private String description;
	private String tips; //TODO maybe transform this into separate class to parse chiffre efficiently
	private List<Log> logs;
		
	public Geocache(String name, String coordinates) {
		this.name = name;
		this.coordinates = coordinates;
	}
	public Geocache() {
		this.logs = new ArrayList<Log>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getHiddenAt() {
		return hiddenAt;
	}

	public void setHiddenAt(String hiddenAt) {
		this.hiddenAt = hiddenAt;
	}

	public String getWaypoint() {
		return waypoint;
	}

	public void setWaypoint(String waypoint) {
		this.waypoint = waypoint;
	}

	public String getCacheType() {
		return cacheType;
	}

	public void setCacheType(String cacheType) {
		this.cacheType = cacheType;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public float getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(float difficulty) {
		this.difficulty = difficulty;
	}

	public float getTerrain() {
		return terrain;
	}

	public void setTerrain(float terrain) {
		this.terrain = terrain;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescriptionSnippet() {
		return descriptionSnippet;
	}

	public void setDescriptionSnippet(String descriptionSnippet) {
		this.descriptionSnippet = descriptionSnippet;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public List<Log> getLogs() {
		return logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}
	
	public void appendLog(Log log) {
		this.logs.add(log);
	}
	
	/**
	 * toString for debugging purposes
	 */
	@Override
	public String toString() {
		return this.getName();
	}
	
	/**
	 * transforms the Geocache Object into a JSONObject
	 * @return JSONObject of the Geocache
	 */
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("name",  this.getName());
		json.put("coordinates", this.getCoordinates());
		json.put("status",  this.getStatus());
		json.put("condition",  this.getCondition());
		json.put("hiddenAt",  this.getHiddenAt());
		json.put("waypoint",  this.getWaypoint());
		json.put("cacheType",  this.getCacheType());
		json.put("caseType",  this.getCaseType());
		json.put("difficulty",  this.getDifficulty());
		json.put("terrain",  this.getTerrain());
		json.put("link",  this.getLink());
		json.put("descriptionSnippet",  this.getDescriptionSnippet());
		json.put("description",  this.getDescription());
		json.put("tips",  this.getTips());
		JSONArray logs = new JSONArray();
		for(Log log : this.logs) {
			logs.put(log.toJSON());
		}
		json.put("logs", logs);
		
		return json;
	}
}