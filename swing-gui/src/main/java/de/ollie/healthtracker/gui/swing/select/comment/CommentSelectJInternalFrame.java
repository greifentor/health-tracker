package de.ollie.healthtracker.gui.swing.select.comment;

import de.ollie.healthtracker.core.service.CommentService;
import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJPanel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import javax.swing.JDesktopPane;

public class CommentSelectJInternalFrame
	extends AbstractSelectJInternalFrame<Comment>
	implements SelectionPanelObserver {

	private static final String CLASS_NAME = "Comment";

	private final CommentService commentService;

	public CommentSelectJInternalFrame(
		CommentService commentService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME + "s", editDialogComponentFactory);
		this.commentService = commentService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<Comment> createSelectPanel() {
		return new CommentSelectJPanel(commentService, CLASS_NAME, desktopPane, editDialogComponentFactory, this);
	}
}
