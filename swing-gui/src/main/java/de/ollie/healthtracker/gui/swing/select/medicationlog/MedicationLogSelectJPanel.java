package de.ollie.healthtracker.gui.swing.select.medicationlog;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.MedicationLogService;
import de.ollie.healthtracker.core.service.MedicationService;
import de.ollie.healthtracker.core.service.MedicationUnitService;
import de.ollie.healthtracker.core.service.model.MedicationLog;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.medicationlog.MedicationLogEditJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJPanel;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectionTableModel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import javax.swing.JDesktopPane;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public class MedicationLogSelectJPanel extends AbstractSelectJPanel<MedicationLog> implements SelectionPanelObserver {

	private final MedicationLogService medicationLogService;
	private final MedicationService medicationService;
	private final MedicationUnitService medicationUnitService;

	public MedicationLogSelectJPanel(
		MedicationLogService medicationLogService,
		MedicationService medicationService,
		MedicationUnitService medicationUnitService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className + "s", editDialogComponentFactory, observer);
		this.medicationLogService = medicationLogService;
		this.medicationService = medicationService;
		this.medicationUnitService = medicationUnitService;
		updateTableSelection();
	}

	@Override
	protected List<MedicationLog> getObjectsToSelect() {
		return medicationLogService != null ? medicationLogService.listMedicationLogs().stream().toList() : List.of();
	}

	@Override
	protected AbstractSelectionTableModel<MedicationLog> createSelectionModel() {
		return new AbstractSelectionTableModel<MedicationLog>(
			getObjectsToSelect(),
			"Date Of Intake",
			"Time Of Intake",
			"Medication",
			"Medication Unit",
			"Self Medication",
			"Confirmed",
			"Comment"
		) {
			@Override
			protected Object getColumnValueFor(MedicationLog t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> DateTimeUtil.DE_DATE_FORMAT.format(t.getDateOfIntake());
					case 1 -> DateTimeUtil.DE_TIME_FORMAT.format(t.getTimeOfIntake());
					case 2 -> (t.getMedication() != null ? t.getMedication().getName() : "-");
					case 3 -> (t.getMedicationUnit() != null ? t.getMedicationUnit().getName() : "-");
					case 4 -> t.isSelfMedication();
					case 5 -> t.isConfirmed();
					case 6 -> t.getComment();
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(MedicationLog selected) {
		new MedicationLogEditJInternalFrame(
			selected,
			() -> medicationService.listMedications(),
			() -> medicationUnitService.listMedicationUnits(),
			getEditDialogComponentFactory(),
			this,
			getDesktopPane()
		);
	}

	@Override
	protected MedicationLog createNewObject() {
		return new MedicationLog()
			.setId(UUID.randomUUID())
			.setComment("")
			.setConfirmed(true)
			.setMedication(null)
			.setMedicationUnit(null)
			.setDateOfIntake(LocalDate.now())
			.setSelfMedication(false)
			.setTimeOfIntake(LocalTime.now())
			.setUnitCount(null);
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
