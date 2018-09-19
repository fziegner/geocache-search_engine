package ir.SearchEngine.GeocacheSearchEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.lucene.util.BytesRef;
import org.json.JSONObject;

import ir.SearchEngine.GeocacheSearchEngine.Model.Geocache;

public class SuggestionResource {
	
	Suggester suggester = new Suggester();
	@GET
	@Path("/suggest/build")
	public void buildSuggester() {
		
		try {
	        ArrayList<Geocache> geocaches = new ArrayList<Geocache>();
	        suggester.getSuggester().build(new GeocacheIterator(geocaches.iterator()));
	         
	        File[] files = new File(CONSTANTS.DATA_DIRECTORY).listFiles();
			for (File file : files) {
				JSONObject json = new JSONObject(Indexer.readFile(file));
				BytesRef name = new BytesRef(json.getString("name"));
				int weight = json.getJSONArray("logs").length();
				suggester.getSuggester().add(name, null, weight, null);
			}
	        
	        suggester.getSuggester().refresh();
	        }
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * REST Method for search suggestions
	 * expected usage: for every keystroke on the frontend execute this call and show the results
	 * @param query Query to get suggestions for
	 * @return JSON containing the search suggestions as a JSONArray ( {"suggestions":[.....]} )
	 */
	@GET
	@Path("/suggest/{query}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchSuggestion(@PathParam("query") String query) {

		List<String> suggestions = null;
		try {
	        suggestions = suggester.lookup(query);
	        suggester.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		JSONObject json = new JSONObject();
		json.put("suggestions", suggestions);
		
		return Response.status(200).entity(json.toString()).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON + ";charset=UTF-8").build();
	}
	
	@GET
	@Path("/suggest/close")
	public void closeSuggester() {

		try {
	        suggester.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}