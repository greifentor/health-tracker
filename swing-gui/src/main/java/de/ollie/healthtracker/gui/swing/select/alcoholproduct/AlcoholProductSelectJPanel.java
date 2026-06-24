package de.ollie.healthtracker.gui.swing.select.alcoholproduct;

import de.ollie.healthtracker.core.service.AlcoholProductService;
import de.ollie.healthtracker.core.service.model.AlcoholProduct;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.alcoholproduct.AlcoholProductEditJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJPanel;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectionTableModel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import java.math.BigDecimal;
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
public class AlcoholProductSelectJPanel extends AbstractSelectJPanel<AlcoholProduct> implements SelectionPanelObserver {

	private final AlcoholProductService alcoholProductService;

	public AlcoholProductSelectJPanel(
		AlcoholProductService alcoholProductService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className + "s", editDialogComponentFactory, observer);
		this.alcoholProductService = alcoholProductService;
		updateTableSelection();
	}

	@Override
	protected List<AlcoholProduct> getObjectsToSelect() {
		return alcoholProductService != null ? alcoholProductService.listAlcoholProducts().stream().toList() : List.of();
	}

	@Override
	protected AbstractSelectionTableModel<AlcoholProduct> createSelectionModel() {
		return new AbstractSelectionTableModel<AlcoholProduct>(getObjectsToSelect(), "Name", "Percent Vol") {
			@Override
			protected Object getColumnValueFor(AlcoholProduct t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> t.getName();
					case 1 -> t.getPercentVol();
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(AlcoholProduct selected) {
		new AlcoholProductEditJInternalFrame(selected, getEditDialogComponentFactory(), this, getDesktopPane());
	}

	@Override
	protected AlcoholProduct createNewObject() {
		return new AlcoholProduct().setId(UUID.randomUUID()).setName("").setPercentVol(null);
	}

	@Override
	protected void delete(AlcoholProduct toDelete) {
		alcoholProductService.deleteAlcoholProduct(toDelete.getId());
	}

	@Override
	protected void save(AlcoholProduct toSave) {
		alcoholProductService.updateAlcoholProduct(toSave);
	}
}
