/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword;

import java.io.Serializable;

/**
 *
 * @author arturhebda
 */
public class BoardCell implements Serializable {
    private String content, writenContent;
    private int nearbyHorizStartID, nearbyVertStartID;

    private int possibleLocations;
    private int row, col;

    public BoardCell(int row, int col) {
        this.row = row;
        this.col = col;
        this.content = "";
        this.writenContent = "";

        this.nearbyHorizStartID = 0;
        this.nearbyVertStartID = 0;

        possibleLocations = 0;
        enableAll();
    }

    public BoardCell copy() {
        BoardCell copy = new BoardCell(row, col);

        copy.content = this.content.trim(); // smart copy!
        copy.writenContent = this.writenContent.trim();

        copy.nearbyHorizStartID = this.nearbyHorizStartID;
        copy.nearbyVertStartID = this.nearbyVertStartID;

        copy.possibleLocations = this.possibleLocations;

        return copy;
    }

    public void clear() { writenContent = ""; }
    public void check() { writenContent = content; }

    public int getRow() { return row; }
    public int getCol() { return col; }

    public int getNearbyHorizStartID() { return nearbyHorizStartID; }
    public int getNearbyVertStartID() { return nearbyVertStartID; }

    public void setNearbyHorizStartID(int startID) { nearbyHorizStartID = startID; }
    public void setNearbyVertStartID(int startID) { nearbyVertStartID = startID; }

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

    public void setWritenContent(String writenContent) { this.writenContent = writenContent; }
    public String getWritenContent() { return writenContent; }

    public void print() { print(""); }

    public void print(String separator) {
        if (! content.isEmpty())
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
