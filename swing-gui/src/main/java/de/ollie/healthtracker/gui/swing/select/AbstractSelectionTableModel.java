package de.ollie.healthtracker.gui.swing.select;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import lombok.AccessLevel;
import lombok.Getter;

public abstract class AbstractSelectionTableModel<T> extends AbstractTableModel {

	@Getter(AccessLevel.PROTECTED)
	private final List<T> objectsToSelect;

	private final String[] columnNames;

	public AbstractSelectionTableModel(List<T> objectsToSelect, String... columnNames) {
		this.objectsToSelect = objectsToSelect;
		this.columnNames = columnNames;
	}

	@Override
	public int getRowCount() {
		return objectsToSelect.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return getColumnValueFor(objectsToSelect.get(rowIndex), columnIndex);
	}

	protected abstract Object getColumnValueFor(T t, int columnIndex);

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
}
