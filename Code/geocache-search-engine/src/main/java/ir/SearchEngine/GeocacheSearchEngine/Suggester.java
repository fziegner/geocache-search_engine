package ir.SearchEngine.GeocacheSearchEngine;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.search.suggest.Lookup;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.store.FSDirectory;

public class Suggester {

    GermanAnalyzer analyzer;
    AnalyzingInfixSuggester suggester;
	FSDirectory index_dir = FSDirectory.open(Paths.get(CONSTANTS.SUGGESTER_DIRECTORY));
    
	public Suggester() throws IOException {
		analyzer = new GermanAnalyzer();
		suggester = new AnalyzingInfixSuggester(index_dir, analyzer);
	}
	
	public AnalyzingInfixSuggester getSuggester() {
		return this.suggester;
	}
	
    public void lookup(AnalyzingInfixSuggester suggester, String name) throws ClassNotFoundException, IOException {
            List<Lookup.LookupResult> results;
            results = suggester.lookup(name, 2, true, false);
            System.out.println("Suggestions for: \"" + name + ":");
            for (Lookup.LookupResult result : results) {
                System.out.println(result.key);
            }
    }
}