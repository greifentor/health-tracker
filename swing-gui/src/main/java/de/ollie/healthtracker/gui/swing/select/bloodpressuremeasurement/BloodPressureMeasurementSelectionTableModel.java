package de.ollie.healthtracker.gui.swing.select.bloodpressuremeasurement;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import java.util.List;
import javax.swing.table.AbstractTableModel;

class BloodPressureMeasurementSelectionTableModel extends AbstractTableModel {

	private final List<BloodPressureMeasurement> bloodPressureMeasurements;
	private final String[] columnNames = { "Date", "Time", "SYS mmHg", "DIA mmHg", "PP", "IHB", "Status" };

	public BloodPressureMeasurementSelectionTableModel(List<BloodPressureMeasurement> bloodPressureMeasurements) {
		this.bloodPressureMeasurements = bloodPressureMeasurements;
	}

	@Override
	public int getRowCount() {
		return bloodPressureMeasurements.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		BloodPressureMeasurement bpm = bloodPressureMeasurements.get(rowIndex);
		return switch (columnIndex) {
			case 0 -> bpm.getDateOfRecording();
			case 1 -> bpm.getTimeOfRecording();
			case 2 -> bpm.getSysMmHg();
			case 3 -> bpm.getDiaMmHg();
			case 4 -> bpm.getPulsePerMinute();
			case 5 -> bpm.isIrregularHeartbeat() ? "Y" : "N";
			case 6 -> bpm.getStatus() == null ? "-" : bpm.getStatus().name();
			default -> null;
		};
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
}
