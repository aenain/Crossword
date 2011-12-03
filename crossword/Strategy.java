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
    public abstract CwEntry findEntry(Crossword cw);
    public abstract void updateBoard(Board b, CwEntry e);
}