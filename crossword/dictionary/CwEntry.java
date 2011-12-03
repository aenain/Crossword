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
    private int x;
    private int y;

    private Direction dir;

    public CwEntry(String word, String clue) {
        super(word, clue);
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void setLocation(int x, int y, Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public Direction getDir() { return dir; }
}
