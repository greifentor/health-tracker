package de.ollie.healthtracker.gui.swing.edit.pointofmeasurement;

import de.ollie.healthtracker.core.service.PointOfMeasurementService;
import de.ollie.healthtracker.core.service.model.PointOfMeasurement;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditJInternalFrame;
import java.math.BigDecimal;
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
public class PointOfMeasurementEditJInternalFrame extends AbstractEditJInternalFrame<PointOfMeasurement> {

	public PointOfMeasurementEditJInternalFrame(
		PointOfMeasurement toEdit,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<PointOfMeasurement> observer,
		JDesktopPane desktopPane
	) {
		super(desktopPane, "Point Of Measurement", toEdit, editDialogComponentFactory, observer, Map.of());
	}

	@Override
	protected JPanel createEditorPanel(PointOfMeasurement toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new PointOfMeasurementEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
