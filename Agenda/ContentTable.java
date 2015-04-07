package Agenda;
import javax.swing.table.AbstractTableModel;

/**
 * This class is used to make a temporary abstracttableModel with selected data so later it can be passed to the JTable so it refreshes.
 * @author Wesley
 * @version 1.0
 */
public class ContentTable extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private Object[][] data;
	private String[] columnNames;
	
	
	public ContentTable(Object[][] data,String[] columnNames) {
		this.data = data;
		this.columnNames = columnNames;
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
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}
	
	@Override
	public String getColumnName(int col) {
        return columnNames[col];
    }	
}
