package de.ollie.healthtracker.gui.swing.select.medicationunit;

import de.ollie.healthtracker.core.service.MedicationUnitService;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
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
public class MedicationUnitSelectJInternalFrame
	extends AbstractSelectJInternalFrame<MedicationUnit>
	implements SelectionPanelObserver {

	private static final String CLASS_NAME = "MedicationUnit";

	private final MedicationUnitService medicationUnitService;

	public MedicationUnitSelectJInternalFrame(
		MedicationUnitService medicationUnitService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME + "s", editDialogComponentFactory);
		this.medicationUnitService = medicationUnitService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<MedicationUnit> createSelectPanel() {
		return new MedicationUnitSelectJPanel(
			medicationUnitService,
			CLASS_NAME,
			desktopPane,
			editDialogComponentFactory,
			this
		);
	}
}
