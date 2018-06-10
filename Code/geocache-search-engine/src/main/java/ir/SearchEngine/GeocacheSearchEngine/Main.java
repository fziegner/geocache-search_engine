package ir.SearchEngine.GeocacheSearchEngine;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		
		String indexDirectory = "index"; //the index will be created inside the project
		String sourceFilesDirectory = "/home/christian/Dokumente/Uni/test";
		Indexer indexer = new Indexer(indexDirectory);
		int numIndexed = indexer.createIndex(sourceFilesDirectory);
		indexer.close();
		System.out.println(numIndexed + " File(s) indexed");
	}
}
