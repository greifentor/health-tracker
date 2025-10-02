package de.ollie.healthtracker.gui.swing.select.bodypart;

import de.ollie.healthtracker.core.service.BodyPartService;
import de.ollie.healthtracker.core.service.GeneralBodyPartService;
import de.ollie.healthtracker.core.service.model.BodyPart;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.bodypart.BodyPartEditJInternalFrame;
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
public class BodyPartSelectJPanel extends AbstractSelectJPanel<BodyPart> implements SelectionPanelObserver {

	private final BodyPartService bodyPartService;
	private final GeneralBodyPartService generalBodyPartService;

	public BodyPartSelectJPanel(
		BodyPartService bodyPartService,
		GeneralBodyPartService generalBodyPartService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className + "s", editDialogComponentFactory, observer);
		this.bodyPartService = bodyPartService;
		this.generalBodyPartService = generalBodyPartService;
		updateTableSelection();
	}

	@Override
	protected List<BodyPart> getObjectsToSelect() {
		return bodyPartService != null
			? bodyPartService.listBodyParts().stream().sorted((d0, d1) -> d1.getName().compareTo(d0.getName())).toList()
			: List.of();
	}

	@Override
	protected AbstractSelectionTableModel<BodyPart> createSelectionModel() {
		return new AbstractSelectionTableModel<BodyPart>(getObjectsToSelect(), "Name", "General Body Part") {
			@Override
			protected Object getColumnValueFor(BodyPart t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> t.getName();
					case 1 -> (t.getGeneralBodyPart() != null ? t.getGeneralBodyPart().getName() : "-");
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(BodyPart selected) {
		new BodyPartEditJInternalFrame(
			selected,
			() -> generalBodyPartService.listGeneralBodyParts(),
			getEditDialogComponentFactory(),
			this,
			getDesktopPane()
		);
	}

	@Override
	protected BodyPart createNewObject() {
		return new BodyPart().setId(UUID.randomUUID()).setGeneralBodyPart(null).setName("");
	}

	@Override
	protected void delete(BodyPart toDelete) {
		bodyPartService.deleteBodyPart(toDelete.getId());
	}

	@Override
	protected void save(BodyPart toSave) {
		bodyPartService.updateBodyPart(toSave);
	}
}
