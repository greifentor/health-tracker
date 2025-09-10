package de.ollie.healthtracker.gui.swing.edit.doctorconsultation;

import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.core.service.model.DoctorConsultation;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditJInternalFrame;
import java.util.Map;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;

public class DoctorConsultationEditJInternalFrame extends AbstractEditJInternalFrame<DoctorConsultation> {

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
			Map.of(DoctorConsultationEditJPanel.DOCTORS_ITEM_PROVIDER_ID, doctors)
		);
	}

	@Override
	protected JPanel createEditorPanel(DoctorConsultation toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new DoctorConsultationEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
