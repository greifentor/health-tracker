package de.ollie.healthtracker.gui.swing.edit.meatproduct;

import de.ollie.healthtracker.core.service.MeatProductService;
import de.ollie.healthtracker.core.service.model.MeatProduct;
import de.ollie.healthtracker.core.service.model.MeatType;
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
public class MeatProductEditJInternalFrame extends AbstractEditJInternalFrame<MeatProduct> {

	public MeatProductEditJInternalFrame(
		MeatProduct toEdit,
		ItemProvider<MeatType> meatTypes,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<MeatProduct> observer,
		JDesktopPane desktopPane
	) {
		super(
			desktopPane,
			"Meat Product",
			toEdit,
			editDialogComponentFactory,
			observer,
			Map.of(MeatProductEditJPanel.MEAT_TYPE_ITEM_PROVIDER_ID, meatTypes)
		);
	}

	@Override
	protected JPanel createEditorPanel(MeatProduct toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new MeatProductEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
