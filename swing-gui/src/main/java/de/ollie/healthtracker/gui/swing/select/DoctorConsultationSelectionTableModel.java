package de.ollie.healthtracker.gui.swing.select;

import de.ollie.healthtracker.core.service.model.DoctorConsultation;
import java.util.List;
import javax.swing.table.AbstractTableModel;

class DoctorConsultationSelectionTableModel extends AbstractTableModel {

	private final List<DoctorConsultation> doctorConsultations;
	private final String[] columnNames = { "Date", "Time", "Doctor", "Doctor Type" };

	public DoctorConsultationSelectionTableModel(List<DoctorConsultation> doctorConsultations) {
		this.doctorConsultations = doctorConsultations;
	}

	@Override
	public int getRowCount() {
		return doctorConsultations.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		DoctorConsultation dc = doctorConsultations.get(rowIndex);
		return switch (columnIndex) {
			case 0 -> dc.getDate();
			case 1 -> dc.getTime();
			case 2 -> dc.getDoctor().getName();
			case 3 -> dc.getDoctor().getDoctorType().getName();
			default -> null;
		};
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
}
