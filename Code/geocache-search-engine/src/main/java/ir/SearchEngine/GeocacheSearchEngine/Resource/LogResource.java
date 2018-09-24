package ir.SearchEngine.GeocacheSearchEngine.Resource;

import java.sql.Timestamp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import ir.SearchEngine.GeocacheSearchEngine.Util.JDBCHandler;

@Path("/logs")
public class LogResource {

	@GET
	@Path("/{ip}/{waypoint}/{query}/{time}")
	public Response getLog(@PathParam("ip") String ip, @PathParam("waypoint") String waypoint, @PathParam("query") String query, @PathParam("time") long time) {
		
		JDBCHandler handler = new JDBCHandler();
		Timestamp stamp = new Timestamp(time);

		String[] waypoints = waypoint.split(",");
		for(String wp : waypoints) {
			handler.postLog(ip, wp, query, stamp);
		}
		
		return Response.status(200).build();
	}	
}