package de.ollie.healthtracker.gui.swing;

import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorConsultation;
import de.ollie.healthtracker.gui.swing.edit.DoctorConsultationEditPanel;
import java.util.Map;
import javax.swing.JPanel;

public class DoctorConsultationEditDialog extends BaseEditDialog<DoctorConsultation> {

	private DoctorConsultationEditPanel editPanel;

	public DoctorConsultationEditDialog(
		String title,
		DoctorConsultation toEdit,
		EditDialogComponentFactory componentFactory,
		Observer<DoctorConsultation> observer,
		ItemProvider<Doctor> doctors
	) {
		super(
			title,
			toEdit,
			componentFactory,
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
