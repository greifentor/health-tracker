package de.ollie.healthtracker.gui.swing.select.medication;

import de.ollie.healthtracker.core.service.ManufacturerService;
import de.ollie.healthtracker.core.service.MedicationService;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.medication.MedicationEditJInternalFrame;
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
public class MedicationSelectJPanel extends AbstractSelectJPanel<Medication> implements SelectionPanelObserver {

	private final MedicationService medicationService;
	private final ManufacturerService manufacturerService;

	public MedicationSelectJPanel(
		MedicationService medicationService,
		ManufacturerService manufacturerService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className + "s", editDialogComponentFactory, observer);
		this.medicationService = medicationService;
		this.manufacturerService = manufacturerService;
		updateTableSelection();
	}

	@Override
	protected List<Medication> getObjectsToSelect() {
		return medicationService != null ? medicationService.listMedications().stream().toList() : List.of();
	}

	@Override
	protected AbstractSelectionTableModel<Medication> createSelectionModel() {
		return new AbstractSelectionTableModel<Medication>(getObjectsToSelect(), "Name", "Manufacturer") {
			@Override
			protected Object getColumnValueFor(Medication t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> t.getName();
					case 1 -> (t.getManufacturer() != null ? t.getManufacturer().getName() : "-");
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(Medication selected) {
		new MedicationEditJInternalFrame(
			selected,
			() -> manufacturerService.listManufacturers(),
			getEditDialogComponentFactory(),
			this,
			getDesktopPane()
		);
	}

	@Override
	protected Medication createNewObject() {
		return new Medication().setId(UUID.randomUUID()).setName("").setManufacturer(null);
	}

	@Override
	protected void delete(Medication toDelete) {
		medicationService.deleteMedication(toDelete.getId());
	}

	@Override
	protected void save(Medication toSave) {
		medicationService.updateMedication(toSave);
	}
}
