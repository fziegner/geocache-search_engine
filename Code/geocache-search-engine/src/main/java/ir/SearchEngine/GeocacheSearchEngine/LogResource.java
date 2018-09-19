package ir.SearchEngine.GeocacheSearchEngine;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/logs")
public class LogResource {

	@POST
	@Path("/{ip}/{waypoint}/{query}/{time}")
	public Response getLog(@PathParam("ip") String ip, @PathParam("waypoint") String waypoint, @PathParam("query") String query, @PathParam("time") int time) {
		//TODO store this to a DB
		
		//new JDBCHandler().postLog(ip, waypoint, query, time);
		
		
		return Response.status(200).build();
	}
	
}
