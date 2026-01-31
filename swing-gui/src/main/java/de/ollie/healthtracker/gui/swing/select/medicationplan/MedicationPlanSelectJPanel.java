package de.ollie.healthtracker.gui.swing.select.medicationplan;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.MedicationPlanService;
import de.ollie.healthtracker.core.service.MedicationService;
import de.ollie.healthtracker.core.service.MedicationUnitService;
import de.ollie.healthtracker.core.service.model.MedicationPlan;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.medicationplan.MedicationPlanEditJInternalFrame;
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
public class MedicationPlanSelectJPanel extends AbstractSelectJPanel<MedicationPlan> implements SelectionPanelObserver {

	private final MedicationPlanService medicationPlanService;
	private final MedicationService medicationService;
	private final MedicationUnitService medicationUnitService;

	public MedicationPlanSelectJPanel(
		MedicationPlanService medicationPlanService,
		MedicationService medicationService,
		MedicationUnitService medicationUnitService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className + "s", editDialogComponentFactory, observer);
		this.medicationPlanService = medicationPlanService;
		this.medicationService = medicationService;
		this.medicationUnitService = medicationUnitService;
		updateTableSelection();
	}

	@Override
	protected List<MedicationPlan> getObjectsToSelect() {
		return medicationPlanService != null ? medicationPlanService.listMedicationPlans().stream().toList() : List.of();
	}

	@Override
	protected AbstractSelectionTableModel<MedicationPlan> createSelectionModel() {
		return new AbstractSelectionTableModel<MedicationPlan>(
			getObjectsToSelect(),
			"Start Date",
			"End Date",
			"Next Date Of Intake",
			"Time Of Intake",
			"Medication",
			"Medication Unit",
			"Unit Count",
			"Self Medication"
		) {
			@Override
			protected Object getColumnValueFor(MedicationPlan t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> DateTimeUtil.DE_DATE_FORMAT.format(t.getStartDate());
					case 1 -> DateTimeUtil.DE_DATE_FORMAT.format(t.getEndDate());
					case 2 -> DateTimeUtil.DE_DATE_FORMAT.format(t.getNextDateOfIntake());
					case 3 -> DateTimeUtil.DE_TIME_FORMAT.format(t.getTimeOfIntake());
					case 4 -> (t.getMedication() != null ? t.getMedication().getName() : "-");
					case 5 -> (t.getMedicationUnit() != null ? t.getMedicationUnit().getName() : "-");
					case 6 -> t.getUnitCount();
					case 7 -> t.isSelfMedication();
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(MedicationPlan selected) {
		new MedicationPlanEditJInternalFrame(
			selected,
			() -> medicationService.listMedications(),
			() -> medicationUnitService.listMedicationUnits(),
			getEditDialogComponentFactory(),
			this,
			getDesktopPane()
		);
	}

	@Override
	protected MedicationPlan createNewObject() {
		return new MedicationPlan()
			.setId(UUID.randomUUID())
			.setEndDate(LocalDate.now())
			.setMedication(null)
			.setMedicationUnit(null)
			.setNextDateOfIntake(LocalDate.now())
			.setSelfMedication(false)
			.setStartDate(LocalDate.now())
			.setTimeOfIntake(LocalTime.now())
			.setUnitCount(new BigDecimal(0));
	}

	@Override
	protected void delete(MedicationPlan toDelete) {
		medicationPlanService.deleteMedicationPlan(toDelete.getId());
	}

	@Override
	protected void save(MedicationPlan toSave) {
		medicationPlanService.updateMedicationPlan(toSave);
	}
}
