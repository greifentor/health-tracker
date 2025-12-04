package de.ollie.healthtracker.gui.swing.select.commenttype;

import de.ollie.healthtracker.core.service.CommentTypeService;
import de.ollie.healthtracker.core.service.model.CommentType;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJPanel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import java.util.UUID;
import javax.swing.JDesktopPane;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public class CommentTypeSelectJInternalFrame
	extends AbstractSelectJInternalFrame<CommentType>
	implements SelectionPanelObserver {

	private static final String CLASS_NAME = "CommentType";

	private final CommentTypeService commentTypeService;

	public CommentTypeSelectJInternalFrame(
		CommentTypeService commentTypeService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME + "s", editDialogComponentFactory);
		this.commentTypeService = commentTypeService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<CommentType> createSelectPanel() {
		return new CommentTypeSelectJPanel(commentTypeService, CLASS_NAME, desktopPane, editDialogComponentFactory, this);
	}
}
