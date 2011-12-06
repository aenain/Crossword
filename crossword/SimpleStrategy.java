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
public class SimpleStrategy extends Strategy {
    private Random randomGenerator;
    private int wordCount, vertWordLength;

    public SimpleStrategy() {
        randomGenerator = new Random();
        wordCount = 0;
        vertWordLength = 0;
    }

    // TODO! ma znaleźć w Board miejsce do wstawienia nowego hasła
    // na podstawie tego, które komórki mogą być startowe.
    // długość losowa, stworzyć wzorzec, dopasować hasło i je zwrócić
    @Override
    public CwEntry findEntry(Crossword cw) throws NoRecordsFoundException, TooSmallBoardException {
        Board board = cw.getBoard();
        InteliCwDB cwdb = cw.getCwDB();
        LinkedList<BoardCell> startCells = board.getStartCells();
        BoardCell startCell = null;
        Entry entry;
        String pattern;
        int startCol = 0;
        int startRow = 0;

        int tries = 50;

        if (startCells.size() < 1)
            return null;

        if (wordCount > 0) {
            Direction direciton = Direction.HORIZ;

            if (board.getCols() - cw.getMinWordLength() < 1)
                throw new TooSmallBoardException();

            startCell = startCells.get(0);
            startCol = startCell.getCol();
            startRow = startCell.getRow();

            while ((tries--) > 0) {
                int length = randomGenerator.nextInt(board.getCols() - cw.getMinWordLength()) + cw.getMinWordLength();
                pattern = board.createPattern(startRow, startCol, startRow, startCol + length - 1);

                LinkedList<Entry> matchEntries = cwdb.findAll(pattern);

                if (matchEntries.size() == 0) {
                    continue;
                }
                else {
                    entry = matchEntries.get(randomGenerator.nextInt(matchEntries.size()));
                    CwEntry cwEntry = new CwEntry(entry.getWord(), entry.getClue());
                    cwEntry.setLocation(startRow, startCol, direciton);
                    wordCount++;

                    return cwEntry;
                }
            }
        }
        else { // pierwsze słowo
            int length = Math.min(board.getRows(), cw.getMaxWordLength());
            LinkedList<Entry> matchEntries = null;

            while ((tries--) > 0) {
                do {
                    length--;
                    matchEntries = cwdb.findAll(length);
                } while (matchEntries.size() == 0 && length > 0);

                if (length == 0)
                    throw new NoRecordsFoundException();

                entry = matchEntries.get(randomGenerator.nextInt(matchEntries.size()));
                CwEntry cwEntry = new CwEntry(entry.getWord(), entry.getClue());
                cwEntry.setLocation(1, 1, Direction.VERT);
                vertWordLength = entry.getWord().length();
                wordCount++;

                return cwEntry;
            }
        }

        return null;
    }

    // TODO! powinna dodać hasło do listy haseł i zaktualizować jego otoczenie
    @Override
    public void updateBoard(Board b, CwEntry e) {
        int startRow = e.getRow();
        int startCol = e.getCol();
        String word = e.getWord();

        int rowMax = b.getRows() - 1;
        int colMax = b.getCols() - 1;

        if (wordCount > 1) { // dodano więcej niż jedno słowo
            if (startCol - 1 >= 0)
                b.getCell(startRow, startCol - 1).setNearbyHorizStartID(++horizEntriesCount);
 
            int endCol = startCol + e.getWord().length() - 1;

            for (int col = startCol; col <= endCol; col++) {
                b.getCell(startRow, col).disableAll();
                b.getCell(startRow, col).setContent(word.substring(col - startCol, col - startCol + 1));
            }

            for (int col = endCol + 1; col <= colMax; col++)
                b.getCell(startRow, col).disableAll();
        }
        else {
            if (startRow - 1 >= 0)
                b.getCell(startRow - 1, startCol).setNearbyVertStartID(++vertEntriesCount);
 
            int endRow = startRow + e.getWord().length() - 1;

            for (int col = 0; col <= colMax; col++) {
                for (int row = 0; row < startRow; row++)
                    b.getCell(row, col).disableAll();

                for (int row = endRow + 1; row <= rowMax; row++)
                    b.getCell(row, col).disableAll();
            }

            for (int col = startCol + 1; col <= colMax; col++) {
                for (int row = startRow; row <= endRow; row++) {
                    b.getCell(row, col).disableVert();
                    b.getCell(row, col).disableStart();
                }
            }

            for (int row = 0; row <= rowMax; row++)
                for (int col = 0; col < startCol; col++)
                    b.getCell(row, col).disableAll();

            for (int row = startRow; row <= endRow; row++) {
                b.getCell(row, startCol).setContent(word.substring(row - startRow, row - startRow + 1));
                b.getCell(row, startCol).disableVert();
            }
        }
    }
}