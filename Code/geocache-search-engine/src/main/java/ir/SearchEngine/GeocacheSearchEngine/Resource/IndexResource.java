package ir.SearchEngine.GeocacheSearchEngine.Resource;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import ir.SearchEngine.GeocacheSearchEngine.Indexer.Indexer;
import ir.SearchEngine.GeocacheSearchEngine.Util.CONSTANTS;

@Path("/index")
public class IndexResource {
	
	@GET
	@Path("/create")
	public Response createIndex() throws IOException {
		
		String indexDirectory = CONSTANTS.INDEX_DIRECTORY; //the index will be created inside the project
		String sourceFilesDirectory = CONSTANTS.DATA_DIRECTORY;
		Indexer indexer = new Indexer(indexDirectory);
		int numIndexed = indexer.createIndex(sourceFilesDirectory);
		indexer.close();
		System.out.println(numIndexed + " File(s) indexed");
		return Response.status(200).build();
	}
}