package ir.SearchEngine.GeocacheSearchEngine;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.FSDirectory;

public class Indexer {
	
	private IndexWriter indexWriter;
	
	public Indexer(String indexDirectoryPath) throws IOException {
		
		FSDirectory indexDirectory = FSDirectory.open(Paths.get(indexDirectoryPath));
        Analyzer analyzer = new GermanAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(OpenMode.CREATE);
		
		indexWriter = new IndexWriter(indexDirectory, config);
	}
	
	private Document getDocument(File file) throws IOException {
		
		Document document = new Document();
				
		TextField contentField = new TextField("content", new FileReader(file));	
		document.add(contentField);
		
		return document;
	}
	
	private void indexFile(File file) throws IOException {
		
		System.out.println("Indexing " + file.getCanonicalPath());
		Document document = getDocument(file);
		indexWriter.addDocument(document);
	}
	
	public int createIndex(String sourceFilesPath) throws IOException {
		
		File[] files = new File(sourceFilesPath).listFiles();
			for (File file : files) {
				indexFile(file);
			}
		return indexWriter.numDocs();
	}
	
	public void close() throws CorruptIndexException, IOException {
		indexWriter.close();
	}
}
