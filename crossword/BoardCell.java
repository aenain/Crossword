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

    private int possibleLocations, possibleChanges;
    private int row, col;

    public BoardCell(int row, int col) {
        this.row = row;
        this.col = col;
        this.content = "";
        this.writenContent = "";

        this.nearbyHorizStartID = 0;
        this.nearbyVertStartID = 0;

        possibleLocations = 0;
        possibleChanges = 0;

        enableAll();
    }

    public BoardCell copy() {
        BoardCell copy = new BoardCell(row, col);

        copy.content = this.content.trim(); // smart copy!
        copy.writenContent = this.writenContent.trim();

        copy.nearbyHorizStartID = this.nearbyHorizStartID;
        copy.nearbyVertStartID = this.nearbyVertStartID;

        copy.possibleLocations = this.possibleLocations;
        copy.possibleChanges = this.possibleChanges;

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

    public final void enableAll() {
        possibleChanges = max();
        enable(max());
    }

    public final void disableAll() { disable(max(), false); }

    public boolean canBeStart() { return canBe(1) || canBe(8); }

    public boolean canBeStartByDirection(Direction direction) {
        return ((direction == Direction.HORIZ) ? canBeHorizStart() : canBeVertStart());
    }

    public void disableHoriz() { disableHoriz(false); }
    public void disableHoriz(boolean softDisabled) { disable(4 + 2 + 1, softDisabled); }

    public void disableVert() { disableVert(false); }
    public void disableVert(boolean softDisabled) { disable(32 + 16 + 8, softDisabled); }

    public void disableStart() { disableStart(false); }
    public void disableStart(boolean softDisabled) { disable(8 + 1, softDisabled); }

    public boolean canBeHorizStart() { return canBe(1); }

    public void disableHorizStart() { disableHorizStart(false); }
    public void disableHorizStart(boolean softDisabled) { disable(1, softDisabled); }

    public void enableHorizStart() { enable(1); }

    public boolean canBeHorizInner() { return canBe(2); }

    public void disableHorizInner() { disableHorizInner(false); }
    public void disableHorizInner(boolean softDisabled) { disable(2, softDisabled); }

    public void enableHorizInner() { enable(2); }

    public boolean canBeHorizEnd() { return canBe(4); }

    public void disableHorizEnd() { disableHorizEnd(false); }
    public void disableHorizEnd(boolean softDisabled) { disable(4, softDisabled); }

    public void enableHorizEnd() { enable(4); }

    public boolean canBeVertStart() { return canBe(8); }

    public void disableVertStart() { disableVertStart(false); }
    public void disableVertStart(boolean softDisabled) { disable(8, softDisabled); }

    public void enableVertStart() { enable(8); }

    public boolean canBeVertInner() { return canBe(16); }

    public void disableVertInner() { disableVertInner(false); }
    public void disableVertInner(boolean softDisabled) { disable(16, softDisabled); }

    public void enableVertInner() { enable(16); }

    public boolean canBeVertEnd() { return canBe(32); }

    public void disableVertEnd() { disableVertEnd(false); }
    public void disableVertEnd(boolean softDisabled) { disable(32, softDisabled); }
    
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

    private boolean couldBe(int number) {
        return canBe(number) || (possibleChanges & number) == number;
    }

    private void enable(int number) {
        if (couldBe(number)) {
            possibleLocations |= number;
            possibleChanges &= (max() - number);
        }
    }

    private void disable(int number, boolean softDisabled) {
        possibleLocations &= (max() - number);

        if (softDisabled)
            possibleChanges |= number;
        else
            possibleChanges &= (max() - number);
    }

    private int max() { return 32 + 16 + 8 + 4 + 2 + 1; }
}
