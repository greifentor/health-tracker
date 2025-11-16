package de.ollie.healthtracker.gui.swing.edit.meattype;

import de.ollie.healthtracker.core.service.MeatTypeService;
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
public class MeatTypeEditJInternalFrame extends AbstractEditJInternalFrame<MeatType> {

	public MeatTypeEditJInternalFrame(
		MeatType toEdit,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<MeatType> observer,
		JDesktopPane desktopPane
	) {
		super(desktopPane, "Meat Type", toEdit, editDialogComponentFactory, observer, Map.of());
	}

	@Override
	protected JPanel createEditorPanel(MeatType toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new MeatTypeEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
