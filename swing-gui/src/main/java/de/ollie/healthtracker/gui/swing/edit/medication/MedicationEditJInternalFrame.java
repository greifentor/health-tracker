package de.ollie.healthtracker.gui.swing.edit.medication;

import de.ollie.healthtracker.core.service.MedicationService;
import de.ollie.healthtracker.core.service.model.Manufacturer;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditJInternalFrame;
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
public class MedicationEditJInternalFrame extends AbstractEditJInternalFrame<Medication> {

	public MedicationEditJInternalFrame(
		Medication toEdit,
		ItemProvider<Manufacturer> manufacturers,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<Medication> observer,
		JDesktopPane desktopPane
	) {
		super(
			desktopPane,
			"Medication",
			toEdit,
			editDialogComponentFactory,
			observer,
			Map.of(MedicationEditJPanel.MANUFACTURER_ITEM_PROVIDER_ID, manufacturers)
		);
	}

	@Override
	protected JPanel createEditorPanel(Medication toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new MedicationEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
