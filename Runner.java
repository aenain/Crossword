/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author arturhebda
 */
import crossword.dictionary.*;
import crossword.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import gui.*;

public class Runner {
    public static void main(String [] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        MainWindow.main(args);

//        InteliCwDB crosswordDB = new InteliCwDB("cwdb.txt");

        //for (int i = 3; i < 20; i++)
          //  System.out.println(i + ": " + crosswordDB.findAll(i).size());
//
//        try {
//            Entry entryByLength = crosswordDB.getRandom(7);
//            Entry entryByPattern = crosswordDB.getRandom("a.*");
//
//            System.out.println(entryByLength.getClue());
//            System.out.println(entryByPattern.getClue());
//        } catch(NoRecordsFoundException e) { e.printStackTrace(); }
//
//        crosswordDB.saveDB("cwdb_order.txt");
//          Crossword crossword = new Crossword(20, 20, "cwdb.txt");
//          Strategy strategy = new RealStrategy();
//          crossword.generate(strategy);
//        crossword.print();
//
//          Writer writer = new Writer("/Users/arturhebda/Desktop/crosswords/");
//          writer.write(crossword);
    }
}
