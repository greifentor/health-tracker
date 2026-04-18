package de.ollie.healthtracker.gui.swing.select.bloodpressuremeasurement;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.BloodPressureMeasurementService;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.WhoBloodPressureClassification;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.bloodpressuremeasurement.BloodPressureMeasurementEditJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJPanel;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectionTableModel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
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
public class BloodPressureMeasurementSelectJPanel
	extends AbstractSelectJPanel<BloodPressureMeasurement>
	implements SelectionPanelObserver {

	private final BloodPressureMeasurementService bloodPressureMeasurementService;

	public BloodPressureMeasurementSelectJPanel(
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
			? bloodPressureMeasurementService.listBloodPressureMeasurements().stream().toList()
			: List.of();
	}

	@Override
	protected AbstractSelectionTableModel<BloodPressureMeasurement> createSelectionModel() {
		return new AbstractSelectionTableModel<BloodPressureMeasurement>(
			getObjectsToSelect(),
			"Date Of Recording",
			"Time Of Recording",
			"Sys Mm Hg",
			"Dia Mm Hg",
			"Pulse Per Minute",
			"Status",
			"Irregular Heartbeat",
			"Comment"
		) {
			@Override
			protected Object getColumnValueFor(BloodPressureMeasurement t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> DateTimeUtil.DE_DATE_FORMAT.format(t.getDateOfRecording());
					case 1 -> DateTimeUtil.DE_TIME_FORMAT.format(t.getTimeOfRecording());
					case 2 -> t.getSysMmHg();
					case 3 -> t.getDiaMmHg();
					case 4 -> t.getPulsePerMinute();
					case 5 -> t.getStatus();
					case 6 -> t.isIrregularHeartbeat();
					case 7 -> t.getComment();
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(BloodPressureMeasurement selected) {
		new BloodPressureMeasurementEditJInternalFrame(selected, getEditDialogComponentFactory(), this, getDesktopPane());
	}

	@Override
	protected BloodPressureMeasurement createNewObject() {
		return new BloodPressureMeasurement()
			.setId(UUID.randomUUID())
			.setComment("")
			.setDateOfRecording(LocalDate.now())
			.setDiaMmHg(80)
			.setPulsePerMinute(60)
			.setSysMmHg(130)
			.setTimeOfRecording(LocalTime.now())
			.setStatus(WhoBloodPressureClassification.HIGH_NORMAL)
			.setIrregularHeartbeat(false);
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
