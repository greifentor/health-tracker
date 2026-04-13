package de.ollie.healthtracker.gui.swing.select.meatproduct;

import de.ollie.healthtracker.core.service.MeatProductService;
import de.ollie.healthtracker.core.service.MeatTypeService;
import de.ollie.healthtracker.core.service.model.MeatProduct;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.meatproduct.MeatProductEditJInternalFrame;
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
public class MeatProductSelectJPanel extends AbstractSelectJPanel<MeatProduct> implements SelectionPanelObserver {

	private final MeatProductService meatProductService;
	private final MeatTypeService meatTypeService;

	public MeatProductSelectJPanel(
		MeatProductService meatProductService,
		MeatTypeService meatTypeService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className + "s", editDialogComponentFactory, observer);
		this.meatProductService = meatProductService;
		this.meatTypeService = meatTypeService;
		updateTableSelection();
	}

	@Override
	protected List<MeatProduct> getObjectsToSelect() {
		return meatProductService != null ? meatProductService.listMeatProducts().stream().toList() : List.of();
	}

	@Override
	protected AbstractSelectionTableModel<MeatProduct> createSelectionModel() {
		return new AbstractSelectionTableModel<MeatProduct>(
			getObjectsToSelect(),
			"Description",
			"Meat Type",
			"Amount In Gr"
		) {
			@Override
			protected Object getColumnValueFor(MeatProduct t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> t.getDescription();
					case 1 -> (t.getMeatType() != null ? t.getMeatType().getName() : "-");
					case 2 -> t.getAmountInGr();
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(MeatProduct selected) {
		new MeatProductEditJInternalFrame(
			selected,
			() -> meatTypeService.listMeatTypes(),
			getEditDialogComponentFactory(),
			this,
			getDesktopPane()
		);
	}

	@Override
	protected MeatProduct createNewObject() {
		return new MeatProduct().setId(UUID.randomUUID()).setAmountInGr(0).setDescription("").setMeatType(null);
	}

	@Override
	protected void delete(MeatProduct toDelete) {
		meatProductService.deleteMeatProduct(toDelete.getId());
	}

	@Override
	protected void save(MeatProduct toSave) {
		meatProductService.updateMeatProduct(toSave);
	}
}
