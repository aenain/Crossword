/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import crossword.BoardCell;
import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellEditor;

/**
 * @inspiration http://www.exampledepot.com/egs/javax.swing.table/CustEdit.html
 * @author arturhebda
 */
public class MyTableCellEditor extends AbstractCellEditor implements TableCellEditor {
    JComponent component = new JTextField();
    LineBorder lineBorder = (LineBorder) LineBorder.createBlackLineBorder();

    BoardCell editedCell = null;

    public MyTableCellEditor() {
        super();
        ((JTextField)component).setDocument(new JTextFieldLimit(1));
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndex, int vColIndex) {
        if (isSelected) {}

        editedCell = (BoardCell)value;
        JTextField fieldComponent = (JTextField)component;

        fieldComponent.setText(editedCell.getWritenContent());
        fieldComponent.setBorder(lineBorder);

        return component;
    }

    @Override
    public Object getCellEditorValue() {
        String writenContent = ((JTextField)component).getText();
        editedCell.setWritenContent(writenContent);

        return editedCell;
    }
}
