package de.ollie.healthtracker.gui.swing.select.alcoholconsumption;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.AlcoholConsumptionService;
import de.ollie.healthtracker.core.service.AlcoholProductService;
import de.ollie.healthtracker.core.service.model.AlcoholConsumption;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.alcoholconsumption.AlcoholConsumptionEditJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJPanel;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectionTableModel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public class AlcoholConsumptionSelectJPanel
	extends AbstractSelectJPanel<AlcoholConsumption>
	implements SelectionPanelObserver {

	private final AlcoholConsumptionService alcoholConsumptionService;
	private final AlcoholProductService alcoholProductService;

	public AlcoholConsumptionSelectJPanel(
		AlcoholConsumptionService alcoholConsumptionService,
		AlcoholProductService alcoholProductService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className + "s", editDialogComponentFactory, observer);
		this.alcoholConsumptionService = alcoholConsumptionService;
		this.alcoholProductService = alcoholProductService;
		updateTableSelection();
	}

	@Override
	protected List<AlcoholConsumption> getObjectsToSelect() {
		return alcoholConsumptionService != null
			? alcoholConsumptionService.listAlcoholConsumptions().stream().toList()
			: List.of();
	}

	@Override
	protected AbstractSelectionTableModel<AlcoholConsumption> createSelectionModel() {
		return new AbstractSelectionTableModel<AlcoholConsumption>(
			getObjectsToSelect(),
			"Date",
			"Alcohol Product",
			"Comment",
			"Liter"
		) {
			@Override
			protected Object getColumnValueFor(AlcoholConsumption t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> DateTimeUtil.DE_DATE_FORMAT.format(t.getDate());
					case 1 -> (t.getAlcoholProduct() != null ? t.getAlcoholProduct().getName() : "-");
					case 2 -> t.getComment();
					case 3 -> t.getLiter();
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(AlcoholConsumption selected) {
		new AlcoholConsumptionEditJInternalFrame(
			selected,
			() -> alcoholProductService.listAlcoholProducts(),
			getEditDialogComponentFactory(),
			this,
			getDesktopPane()
		);
	}

	@Override
	protected AlcoholConsumption createNewObject() {
		return new AlcoholConsumption()
			.setId(UUID.randomUUID())
			.setDate(LocalDate.now())
			.setAlcoholProduct(null)
			.setComment("")
			.setLiter(null);
	}

	@Override
	protected void delete(AlcoholConsumption toDelete) {
		alcoholConsumptionService.deleteAlcoholConsumption(toDelete.getId());
	}

	@Override
	protected void save(AlcoholConsumption toSave) {
		alcoholConsumptionService.updateAlcoholConsumption(toSave);
	}
}
