package ir.SearchEngine.GeocacheSearchEngine;

public class SuggesterHelper {
	
	static boolean readLock = false;
	static Suggester suggester;
	
	public static void enableLock() {
		readLock = true;
	}
	
	public static boolean getLock() {
		return readLock;
	}
	
	public static void setSuggester(Suggester sug) {
		suggester = sug;
	}
	
	public static Suggester getSuggester() {
		return suggester;
	}
}