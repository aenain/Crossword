/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword;
import java.io.Serializable;
import java.util.LinkedList;
/**
 *
 * @author arturhebda
 */
public class Board implements Serializable {
    private BoardCell[][] board; // (x, y)
    private int rows, cols;

    public Board(int cols, int rows) {
        board = new BoardCell[cols][rows];

        this.rows = rows;
        this.cols = cols;

        for (int col = 0; col < cols; col++)
            for (int row = 0; row < rows; row++)
                board[col][row] = new BoardCell(col, row);

        setUpCellsAbilities();
    }

    public Board copy() {
        Board copy = new Board(rows, cols);

        for (int col = 0; col < cols; col++)
            for (int row = 0; row < rows; row++)
                copy.setCell(col, row, getCell(col, row).copy());

        return copy;
    }

    public int getWidth() { return cols; }
    public int getHeight() { return rows; }

    public int size(Direction direction) {
        return ((direction == Direction.HORIZ) ? getWidth() : getHeight());
    }

    public BoardCell getCell(int x, int y) { return board[x][y]; }
    public void setCell(int x, int y, BoardCell c) { board[x][y] = c; }

    public LinkedList<BoardCell> getStartCells() {
        LinkedList<BoardCell> startCells = new LinkedList<BoardCell>();

        for (BoardCell[] BoardCol : board)
            for (BoardCell cell : BoardCol)
                if (cell.canBeStart())
                    startCells.add(cell);

        return startCells;
    }

    public String createPattern(int fromx, int fromy, int tox, int toy) {
        String pattern = "";
        String content;

        if (fromx == tox) { // pionowo
            int x = fromx;

            for (int y = fromy; y <= toy; y++) {
                content = board[x][y].getContent();
                pattern += (content != null) ? content : ".";
            }
        }
        else if (fromy == toy) { // poziomo
            int y = fromy;

            for (int x = fromx; x <= tox; x++) {
                content = board[x][y].getContent();
                pattern += (content != null) ? content : ".";
            }
        }
        else {
            // TODO! rzuć wyjątkiem
        }

        return pattern;
    }

    private void setUpCellsAbilities() {
        // długość słowa to przynajmniej 3 znaki
        // poziomo
        for (int row = 0; row < this.rows; row++) {
            board[0][row].disableHorizInner();
            board[this.cols - 1][row].disableHorizInner();

            for (int col = 0; col < 2; col++)
                board[col][row].disableHorizEnd();

            for (int col = this.cols - 3; col < this.cols; col++)
                board[col][row].disableHorizStart();
        }

        // pionowo
        for (int col = 0; col < this.cols; col++) {
            board[col][0].disableVertInner();
            board[col][this.rows - 1].disableVertInner();

            for (int row = 0; row < 2; row++)
                board[col][row].disableVertEnd();

            for (int row = this.rows - 3; row < this.rows; row++)
                board[col][row].disableVertStart();
        }
    }
}
