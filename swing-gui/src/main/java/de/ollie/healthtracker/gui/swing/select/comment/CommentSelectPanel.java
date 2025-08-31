package de.ollie.healthtracker.gui.swing.select.comment;

import de.ollie.healthtracker.core.service.CommentService;
import de.ollie.healthtracker.core.service.model.Comment;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.comment.CommentEditInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectPanel;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectionTableModel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JDesktopPane;

public class CommentSelectPanel extends AbstractSelectPanel<Comment> implements SelectionPanelObserver {

	private final CommentService commentService;

	public CommentSelectPanel(
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
		return commentService != null
			? commentService
				.listComments()
				.stream()
				.sorted((d0, d1) -> d1.getDateOfRecording().compareTo(d0.getDateOfRecording()))
				.toList()
			: List.of();
	}

	@Override
	protected AbstractSelectionTableModel<Comment> createSelectionModel() {
		return new AbstractSelectionTableModel<Comment>(getObjectsToSelect(), "Date", "Content") {
			@Override
			protected Object getColumnValueFor(Comment t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> t.getDateOfRecording();
					case 1 -> t.getContent();
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(Comment selected) {
		new CommentEditInternalFrame(selected, getClassName(), getEditDialogComponentFactory(), this, getDesktopPane());
	}

	@Override
	protected Comment createNewObject() {
		return commentService.createComment("?", LocalDate.now());
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
