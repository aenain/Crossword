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

        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);

        if (! cell.getContent().isEmpty())
            markAsContent(cell.getWritenContent());
        else
            markAsEmpty();

        if (cell.getNearbyHorizStartID() > 0 || cell.getNearbyVertStartID() > 0)
            setStartMarkers(cell);
            
        return this;
    }

    private void markAsContent(String content) {
        setBorder(lineBorder);
        setText(content);
    }

    private void markAsEmpty() {
        setBorder(emptyBorder);
        setText("");
    }

    private void setStartMarkers(BoardCell cell) {
        int horizStartID = cell.getNearbyHorizStartID();
        int vertStartID = cell.getNearbyVertStartID();

        StringBuilder builder = new StringBuilder();
        builder.append("<html><span style='font-size: xx-small;'>");

        if (horizStartID > 0 && vertStartID > 0)
            builder.append(vertStartID).append(",").append(horizStartID);
        else if (horizStartID > 0)
            builder.append(horizStartID);
        else if (vertStartID > 0)
            builder.append(vertStartID);

        builder.append("</span></html>");
        setText(builder.toString());
    }
}
