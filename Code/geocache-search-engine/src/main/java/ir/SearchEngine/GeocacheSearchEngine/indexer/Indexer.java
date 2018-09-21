package ir.SearchEngine.GeocacheSearchEngine.indexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.analysis.shingle.ShingleFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.FSDirectory;
import org.json.JSONObject;

public class Indexer {
	
	private IndexWriter indexWriter;
	
	/**
	 * Constructor of the Index.
	 * 1. index directory should be inside the project
	 * 2. starts a GermanAnalyzer
	 * 3. binds the analyzer to a config and creates the IndexWriter
	 * @param indexDirectoryPath path where the index should be, inside the project is recommended 
	 * @throws IOException
	 */
	public Indexer(String indexDirectoryPath) throws IOException {
		
		FSDirectory indexDirectory = FSDirectory.open(Paths.get(indexDirectoryPath));
		Analyzer analyzer = new GermanAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(OpenMode.CREATE);
		
		indexWriter = new IndexWriter(indexDirectory, config);
	}
	
	/**
	 * add a document to the index, tips and link are ignored
	 * @param file File to a JSON
	 * @return the parsed Document from the JSON file
	 * @throws IOException
	 */
	private Document getDocument(File file) throws IOException {
		
		Document document = new Document();
		JSONObject json = new JSONObject(readFile(file));
		Analyzer analyzer = new GermanAnalyzer();
		
		for(String key : json.keySet()) {
			if(key.equals("tips") || key.equals("link") || key.equals("waypoint")) {
				continue; //we do not want to index irrelevant info, such as tips or the link
			}
			String value = json.get(key).toString(); //TODO: do not transform every key to a string but handle datatypes, e.g. difficulty is int
			TokenStream tokenStream = analyzer.tokenStream(key, value);
			CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
			tokenStream = new StopFilter(tokenStream, GermanAnalyzer.getDefaultStopSet());
			tokenStream = new ShingleFilter(tokenStream, 3);
			tokenStream.reset();
			StringBuilder stringBuilder = new StringBuilder();
			while (tokenStream.incrementToken()) {
			    String term = charTermAttribute.toString();
			    stringBuilder.append(term.toString());
			}
			TextField field = new TextField(key, stringBuilder.toString(), Field.Store.YES);
			tokenStream.end();
			tokenStream.close();
			document.add(field);
		}
		for(String key : json.keySet()) {
			if(key.equals("waypoint")) {
				String value = json.get(key).toString();
				StringField field = new StringField(key, value, Field.Store.YES);
				document.add(field);	
			}
		}
		//document.add(new StringField("contents", json.toString(), Field.Store.YES));
		
		analyzer.close();
		return document;
	}
	
	/**
	 * Wrapper for indexing a File, @see getDocument() for the actual indexing process
	 * @param file .json File that should be indexed
	 * @throws IOException
	 */
	private void indexFile(File file) throws IOException {
		//TODO: handle that file is indeed a .json file
		System.out.println("Indexing " + file.getCanonicalPath());
		Document document = getDocument(file);
		indexWriter.addDocument(document);
	}
	
	/**
	 * indexes all Files in the given directory, must be .json files
	 * @param sourceFilesPath the directory holding the .json for indexing
	 * @return int amount of documents added to the index
	 * @throws IOException
	 */
	public int createIndex(String sourceFilesPath) throws IOException {
		
		File[] files = new File(sourceFilesPath).listFiles();
			for (File file : files) {
				indexFile(file);
			}
		return indexWriter.numDocs();
	}
	
	/**
	 * closes the IndexWriter to prevent memory leaks
	 * highly important that this function is called because otherwise tomcat server might get memory problems
	 * @throws CorruptIndexException
	 * @throws IOException
	 */
	public void close() throws CorruptIndexException, IOException {
		indexWriter.close();
	}
	
	/**
	 * Helper function to import the .json files.
	 * Reads a given File and stores it in a String
	 * @param file File to be read from
	 * @return String content of the file
	 */
	public static String readFile(File file) {
		StringBuilder data = new StringBuilder(); //use Stringbuilder because it is mutable, e.g. appending is faster than += on Strings
		try {
			BufferedReader br = new BufferedReader(new FileReader(file)); //open file
			String line;
			while((line = br.readLine()) != null) {
				data.append(line).append("\n"); //read and store the whole file
			}
			br.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data.toString();
	}
	
}
