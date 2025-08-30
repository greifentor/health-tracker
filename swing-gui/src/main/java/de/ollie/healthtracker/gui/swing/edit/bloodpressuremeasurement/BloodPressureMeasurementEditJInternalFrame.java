package de.ollie.healthtracker.gui.swing.edit.bloodpressuremeasurement;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.BaseEditInternalFrame;
import java.util.Map;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;

public class BloodPressureMeasurementEditJInternalFrame extends BaseEditInternalFrame<BloodPressureMeasurement> {

	private BloodPressureMeasurementEditPanel editPanel;

	public BloodPressureMeasurementEditJInternalFrame(
		BloodPressureMeasurement toEdit,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<BloodPressureMeasurement> observer,
		JDesktopPane desktopPane
	) {
		super(desktopPane, "Doctor Consultation", toEdit, editDialogComponentFactory, observer, Map.of());
	}

	@Override
	protected JPanel createEditorPanel(BloodPressureMeasurement toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new BloodPressureMeasurementEditPanel(toEdit, itemProviders);
		return editPanel;
	}

	@Override
	public BloodPressureMeasurement getCurrentContent() {
		return editPanel.getCurrentContent();
	}
}
