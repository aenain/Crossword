/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword;
import crossword.dictionary.*;
import java.util.Random;
import java.util.LinkedList;

/**
 *
 * @author arturhebda
 */
public class RealStrategy extends Strategy {
    private Random randomGenerator;
    private Direction direction;
    private int wordCount;

    public RealStrategy() {
        randomGenerator = new Random();
        direction = Direction.HORIZ;
        wordCount = 0;
    }

    // TODO! ma znaleźć w Board miejsce do wstawienia nowego hasła
    // na podstawie tego, które komórki mogą być startowe.
    // długość losowa, stworzyć wzorzec, dopasować hasło i je zwrócić
    @Override
    public CwEntry findEntry(Crossword cw) throws NoRecordsFoundException, TooSmallBoardException {
        Board board = cw.getBoard();
        InteliCwDB cwdb = cw.getCwDB();

        LinkedList<BoardCell> startCells = board.getStartCellsByDirection(direction);
        BoardCell startCell;

        LinkedList<Entry> matchEntries;
        Entry entry;

        String pattern;
 
        int tries = 50;
        int minLength = cw.getMinWordLength();
        int maxLength = cw.getMaxWordLength();
        int selectWordTries, selectLengthTries, length;
  
        if (! startCells.isEmpty()) {
            while ((tries--) > 0) {
                startCell = startCells.get(rand(startCells.size()));
                LinkedList<Integer> availableLengths = availableLengthsByStartInBoard(startCell, minLength, maxLength, board);

                selectLengthTries = 3;

                while (! availableLengths.isEmpty() && (selectLengthTries--) > 0) {
                    length = availableLengths.get(biggerMoreLikelyRand(0, availableLengths.size()));
                    pattern = patternByStartAndLengthInBoard(startCell, length, board);

                    matchEntries = cwdb.findAll(pattern);

                    if (! matchEntries.isEmpty()) {
                        selectWordTries = 5;

                        while ((selectWordTries--) > 0) {
                            entry = matchEntries.get(rand(matchEntries.size()));

                            if (! cw.contains(entry.getWord())) {
                                CwEntry cwEntry = new CwEntry(entry.getWord(), entry.getClue());
                                cwEntry.setLocation(startCell.getRow(), startCell.getCol(), direction);

                                switchDirection();
                                return cwEntry;
                            }
                        }
                    }
                }
            }
        }
        else {} // TODO! no start cells found

        return null;
    }
    
    // TODO! powinna dodać hasło do listy haseł i zaktualizować jego otoczenie
    @Override
    public void updateBoard(Board b, CwEntry e) {
        BoardCell cell, previousCell;

        int startRow = e.getRow();
        int startCol = e.getCol();

        String word = e.getWord();
        boolean isHoriz = (e.getDir() == Direction.HORIZ);

        int endRow = !isHoriz ? startRow + e.getWord().length() - 1 : startRow;
        int endCol = isHoriz ? startCol + e.getWord().length() - 1 : startCol;

        int minRow = Math.max(startRow - 1, 0);
        int maxRow = Math.min(endRow + 1, b.getRows() - 1);

        int minCol = Math.max(startCol - 1, 0);
        int maxCol = Math.min(endCol + 1, b.getCols() - 1);

        if ((wordCount++) == 0) {
            for (int row = 0; row < b.getRows(); row++)
                for (int col = 0; col < b.getCols(); col++)
                    b.getCell(row, col).disableStart(true);
        }

        if (isHoriz) {
            if (minCol < startCol) {
                previousCell = b.getCell(startRow, minCol);
                previousCell.disableAll();
                previousCell.setNearbyHorizStartID(++horizEntriesCount);
            }

            if (maxCol > endCol)
                b.getCell(startRow, maxCol).disableAll();

            if (minRow < startRow)
                for (int col = startCol; col <= endCol; col++)
                    b.getCell(minRow, col).disableVertEnd();

            if (maxRow > endRow)
                for (int col = startCol; col <= endCol; col++)
                    b.getCell(maxRow, col).disableVertStart();

            for (int row = minRow; row <= maxRow; row++)
                for (int col = startCol; col <= endCol; col++)
                    b.getCell(row, col).disableHoriz();

            for (int col = startCol; col <= endCol; col++) {
                cell = b.getCell(startRow, col);
                cell.setContent(word.substring(col - startCol, col - startCol + 1));
            }

            for (int row = Math.max(startRow - b.getMinWordLength() + 1, 0); row <= startRow; row++)
                for (int col = startCol; col <= endCol; col++)
                    b.getCell(row, col).enableVertStart();
        }
        else {
            if (minRow < startRow) {
                previousCell = b.getCell(minRow, startCol);
                previousCell.disableAll();
                previousCell.setNearbyVertStartID(++vertEntriesCount);
            }
 
            if (maxRow > endRow)
                b.getCell(maxRow, startCol).disableAll();

            if (minCol < startCol)
                for (int row = startRow; row <= endRow; row++)
                    b.getCell(row, minCol).disableHorizEnd();

            if (maxCol > endCol)
                for (int row = startRow; row <= endRow; row++)
                    b.getCell(row, maxCol).disableHorizStart();

            for (int row = startRow; row <= endRow; row++)
                for (int col = minCol; col <= maxCol; col++)
                    b.getCell(row, col).disableVert();

            for (int row = startRow; row <= endRow; row++) {
                cell = b.getCell(row, startCol);
                cell.setContent(word.substring(row - startRow, row - startRow + 1));
            }

            for (int col = Math.max(startCol - b.getMinWordLength() + 1, 0); col <= startCol; col++)
                for (int row = startRow; row <= endRow; row++)
                    b.getCell(row, col).enableHorizStart();
        }
    }
    

    private int rand(int exclusiveMax) {
        return randomGenerator.nextInt(exclusiveMax);
    }

    private int biggerMoreLikelyRand(int inclusiveMin, int exclusiveMax) {
        if (inclusiveMin == exclusiveMax)
            return inclusiveMin;

        int internalRandExclusiveMax = (int) Math.pow(exclusiveMax - inclusiveMin + 1, 2) - 1;
        int rand = inclusiveMin + (int)Math.sqrt(rand(internalRandExclusiveMax));

        return Math.max(Math.min(rand, exclusiveMax - 1), 0);
    }

    private void switchDirection() {
        direction = (direction == Direction.HORIZ) ? Direction.VERT : Direction.HORIZ;
    }

    /**
     * 
     * @param startCell - assuming that this cell can be start in given direction
     * @param minLength
     * @param maxLength
     * @param board
     * @return 
     */
    private LinkedList<Integer> availableLengthsByStartInBoard(BoardCell startCell, int minLength, int maxLength, Board board) {
        LinkedList<Integer> availableLengths = new LinkedList<Integer>();
        BoardCell cell = null;

        if (direction == Direction.HORIZ) {
            int row = startCell.getRow();
            int startCol = startCell.getCol();
            int stopCol = Math.min(startCol + maxLength - 1, board.getCols() - 1);

            for (int col = startCol + 1, length = 2; col <= stopCol; col++, length++) {
                cell = board.getCell(row, col);
                
                if (cell.canBeHorizEnd() && length >= minLength) {
                    availableLengths.add(length);
                }

                if (! cell.canBeHorizInner())
                    break;
            }
        }
        else {
            int col = startCell.getCol();
            int startRow = startCell.getRow();
            int stopRow = Math.min(startRow + maxLength - 1, board.getRows() - 1);

            for (int row = startRow + 1, length = 2; row <= stopRow; row++, length++) {
                cell = board.getCell(row, col);

                if (cell.canBeVertEnd() && length >= minLength)
                    availableLengths.add(length);

                if (! cell.canBeVertInner())
                    break;
            }
        }

        return availableLengths;
    }

    private String patternByStartAndLengthInBoard(BoardCell startCell, int length, Board board) {
        int startRow = startCell.getRow();
        int startCol = startCell.getCol();

        if (direction == Direction.HORIZ)
            return board.createPattern(startRow, startCol, startRow, startCol + length - 1);
        else
            return board.createPattern(startRow, startCol, startRow + length - 1, startCol);
    }
}