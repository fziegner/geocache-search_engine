package ir.SearchEngine.GeocacheSearchEngine;

import java.io.IOException;

import ir.SearchEngine.GeocacheSearchEngine.Parser.Parser;

public class Main {

	public static void main(String[] args) throws IOException {
		
		//run a whole setup 
		
		String data = "/home/christian/Schreibtisch/irData";
		new Parser(data, CONSTANTS.DATA_DIRECTORY);
		
		
		
		
		String indexDirectory = CONSTANTS.INDEX_DIRECTORY; //the index will be created inside the project
		String sourceFilesDirectory = CONSTANTS.DATA_DIRECTORY;
		Indexer indexer = new Indexer(indexDirectory);
		int numIndexed = indexer.createIndex(sourceFilesDirectory);
		indexer.close();
		System.out.println(numIndexed + " File(s) indexed");
	}
}
