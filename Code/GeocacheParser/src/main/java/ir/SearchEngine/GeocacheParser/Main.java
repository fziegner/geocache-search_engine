package ir.SearchEngine.GeocacheParser;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		 String testDirectory = "C:\\Users\\chris\\Desktop\\test";
		
		 List<String> filesInTestDirectory = Parser.listAllFiles(testDirectory);
		 for(String file : filesInTestDirectory) {
			 System.out.println(file);
		 }
		 

	}

}
