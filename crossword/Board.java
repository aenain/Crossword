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
    private BoardCell[][] board; // (y, x)
    private int rows, cols;

    public Board(int rows, int cols) {
        board = new BoardCell[rows][cols];

        this.rows = rows;
        this.cols = cols;

        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++)
                board[row][col] = new BoardCell(row, col);

        setUpCellsAbilities();
    }

    public Board copy() {
        Board copy = new Board(rows, cols);

        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++)
                copy.setCell(row, col, getCell(row, col).copy());

        return copy;
    }

    public void clear() {
        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++)
                getCell(row, col).clear();
    }

    public BoardCell[][] getBoard() { return board; } 

    public int getCols() { return cols; }
    public int getRows() { return rows; }

    public int size(Direction direction) {
        return ((direction == Direction.HORIZ) ? getCols() : getRows());
    }

    public BoardCell getCell(int row, int col) { return board[row][col]; }
    public void setCell(int row, int col, BoardCell c) { board[row][col] = c; }

    public LinkedList<BoardCell> getStartCells() {
        LinkedList<BoardCell> startCells = new LinkedList<BoardCell>();

        for (BoardCell[] BoardCol : board)
            for (BoardCell cell : BoardCol)
                if (cell.canBeStart())
                    startCells.add(cell);

        return startCells;
    }

    public String createPattern(int fromRow, int fromCol, int toRow, int toCol) {
        String pattern = "";
        String content;

        if (fromRow == toRow) { // horizontally
            int row = fromRow;

            for (int col = fromCol; col <= toCol; col++) {
                content = board[row][col].getContent();
                pattern += (content == null || content.isEmpty()) ? "." : content;
            }
        }
        else if (fromCol == toCol) { // vertically
            int col = fromCol;

            for (int row = fromRow; row <= toRow; row++) {
                content = board[row][col].getContent();
                pattern += (content == null || content.isEmpty()) ? "." : content;
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
            board[row][0].disableHorizInner();
            board[row][this.cols - 1].disableHorizInner();

            for (int col = 0; col < 2; col++)
                board[row][col].disableHorizEnd();

            for (int col = this.cols - 3; col < this.cols; col++)
                board[row][col].disableHorizStart();
        }

        // pionowo
        for (int col = 0; col < this.cols; col++) {
            board[0][col].disableVertInner();
            board[this.rows - 1][col].disableVertInner();

            for (int row = 0; row < 2; row++)
                board[row][col].disableVertEnd();

            for (int row = this.rows - 3; row < this.rows; row++)
                board[row][col].disableVertStart();
        }
    }
}
