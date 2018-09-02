package ir.SearchEngine.GeocacheSearchEngine;

import java.io.IOException;

import ir.SearchEngine.GeocacheSearchEngine.Parser.Parser;

public class Main {

	public static void main(String[] args) throws IOException {
		
		//run a whole setup 
		
		String data = "C:\\Users\\Christian-PC2\\Desktop\\data";
		new Parser(data, CONSTANTS.DATA_DIRECTORY);
		
		/* code for getting a suggestion:
		Suggester suggester = new Suggester();
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

        suggester.lookup(suggester.getSuggester(), "wald");
		*/
		
		String indexDirectory = CONSTANTS.INDEX_DIRECTORY; //the index will be created inside the project
		String sourceFilesDirectory = CONSTANTS.DATA_DIRECTORY;
		Indexer indexer = new Indexer(indexDirectory);
		int numIndexed = indexer.createIndex(sourceFilesDirectory);
		indexer.close();
		System.out.println(numIndexed + " File(s) indexed");
	}
}
