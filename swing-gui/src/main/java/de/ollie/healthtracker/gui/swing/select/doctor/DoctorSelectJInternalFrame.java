package de.ollie.healthtracker.gui.swing.select.doctor;

import de.ollie.healthtracker.core.service.DoctorService;
import de.ollie.healthtracker.core.service.DoctorTypeService;
import de.ollie.healthtracker.core.service.model.Doctor;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJPanel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import javax.swing.JDesktopPane;

public class DoctorSelectJInternalFrame extends AbstractSelectJInternalFrame<Doctor> implements SelectionPanelObserver {

	private static final String CLASS_NAME = "Doctor";

	private final DoctorService doctorService;
	private final DoctorTypeService doctorTypeService;

	public DoctorSelectJInternalFrame(
		DoctorService doctorService,
		DoctorTypeService doctorTypeService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME + "s", editDialogComponentFactory);
		this.doctorService = doctorService;
		this.doctorTypeService = doctorTypeService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<Doctor> createSelectPanel() {
		return new DoctorSelectJPanel(
			doctorService,
			doctorTypeService,
			CLASS_NAME,
			desktopPane,
			editDialogComponentFactory,
			this
		);
	}
}
