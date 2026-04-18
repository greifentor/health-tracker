package de.ollie.healthtracker.gui.swing.select.weightmeasurement;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.WeightMeasurementService;
import de.ollie.healthtracker.core.service.model.WeightMeasurement;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.weightmeasurement.WeightMeasurementEditJInternalFrame;
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
public class WeightMeasurementSelectJPanel
	extends AbstractSelectJPanel<WeightMeasurement>
	implements SelectionPanelObserver {

	private final WeightMeasurementService weightMeasurementService;

	public WeightMeasurementSelectJPanel(
		WeightMeasurementService weightMeasurementService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className + "s", editDialogComponentFactory, observer);
		this.weightMeasurementService = weightMeasurementService;
		updateTableSelection();
	}

	@Override
	protected List<WeightMeasurement> getObjectsToSelect() {
		return weightMeasurementService != null
			? weightMeasurementService.listWeightMeasurements().stream().toList()
			: List.of();
	}

	@Override
	protected AbstractSelectionTableModel<WeightMeasurement> createSelectionModel() {
		return new AbstractSelectionTableModel<WeightMeasurement>(
			getObjectsToSelect(),
			"Date Of Recording",
			"Time Of Recording",
			"Kg",
			"Comment"
		) {
			@Override
			protected Object getColumnValueFor(WeightMeasurement t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> DateTimeUtil.DE_DATE_FORMAT.format(t.getDateOfRecording());
					case 1 -> DateTimeUtil.DE_TIME_FORMAT.format(t.getTimeOfRecording());
					case 2 -> t.getKg();
					case 3 -> t.getComment();
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(WeightMeasurement selected) {
		new WeightMeasurementEditJInternalFrame(selected, getEditDialogComponentFactory(), this, getDesktopPane());
	}

	@Override
	protected WeightMeasurement createNewObject() {
		return new WeightMeasurement()
			.setId(UUID.randomUUID())
			.setComment("")
			.setDateOfRecording(LocalDate.now())
			.setKg(new BigDecimal(70.0))
			.setTimeOfRecording(LocalTime.now());
	}

	@Override
	protected void delete(WeightMeasurement toDelete) {
		weightMeasurementService.deleteWeightMeasurement(toDelete.getId());
	}

	@Override
	protected void save(WeightMeasurement toSave) {
		weightMeasurementService.updateWeightMeasurement(toSave);
	}
}
