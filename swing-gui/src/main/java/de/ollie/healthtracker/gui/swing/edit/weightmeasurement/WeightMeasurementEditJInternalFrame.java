package de.ollie.healthtracker.gui.swing.edit.weightmeasurement;

import de.ollie.healthtracker.core.service.WeightMeasurementService;
import de.ollie.healthtracker.core.service.model.WeightMeasurement;
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
public class WeightMeasurementEditJInternalFrame extends AbstractEditJInternalFrame<WeightMeasurement> {

	public WeightMeasurementEditJInternalFrame(
		WeightMeasurement toEdit,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<WeightMeasurement> observer,
		JDesktopPane desktopPane
	) {
		super(desktopPane, "Weight Measurement", toEdit, editDialogComponentFactory, observer, Map.of());
	}

	@Override
	protected JPanel createEditorPanel(WeightMeasurement toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new WeightMeasurementEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
