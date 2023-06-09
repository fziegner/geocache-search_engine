package ir.SearchEngine.GeocacheSearchEngine.Searcher;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.simple.SimpleQueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import ir.SearchEngine.GeocacheSearchEngine.Indexer.CustomGermanAnalyzer;
import ir.SearchEngine.GeocacheSearchEngine.Util.CONSTANTS;

public class Searcher {
		
	private IndexSearcher indexSearcher;
	private IndexReader indexReader;
	private QueryParser queryParser;
	private Query query;
	
	public Searcher(String indexDirectoryPath) throws IOException {
		FSDirectory indexDirectory = FSDirectory.open(Paths.get(indexDirectoryPath));
		System.out.println("open Searcher on: " + indexDirectory.toString());
		CharArraySet stopwords = GermanAnalyzer.getDefaultStopSet();
		stopwords.add("Geocache");
		stopwords.add("geocach");
		stopwords.add("cache");
		stopwords.add("geocache");
		Analyzer analyzer = new GermanAnalyzer(stopwords);
		
	  	indexReader = DirectoryReader.open(indexDirectory);
	  	indexSearcher = new IndexSearcher(indexReader);
	  	Map<String, Float> boosts = new HashMap<String, Float>();
	  	boosts.put("name", 2.5f);
	  	boosts.put("description", 1.5f);
	  	queryParser = new MultiFieldQueryParser(
	  			new String[] {"waypoint", "name", "logs", "description", "coordinates", "hiddenAt", "descriptionSnippet", "cacheType", "difficulty", "terrain", "caseType", "condition", "status"},
                analyzer, boosts);
	}
	
	public TopDocs search(String searchQuery) throws IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {
		query = queryParser.parse(searchQuery);
	  	System.out.println("parsed query: " + query);
	  	return indexSearcher.search(query, CONSTANTS.MAX_SEARCH_RESULTS);
	}
	
	public Document getDocument(ScoreDoc scoreDoc) throws CorruptIndexException, IOException {
	  	return indexSearcher.doc(scoreDoc.doc);	
	}
	
	public void close() throws IOException {
	  	indexReader.close();
	}
	
	public TopDocs searchExtended(String searchQuery, Map<String, String> values) throws org.apache.lucene.queryparser.classic.ParseException, IOException {
		List<Query> queries = new ArrayList<Query>();
		Analyzer analyzer = new CustomGermanAnalyzer();
		query = queryParser.parse(searchQuery);
		queries.add(query);
		String[] fields = values.keySet().toArray(new String[0]);
		String[] queryParams = values.values().toArray(new String[0]);
		
		//parse every field that was given by REST into separate Query
		for(int i = 0; i < fields.length; i++) {
			SimpleQueryParser qp = new SimpleQueryParser(analyzer, fields[i]);
			Query q = qp.parse(queryParams[i]);
			queries.add(q);
		}
		//build a query out of all small queries, such that every query only searches in its desired field
		BooleanQuery.Builder finalQueryBuilder = new BooleanQuery.Builder();
		for(Query q : queries) {
			finalQueryBuilder.add(q, Occur.MUST);
		}
		BooleanQuery finalQuery = finalQueryBuilder.build();
		
		System.out.println("extended query: " + finalQuery);
		
		return indexSearcher.search(finalQuery, CONSTANTS.MAX_SEARCH_RESULTS);
	}
}