/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword;
import crossword.dictionary.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Iterator;
/**
 *
 * @author arturhebda
 */

public class Crossword {
    private LinkedList<CwEntry> entries;
    private Board b;
    private InteliCwDB cwdb;
    private final long ID;

    public Crossword(int width, int height, String filename) throws FileNotFoundException, IOException {
        cwdb = new InteliCwDB(filename);
        entries = new LinkedList<CwEntry>();
        b = new Board(width, height);
        ID = 0;
    }

    public Crossword(long id) {
        ID = id;
    }

    public void readObject(final ObjectInputStream objectStream) throws IOException, ClassNotFoundException {
        b = (Board) objectStream.readObject();
        entries = (LinkedList<CwEntry>) objectStream.readObject();
    }

    public void writeObject(final ObjectOutputStream objectStream) throws IOException {
        objectStream.writeObject(b);
        objectStream.writeObject(entries);
    }

    public Iterator<CwEntry> getROEntryIter() {
        return Collections.unmodifiableList(entries).iterator();
    }

    public Board getBoardCopy() {
        return b.copy();
    }

    public Object[] getHorizontalClues() {
        return getCluesByDirection(Direction.HORIZ);
    }

    public Object[] getVerticalClues() {
        return getCluesByDirection(Direction.VERT);
    }

    private Object[] getCluesByDirection(Direction dir) {
        Iterator<CwEntry> entryIterator = getROEntryIter();
        LinkedList<String> clues = new LinkedList<String>();

        while (entryIterator.hasNext()) {
            CwEntry entry = entryIterator.next();

            if (entry.getDir() == dir)
                clues.add(entry.getClue());
        }

        return clues.toArray();
    }

    public Board getBoard() {
        return b;
    }

    public InteliCwDB getCwDB() {
        return cwdb;
    }

    public void setCwDB(InteliCwDB cwdb) {
        this.cwdb = cwdb;
    }
    
    public boolean contains(String word) {
       Iterator<CwEntry> entryIterator = getROEntryIter();

       while (entryIterator.hasNext())
           if (entryIterator.next().getWord().equals(word))
               return true;

       return false;
    }

    public final void addCwEntry(CwEntry cwe, Strategy s) {
        entries.add(cwe);
        s.updateBoard(b, cwe);
    }

    public final void generate(Strategy s) {
        CwEntry e = null;

        while ((e = s.findEntry(this)) != null)
            addCwEntry(e, s);
    }

    public void print() {
        for (int row = 0; row < b.getHeight(); row++) {
            for (int col = 0; col < b.getWidth(); col++)
                b.getCell(col, row).print(" ");

            System.out.println();
        }
    }
}