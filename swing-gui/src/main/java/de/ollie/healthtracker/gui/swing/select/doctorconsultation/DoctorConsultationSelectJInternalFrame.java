package de.ollie.healthtracker.gui.swing.select.doctorconsultation;

import de.ollie.healthtracker.core.service.DoctorConsultationService;
import de.ollie.healthtracker.core.service.DoctorService;
import de.ollie.healthtracker.core.service.model.DoctorConsultation;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJPanel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import javax.swing.JDesktopPane;

public class DoctorConsultationSelectJInternalFrame
	extends AbstractSelectJInternalFrame<DoctorConsultation>
	implements SelectionPanelObserver {

	private final DoctorConsultationService doctorConsultationService;
	private final DoctorService doctorService;

	public DoctorConsultationSelectJInternalFrame(
		DoctorConsultationService doctorConsultationService,
		DoctorService doctorService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, "Doctor Consultations", editDialogComponentFactory);
		this.doctorConsultationService = doctorConsultationService;
		this.doctorService = doctorService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<DoctorConsultation> createSelectPanel() {
		return new DoctorConsultationSelectJPanel(
			doctorConsultationService,
			doctorService,
			desktopPane,
			editDialogComponentFactory,
			this
		);
	}
}
