package de.ollie.healthtracker.gui.swing.select.pointofmeasurement;

import de.ollie.healthtracker.core.service.PointOfMeasurementService;
import de.ollie.healthtracker.core.service.model.PointOfMeasurement;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.pointofmeasurement.PointOfMeasurementEditJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJPanel;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectionTableModel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import java.math.BigDecimal;
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
public class PointOfMeasurementSelectJPanel
	extends AbstractSelectJPanel<PointOfMeasurement>
	implements SelectionPanelObserver {

	private final PointOfMeasurementService pointOfMeasurementService;

	public PointOfMeasurementSelectJPanel(
		PointOfMeasurementService pointOfMeasurementService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className + "s", editDialogComponentFactory, observer);
		this.pointOfMeasurementService = pointOfMeasurementService;
		updateTableSelection();
	}

	@Override
	protected List<PointOfMeasurement> getObjectsToSelect() {
		return pointOfMeasurementService != null
			? pointOfMeasurementService.listPointOfMeasurements().stream().toList()
			: List.of();
	}

	@Override
	protected AbstractSelectionTableModel<PointOfMeasurement> createSelectionModel() {
		return new AbstractSelectionTableModel<PointOfMeasurement>(
			getObjectsToSelect(),
			"Name",
			"Regular Max Celsius",
			"Regular Min Celsius"
		) {
			@Override
			protected Object getColumnValueFor(PointOfMeasurement t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> t.getName();
					case 1 -> t.getRegularMaxCelsius() == null
						? null
						: t.getRegularMaxCelsius().setScale(1, java.math.RoundingMode.HALF_UP);
					case 2 -> t.getRegularMinCelsius() == null
						? null
						: t.getRegularMinCelsius().setScale(1, java.math.RoundingMode.HALF_UP);
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(PointOfMeasurement selected) {
		new PointOfMeasurementEditJInternalFrame(selected, getEditDialogComponentFactory(), this, getDesktopPane());
	}

	@Override
	protected PointOfMeasurement createNewObject() {
		return new PointOfMeasurement()
			.setId(UUID.randomUUID())
			.setName("")
			.setRegularMaxCelsius(new BigDecimal(37.0))
			.setRegularMinCelsius(new BigDecimal(37.0));
	}

	@Override
	protected void delete(PointOfMeasurement toDelete) {
		pointOfMeasurementService.deletePointOfMeasurement(toDelete.getId());
	}

	@Override
	protected void save(PointOfMeasurement toSave) {
		pointOfMeasurementService.updatePointOfMeasurement(toSave);
	}
}
