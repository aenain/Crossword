/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword;

import java.io.File;

/**
 *
 * @author arturhebda
 */
public class Settings {
    private File crosswordsDirectory;
    private int rows, cols;
    private File databaseFile;

    public Settings() {
        crosswordsDirectory = null;
        rows = 0;
        cols = 0;
        databaseFile = null;
    }

    public String getCrosswordsDirectoryPath() { 
        if (crosswordsDirectory != null)
            return crosswordsDirectory.getAbsolutePath();
        else
            return "";
    }

    public File getCrosswordsDirectory() { return crosswordsDirectory; }

    public int getRows() { return rows; }
    public int getCols() { return cols; }

    public String getDatabaseFilePath() { 
        if (databaseFile != null)
            return databaseFile.getAbsolutePath();
        else
            return "";
    }

    public File getDatabaseFile() { return databaseFile; }

    public void setCrosswordsDirectoryPath(String crosswordsDirectoryPath) {
        this.crosswordsDirectory = new File(crosswordsDirectoryPath);
    }

    public void setRows(int rows) { this.rows = rows; }
    public void setCols(int cols) { this.cols = cols; }

    public void setDatabaseFilePath(String databaseFilePath) {
        this.databaseFile = new File(databaseFilePath);
    }
}
