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
        FileOutputStream fstream = null;
        DataOutputStream out = null;
        BufferedWriter bw = null;

        try {
            fstream = new FileOutputStream(filename);
            out = new DataOutputStream(fstream);
            bw = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

            Entry entry;
            
            for (int i = 0; i < dict.size(); i++) {
                entry = dict.get(i);
                bw.write(entry.getWord());
                bw.newLine();
                bw.write(entry.getClue());
                bw.newLine();
            }
        } finally {
            if (bw != null)
                bw.close();

            if (out != null)
                out.close();

            if (fstream != null)
                fstream.close();
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
            br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
 
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
