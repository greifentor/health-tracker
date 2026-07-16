package de.ollie.healthtracker.gui.swing.edit.bodytemperaturemeasurement;

import de.ollie.healthtracker.core.service.BodyTemperatureMeasurementService;
import de.ollie.healthtracker.core.service.model.BodyTemperatureMeasurement;
import de.ollie.healthtracker.core.service.model.PointOfMeasurement;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditJInternalFrame;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.UUID;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public class BodyTemperatureMeasurementEditJInternalFrame
	extends AbstractEditJInternalFrame<BodyTemperatureMeasurement> {

	public BodyTemperatureMeasurementEditJInternalFrame(
		BodyTemperatureMeasurement toEdit,
		ItemProvider<PointOfMeasurement> pointOfMeasurements,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<BodyTemperatureMeasurement> observer,
		JDesktopPane desktopPane
	) {
		super(
			desktopPane,
			"Body Temperature Measurement",
			toEdit,
			editDialogComponentFactory,
			observer,
			Map.of(BodyTemperatureMeasurementEditJPanel.POINT_OF_MEASUREMENT_ITEM_PROVIDER_ID, pointOfMeasurements)
		);
	}

	@Override
	protected JPanel createEditorPanel(BodyTemperatureMeasurement toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new BodyTemperatureMeasurementEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
