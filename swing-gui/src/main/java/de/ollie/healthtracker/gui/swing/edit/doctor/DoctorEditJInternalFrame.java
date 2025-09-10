package de.ollie.healthtracker.gui.swing.edit.doctor;

import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorType;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditJInternalFrame;
import java.util.Map;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;

public class DoctorEditJInternalFrame extends AbstractEditJInternalFrame<Doctor> {

	public DoctorEditJInternalFrame(
		Doctor toEdit,
		ItemProvider<DoctorType> doctorTypes,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<Doctor> observer,
		JDesktopPane desktopPane
	) {
		super(
			desktopPane,
			"Doctor",
			toEdit,
			editDialogComponentFactory,
			observer,
			Map.of(DoctorEditJPanel.DOCTOR_TYPES_ITEM_PROVIDER_ID, doctorTypes)
		);
	}

	@Override
	protected JPanel createEditorPanel(Doctor toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new DoctorEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
