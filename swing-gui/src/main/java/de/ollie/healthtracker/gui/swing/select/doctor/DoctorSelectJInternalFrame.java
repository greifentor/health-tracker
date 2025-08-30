package de.ollie.healthtracker.gui.swing.select.doctor;

import de.ollie.healthtracker.core.service.DoctorService;
import de.ollie.healthtracker.core.service.DoctorTypeService;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

public class DoctorSelectJInternalFrame extends JInternalFrame implements DoctorSelectPanel.Observer {

	public JDesktopPane desktopPane;

	public DoctorSelectJInternalFrame(
		DoctorService doctorService,
		DoctorTypeService doctorTypeService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super("Doctors", true, true, true, true);
		this.desktopPane = desktopPane;
		setBounds(10, 10, 600, 400);
		setContentPane(
			new DoctorSelectPanel(doctorService, doctorTypeService, desktopPane, editDialogComponentFactory, this)
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
