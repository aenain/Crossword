/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author arturhebda
 */
import crossword.*;
import crossword.dictionary.NoRecordsFoundException;
import crossword.dictionary.TooSmallBoardException;
import java.io.FileNotFoundException;
import java.io.IOException;
import gui.*;
import java.util.LinkedList;

public class Runner {
    public static void main(String [] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        MainWindow.main(args);
        //Runner.generate();
        //Runner.print();
    }

    public static void print() throws IOException, FileNotFoundException, ClassNotFoundException {
        Reader reader = new Reader("/Users/arturhebda/Desktop/crosswords/");
        reader.getAllCws();
        LinkedList<Crossword> crosswords = reader.getCrosswords();
        Crossword crossword = crosswords.get(0);
        crossword.print();
    }

    public static void generate() throws IOException, NoRecordsFoundException, TooSmallBoardException {
        for (int i = 0; i < 5; i++) {
            Crossword crossword = new Crossword(20, 20, "/Users/arturhebda/Dropbox/AGH/II/Java/lab2.zadanie/cwdb.txt");
            Strategy strategy = new RealStrategy();
            crossword.generate(strategy);

            Writer writer = new Writer("/Users/arturhebda/Desktop/crosswords/");
            writer.write(crossword);
        }
    }
}
