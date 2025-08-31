package de.ollie.healthtracker.gui.swing.select.doctor;

import de.ollie.healthtracker.core.service.DoctorService;
import de.ollie.healthtracker.core.service.DoctorTypeService;
import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorType;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.doctor.DoctorEditInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectPanel;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectionTableModel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import java.util.List;
import javax.swing.JDesktopPane;

public class DoctorSelectPanel extends AbstractSelectPanel<Doctor> implements SelectionPanelObserver {

	private final DoctorService doctorService;
	private final DoctorTypeService doctorTypeService;

	public DoctorSelectPanel(
		DoctorService doctorService,
		DoctorTypeService doctorTypeService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className, editDialogComponentFactory, observer);
		this.doctorService = doctorService;
		this.doctorTypeService = doctorTypeService;
		updateTableSelection();
	}

	@Override
	protected List<Doctor> getObjectsToSelect() {
		return doctorService != null
			? doctorService.listDoctors().stream().sorted((d0, d1) -> d0.getName().compareTo(d1.getName())).toList()
			: List.of();
	}

	@Override
	protected AbstractSelectionTableModel<Doctor> createSelectionModel() {
		return new AbstractSelectionTableModel<Doctor>(getObjectsToSelect(), "Name", "Doctor Type") {
			@Override
			protected Object getColumnValueFor(Doctor t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> t.getName();
					case 1 -> t.getDoctorType().getName();
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(Doctor selected) {
		new DoctorEditInternalFrame(
			selected,
			() ->
				doctorTypeService.listDoctorTypes().stream().sorted((d0, d1) -> d0.getName().compareTo(d1.getName())).toList(),
			getEditDialogComponentFactory(),
			this,
			getDesktopPane()
		);
	}

	@Override
	protected Doctor createNewObject() {
		List<DoctorType> doctorTypes = doctorTypeService
			.listDoctorTypes()
			.stream()
			.sorted((d0, d1) -> d0.getName().compareTo(d1.getName()))
			.toList();
		return doctorService.createDoctor("?", doctorTypes.get(0));
	}

	@Override
	protected void delete(Doctor toDelete) {
		doctorService.deleteDoctor(toDelete.getId());
	}

	@Override
	protected void save(Doctor toSave) {
		doctorService.updateDoctor(toSave);
	}
}
