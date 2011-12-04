/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import crossword.BoardCell;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;

/**
 * @inspiration http://www.exampledepot.com/egs/javax.swing.table/CustRend.html
 * @author arturhebda
 */
public class MyTableCellRenderer extends JLabel implements TableCellRenderer {
    private EmptyBorder emptyBorder = new EmptyBorder(0, 0, 0, 0);
    private LineBorder lineBorder = (LineBorder) LineBorder.createBlackLineBorder();
    private BoardCell cell = null;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
        if (isSelected) {}
        if (hasFocus) {}

        cell = (BoardCell)value;

        if (! cell.getContent().isEmpty())
            markCellAsWord(cell.getWritenContent());
        else
            markCellAsEmpty();

        return this;
    }

    private void markCellAsWord(String content) {
        setBorder(lineBorder);
        setText(content);
    }

    private void markCellAsEmpty() {
        setBorder(emptyBorder);
        setText("");
    }
}
