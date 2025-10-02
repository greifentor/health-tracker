package de.ollie.healthtracker.gui.swing.select.exercise;

import de.ollie.healthtracker.core.service.BodyPartService;
import de.ollie.healthtracker.core.service.ExerciseService;
import de.ollie.healthtracker.core.service.model.Exercise;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.exercise.ExerciseEditJInternalFrame;
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
public class ExerciseSelectJPanel extends AbstractSelectJPanel<Exercise> implements SelectionPanelObserver {

	private final ExerciseService exerciseService;
	private final BodyPartService bodyPartService;

	public ExerciseSelectJPanel(
		ExerciseService exerciseService,
		BodyPartService bodyPartService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className + "s", editDialogComponentFactory, observer);
		this.exerciseService = exerciseService;
		this.bodyPartService = bodyPartService;
		updateTableSelection();
	}

	@Override
	protected List<Exercise> getObjectsToSelect() {
		return exerciseService != null
			? exerciseService.listExercises().stream().sorted((d0, d1) -> d1.getName().compareTo(d0.getName())).toList()
			: List.of();
	}

	@Override
	protected AbstractSelectionTableModel<Exercise> createSelectionModel() {
		return new AbstractSelectionTableModel<Exercise>(getObjectsToSelect(), "Name", "Body Part", "Description") {
			@Override
			protected Object getColumnValueFor(Exercise t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> t.getName();
					case 1 -> (t.getBodyPart() != null ? t.getBodyPart().getName() : "-");
					case 2 -> t.getDescription();
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(Exercise selected) {
		new ExerciseEditJInternalFrame(
			selected,
			() -> bodyPartService.listBodyParts(),
			getEditDialogComponentFactory(),
			this,
			getDesktopPane()
		);
	}

	@Override
	protected Exercise createNewObject() {
		return new Exercise().setId(UUID.randomUUID()).setBodyPart(null).setDescription("").setName("");
	}

	@Override
	protected void delete(Exercise toDelete) {
		exerciseService.deleteExercise(toDelete.getId());
	}

	@Override
	protected void save(Exercise toSave) {
		exerciseService.updateExercise(toSave);
	}
}
