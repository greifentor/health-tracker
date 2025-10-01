package de.ollie.healthtracker.gui.swing.edit.bodypart;

import de.ollie.healthtracker.core.service.BodyPartService;
import de.ollie.healthtracker.core.service.model.BodyPart;
import de.ollie.healthtracker.core.service.model.GeneralBodyPart;
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
public class BodyPartEditJInternalFrame extends AbstractEditJInternalFrame<BodyPart> {

	public BodyPartEditJInternalFrame(
		BodyPart toEdit,
		ItemProvider<GeneralBodyPart> generalBodyParts,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<BodyPart> observer,
		JDesktopPane desktopPane
	) {
		super(
			desktopPane,
			"Body Part",
			toEdit,
			editDialogComponentFactory,
			observer,
			Map.of(BodyPartEditJPanel.GENERAL_BODY_PART_ITEM_PROVIDER_ID, generalBodyParts)
		);
	}

	@Override
	protected JPanel createEditorPanel(BodyPart toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new BodyPartEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
