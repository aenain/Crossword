/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword.dictionary;
import crossword.Direction;
/**
 *
 * @author arturhebda
 */

public class CwEntry extends Entry {
    private int row, col;

    private Direction dir;

    public CwEntry(String word, String clue) {
        super(word, clue);
    }

    public int getRow() { return row; }
    public int getCol() { return col; }

    public void setLocation(int row, int col, Direction dir) {
        this.row = row;
        this.col = col;
        this.dir = dir;
    }

    public Direction getDir() { return dir; }
}
