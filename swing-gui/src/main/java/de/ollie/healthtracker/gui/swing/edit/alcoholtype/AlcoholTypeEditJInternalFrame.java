package de.ollie.healthtracker.gui.swing.edit.alcoholtype;

import de.ollie.healthtracker.core.service.AlcoholTypeService;
import de.ollie.healthtracker.core.service.model.AlcoholType;
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
public class AlcoholTypeEditJInternalFrame extends AbstractEditJInternalFrame<AlcoholType> {

	public AlcoholTypeEditJInternalFrame(
		AlcoholType toEdit,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<AlcoholType> observer,
		JDesktopPane desktopPane
	) {
		super(desktopPane, "Alcohol Type", toEdit, editDialogComponentFactory, observer, Map.of());
	}

	@Override
	protected JPanel createEditorPanel(AlcoholType toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new AlcoholTypeEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
