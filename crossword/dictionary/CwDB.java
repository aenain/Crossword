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

    public CwDB(String filename) throws FileNotFoundException, IOException {
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
    
    public void saveDB(String filename) throws IOException {
        BufferedWriter out = null;

        try {
            out = new BufferedWriter(new FileWriter(filename));
            Entry entry;
            
            for (int i = 0; i < dict.size(); i++) {
                entry = dict.get(i);
                out.write(entry.getWord());
                out.newLine();
                out.write(entry.getClue());
                out.newLine();
            }
        } finally {
            if (out != null)
                out.close();
        }
    }
    
    public int getSize() { return dict.size(); }
    
    protected final void createDB(String filename) throws FileNotFoundException, IOException {
        FileInputStream fstream = null;
        DataInputStream in = null;
        BufferedReader br = null;

        try {
            fstream = new FileInputStream(filename);
            in = new DataInputStream(fstream);
            br = new BufferedReader(new InputStreamReader(in));
 
            String word, clue;

            while((word = br.readLine()) != null && (clue = br.readLine()) != null) {
                add(word, clue);
            }

            in.close();
        } finally {
            if (br != null)
                br.close();

            if (in != null)
                in.close();

            if (fstream != null)
                fstream.close();
        }
    }
}
