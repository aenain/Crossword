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

    public RealStrategy() {
        randomGenerator = new Random();
    }

    // TODO! ma znaleźć w Board miejsce do wstawienia nowego hasła
    // na podstawie tego, które komórki mogą być startowe.
    // długość losowa, stworzyć wzorzec, dopasować hasło i je zwrócić
    @Override
    public CwEntry findEntry(Crossword cw) {
        Board board = cw.getBoard();
        InteliCwDB cwdb = cw.getCwDB();
        LinkedList<BoardCell> startCells = board.getStartCells();
        BoardCell startCell = null;
        Direction direction;
        Entry entry;

        int length = 0;
        int tries = 50;
        int selectWordTries;
        int startRow = 0;
        int startCol = 0;

        if (startCells.size() > 0) {
            int availableLength = 0;
            BoardCell currentCell;
            String pattern;

            while ((tries--) > 0) {
                direction = randomGenerator.nextBoolean() ? Direction.HORIZ : Direction.VERT;

                startCell = startCells.get(rand(startCells.size()));
                availableLength = 1;
                selectWordTries = 3;
                int randomExclusiveMax = board.size(direction);

                if (direction == Direction.HORIZ) {
                    if (startCell.canBeStartByDirection(direction)) {
                        startRow = startCell.getRow();
                        startCol = startCell.getCol();

                        length = rand(randomExclusiveMax - startCol - 1) + 3;

                        for (int col = startCol + 1; col < startCol + length; col++) {
                            currentCell = board.getCell(startRow, col);

                            if (currentCell.canBeHorizInner() && currentCell.canBeHorizEnd())
                                availableLength++;
                            else
                                break;
                        }

                        if (availableLength >= 3) {
                            pattern = board.createPattern(startRow, startCol, startRow, startCol + availableLength - 1);
                            LinkedList<Entry> matchEntries = cwdb.findAll(pattern);

                            if (matchEntries.size() > 0) {
                                while ((selectWordTries--) > 0) {
                                    entry = matchEntries.get(rand(matchEntries.size()));

                                    if (! cw.contains(entry.getWord())) {
                                        CwEntry cwEntry = new CwEntry(entry.getWord(), entry.getClue());
                                        cwEntry.setLocation(startRow, startCol, direction);

                                        return cwEntry;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (startCell.canBeStartByDirection(direction)) {
                        startRow = startCell.getRow();
                        startCol = startCell.getCol();

                        length = rand(randomExclusiveMax - startRow - 2) + 3;

                        for (int row = startRow + 1; row < startRow + length; row++) {
                            currentCell = board.getCell(row, startCol);

                            if (currentCell.canBeVertInner() && currentCell.canBeVertEnd())
                                availableLength++;
                            else
                                break;
                        }

                        if (availableLength >= 3) {
                            pattern = board.createPattern(startRow, startCol, startRow + availableLength - 1, startCol);
                            LinkedList<Entry> matchEntries = cwdb.findAll(pattern);

                            if (matchEntries.size() > 0) {
                                while ((selectWordTries--) > 0) {
                                    entry = matchEntries.get(rand(matchEntries.size()));

                                    if (! cw.contains(entry.getWord())) {
                                        CwEntry cwEntry = new CwEntry(entry.getWord(), entry.getClue());
                                        cwEntry.setLocation(startRow, startCol, direction);

                                        return cwEntry;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // TODO! no records found.
        }

        return null;
    }

    // TODO! powinna dodać hasło do listy haseł i zaktualizować jego otoczenie
    @Override
    public void updateBoard(Board b, CwEntry e) {
        BoardCell cell;

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

        if (isHoriz) {
            if (minRow < startRow) {
                for (int col = startCol; col <= endCol; col++) {
                    cell = b.getCell(minRow, col);
                    cell.disableVertEnd();
                    cell.disableHoriz();
                }
            }

            if (maxRow > startRow) {
                for (int col = startCol; col <= endCol; col++) {
                    cell = b.getCell(maxRow, col);
                    cell.disableVertStart();
                    cell.disableHoriz();
                }
            }

            for (int col = startCol; col <= endCol; col++) {
                cell = b.getCell(startRow, col);
                cell.disableHoriz();
                cell.setContent(word.substring(col - startCol, col - startCol + 1));
            }

            if (minCol < startCol)
                b.getCell(startRow, minCol).disableAll();

            if (maxCol > startCol)
                b.getCell(startRow, maxCol).disableAll();
        }
        else {
            if (minCol < startCol) {
                for (int row = startRow; row <= endRow; row++) {
                    cell = b.getCell(row, minCol);
                    cell.disableHorizEnd();
                    cell.disableVert();
                }
            }

            if (maxCol > startCol) {
                for (int row = startRow; row <= endRow; row++) {
                    cell = b.getCell(row, maxCol);
                    cell.disableHorizStart();
                    cell.disableVert();
                }
            }

            for (int row = startRow; row <= endRow; row++) {
                cell = b.getCell(row, startCol);
                cell.disableVert();
                cell.setContent(word.substring(row - startRow, row - startRow + 1));
            }

            if (minRow < startRow)
                b.getCell(minRow, startCol).disableAll();

            if (maxRow > startRow)
                b.getCell(maxRow, startCol).disableAll();
        }
    }

    private int rand(int exclusiveMax) {
        return randomGenerator.nextInt(exclusiveMax);
    }
}