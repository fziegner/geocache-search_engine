package ir.SearchEngine.GeocacheSearchEngine.Model;

import org.json.JSONObject;

public class Log {
	
	private String person;
	private String date;
	private String message;
	private boolean found;
	
	
	
	public Log(String person, String date, String message, boolean found) {
		this.person = person;
		this.date = date;
		this.message = message;
		this.found = found;
	}



	public boolean isFound() {
		return found;
	}



	public void setFound(boolean found) {
		this.found = found;
	}



	public String getPerson() {
		return person;
	}



	public void setPerson(String person) {
		this.person = person;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return this.person + " # " + this.date + " # " + this.found + " # " + this.message;
	}
	
	
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("person", this.person);
		json.put("date",  this.date);
		json.put("message", this.message);
		return json;
	}

}
