package de.ollie.healthtracker.gui.swing.edit.alcoholproduct;

import de.ollie.healthtracker.core.service.AlcoholProductService;
import de.ollie.healthtracker.core.service.model.AlcoholProduct;
import de.ollie.healthtracker.core.service.model.AlcoholType;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditJInternalFrame;
import java.math.BigDecimal;
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
public class AlcoholProductEditJInternalFrame extends AbstractEditJInternalFrame<AlcoholProduct> {

	public AlcoholProductEditJInternalFrame(
		AlcoholProduct toEdit,
		ItemProvider<AlcoholType> alcoholTypes,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<AlcoholProduct> observer,
		JDesktopPane desktopPane
	) {
		super(
			desktopPane,
			"Alcohol Product",
			toEdit,
			editDialogComponentFactory,
			observer,
			Map.of(AlcoholProductEditJPanel.ALCOHOL_TYPE_ITEM_PROVIDER_ID, alcoholTypes)
		);
	}

	@Override
	protected JPanel createEditorPanel(AlcoholProduct toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new AlcoholProductEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
