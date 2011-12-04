/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
/**
 *
 * @author arturhebda
 */
public class CwBrowser {
    private Writer writer;
    private Reader reader;
    private File directory;

    public CwBrowser(String dirPath) {
        directory = new File(dirPath);

        writer = new Writer(dirPath);
        reader = new Reader(dirPath);
    }

    public void setDirectory(File directory) {
        this.directory = directory;
        writer.setDirectory(directory);
        reader.setDirectory(directory);
    }

    public Crossword getCrosswordByID(long id) throws IOException, FileNotFoundException, ClassNotFoundException {
        return reader.getCrosswordByID(id);
    }

    public String getDirectoryPath() { 
        if (directory != null)
            return directory.getAbsolutePath();
        else
            return ""; // TODO! wyjątek?
    }

    public LinkedList<Long> getAllIDs() {
        LinkedList<Long> ids = new LinkedList<Long>();

        if (directory != null) {
            File[] files = directory.listFiles(reader);

            for (File file : files)
                ids.add(Long.parseLong(file.getName().trim()));
        }

        // TODO! może tu też rzucić wyjątkiem?
        return ids;
    }
}
