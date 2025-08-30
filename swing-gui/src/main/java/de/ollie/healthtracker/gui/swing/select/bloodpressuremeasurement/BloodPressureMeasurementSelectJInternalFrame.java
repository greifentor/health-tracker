package de.ollie.healthtracker.gui.swing.select.bloodpressuremeasurement;

import de.ollie.healthtracker.core.service.BloodPressureMeasurementService;
import de.ollie.healthtracker.core.service.DoctorService;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

public class BloodPressureMeasurementSelectJInternalFrame
	extends JInternalFrame
	implements BloodPressureMeasurementSelectPanel.Observer {

	public JDesktopPane desktopPane;

	public BloodPressureMeasurementSelectJInternalFrame(
		BloodPressureMeasurementService bloodPressureMeasurementService,
		DoctorService doctorService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super("Doctor Consultations", true, true, true, true);
		this.desktopPane = desktopPane;
		setBounds(10, 10, 600, 400);
		setContentPane(
			new BloodPressureMeasurementSelectPanel(
				bloodPressureMeasurementService,
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
