package de.ollie.healthtracker.gui.swing.select.medicationunit;

import de.ollie.healthtracker.core.service.MedicationUnitService;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.medicationunit.MedicationUnitEditJInternalFrame;
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
public class MedicationUnitSelectJPanel extends AbstractSelectJPanel<MedicationUnit> implements SelectionPanelObserver {

	private final MedicationUnitService medicationUnitService;

	public MedicationUnitSelectJPanel(
		MedicationUnitService medicationUnitService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className + "s", editDialogComponentFactory, observer);
		this.medicationUnitService = medicationUnitService;
		updateTableSelection();
	}

	@Override
	protected List<MedicationUnit> getObjectsToSelect() {
		return medicationUnitService != null
			? medicationUnitService
				.listMedicationUnits()
				.stream()
				.sorted((d0, d1) -> d1.getName().compareTo(d0.getName()))
				.toList()
			: List.of();
	}

	@Override
	protected AbstractSelectionTableModel<MedicationUnit> createSelectionModel() {
		return new AbstractSelectionTableModel<MedicationUnit>(getObjectsToSelect(), "Name", "Token") {
			@Override
			protected Object getColumnValueFor(MedicationUnit t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> t.getName();
					case 1 -> t.getToken();
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(MedicationUnit selected) {
		new MedicationUnitEditJInternalFrame(selected, getEditDialogComponentFactory(), this, getDesktopPane());
	}

	@Override
	protected MedicationUnit createNewObject() {
		return medicationUnitService.createMedicationUnit(null, null);
	}

	@Override
	protected void delete(MedicationUnit toDelete) {
		medicationUnitService.deleteMedicationUnit(toDelete.getId());
	}

	@Override
	protected void save(MedicationUnit toSave) {
		medicationUnitService.updateMedicationUnit(toSave);
	}
}
