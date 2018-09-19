package ir.SearchEngine.GeocacheSearchEngine;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.json.JSONArray;
import org.json.JSONObject;

import ir.SearchEngine.GeocacheSearchEngine.Parser.FileIO;

@Path("/search")
public class SearchResource {
	
	/**
	 * execute a search by a given query
	 * @param query String query to search for
	 * @return JSONArray containing the JSONObject of the caches that matched the query
	 */
	@GET
	@Path("/{query}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(@PathParam("query") String query) {
		System.out.println("you searched for: " + query);
		Searcher searcher = null;
		TopDocs docs;
		ScoreDoc[] hits; 
		List<String> cacheWaypoints = new ArrayList<String>();
		try {
			//execute the search in the index
			searcher = new Searcher(CONSTANTS.INDEX_DIRECTORY);
			docs = searcher.search(query);
			System.out.println("after seach, found: " + docs.toString() + ", max score: " + docs.getMaxScore());
			hits = docs.scoreDocs;
			System.out.println("number of hits: " + hits.length);
			for(int i = 0; i < hits.length; i++) {
				Document d = searcher.getDocument(hits[i]);
				String waypoint = d.get("waypoint");
				cacheWaypoints.add(waypoint);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (org.apache.lucene.queryparser.classic.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				searcher.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		JSONArray caches = new JSONArray();
		
		//open all found caches and append them to the result
		for(String waypoint : cacheWaypoints) {
			String jsonStr = FileIO.readFile(CONSTANTS.DATA_DIRECTORY + "/" + waypoint + ".json");
			System.out.println("path to found data: " + CONSTANTS.DATA_DIRECTORY + "/" + waypoint + ".json");
			caches.put(new JSONObject(jsonStr));
		}
		
		
		return Response.status(200).entity(caches.toString()).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON + ";charset=UTF-8").build();
		}
	
	/**
	 * supplies extended search for a given query and optional parameters
	 * @param query the query to search for
	 * @param hiddenAfter date, only search for caches hidden after this date
	 * @param coordinates search for caches from the given coordinates by the given range (next param, default 1000)
	 * @param range range to search from the the given coordinates
	 * @param caseType type of case
	 * @param condition condition of the cache
	 * @param minDifficulty difficulty of the cache, supply the minimum value
	 * @param maxDifficulty difficulty of the cache, supply maximum value
	 * @param cacheType type of cache
	 * @param terrain rating for the terrain, supply minimum value
	 * @param status state of the cache
	 * @return JSONArray containing the JSONObjects of the caches that matched the search
	 */
	@GET
	@Path("/extended/{query}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response extendedSearch(@PathParam("query") String query,
								 @QueryParam("hiddenAfter") String hiddenAfter, 
								 @QueryParam("coordinates") String coordinates,
								 @DefaultValue("1000") @QueryParam("range") int range,
								 @QueryParam("caseType") String caseType,
								 @QueryParam("condition") String condition,
								 @DefaultValue("-1") @QueryParam("minDifficulty") int minDifficulty,
								 @DefaultValue("-1") @QueryParam("maxDifficulty") int maxDifficulty,
								 @QueryParam("cacheType") String cacheType,
								 @QueryParam("terrain") String terrain,
								 @QueryParam("status") String status) {
		//queryParams that are not given in the url have either the specified default or NULL value, same applies for query: if not present it is NULL
		//TODO if range is set, but no coordinates supplied, use center of germany
		Searcher searcher = null;
		TopDocs docs;
		ScoreDoc[] hits; 
		List<String> cacheWaypoints = new ArrayList<String>();
		Map<String, String> values = new HashMap<String, String>();
		if(hiddenAfter != null) {
			values.put("hiddenAfter", hiddenAfter);
		}
		if(caseType != null) {
			values.put("caseType", caseType);
		}
		if(condition != null) {
			values.put("condition",  condition);
		}
		if(cacheType != null) {
			values.put("cacheType",  cacheType);
		}
		if(terrain != null) {
			values.put("terrain",  terrain);
		}
		if(status != null) {
			values.put("status",  status);
		}
		
		
		//TODO search this massive boi, put waypoints into cacheWaypoints just as in normal search
		try {
			//execute the search in the index
			searcher = new Searcher(CONSTANTS.INDEX_DIRECTORY);
			docs = searcher.searchExtended(query, values);
			hits = docs.scoreDocs;
			System.out.println("number of hits: " + hits.length);
			for(int i = 0; i < hits.length; i++) {
				Document d = searcher.getDocument(hits[i]);
				String waypoint = d.get("waypoint");
				cacheWaypoints.add(waypoint);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (org.apache.lucene.queryparser.classic.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				searcher.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		JSONArray caches = new JSONArray();
		for(String waypoint : cacheWaypoints) {
			String jsonStr = FileIO.readFile(CONSTANTS.DATA_DIRECTORY + "/" + waypoint + ".json");
			System.out.println("path to found data: " + CONSTANTS.DATA_DIRECTORY + "/" + waypoint + ".json");
			caches.put(new JSONObject(jsonStr));
		}
		
		return Response.status(200).entity(caches.toString()).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON + ";charset=UTF-8").build();
		//return Response.status(200).entity("{\"status\":\"not yet implemented\"}").header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON + ";charset=UTF-8").build();
		
	}	
}