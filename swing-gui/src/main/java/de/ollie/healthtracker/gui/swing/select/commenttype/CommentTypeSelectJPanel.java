package de.ollie.healthtracker.gui.swing.select.commenttype;

import de.ollie.healthtracker.core.service.CommentTypeService;
import de.ollie.healthtracker.core.service.model.CommentType;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.commenttype.CommentTypeEditJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJPanel;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectionTableModel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
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
public class CommentTypeSelectJPanel extends AbstractSelectJPanel<CommentType> implements SelectionPanelObserver {

	private final CommentTypeService commentTypeService;

	public CommentTypeSelectJPanel(
		CommentTypeService commentTypeService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className + "s", editDialogComponentFactory, observer);
		this.commentTypeService = commentTypeService;
		updateTableSelection();
	}

	@Override
	protected List<CommentType> getObjectsToSelect() {
		return commentTypeService != null ? commentTypeService.listCommentTypes().stream().toList() : List.of();
	}

	@Override
	protected AbstractSelectionTableModel<CommentType> createSelectionModel() {
		return new AbstractSelectionTableModel<CommentType>(getObjectsToSelect(), "Name") {
			@Override
			protected Object getColumnValueFor(CommentType t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> t.getName();
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(CommentType selected) {
		new CommentTypeEditJInternalFrame(selected, getEditDialogComponentFactory(), this, getDesktopPane());
	}

	@Override
	protected CommentType createNewObject() {
		return new CommentType().setId(UUID.randomUUID()).setName("");
	}

	@Override
	protected void delete(CommentType toDelete) {
		commentTypeService.deleteCommentType(toDelete.getId());
	}

	@Override
	protected void save(CommentType toSave) {
		commentTypeService.updateCommentType(toSave);
	}
}
