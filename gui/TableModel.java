/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import crossword.Board;
import crossword.BoardCell;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author arturhebda
 */
public class TableModel extends AbstractTableModel {
    private String[] columnNames;
    private Object[][] data;

    public TableModel() {
        columnNames = new String[1];
        data = new Object[1][1];
    }

    public void updateBoard(Board board) {
        data = board.getBoard();
        columnNames = new String[board.getCols()];

        fireTableDataChanged();
        fireTableStructureChanged();
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        return BoardCell.class;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        BoardCell cell = (BoardCell) data[row][col];
        return (! cell.getContent().isEmpty());
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    @Override
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}