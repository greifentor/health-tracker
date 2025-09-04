package de.ollie.healthtracker.gui.swing.select.doctortype;

import de.ollie.healthtracker.core.service.DoctorTypeService;
import de.ollie.healthtracker.core.service.model.DoctorType;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectPanel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import javax.swing.JDesktopPane;

public class DoctorTypeSelectJInternalFrame
	extends AbstractSelectJInternalFrame<DoctorType>
	implements SelectionPanelObserver {

	private static final String CLASS_NAME = "Doctor Type";

	private final DoctorTypeService doctorTypeService;

	public DoctorTypeSelectJInternalFrame(
		DoctorTypeService doctorTypeService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME + "s", editDialogComponentFactory);
		this.doctorTypeService = doctorTypeService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectPanel<DoctorType> createSelectPanel() {
		return new DoctorTypeSelectPanel(doctorTypeService, CLASS_NAME, desktopPane, editDialogComponentFactory, this);
	}
}
