package de.ollie.healthtracker.gui.swing.select.medication;

import de.ollie.healthtracker.core.service.ManufacturerService;
import de.ollie.healthtracker.core.service.MedicationService;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJPanel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import java.util.UUID;
import javax.swing.JDesktopPane;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public class MedicationSelectJInternalFrame
	extends AbstractSelectJInternalFrame<Medication>
	implements SelectionPanelObserver {

	private static final String CLASS_NAME = "Medication";

	private final MedicationService medicationService;
	private final ManufacturerService manufacturerService;

	public MedicationSelectJInternalFrame(
		MedicationService medicationService,
		ManufacturerService manufacturerService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME + "s", editDialogComponentFactory);
		this.medicationService = medicationService;
		this.manufacturerService = manufacturerService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<Medication> createSelectPanel() {
		return new MedicationSelectJPanel(
			medicationService,
			manufacturerService,
			CLASS_NAME,
			desktopPane,
			editDialogComponentFactory,
			this
		);
	}
}
