/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword.dictionary;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

/**
 *
 * @author arturhebda
 */
public class InteliCwDB extends CwDB {
    public InteliCwDB(String filename) throws FileNotFoundException, IOException {
        super(filename);
    }

    public LinkedList<Entry> findAll(String pattern) {
        LinkedList<Entry> matches = new LinkedList<Entry>();
        Entry entry;
        
        for(int i = 0; i < dict.size(); i++) {
            entry = dict.get(i);

            if (entry.getWord().matches(pattern)) {
                matches.add(entry);
            }
        }

        return matches;
    }

    public LinkedList<Entry> findAll(int length) {
        LinkedList<Entry> matches = new LinkedList<Entry>();
        Entry entry;
        
        for(int i = 0; i < dict.size(); i++) {
            entry = dict.get(i);

            if (entry.getWord().length() == length) {
                matches.add(entry);
            }
        }
        
        return matches;
    }

    public Entry getRandom() throws NoRecordsFoundException {
        if (dict.size() == 0) { throw new NoRecordsFoundException(); }

        return dict.get(random(dict.size()));
    }
    
    public Entry getRandom(int length) throws NoRecordsFoundException {
        LinkedList<Entry> matches = findAll(length);
        if (matches.size() == 0) { throw new NoRecordsFoundException(); }

        return matches.get(random(matches.size()));
    }
    
    public Entry getRandom(String pattern) throws NoRecordsFoundException {
        LinkedList<Entry> matches = findAll(pattern);
        if (matches.size() == 0) { throw new NoRecordsFoundException(); }

        return matches.get(random(matches.size()));
    }
    
    @Override
    public void add(String word, String clue) {
        int i;

        for (i = 0; i < dict.size(); i++) {
            if (dict.get(i).getWord().compareTo(word) > 0) {
                break;
            }
        }

        Entry entry = new Entry(word, clue);
        dict.add(i, entry);
    }

    protected int random(int exclusiveMax) {
        int i = (int)(Math.random() * exclusiveMax);
        return i;
    }
}
