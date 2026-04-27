package de.ollie.healthtracker.gui.swing.select.comment;

import de.ollie.healthtracker.core.service.CommentService;
import de.ollie.healthtracker.core.service.CommentTypeService;
import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJPanel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import java.time.LocalDate;
import java.util.UUID;
import javax.swing.JDesktopPane;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public class CommentSelectJInternalFrame
	extends AbstractSelectJInternalFrame<Comment>
	implements SelectionPanelObserver {

	private static final String CLASS_NAME = "Comment";

	private final CommentService commentService;
	private final CommentTypeService commentTypeService;

	public CommentSelectJInternalFrame(
		CommentService commentService,
		CommentTypeService commentTypeService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME, editDialogComponentFactory);
		this.commentService = commentService;
		this.commentTypeService = commentTypeService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<Comment> createSelectPanel() {
		return new CommentSelectJPanel(
			commentService,
			commentTypeService,
			CLASS_NAME,
			desktopPane,
			editDialogComponentFactory,
			this
		);
	}
}
