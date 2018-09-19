package ir.SearchEngine.GeocacheSearchEngine;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.search.suggest.Lookup;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.store.FSDirectory;

public class Suggester {

    GermanAnalyzer analyzer;
    AnalyzingInfixSuggester suggester;
	FSDirectory index_dir;
    
	public Suggester() {
		try {
			index_dir = FSDirectory.open(Paths.get(CONSTANTS.SUGGESTER_DIRECTORY));
		} catch (IOException e) {
			e.printStackTrace();
		}
		analyzer = new GermanAnalyzer();
		try {
			suggester = new AnalyzingInfixSuggester(index_dir, analyzer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public AnalyzingInfixSuggester getSuggester() {
		return this.suggester;
	}
	
    public List<String> lookup(String name) throws ClassNotFoundException, IOException {
            List<Lookup.LookupResult> results;
            List<String> suggestions = new ArrayList<String>();
            results = suggester.lookup(name, 2, true, false);
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