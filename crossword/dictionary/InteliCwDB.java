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
    private int minWordLength, maxWordLength;

    public InteliCwDB(String filename) throws FileNotFoundException, IOException {
        super(filename);
        setWordLengthBounds();
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

    public int getMinWordLength() { return minWordLength; }
    public int getMaxWordLength() { return maxWordLength; }

    private void setWordLengthBounds() {
        if (dict.size() == 0) {
            minWordLength = 10000;
            maxWordLength = 0;
        }
        else {
            String word = dict.get(0).getWord();

            minWordLength = word.length();
            maxWordLength = minWordLength;

            for (int i = 1; i < dict.size(); i++) {
                word = dict.get(i).getWord();

                if (word.length() < minWordLength)
                    minWordLength = word.length();
                else if (word.length() > maxWordLength)
                    maxWordLength = word.length();
            }
        }
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

        if (word.length() < minWordLength)
            minWordLength = word.length();
        else if (word.length() > maxWordLength)
            maxWordLength = word.length();
    }

    protected int random(int exclusiveMax) {
        int i = (int)(Math.random() * exclusiveMax);
        return i;
    }
}
