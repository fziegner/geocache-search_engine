package ir.SearchEngine.GeocacheSearchEngine.suggester;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

import org.apache.lucene.search.suggest.InputIterator;
import org.apache.lucene.util.BytesRef;

import ir.SearchEngine.GeocacheSearchEngine.Model.Geocache;

public class GeocacheIterator implements InputIterator{
	
	private Iterator<Geocache> geocacheIterator;
    private Geocache currentGeocache;

    public GeocacheIterator(Iterator<Geocache> geocacheIterator) {
        this.geocacheIterator = geocacheIterator;
    }

    public boolean hasContexts() {
        return true;
    }

    public boolean hasPayloads() {
        return true;
    }

    public Comparator<BytesRef> getComparator() {
        return null;
    }

    public BytesRef next() {
        return null;
    }

    public BytesRef payload() {
        return null;
    }

    public Set<BytesRef> contexts() {
    	return null;
    }

    public long weight() {
        return currentGeocache.getLogs().size();
    }
}