/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword;

import java.util.LinkedList;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author arturhebda
 */
public class Reader implements FileFilter {
    private String dirPath;
    private File directory;
    private LinkedList<Crossword> crosswords;

    public Reader(String dirPath) {
        this.dirPath = dirPath;
        directory = new File(this.dirPath);
        crosswords = new LinkedList<Crossword>();
    }

    public void setDirectory(File directory) {
        if (directory != null) {
            dirPath = directory.getAbsolutePath();
            this.directory = directory;
        }
        
        crosswords.clear();
    }

    public Crossword getCrosswordByID(long id) throws IOException, FileNotFoundException, ClassNotFoundException {
        FileInputStream fileStream = null;
        ObjectInputStream objectStream = null;

        try {
            fileStream = new FileInputStream(new File(directory, Long.toString(id)));
            objectStream = new ObjectInputStream(fileStream);

            Crossword crossword = new Crossword(id);
            crossword.readObject(objectStream);

            return crossword;
        } finally {
            if (objectStream != null)
                objectStream.close();

            if (fileStream != null)
                fileStream.close();
        }
    }

    public void getAllCws() throws IOException, FileNotFoundException, ClassNotFoundException {
        crosswords.clear();

        File[] files = directory.listFiles(this);
        FileInputStream fileStream = null;
        ObjectInputStream objectStream = null;
        
        for (File file : files) {
            try {
                fileStream = new FileInputStream(file);
                objectStream = new ObjectInputStream(fileStream);
                long id = Long.parseLong(file.getName().trim());

                Crossword crossword = new Crossword(id);
                crossword.readObject(objectStream);
                crosswords.add(crossword);
            } finally {
                if (objectStream != null)
                    objectStream.close();

                if (fileStream != null)
                    fileStream.close();
            }
        }
    }

    @Override
    public boolean accept(File pathname) {
        Pattern pattern = Pattern.compile("^\\d{13}$");
        Matcher matcher = pattern.matcher(pathname.getName());

        return matcher.find();
    }
}