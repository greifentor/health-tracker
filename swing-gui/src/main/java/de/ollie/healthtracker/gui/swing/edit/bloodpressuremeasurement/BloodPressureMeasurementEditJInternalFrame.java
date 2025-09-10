package de.ollie.healthtracker.gui.swing.edit.bloodpressuremeasurement;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditJInternalFrame;
import java.util.Map;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;

public class BloodPressureMeasurementEditJInternalFrame extends AbstractEditJInternalFrame<BloodPressureMeasurement> {

	public BloodPressureMeasurementEditJInternalFrame(
		BloodPressureMeasurement toEdit,
		String className,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<BloodPressureMeasurement> observer,
		JDesktopPane desktopPane
	) {
		super(desktopPane, className, toEdit, editDialogComponentFactory, observer, Map.of());
	}

	@Override
	protected JPanel createEditorPanel(BloodPressureMeasurement toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new BloodPressureMeasurementEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
