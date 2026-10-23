package de.ollie.healthtracker.gui.swing.select.alcoholtype;

import de.ollie.healthtracker.core.service.AlcoholTypeService;
import de.ollie.healthtracker.core.service.model.AlcoholType;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.alcoholtype.AlcoholTypeEditJInternalFrame;
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
public class AlcoholTypeSelectJPanel extends AbstractSelectJPanel<AlcoholType> implements SelectionPanelObserver {

	private final AlcoholTypeService alcoholTypeService;

	public AlcoholTypeSelectJPanel(
		AlcoholTypeService alcoholTypeService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className + "s", editDialogComponentFactory, observer);
		this.alcoholTypeService = alcoholTypeService;
		updateTableSelection();
	}

	@Override
	protected List<AlcoholType> getObjectsToSelect() {
		return alcoholTypeService != null ? alcoholTypeService.listAlcoholTypes().stream().toList() : List.of();
	}

	@Override
	protected AbstractSelectionTableModel<AlcoholType> createSelectionModel() {
		return new AbstractSelectionTableModel<AlcoholType>(getObjectsToSelect(), "Name") {
			@Override
			protected Object getColumnValueFor(AlcoholType t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> t.getName();
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(AlcoholType selected) {
		new AlcoholTypeEditJInternalFrame(selected, getEditDialogComponentFactory(), this, getDesktopPane());
	}

	@Override
	protected AlcoholType createNewObject() {
		return new AlcoholType().setId(UUID.randomUUID()).setName("");
	}

	@Override
	protected void delete(AlcoholType toDelete) {
		alcoholTypeService.deleteAlcoholType(toDelete.getId());
	}

	@Override
	protected void save(AlcoholType toSave) {
		alcoholTypeService.updateAlcoholType(toSave);
	}
}
