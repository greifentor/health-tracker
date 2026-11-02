package de.ollie.healthtracker.gui.swing.select.medicationproduct;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.MedicationProductService;
import de.ollie.healthtracker.core.service.MedicationService;
import de.ollie.healthtracker.core.service.MedicationUnitService;
import de.ollie.healthtracker.core.service.model.MedicationProduct;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.medicationproduct.MedicationProductEditJInternalFrame;
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
public class MedicationProductSelectJPanel
	extends AbstractSelectJPanel<MedicationProduct>
	implements SelectionPanelObserver {

	private final MedicationProductService medicationProductService;
	private final MedicationService medicationService;
	private final MedicationUnitService medicationUnitService;

	public MedicationProductSelectJPanel(
		MedicationProductService medicationProductService,
		MedicationService medicationService,
		MedicationUnitService medicationUnitService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className + "s", editDialogComponentFactory, observer);
		this.medicationProductService = medicationProductService;
		this.medicationService = medicationService;
		this.medicationUnitService = medicationUnitService;
		updateTableSelection();
	}

	@Override
	protected List<MedicationProduct> getObjectsToSelect() {
		return medicationProductService != null
			? medicationProductService.listMedicationProducts().stream().toList()
			: List.of();
	}

	@Override
	protected AbstractSelectionTableModel<MedicationProduct> createSelectionModel() {
		return new AbstractSelectionTableModel<MedicationProduct>(
			getObjectsToSelect(),
			"Expiration Date",
			"Medication",
			"Package Size",
			"Medication Unit"
		) {
			@Override
			protected Object getColumnValueFor(MedicationProduct t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> DateTimeUtil.DE_DATE_FORMAT.format(t.getExpirationDate());
					case 1 -> (t.getMedication() != null ? t.getMedication().getName() : "-");
					case 2 -> t.getPackageSize() == null ? null : t.getPackageSize().setScale(1, java.math.RoundingMode.HALF_UP);
					case 3 -> (t.getMedicationUnit() != null ? t.getMedicationUnit().getName() : "-");
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(MedicationProduct selected) {
		new MedicationProductEditJInternalFrame(
			selected,
			() -> medicationService.listMedications(),
			() -> medicationUnitService.listMedicationUnits(),
			getEditDialogComponentFactory(),
			this,
			getDesktopPane()
		);
	}

	@Override
	protected MedicationProduct createNewObject() {
		return new MedicationProduct()
			.setId(UUID.randomUUID())
			.setExpirationDate(LocalDate.now())
			.setMedication(null)
			.setPackageSize(null)
			.setMedicationUnit(null);
	}

	@Override
	protected void delete(MedicationProduct toDelete) {
		medicationProductService.deleteMedicationProduct(toDelete.getId());
	}

	@Override
	protected void save(MedicationProduct toSave) {
		medicationProductService.updateMedicationProduct(toSave);
	}
}
