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

public class Runner {
    public static void main(String [] argv) {
//        InteliCwDB crosswordDB = new InteliCwDB("cwdb.txt");
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
        Crossword crossword = new Crossword(20, 20, "cwdb.txt");
        Strategy strategy = new RealStrategy();
        crossword.generate(strategy);
        crossword.print();
    }
}
