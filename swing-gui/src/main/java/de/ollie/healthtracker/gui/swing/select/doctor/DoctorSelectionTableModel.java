package de.ollie.healthtracker.gui.swing.select.doctor;

import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectionTableModel;
import java.util.List;

class DoctorSelectionTableModel extends AbstractSelectionTableModel<Doctor> {

	public DoctorSelectionTableModel(List<Doctor> doctors) {
		super(doctors, "Name", "Doctor Type");
	}

	@Override
	protected Object getColumnValueFor(Doctor t, int columnIndex) {
		return switch (columnIndex) {
			case 0 -> t.getName();
			case 1 -> t.getDoctorType().getName();
			default -> null;
		};
	}
}
