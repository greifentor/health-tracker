package de.ollie.healthtracker.gui.swing.edit.meatconsumption;

import de.ollie.healthtracker.core.service.MeatConsumptionService;
import de.ollie.healthtracker.core.service.model.MeatConsumption;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditJInternalFrame;
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
public class MeatConsumptionEditJInternalFrame extends AbstractEditJInternalFrame<MeatConsumption> {

	public MeatConsumptionEditJInternalFrame(
		MeatConsumption toEdit,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<MeatConsumption> observer,
		JDesktopPane desktopPane
	) {
		super(desktopPane, "Meat Consumption", toEdit, editDialogComponentFactory, observer, Map.of());
	}

	@Override
	protected JPanel createEditorPanel(MeatConsumption toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new MeatConsumptionEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
