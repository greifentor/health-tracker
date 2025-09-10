package de.ollie.healthtracker.gui.swing.select.medicationlog;

import de.ollie.healthtracker.core.service.MedicationLogService;
import de.ollie.healthtracker.core.service.MedicationService;
import de.ollie.healthtracker.core.service.MedicationUnitService;
import de.ollie.healthtracker.core.service.model.MedicationLog;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJPanel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import javax.swing.JDesktopPane;

public class MedicationLogSelectJInternalFrame
	extends AbstractSelectJInternalFrame<MedicationLog>
	implements SelectionPanelObserver {

	private static final String CLASS_NAME = "Medication Log";

	private final MedicationLogService medicationLogService;
	private final MedicationService medicationService;
	private final MedicationUnitService medicationUnitService;

	public MedicationLogSelectJInternalFrame(
		MedicationService medicationService,
		MedicationLogService medicationLogService,
		MedicationUnitService medicationUnitService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME + "s", editDialogComponentFactory);
		this.medicationService = medicationService;
		this.medicationLogService = medicationLogService;
		this.medicationUnitService = medicationUnitService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<MedicationLog> createSelectPanel() {
		return new MedicationLogSelectJPanel(
			medicationService,
			medicationLogService,
			medicationUnitService,
			CLASS_NAME,
			desktopPane,
			editDialogComponentFactory,
			this
		);
	}
}
