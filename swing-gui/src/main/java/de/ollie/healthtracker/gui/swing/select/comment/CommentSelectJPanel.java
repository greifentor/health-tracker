package de.ollie.healthtracker.gui.swing.select.comment;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.CommentService;
import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.comment.CommentEditJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJPanel;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectionTableModel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import javax.swing.JDesktopPane;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public class CommentSelectJPanel extends AbstractSelectJPanel<Comment> implements SelectionPanelObserver {

	private final CommentService commentService;

	public CommentSelectJPanel(
		CommentService commentService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className + "s", editDialogComponentFactory, observer);
		this.commentService = commentService;
		updateTableSelection();
	}

	@Override
	protected List<Comment> getObjectsToSelect() {
		return commentService != null ? commentService.listComments().stream().toList() : List.of();
	}

	@Override
	protected AbstractSelectionTableModel<Comment> createSelectionModel() {
		return new AbstractSelectionTableModel<Comment>(getObjectsToSelect(), "Date Of Recording", "Content") {
			@Override
			protected Object getColumnValueFor(Comment t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> DateTimeUtil.DE_DATE_FORMAT.format(t.getDateOfRecording());
					case 1 -> t.getContent();
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(Comment selected) {
		new CommentEditJInternalFrame(selected, getEditDialogComponentFactory(), this, getDesktopPane());
	}

	@Override
	protected Comment createNewObject() {
		return new Comment().setId(UUID.randomUUID()).setContent("").setDateOfRecording(LocalDate.now());
	}

	@Override
	protected void delete(Comment toDelete) {
		commentService.deleteComment(toDelete.getId());
	}

	@Override
	protected void save(Comment toSave) {
		commentService.updateComment(toSave);
	}
}
