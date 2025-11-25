package de.ollie.healthtracker.gui.swing.select.manufacturer;

import de.ollie.healthtracker.core.service.ManufacturerService;
import de.ollie.healthtracker.core.service.model.Manufacturer;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.manufacturer.ManufacturerEditJInternalFrame;
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
public class ManufacturerSelectJPanel extends AbstractSelectJPanel<Manufacturer> implements SelectionPanelObserver {

	private final ManufacturerService manufacturerService;

	public ManufacturerSelectJPanel(
		ManufacturerService manufacturerService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className + "s", editDialogComponentFactory, observer);
		this.manufacturerService = manufacturerService;
		updateTableSelection();
	}

	@Override
	protected List<Manufacturer> getObjectsToSelect() {
		return manufacturerService != null ? manufacturerService.listManufacturers().stream().toList() : List.of();
	}

	@Override
	protected AbstractSelectionTableModel<Manufacturer> createSelectionModel() {
		return new AbstractSelectionTableModel<Manufacturer>(getObjectsToSelect(), "Name") {
			@Override
			protected Object getColumnValueFor(Manufacturer t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> t.getName();
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(Manufacturer selected) {
		new ManufacturerEditJInternalFrame(selected, getEditDialogComponentFactory(), this, getDesktopPane());
	}

	@Override
	protected Manufacturer createNewObject() {
		return new Manufacturer().setId(UUID.randomUUID()).setName("");
	}

	@Override
	protected void delete(Manufacturer toDelete) {
		manufacturerService.deleteManufacturer(toDelete.getId());
	}

	@Override
	protected void save(Manufacturer toSave) {
		manufacturerService.updateManufacturer(toSave);
	}
}
