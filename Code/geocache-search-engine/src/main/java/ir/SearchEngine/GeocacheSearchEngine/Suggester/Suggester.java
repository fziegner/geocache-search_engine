package ir.SearchEngine.GeocacheSearchEngine.Suggester;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.search.suggest.Lookup;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.store.FSDirectory;

import ir.SearchEngine.GeocacheSearchEngine.Util.CONSTANTS;

public class Suggester {

    GermanAnalyzer analyzer;
    AnalyzingInfixSuggester suggester;
	FSDirectory index_dir = null;
    
	public Suggester(){
		analyzer = new GermanAnalyzer();
		try {
			index_dir = FSDirectory.open(Paths.get(CONSTANTS.SUGGESTER_DIRECTORY));
			suggester = new AnalyzingInfixSuggester(index_dir, analyzer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public AnalyzingInfixSuggester getSuggester() {
		return this.suggester;
	}

    public List<String> lookup(String name) throws ClassNotFoundException, IOException {
            List<Lookup.LookupResult> results;
            List<String> suggestions = new ArrayList<String>();
            results = suggester.lookup(name, CONSTANTS.MAX_SUGGESTION_RESULTS, true, false);
            System.out.println("Suggestions for: \"" + name + ":");
            for (Lookup.LookupResult result : results) {
                System.out.println(result.key);
                suggestions.add(result.key.toString());
            }
            return suggestions;
    }
    
    public void close() throws IOException {
    	suggester.close();
    }
}