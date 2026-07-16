package de.ollie.healthtracker.gui.swing.edit.medicationlog;

import de.ollie.healthtracker.core.service.MedicationLogService;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationLog;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditJInternalFrame;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.UUID;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public class MedicationLogEditJInternalFrame extends AbstractEditJInternalFrame<MedicationLog> {

	public MedicationLogEditJInternalFrame(
		MedicationLog toEdit,
		ItemProvider<Medication> medications,
		ItemProvider<MedicationUnit> medicationUnits,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<MedicationLog> observer,
		JDesktopPane desktopPane
	) {
		super(
			desktopPane,
			"Medication Log",
			toEdit,
			editDialogComponentFactory,
			observer,
			Map.of(
				MedicationLogEditJPanel.MEDICATION_ITEM_PROVIDER_ID,
				medications,
				MedicationLogEditJPanel.MEDICATION_UNIT_ITEM_PROVIDER_ID,
				medicationUnits
			)
		);
	}

	@Override
	protected JPanel createEditorPanel(MedicationLog toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new MedicationLogEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
