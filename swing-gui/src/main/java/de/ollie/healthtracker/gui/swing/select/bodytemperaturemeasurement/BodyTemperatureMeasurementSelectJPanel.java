package de.ollie.healthtracker.gui.swing.select.bodytemperaturemeasurement;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.BodyTemperatureMeasurementService;
import de.ollie.healthtracker.core.service.PointOfMeasurementService;
import de.ollie.healthtracker.core.service.model.BodyTemperatureMeasurement;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.bodytemperaturemeasurement.BodyTemperatureMeasurementEditJInternalFrame;
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
public class BodyTemperatureMeasurementSelectJPanel
	extends AbstractSelectJPanel<BodyTemperatureMeasurement>
	implements SelectionPanelObserver {

	private final BodyTemperatureMeasurementService bodyTemperatureMeasurementService;
	private final PointOfMeasurementService pointOfMeasurementService;

	public BodyTemperatureMeasurementSelectJPanel(
		BodyTemperatureMeasurementService bodyTemperatureMeasurementService,
		PointOfMeasurementService pointOfMeasurementService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className + "s", editDialogComponentFactory, observer);
		this.bodyTemperatureMeasurementService = bodyTemperatureMeasurementService;
		this.pointOfMeasurementService = pointOfMeasurementService;
		updateTableSelection();
	}

	@Override
	protected List<BodyTemperatureMeasurement> getObjectsToSelect() {
		return bodyTemperatureMeasurementService != null
			? bodyTemperatureMeasurementService.listBodyTemperatureMeasurements().stream().toList()
			: List.of();
	}

	@Override
	protected AbstractSelectionTableModel<BodyTemperatureMeasurement> createSelectionModel() {
		return new AbstractSelectionTableModel<BodyTemperatureMeasurement>(
			getObjectsToSelect(),
			"Date Of Recording",
			"Time Of Recording",
			"Celsius",
			"Point Of Measurement",
			"Comment"
		) {
			@Override
			protected Object getColumnValueFor(BodyTemperatureMeasurement t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> DateTimeUtil.DE_DATE_FORMAT.format(t.getDateOfRecording());
					case 1 -> DateTimeUtil.DE_TIME_FORMAT.format(t.getTimeOfRecording());
					case 2 -> t.getCelsius() == null ? null : t.getCelsius().setScale(1, java.math.RoundingMode.HALF_UP);
					case 3 -> (t.getPointOfMeasurement() != null ? t.getPointOfMeasurement().getName() : "-");
					case 4 -> t.getComment();
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(BodyTemperatureMeasurement selected) {
		new BodyTemperatureMeasurementEditJInternalFrame(
			selected,
			() -> pointOfMeasurementService.listPointOfMeasurements(),
			getEditDialogComponentFactory(),
			this,
			getDesktopPane()
		);
	}

	@Override
	protected BodyTemperatureMeasurement createNewObject() {
		return new BodyTemperatureMeasurement()
			.setId(UUID.randomUUID())
			.setComment("")
			.setDateOfRecording(LocalDate.now())
			.setCelsius(new BigDecimal(37.0))
			.setTimeOfRecording(LocalTime.now())
			.setPointOfMeasurement(null);
	}

	@Override
	protected void delete(BodyTemperatureMeasurement toDelete) {
		bodyTemperatureMeasurementService.deleteBodyTemperatureMeasurement(toDelete.getId());
	}

	@Override
	protected void save(BodyTemperatureMeasurement toSave) {
		bodyTemperatureMeasurementService.updateBodyTemperatureMeasurement(toSave);
	}
}
