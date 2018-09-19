package ir.SearchEngine.GeocacheSearchEngine;

import java.io.File;
<<<<<<< HEAD
import java.io.IOException;
=======
>>>>>>> 72b602945df34f7aad9e53ecbb82d825a895ef54
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
<<<<<<< HEAD
=======
import javax.ws.rs.Produces;
>>>>>>> 72b602945df34f7aad9e53ecbb82d825a895ef54
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.lucene.util.BytesRef;
import org.json.JSONObject;

import ir.SearchEngine.GeocacheSearchEngine.Model.Geocache;

@Path("/suggest")
public class SuggestionResource {
	
	private Suggester suggester = new Suggester();
	{
		try {	
		    ArrayList<Geocache> geocaches = new ArrayList<Geocache>();
		    this.suggester.getSuggester().build(new GeocacheIterator(geocaches.iterator()));
		     
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
	@GET
	@Path("/{query}")
	public Response suggest(@PathParam("query") String query) {

		List<String> suggestions = null;
		try {
		
	        suggestions = suggester.lookup(suggester.getSuggester(), query);
	        
	       suggester.close();

		catch(Exception e) {
			e.printStackTrace();
		}
		
		JSONObject json = new JSONObject();
		json.put("suggestions", suggestions);
		
		return Response.status(200).entity(json.toString()).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON + ";charset=UTF-8").build();

	}
	
	@GET
	@Path("/close")
	public void closeSuggester() {

		try {
	        suggester.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
