package de.ollie.healthtracker.gui.swing.select.medicationlog;

import de.ollie.healthtracker.core.service.MedicationLogService;
import de.ollie.healthtracker.core.service.MedicationService;
import de.ollie.healthtracker.core.service.MedicationUnitService;
import de.ollie.healthtracker.core.service.model.MedicationLog;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJPanel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import javax.swing.JDesktopPane;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public class MedicationLogSelectJInternalFrame
	extends AbstractSelectJInternalFrame<MedicationLog>
	implements SelectionPanelObserver {

	private static final String CLASS_NAME = "MedicationLog";

	private final MedicationLogService medicationLogService;
	private final MedicationService medicationService;
	private final MedicationUnitService medicationUnitService;

	public MedicationLogSelectJInternalFrame(
		MedicationLogService medicationLogService,
		MedicationService medicationService,
		MedicationUnitService medicationUnitService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME, editDialogComponentFactory);
		this.medicationLogService = medicationLogService;
		this.medicationService = medicationService;
		this.medicationUnitService = medicationUnitService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<MedicationLog> createSelectPanel() {
		return new MedicationLogSelectJPanel(
			medicationLogService,
			medicationService,
			medicationUnitService,
			CLASS_NAME,
			desktopPane,
			editDialogComponentFactory,
			this
		);
	}
}
