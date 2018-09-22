package ir.SearchEngine.GeocacheSearchEngine.Resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import ir.SearchEngine.GeocacheSearchEngine.Util.JDBCHandler;

@Path("/logs")
public class LogResource {

	@POST
	@Path("/{ip}/{waypoint}/{query}/{time}")
	public Response getLog(@PathParam("ip") String ip, @PathParam("waypoint") String waypoint, @PathParam("query") String query, @PathParam("time") int time) {
		//TODO store this to a DB
		
		JDBCHandler handler = new JDBCHandler();
		String[] waypoints = waypoint.split(",");
		for(String wp : waypoints) {
			handler.postLog(ip, wp, query, time);
		}
		
		
		return Response.status(200).build();
	}
	
}
