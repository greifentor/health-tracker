package de.ollie.healthtracker.gui.swing.edit.generalbodypart;

import de.ollie.healthtracker.core.service.GeneralBodyPartService;
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
public class GeneralBodyPartEditJInternalFrame extends AbstractEditJInternalFrame<GeneralBodyPart> {

	public GeneralBodyPartEditJInternalFrame(
		GeneralBodyPart toEdit,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<GeneralBodyPart> observer,
		JDesktopPane desktopPane
	) {
		super(desktopPane, "General Body Part", toEdit, editDialogComponentFactory, observer, Map.of());
	}

	@Override
	protected JPanel createEditorPanel(GeneralBodyPart toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new GeneralBodyPartEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
