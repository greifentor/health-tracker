package de.ollie.healthtracker.gui.swing.select.meattype;

import de.ollie.healthtracker.core.service.MeatTypeService;
import de.ollie.healthtracker.core.service.model.MeatType;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.meattype.MeatTypeEditJInternalFrame;
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
public class MeatTypeSelectJPanel extends AbstractSelectJPanel<MeatType> implements SelectionPanelObserver {

	private final MeatTypeService meatTypeService;

	public MeatTypeSelectJPanel(
		MeatTypeService meatTypeService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className + "s", editDialogComponentFactory, observer);
		this.meatTypeService = meatTypeService;
		updateTableSelection();
	}

	@Override
	protected List<MeatType> getObjectsToSelect() {
		return meatTypeService != null ? meatTypeService.listMeatTypes().stream().toList() : List.of();
	}

	@Override
	protected AbstractSelectionTableModel<MeatType> createSelectionModel() {
		return new AbstractSelectionTableModel<MeatType>(getObjectsToSelect(), "Name") {
			@Override
			protected Object getColumnValueFor(MeatType t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> t.getName();
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(MeatType selected) {
		new MeatTypeEditJInternalFrame(selected, getEditDialogComponentFactory(), this, getDesktopPane());
	}

	@Override
	protected MeatType createNewObject() {
		return new MeatType().setId(UUID.randomUUID()).setName("");
	}

	@Override
	protected void delete(MeatType toDelete) {
		meatTypeService.deleteMeatType(toDelete.getId());
	}

	@Override
	protected void save(MeatType toSave) {
		meatTypeService.updateMeatType(toSave);
	}
}
