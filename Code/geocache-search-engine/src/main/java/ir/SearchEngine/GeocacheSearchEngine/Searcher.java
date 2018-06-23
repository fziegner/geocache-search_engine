package ir.SearchEngine.GeocacheSearchEngine;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class Searcher {
		
	private IndexSearcher indexSearcher;
	private IndexReader indexReader;
	private QueryParser queryParser;
	private Query query;
	
	public Searcher(String indexDirectoryPath) throws IOException {
		FSDirectory indexDirectory = FSDirectory.open(Paths.get(indexDirectoryPath));
		System.out.println("open Searcher on: " + indexDirectory.toString());
		Analyzer analyzer = new GermanAnalyzer();
	  	indexReader = DirectoryReader.open(indexDirectory);
	  	indexSearcher = new IndexSearcher(indexReader);
	  	queryParser = new MultiFieldQueryParser(
	  			new String[] {"waypoint", "name", "logs", "description", "coordinates", "hiddenAt", "descriptionSnippet", "cacheType", "difficulty", "terrain", "caseType", "condition", "status"},
                analyzer);
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
}