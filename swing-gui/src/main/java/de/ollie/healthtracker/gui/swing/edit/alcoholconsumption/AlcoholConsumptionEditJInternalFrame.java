package de.ollie.healthtracker.gui.swing.edit.alcoholconsumption;

import de.ollie.healthtracker.core.service.AlcoholConsumptionService;
import de.ollie.healthtracker.core.service.model.AlcoholConsumption;
import de.ollie.healthtracker.core.service.model.AlcoholProduct;
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
public class AlcoholConsumptionEditJInternalFrame extends AbstractEditJInternalFrame<AlcoholConsumption> {

	public AlcoholConsumptionEditJInternalFrame(
		AlcoholConsumption toEdit,
		ItemProvider<AlcoholProduct> alcoholProducts,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<AlcoholConsumption> observer,
		JDesktopPane desktopPane
	) {
		super(
			desktopPane,
			"Alcohol Consumption",
			toEdit,
			editDialogComponentFactory,
			observer,
			Map.of(AlcoholConsumptionEditJPanel.ALCOHOL_PRODUCT_ITEM_PROVIDER_ID, alcoholProducts)
		);
	}

	@Override
	protected JPanel createEditorPanel(AlcoholConsumption toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new AlcoholConsumptionEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
