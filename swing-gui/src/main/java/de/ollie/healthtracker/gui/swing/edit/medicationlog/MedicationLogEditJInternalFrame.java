package de.ollie.healthtracker.gui.swing.edit.medicationlog;

import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationLog;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditJInternalFrame;
import java.util.Map;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;

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
				MedicationLogEditJPanel.MEDICATIONS_PROVIDER_ID,
				medications,
				MedicationLogEditJPanel.MEDICATION_UNITS_PROVIDER_ID,
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
