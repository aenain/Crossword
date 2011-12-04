/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import crossword.Board;
import crossword.Crossword;
import crossword.Settings;
import crossword.CwBrowser;
import crossword.TableModel;
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
        String currentCrosswordsDirectoryPath = settings.getCrosswordsDirectoryPath();

        if (! currentCrosswordsDirectoryPath.equals(browser.getDirectoryPath()))
            browser.setDirectory(settings.getCrosswordsDirectory());

        JList crosswordsList = (JList) components[0];
        LinkedList<Long> ids = browser.getAllIDs();

        crosswordsList.setListData(ids.toArray());
        crosswordsList.setSelectedIndex(0); // TODO! można zaznaczać tą, którą się rozwiązywało
    }

    /**
     * 
     * @param CrosswordID
     * @param components - { crosswordsList, boardTable }
     */
    public void showCrossword(JComponent[] components) throws IOException, FileNotFoundException, ClassNotFoundException {
        JList crosswordsList = (JList) components[0];
        JTable boardTable = (JTable) components[1];

        Long id = (Long) crosswordsList.getSelectedValue();

        crossword = browser.getCrosswordByID(id);

        if (crossword != null) {
            boardTable.setModel(tableModel);
            tableModel.doMagic(crossword.getBoardCopy());
        }
    }
}
