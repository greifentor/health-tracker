package de.ollie.healthtracker.gui.swing.edit.commenttype;

import de.ollie.healthtracker.core.service.CommentTypeService;
import de.ollie.healthtracker.core.service.model.CommentType;
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
public class CommentTypeEditJInternalFrame extends AbstractEditJInternalFrame<CommentType> {

	public CommentTypeEditJInternalFrame(
		CommentType toEdit,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<CommentType> observer,
		JDesktopPane desktopPane
	) {
		super(desktopPane, "Comment Type", toEdit, editDialogComponentFactory, observer, Map.of());
	}

	@Override
	protected JPanel createEditorPanel(CommentType toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new CommentTypeEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
