package ir.SearchEngine.GeocacheParser;

/**
 * Main class for testing purposes
 * @author Christian Schlecht
 *
 */
public class Main {

	public static void main(String[] args) {
		
		String data = "/home/christian/Dokumente/Uni/testString";
		String dataParsed = "/home/christian/Dokumente/Uni/testString";
		new Parser(data, dataParsed);
	}

}