package de.ollie.healthtracker.gui.swing.select;

import de.ollie.healthtracker.core.service.DoctorConsultationService;
import de.ollie.healthtracker.core.service.DoctorService;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

public class DoctorConsultationSelectJInternalFrame
	extends JInternalFrame
	implements DoctorConsultationSelectPanel.Observer {

	public JDesktopPane desktopPane;

	public DoctorConsultationSelectJInternalFrame(
		DoctorConsultationService doctorConsultationService,
		DoctorService doctorService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super("Doctor Consultations", true, true, true, true);
		this.desktopPane = desktopPane;
		setBounds(10, 10, 600, 400);
		setContentPane(
			new DoctorConsultationSelectPanel(
				doctorConsultationService,
				doctorService,
				desktopPane,
				editDialogComponentFactory,
				this
			)
		);
		desktopPane.add(this);
		try {
			setSelected(true);
		} catch (java.beans.PropertyVetoException e) {
			e.printStackTrace();
		}
		setVisible(true);
	}

	@Override
	public void onCancel() {
		setVisible(false);
		desktopPane.remove(this);
	}
}
