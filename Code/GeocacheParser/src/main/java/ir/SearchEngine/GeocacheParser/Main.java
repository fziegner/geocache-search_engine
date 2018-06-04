package ir.SearchEngine.GeocacheParser;

/**
 * Main class for testing purposes
 * @author Christian Schlecht
 *
 */
public class Main {

	public static void main(String[] args) {
		
		String data = "C:\\Users\\Christian-PC2\\Desktop\\data";
		String dataParsed = "C:\\Users\\Christian-PC2\\Desktop\\dataParsed";
		new Parser(data, dataParsed);
	}

}