package de.ollie.healthtracker.gui.swing.edit.comment;

import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditInternalFrame;
import java.util.Map;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;

public class CommentEditInternalFrame extends AbstractEditInternalFrame<Comment> {

	public CommentEditInternalFrame(
		Comment toEdit,
		String className,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<Comment> observer,
		JDesktopPane desktopPane
	) {
		super(desktopPane, className, toEdit, editDialogComponentFactory, observer, Map.of());
	}

	@Override
	protected JPanel createEditorPanel(Comment toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new CommentEditPanel(toEdit, itemProviders);
		return editPanel;
	}
}
