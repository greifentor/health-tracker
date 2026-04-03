package de.ollie.healthtracker.gui.swing.edit.medicationplan;

import de.ollie.healthtracker.core.service.MedicationPlanService;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationPlan;
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
public class MedicationPlanEditJInternalFrame extends AbstractEditJInternalFrame<MedicationPlan> {

	public MedicationPlanEditJInternalFrame(
		MedicationPlan toEdit,
		ItemProvider<Medication> medications,
		ItemProvider<MedicationUnit> medicationUnits,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<MedicationPlan> observer,
		JDesktopPane desktopPane
	) {
		super(
			desktopPane,
			"Medication Plan",
			toEdit,
			editDialogComponentFactory,
			observer,
			Map.of(
				MedicationPlanEditJPanel.MEDICATION_ITEM_PROVIDER_ID,
				medications,
				MedicationPlanEditJPanel.MEDICATION_UNIT_ITEM_PROVIDER_ID,
				medicationUnits
			)
		);
	}

	@Override
	protected JPanel createEditorPanel(MedicationPlan toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new MedicationPlanEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
