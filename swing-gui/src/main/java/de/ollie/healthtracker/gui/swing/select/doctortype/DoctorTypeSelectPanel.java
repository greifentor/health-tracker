package de.ollie.healthtracker.gui.swing.select.doctortype;

import de.ollie.healthtracker.core.service.DoctorTypeService;
import de.ollie.healthtracker.core.service.model.DoctorType;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.doctortype.DoctorTypeEditInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectPanel;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectionTableModel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import java.util.List;
import javax.swing.JDesktopPane;

public class DoctorTypeSelectPanel extends AbstractSelectPanel<DoctorType> implements SelectionPanelObserver {

	private final DoctorTypeService doctorTypeService;

	public DoctorTypeSelectPanel(
		DoctorTypeService doctorTypeService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className, editDialogComponentFactory, observer);
		this.doctorTypeService = doctorTypeService;
		updateTableSelection();
	}

	@Override
	protected List<DoctorType> getObjectsToSelect() {
		return doctorTypeService != null
			? doctorTypeService.listDoctorTypes().stream().sorted((d0, d1) -> d0.getName().compareTo(d1.getName())).toList()
			: List.of();
	}

	@Override
	protected AbstractSelectionTableModel<DoctorType> createSelectionModel() {
		return new AbstractSelectionTableModel<DoctorType>(getObjectsToSelect(), "Name") {
			@Override
			protected Object getColumnValueFor(DoctorType t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> t.getName();
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(DoctorType selected) {
		new DoctorTypeEditInternalFrame(selected, getClassName(), getEditDialogComponentFactory(), this, getDesktopPane());
	}

	@Override
	protected DoctorType createNewObject() {
		return doctorTypeService.createDoctorType("?");
	}

	@Override
	protected void delete(DoctorType toDelete) {
		doctorTypeService.deleteDoctorType(toDelete.getId());
	}

	@Override
	protected void save(DoctorType toSave) {
		doctorTypeService.updateDoctorType(toSave);
	}
}
