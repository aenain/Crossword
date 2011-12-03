/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package crossword.dictionary;
import java.io.Serializable;
/**
 *
 * @author arturhebda
 */
public class Entry implements Serializable {
    private String word;
    private String clue;

    public Entry(String word, String clue) {
        this.word = word;
        this.clue = clue;
    }
    
    public String getWord() { return word; }
    
    public String getClue() { return clue; }
}
