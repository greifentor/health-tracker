package de.ollie.healthtracker.gui.swing.select.exercise;

import de.ollie.healthtracker.core.service.BodyPartService;
import de.ollie.healthtracker.core.service.ExerciseService;
import de.ollie.healthtracker.core.service.model.Exercise;
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
public class ExerciseSelectJInternalFrame
	extends AbstractSelectJInternalFrame<Exercise>
	implements SelectionPanelObserver {

	private static final String CLASS_NAME = "Exercise";

	private final ExerciseService exerciseService;
	private final BodyPartService bodyPartService;

	public ExerciseSelectJInternalFrame(
		ExerciseService exerciseService,
		BodyPartService bodyPartService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME + "s", editDialogComponentFactory);
		this.exerciseService = exerciseService;
		this.bodyPartService = bodyPartService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<Exercise> createSelectPanel() {
		return new ExerciseSelectJPanel(
			exerciseService,
			bodyPartService,
			CLASS_NAME,
			desktopPane,
			editDialogComponentFactory,
			this
		);
	}
}
