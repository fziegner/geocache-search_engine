package ir.SearchEngine.GeocacheParser;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;

public class Geocache {
	
	private String name;
	private String coordinates;
	private String status;
	private String condition;
	private Date hiddenAt;
	private String waypoint;
	private String cacheType;
	private String caseType;
	private float difficulty;
	private float terrain;
	private String link;
	private String descriptionSnippet;
	private String description;
	private String tipps; //TODO maybe transform this into separate class to parse chiffre efficiently
	private List<String> logs;
	
		
	public Geocache(String name, String coordinates) {
		this.name = name;
		this.coordinates = coordinates;
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

	public Date getHiddenAt() {
		return hiddenAt;
	}

	public void setHiddenAt(Date hiddenAt) {
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

	public String getTipps() {
		return tipps;
	}

	public void setTipps(String tipps) {
		this.tipps = tipps;
	}

	public List<String> getLogs() {
		return logs;
	}

	public void setLogs(List<String> logs) {
		this.logs = logs;
	}
	
	/**
	 * transforms the Geocache Object into a JSONObject
	 * @return JSONObject of the Geocache
	 */
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		//TODO transform cache into jsonObject
		
		return json;
	}


}
