package de.ollie.healthtracker.gui.swing.edit.medicationlog;

import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationLog;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditInternalFrame;
import java.util.Map;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;

public class MedicationLogEditInternalFrame extends AbstractEditInternalFrame<MedicationLog> {

	public MedicationLogEditInternalFrame(
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
				MedicationLogEditPanel.MEDICATIONS_PROVIDER_ID,
				medications,
				MedicationLogEditPanel.MEDICATION_UNITS_PROVIDER_ID,
				medicationUnits
			)
		);
	}

	@Override
	protected JPanel createEditorPanel(MedicationLog toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new MedicationLogEditPanel(toEdit, itemProviders);
		return editPanel;
	}
}
