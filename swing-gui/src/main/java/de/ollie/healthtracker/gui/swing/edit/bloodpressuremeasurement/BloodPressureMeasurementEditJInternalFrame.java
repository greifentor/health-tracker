package de.ollie.healthtracker.gui.swing.edit.bloodpressuremeasurement;

import de.ollie.healthtracker.core.service.BloodPressureMeasurementService;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditJInternalFrame;
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
public class BloodPressureMeasurementEditJInternalFrame extends AbstractEditJInternalFrame<BloodPressureMeasurement> {

	public BloodPressureMeasurementEditJInternalFrame(
		BloodPressureMeasurement toEdit,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<BloodPressureMeasurement> observer,
		JDesktopPane desktopPane
	) {
		super(desktopPane, "Blood Pressure Measurement", toEdit, editDialogComponentFactory, observer, Map.of());
	}

	@Override
	protected JPanel createEditorPanel(BloodPressureMeasurement toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new BloodPressureMeasurementEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
