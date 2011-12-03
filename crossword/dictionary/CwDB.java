/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword.dictionary;
import java.util.LinkedList;
import java.io.*;

/**
 *
 * @author arturhebda
 */
public class CwDB {
    protected LinkedList<Entry> dict;

    public CwDB(String filename) {
        dict = new LinkedList<Entry>();
        createDB(filename);
    }

    public void add(String word, String clue) {
        Entry entry = new Entry(word, clue);
        dict.add(entry);
    }

    public Entry get(String word) {
        Entry entry = dict.getFirst();

        for (int i = 0; i < dict.size(); i++) {
            entry = dict.get(i);
            if (entry.getWord().equals(word)) {
                return entry;
            }
        }

        return entry; // tu mógłby by być wyjątek rzucany
    }
    
    public void remove(String word) {
        Entry entry;

        for (int i = 0; i < dict.size(); i++) {
            entry = dict.get(i);
            if (entry.getWord().equals(word)) {
                dict.remove(i);
                break;
            }
        }
    }
    
    public void saveDB(String filename) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filename));
            Entry entry;
            
            for (int i = 0; i < dict.size(); i++) {
                entry = dict.get(i);
                out.write(entry.getWord());
                out.newLine();
                out.write(entry.getClue());
                out.newLine();
            }

            out.close();
        } catch(IOException e) { e.printStackTrace(System.out); }
    }
    
    public int getSize() { return dict.size(); }
    
    protected final void createDB(String filename) {
        try {
            FileInputStream fstream = new FileInputStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
 
            String word, clue;

            while((word = br.readLine()) != null && (clue = br.readLine()) != null) {
                add(word, clue);
            }

            in.close();
        } catch (IOException e) { e.printStackTrace(System.out); }
    }
}
