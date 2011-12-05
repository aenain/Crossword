/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword;
import crossword.dictionary.*;

/**
 *
 * @author arturhebda
 */
public abstract class Strategy {
    protected int horizEntriesCount = 0;
    protected int vertEntriesCount = 0;

    public abstract CwEntry findEntry(Crossword cw) throws NoRecordsFoundException, TooSmallBoardException;
    public abstract void updateBoard(Board b, CwEntry e);
}