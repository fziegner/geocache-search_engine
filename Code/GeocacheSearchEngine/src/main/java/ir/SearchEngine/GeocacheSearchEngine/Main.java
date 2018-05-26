package ir.SearchEngine.GeocacheSearchEngine;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		
		String indexDirectory = "C:\\Users\\Fabian\\Desktop\\test\\index";
		String sourceFilesDirectory = "C:\\Users\\Fabian\\Desktop\\test\\source";
		Indexer indexer = new Indexer(indexDirectory);
		int numIndexed = indexer.createIndex(sourceFilesDirectory);
		indexer.close();
		System.out.println(numIndexed + " File(s) indexed");
	}
}