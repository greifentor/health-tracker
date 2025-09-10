package de.ollie.healthtracker.gui.swing.select.doctorconsultation;

import de.ollie.healthtracker.core.service.DoctorConsultationService;
import de.ollie.healthtracker.core.service.DoctorService;
import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorConsultation;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.doctorconsultation.DoctorConsultationEditJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJPanel;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectionTableModel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.swing.JDesktopPane;

public class DoctorConsultationSelectJPanel
	extends AbstractSelectJPanel<DoctorConsultation>
	implements SelectionPanelObserver {

	private DoctorService doctorService;
	private DoctorConsultationService doctorConsultationService;

	public DoctorConsultationSelectJPanel(
		DoctorConsultationService doctorConsultationService,
		DoctorService doctorService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, "Doctor Consultation", editDialogComponentFactory, observer);
		this.doctorService = doctorService;
		this.doctorConsultationService = doctorConsultationService;
		updateTableSelection();
	}

	@Override
	protected List<DoctorConsultation> getObjectsToSelect() {
		return doctorConsultationService != null
			? doctorConsultationService.listDoctorConsultations().stream().sorted((dc0, dc1) -> compare(dc0, dc1)).toList()
			: List.of();
	}

	private int compare(DoctorConsultation dc0, DoctorConsultation dc1) {
		int r = dc0.getDate().compareTo(dc1.getDate());
		if (r == 0) {
			r = dc0.getTime().compareTo(dc1.getTime());
			if (r == 0) {
				r = dc0.getDoctor().getName().compareTo(dc1.getDoctor().getName());
			}
		}
		return r;
	}

	@Override
	protected AbstractSelectionTableModel<DoctorConsultation> createSelectionModel() {
		return new AbstractSelectionTableModel<>(getObjectsToSelect(), "Date", "Time", "Doctor", "Doctor Type") {
			@Override
			protected Object getColumnValueFor(DoctorConsultation t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> t.getDate();
					case 1 -> t.getTime();
					case 2 -> t.getDoctor().getName();
					case 3 -> t.getDoctor().getDoctorType().getName();
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(DoctorConsultation selected) {
		new DoctorConsultationEditJInternalFrame(
			selected,
			() -> doctorService.listDoctors().stream().sorted((d0, d1) -> d0.getName().compareTo(d1.getName())).toList(),
			getEditDialogComponentFactory(),
			this,
			getDesktopPane()
		);
	}

	@Override
	protected DoctorConsultation createNewObject() {
		List<Doctor> doctors = doctorService
			.listDoctors()
			.stream()
			.sorted((d0, d1) -> d0.getName().compareTo(d1.getName()))
			.toList();
		return doctorConsultationService.createDoctorConsultation(
			LocalDate.now(),
			LocalTime.now(),
			doctors.get(0),
			"-",
			"-"
		);
	}

	@Override
	protected void delete(DoctorConsultation toDelete) {
		doctorConsultationService.deleteDoctorConsultation(toDelete.getId());
	}

	@Override
	protected void save(DoctorConsultation toSave) {
		doctorConsultationService.updateDoctorConsultation(toSave);
	}
}
