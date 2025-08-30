package de.ollie.healthtracker.gui.swing.select.medicationlog;

import de.ollie.healthtracker.core.service.MedicationLogService;
import de.ollie.healthtracker.core.service.MedicationService;
import de.ollie.healthtracker.core.service.MedicationUnitService;
import de.ollie.healthtracker.core.service.model.MedicationLog;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectPanel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import javax.swing.JDesktopPane;

public class MedicationLogSelectJInternalFrame
	extends AbstractSelectJInternalFrame<MedicationLog>
	implements SelectionPanelObserver {

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
		super(desktopPane, "Medication Logs", editDialogComponentFactory);
		this.medicationService = medicationService;
		this.medicationLogService = medicationLogService;
		this.medicationUnitService = medicationUnitService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectPanel<MedicationLog> createSelectPanel() {
		return new MedicationLogSelectPanel(
			medicationService,
			medicationLogService,
			medicationUnitService,
			desktopPane,
			editDialogComponentFactory,
			this
		);
	}
}
