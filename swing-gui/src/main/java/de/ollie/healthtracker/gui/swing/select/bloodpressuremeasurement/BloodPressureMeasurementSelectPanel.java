package de.ollie.healthtracker.gui.swing.select.bloodpressuremeasurement;

import de.ollie.healthtracker.core.service.BloodPressureMeasurementService;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.bloodpressuremeasurement.BloodPressureMeasurementEditInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectPanel;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectionTableModel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.swing.JDesktopPane;

public class BloodPressureMeasurementSelectPanel
	extends AbstractSelectPanel<BloodPressureMeasurement>
	implements SelectionPanelObserver {

	private final BloodPressureMeasurementService bloodPressureMeasurementService;

	public BloodPressureMeasurementSelectPanel(
		BloodPressureMeasurementService bloodPressureMeasurementService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className + "s", editDialogComponentFactory, observer);
		this.bloodPressureMeasurementService = bloodPressureMeasurementService;
		updateTableSelection();
	}

	@Override
	protected List<BloodPressureMeasurement> getObjectsToSelect() {
		return bloodPressureMeasurementService != null
			? bloodPressureMeasurementService
				.listBloodPressureMeasurements()
				.stream()
				.sorted((bpm0, bpm1) -> compare(bpm0, bpm1))
				.toList()
			: List.of();
	}

	private int compare(BloodPressureMeasurement bpm0, BloodPressureMeasurement bpm1) {
		int r = bpm1.getDateOfRecording().compareTo(bpm0.getDateOfRecording());
		if (r == 0) {
			r = bpm1.getTimeOfRecording().compareTo(bpm0.getTimeOfRecording());
		}
		return r;
	}

	@Override
	protected AbstractSelectionTableModel<BloodPressureMeasurement> createSelectionModel() {
		return new AbstractSelectionTableModel<BloodPressureMeasurement>(
			getObjectsToSelect(),
			"Date",
			"Time",
			"SYS mmHg",
			"DIA mmHg",
			"PP",
			"IHB",
			"Status"
		) {
			@Override
			protected Object getColumnValueFor(BloodPressureMeasurement bpm, int columnIndex) {
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
		};
	}

	@Override
	protected void createEditInternalFrame(BloodPressureMeasurement selected) {
		new BloodPressureMeasurementEditInternalFrame(
			selected,
			getClassName(),
			getEditDialogComponentFactory(),
			this,
			getDesktopPane()
		);
	}

	@Override
	protected BloodPressureMeasurement createNewObject() {
		return bloodPressureMeasurementService.createBloodPressureMeasurement(
			LocalDate.now(),
			80,
			60,
			130,
			LocalTime.now(),
			BloodPressureMeasurementStatus.YELLOW,
			false
		);
	}

	@Override
	protected void delete(BloodPressureMeasurement toDelete) {
		bloodPressureMeasurementService.deleteBloodPressureMeasurement(toDelete.getId());
	}

	@Override
	protected void save(BloodPressureMeasurement toSave) {
		bloodPressureMeasurementService.updateBloodPressureMeasurement(toSave);
	}
}
