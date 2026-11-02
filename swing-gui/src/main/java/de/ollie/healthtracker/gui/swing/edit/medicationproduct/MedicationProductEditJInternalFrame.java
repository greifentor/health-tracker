package de.ollie.healthtracker.gui.swing.edit.medicationproduct;

import de.ollie.healthtracker.core.service.MedicationProductService;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationProduct;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditJInternalFrame;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public class MedicationProductEditJInternalFrame extends AbstractEditJInternalFrame<MedicationProduct> {

	public MedicationProductEditJInternalFrame(
		MedicationProduct toEdit,
		ItemProvider<Medication> medications,
		ItemProvider<MedicationUnit> medicationUnits,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<MedicationProduct> observer,
		JDesktopPane desktopPane
	) {
		super(
			desktopPane,
			"Medication Product",
			toEdit,
			editDialogComponentFactory,
			observer,
			Map.of(
				MedicationProductEditJPanel.MEDICATION_ITEM_PROVIDER_ID,
				medications,
				MedicationProductEditJPanel.MEDICATION_UNIT_ITEM_PROVIDER_ID,
				medicationUnits
			)
		);
	}

	@Override
	protected JPanel createEditorPanel(MedicationProduct toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new MedicationProductEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
