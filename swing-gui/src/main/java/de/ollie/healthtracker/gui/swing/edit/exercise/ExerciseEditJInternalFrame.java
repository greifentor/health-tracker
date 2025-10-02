package de.ollie.healthtracker.gui.swing.edit.exercise;

import de.ollie.healthtracker.core.service.ExerciseService;
import de.ollie.healthtracker.core.service.model.BodyPart;
import de.ollie.healthtracker.core.service.model.Exercise;
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
public class ExerciseEditJInternalFrame extends AbstractEditJInternalFrame<Exercise> {

	public ExerciseEditJInternalFrame(
		Exercise toEdit,
		ItemProvider<BodyPart> bodyParts,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<Exercise> observer,
		JDesktopPane desktopPane
	) {
		super(
			desktopPane,
			"Exercise",
			toEdit,
			editDialogComponentFactory,
			observer,
			Map.of(ExerciseEditJPanel.BODY_PART_ITEM_PROVIDER_ID, bodyParts)
		);
	}

	@Override
	protected JPanel createEditorPanel(Exercise toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new ExerciseEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
