package de.ollie.healthtracker.gui.swing.edit;

import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorConsultation;
import de.ollie.healthtracker.gui.swing.DoctorConsultationEditPanel;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import java.util.Map;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;

public class DoctorConsultationEditJInternalFrame extends BaseEditInternalFrame<DoctorConsultation> {

	private DoctorConsultationEditPanel editPanel;

	public DoctorConsultationEditJInternalFrame(
		DoctorConsultation toEdit,
		ItemProvider<Doctor> doctors,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<DoctorConsultation> observer,
		JDesktopPane desktopPane
	) {
		super(
			desktopPane,
			"Doctor Consultation",
			toEdit,
			editDialogComponentFactory,
			observer,
			Map.of(DoctorConsultationEditPanel.DOCTORS_ITEM_PROVIDER_ID, doctors)
		);
	}

	@Override
	JPanel createEditorPanel(DoctorConsultation toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new DoctorConsultationEditPanel(toEdit, itemProviders);
		return editPanel;
	}

	@Override
	public DoctorConsultation getCurrentContent() {
		return editPanel.getCurrentContent();
	}
}
