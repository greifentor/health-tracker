package de.ollie.healthtracker.gui.swing.select.generalbodypart;

import de.ollie.healthtracker.core.service.GeneralBodyPartService;
import de.ollie.healthtracker.core.service.model.GeneralBodyPart;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.generalbodypart.GeneralBodyPartEditJInternalFrame;
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
public class GeneralBodyPartSelectJPanel
	extends AbstractSelectJPanel<GeneralBodyPart>
	implements SelectionPanelObserver {

	private final GeneralBodyPartService generalBodyPartService;

	public GeneralBodyPartSelectJPanel(
		GeneralBodyPartService generalBodyPartService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className + "s", editDialogComponentFactory, observer);
		this.generalBodyPartService = generalBodyPartService;
		updateTableSelection();
	}

	@Override
	protected List<GeneralBodyPart> getObjectsToSelect() {
		return generalBodyPartService != null
			? generalBodyPartService
				.listGeneralBodyParts()
				.stream()
				.sorted((d0, d1) -> d1.getName().compareTo(d0.getName()))
				.toList()
			: List.of();
	}

	@Override
	protected AbstractSelectionTableModel<GeneralBodyPart> createSelectionModel() {
		return new AbstractSelectionTableModel<GeneralBodyPart>(getObjectsToSelect(), "Name") {
			@Override
			protected Object getColumnValueFor(GeneralBodyPart t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> t.getName();
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(GeneralBodyPart selected) {
		new GeneralBodyPartEditJInternalFrame(selected, getEditDialogComponentFactory(), this, getDesktopPane());
	}

	@Override
	protected GeneralBodyPart createNewObject() {
		return new GeneralBodyPart().setId(UUID.randomUUID()).setName("");
	}

	@Override
	protected void delete(GeneralBodyPart toDelete) {
		generalBodyPartService.deleteGeneralBodyPart(toDelete.getId());
	}

	@Override
	protected void save(GeneralBodyPart toSave) {
		generalBodyPartService.updateGeneralBodyPart(toSave);
	}
}
