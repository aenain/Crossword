/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import crossword.Board;
import crossword.Crossword;
import crossword.Settings;
import crossword.CwBrowser;
import crossword.Strategy;
import crossword.dictionary.NoRecordsFoundException;
import crossword.dictionary.TooSmallBoardException;
import gui.TableModel;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.*;
/**
 *
 * @author arturhebda
 */
public class CrosswordsController extends BaseController {
    private CwBrowser browser;
    private Crossword crossword;
    private TableModel tableModel;

    public CrosswordsController(Settings settings) {
        super(settings);

        browser = new CwBrowser(settings.getCrosswordsDirectoryPath());
        crossword = null;
        tableModel = new TableModel();
    }

    /**
     * 
     * @param settings
     * @param components - { crosswordsList }
     */
    // TODO! być może już tutaj rzucać wyjątkiem, że nie ma krzyżówek
    public void prepareCrosswordsList(JComponent[] components) {
        refreshBrowserDirectory();

        JList crosswordsList = (JList) components[0];
        LinkedList<Long> ids = browser.getAllIDs();

        crosswordsList.setListData(ids.toArray());
        crosswordsList.setSelectedIndex(0); // TODO! można zaznaczać tą, którą się rozwiązywało
    }

    /**
     * 
     * @param components - { boardTable, horizontalClues, verticalClues }
     * @throws InstantiationException
     * @throws IllegalAccessException 
     */
    public void generateAndShowCrossword(JComponent[] components) throws FileNotFoundException, InstantiationException, IllegalAccessException, IOException, NoRecordsFoundException, TooSmallBoardException {
        crossword = new Crossword(settings.getRows(), settings.getCols(), settings.getDatabaseFilePath());
        Strategy strategy = (Strategy) settings.getCrosswordsStrategyClass().newInstance();
        crossword.generate(strategy);

        if (crossword != null)
            showCrossword(crossword, components);
    }

    public void saveCrossword() throws IOException {
        if (crossword != null) {
            refreshBrowserDirectory();
            browser.write(crossword);
        }
        else {} // TODO! wyjątek?
    }

    /**
     * 
     * @param components - { boardTable }
     */
    public void checkCrossword(JComponent[] components) {
        JTable boardTable = (JTable) components[0];

        if (crossword != null) {
            Board board = crossword.getBoard();
            board.check();

            boardTable.setModel(tableModel);
            tableModel.updateBoard(board, false);
        }
        else {} // TODO! wyjątek?
    }

    /**
     * 
     * @param components - { boardTable }
     */
    public void resetCrossword(JComponent[] components) {
        JTable boardTable = (JTable) components[0];

        if (crossword != null) {
            Board board = crossword.getBoard();
            board.clear();

            boardTable.setModel(tableModel);
            tableModel.updateBoard(board, false);
        }
        else {} // TODO! wyjątek?
    }
    
    /**
     * 
     * @param components - { crosswordsList, boardTable, horizontalClues, verticalClues }
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException 
     */
    public void showCrossword(JComponent[] components) throws IOException, FileNotFoundException, ClassNotFoundException {
        JList crosswordsList = (JList) components[0];
        Long id = (Long) crosswordsList.getSelectedValue();
        crossword = browser.getCrosswordByID(id);

        if (crossword != null) {
            JComponent[] neededComponents = { components[1], components[2], components[3] };
            showCrossword(crossword, neededComponents);
        }
    }

    /**
     * 
     * @param crossword - crossword to show
     * @param components - { boardTable, horizontalClues, verticalClues }
     */
    private void showCrossword(Crossword crossword, JComponent[] components) {
        JTable boardTable = (JTable) components[0];
        JTextArea horizontalClues = (JTextArea) components[1];
        JTextArea verticalClues = (JTextArea) components[2];

        Board board = crossword.getBoard();
        boardTable.setModel(tableModel);
        tableModel.updateBoard(board, true);

        horizontalClues.setText(prepareCluesList(crossword.getHorizontalClues()));
        verticalClues.setText(prepareCluesList(crossword.getVerticalClues()));
    }

    private String prepareCluesList(Object[] clues) {
        String cluesList = "";

        for (int i = 0; i < clues.length; i++)
            cluesList += (i + 1) + ". " + (String)(clues[i]) + "\n";

        return cluesList;
    }

    private void refreshBrowserDirectory() {
        String currentCrosswordsDirectoryPath = settings.getCrosswordsDirectoryPath();

        if (! currentCrosswordsDirectoryPath.equals(browser.getDirectoryPath()))
            browser.setDirectory(settings.getCrosswordsDirectory());
    }
}
