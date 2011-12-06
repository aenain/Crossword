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
    private Class strategyClass;

    public static final int MIN_ROWS = 4;
    public static final int MIN_COLS = 5;

    public static final int MAX_ROWS = 22;
    public static final int MAX_COLS = 25;

    public static final int DEFAULT_ROWS = 20;
    public static final int DEFAULT_COLS = 20;

    public Settings() {
        crosswordsDirectory = new File("generated_crosswords");
        databaseFile = new File("cwdb.txt");

        rows = Settings.DEFAULT_ROWS;
        cols = Settings.DEFAULT_COLS;

        strategyClass = RealStrategy.class;
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

    public void setCrosswordsSimpleStrategy(boolean isSelected) {
        if (isSelected)
            setCrosswordsStrategyClass(SimpleStrategy.class);
        else
            setCrosswordsStrategyClass(RealStrategy.class);
    }

    public void setCrosswordsStrategyClass(Class strategyClass) {
        this.strategyClass = strategyClass;
    }

    public void setCrosswordsStrategyClassByName(String strategyClassName) throws ClassNotFoundException {
        this.strategyClass = Class.forName(strategyClassName);
    }

    public boolean isStrategyByName(String strategyName) {
        return (getCrosswordsStrategyClassName().equals(strategyName));
    }

    public String getCrosswordsStrategyClassName() { return strategyClass.getSimpleName(); }

    public Class getCrosswordsStrategyClass() { return strategyClass; }

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
