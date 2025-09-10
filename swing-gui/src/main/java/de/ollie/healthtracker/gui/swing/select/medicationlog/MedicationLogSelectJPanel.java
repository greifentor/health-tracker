package de.ollie.healthtracker.gui.swing.select.medicationlog;

import de.ollie.healthtracker.core.service.MedicationLogService;
import de.ollie.healthtracker.core.service.MedicationService;
import de.ollie.healthtracker.core.service.MedicationUnitService;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationLog;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.medicationlog.MedicationLogEditJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJPanel;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectionTableModel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.swing.JDesktopPane;

public class MedicationLogSelectJPanel extends AbstractSelectJPanel<MedicationLog> implements SelectionPanelObserver {

	private MedicationService medicationService;
	private MedicationLogService medicationLogService;
	private MedicationUnitService medicationUnitService;

	public MedicationLogSelectJPanel(
		MedicationService medicationService,
		MedicationLogService medicationLogService,
		MedicationUnitService medicationUnitService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className, editDialogComponentFactory, observer);
		this.medicationService = medicationService;
		this.medicationLogService = medicationLogService;
		this.medicationUnitService = medicationUnitService;
		updateTableSelection();
	}

	@Override
	protected List<MedicationLog> getObjectsToSelect() {
		return medicationLogService != null
			? medicationLogService.listMedicationLogs().stream().sorted((ml0, ml1) -> compare(ml0, ml1)).toList()
			: List.of();
	}

	private int compare(MedicationLog ml0, MedicationLog ml1) {
		int r = ml1.getDateOfIntake().compareTo(ml0.getDateOfIntake());
		if (r == 0) {
			r = ml1.getTimeOfIntake().compareTo(ml0.getTimeOfIntake());
			if (r == 0) {
				r = ml0.getMedication().getName().compareTo(ml1.getMedication().getName());
			}
		}
		return r;
	}

	@Override
	protected AbstractSelectionTableModel<MedicationLog> createSelectionModel() {
		return new AbstractSelectionTableModel<>(getObjectsToSelect(), "Date", "Time", "Medication", "Unit", "Count") {
			@Override
			protected Object getColumnValueFor(MedicationLog t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> t.getDateOfIntake();
					case 1 -> t.getTimeOfIntake();
					case 2 -> t.getMedication().getName();
					case 3 -> t.getMedicationUnit().getName();
					case 4 -> t.getUnitCount();
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(MedicationLog selected) {
		new MedicationLogEditJInternalFrame(
			selected,
			() ->
				medicationService.listMedications().stream().sorted((m0, m1) -> m0.getName().compareTo(m1.getName())).toList(),
			() ->
				medicationUnitService
					.listMedicationUnits()
					.stream()
					.sorted((m0, m1) -> m0.getName().compareTo(m1.getName()))
					.toList(),
			getEditDialogComponentFactory(),
			this,
			getDesktopPane()
		);
	}

	@Override
	protected MedicationLog createNewObject() {
		List<Medication> medications = medicationService
			.listMedications()
			.stream()
			.sorted((d0, d1) -> d0.getName().compareTo(d1.getName()))
			.toList();
		List<MedicationUnit> medicationUnits = medicationUnitService
			.listMedicationUnits()
			.stream()
			.sorted((d0, d1) -> d0.getName().compareTo(d1.getName()))
			.toList();
		return medicationLogService.createMedicationLog(
			medications.get(0),
			medicationUnits.get(0),
			LocalDate.now(),
			LocalTime.now(),
			new BigDecimal(1)
		);
	}

	@Override
	protected void delete(MedicationLog toDelete) {
		medicationLogService.deleteMedicationLog(toDelete.getId());
	}

	@Override
	protected void save(MedicationLog toSave) {
		medicationLogService.updateMedicationLog(toSave);
	}
}
