package de.ollie.healthtracker.gui.swing.edit.comment;

import de.ollie.healthtracker.core.service.CommentService;
import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.core.service.model.CommentType;
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
public class CommentEditJInternalFrame extends AbstractEditJInternalFrame<Comment> {

	public CommentEditJInternalFrame(
		Comment toEdit,
		ItemProvider<CommentType> commentTypes,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<Comment> observer,
		JDesktopPane desktopPane
	) {
		super(
			desktopPane,
			"Comment",
			toEdit,
			editDialogComponentFactory,
			observer,
			Map.of(CommentEditJPanel.COMMENT_TYPE_ITEM_PROVIDER_ID, commentTypes)
		);
	}

	@Override
	protected JPanel createEditorPanel(Comment toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new CommentEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
