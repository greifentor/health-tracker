package de.ollie.healthtracker.gui.swing.edit.doctorconsultation;

import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorConsultation;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditInternalFrame;
import java.util.Map;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;

public class DoctorConsultationEditInternalFrame extends AbstractEditInternalFrame<DoctorConsultation> {

	public DoctorConsultationEditInternalFrame(
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
	protected JPanel createEditorPanel(DoctorConsultation toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new DoctorConsultationEditPanel(toEdit, itemProviders);
		return editPanel;
	}
}
