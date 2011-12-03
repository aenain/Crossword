/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword;

import java.util.Locale;

/**
 *
 * @author arturhebda
 */
public class BoardCell {
    private String content;
    private int possibleLocations;

    private int col, row;

    public BoardCell(int col, int row) {
        this.col = col;
        this.row = row;

        possibleLocations = 0;
        enableAll();
    }

    public BoardCell copy() {
        BoardCell copy = new BoardCell(col, row);

        copy.content = this.content;
        copy.possibleLocations = this.possibleLocations;

        return copy;
    }
    
    public int getRow() { return row; }
    public int getCol() { return col; }

    public final void enableAll() { enable(max()); }
    public final void disableAll() { disable(max()); }

    public boolean canBeStart() { return canBe(1) || canBe(8); }

    public boolean canBeStartByDirection(Direction direction) {
        return ((direction == Direction.HORIZ) ? canBeHorizStart() : canBeVertStart());
    }

    public void disableHoriz() { disable(4 + 2 + 1); }
    public void disableVert() { disable(32 + 16 + 8); }
    public void disableStart() { disable(8 + 1); }

    public boolean canBeHorizStart() { return canBe(1); }
    public void disableHorizStart() { disable(1); }
    public void enableHorizStart() { enable(1); }

    public boolean canBeHorizInner() { return canBe(2); }
    public void disableHorizInner() { disable(2); }
    public void enableHorizInner() { enable(2); }

    public boolean canBeHorizEnd() { return canBe(4); }
    public void disableHorizEnd() { disable(4); }
    public void enableHorizEnd() { enable(4); }

    public boolean canBeVertStart() { return canBe(8); }
    public void disableVertStart() { disable(8); }
    public void enableVertStart() { enable(8); }

    public boolean canBeVertInner() { return canBe(16); }
    public void disableVertInner() { disable(16); }
    public void enableVertInner() { enable(16); }

    public boolean canBeVertEnd() { return canBe(32); }
    public void disableVertEnd() { disable(32); }
    public void enableVertEnd() { enable(32); }

    public void setContent(String content) { this.content = content; }
    public String getContent() { return content; }

    public void print() { print(""); }

    public void print(String separator) {
        if (content != null)
            System.out.print(content.toUpperCase() + separator);
        else
            System.out.print("-" + separator);
    }

    private boolean canBe(int number) {
        return (possibleLocations & number) == number;
    }

    private void enable(int number) {
        possibleLocations |= number;
    }

    private void disable(int number) {
        possibleLocations &= (max() - number);
    }

    private int max() { return 32 + 16 + 8 + 4 + 2 + 1; }
}
