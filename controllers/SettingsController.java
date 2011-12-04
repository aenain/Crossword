/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import crossword.Settings;
import java.io.File;
import javax.swing.*;
/**
 *
 * @author arturhebda
 */
public class SettingsController extends BaseController {
    public SettingsController(Settings settings) {
        super(settings);
    }

    /**
     * @param settings
     * @param components - databaseFilePath, crosswordsDirectoryPath, rows, cols 
     */
    public void saveChanges(JComponent[] components) {
        String databaseFilePath = ((JTextField) components[0]).getText();
        String crosswordsDirectoryPath = ((JTextField) components[1]).getText();

        int rows = ((JSlider) components[2]).getValue();
        int cols = ((JSlider) components[3]).getValue();

        settings.setDatabaseFilePath(databaseFilePath);
        settings.setCrosswordsDirectoryPath(crosswordsDirectoryPath);
        settings.setRows(rows);
        settings.setCols(cols);
    }

    /**
     * @param settings
     * @param components - databaseFilePath, crosswordsDirectoryPath, rows, cols 
     */
    public void discardChanges(JComponent[] components) {
        int rows = settings.getRows();
        int cols = settings.getCols();

        String databaseFilePath = settings.getDatabaseFilePath();
        String crosswordsDirectoryPath = settings.getCrosswordsDirectoryPath();

        ((JTextField) components[0]).setText(databaseFilePath);
        ((JTextField) components[1]).setText(crosswordsDirectoryPath);
        ((JSlider) components[2]).setValue(rows);
        ((JSlider) components[3]).setValue(cols);
    }

    /**
     * @param settings
     * @param components - fileChooser, filePathTextField, parentComponent
     */
    public void chooseFile(JComponent[] components) {
        JFileChooser fileChooser = (JFileChooser) components[0];
        JTextField filePathField = (JTextField) components[1];

        fileChooser.setSelectedFile(new File(filePathField.getText()));
        int clickedOption = fileChooser.showOpenDialog(components[2]);

        if (clickedOption == javax.swing.JFileChooser.APPROVE_OPTION)
            filePathField.setText(fileChooser.getSelectedFile().getAbsolutePath());
    }
}
