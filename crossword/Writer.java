/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
/**
 *
 * @author arturhebda
 */
public class Writer {
    private String dirPath;

    public Writer(String dirPath) {
        this.dirPath = dirPath;
    }

    public void setDirectory(File directory) {
        if (directory != null)
            this.dirPath = directory.getAbsolutePath();
    }

    public void write(Crossword crossword) throws IOException, FileNotFoundException {
        long id = getUniqueID();
        FileOutputStream fileStream = null;
        ObjectOutputStream objectStream = null;

        try {
            fileStream = new FileOutputStream(dirPath + id);
            objectStream = new ObjectOutputStream(fileStream);

            crossword.writeObject(objectStream);
        } finally {
            if (objectStream != null)
                objectStream.close();

            if (fileStream != null)
                fileStream.close();
        }
    }

    private long getUniqueID() {
        Date date = new Date();
        return date.getTime(); // in miliseconds
    }
}
