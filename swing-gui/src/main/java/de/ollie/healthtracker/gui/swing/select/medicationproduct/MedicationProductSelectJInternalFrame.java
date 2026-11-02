package de.ollie.healthtracker.gui.swing.select.medicationproduct;

import de.ollie.healthtracker.core.service.MedicationProductService;
import de.ollie.healthtracker.core.service.MedicationService;
import de.ollie.healthtracker.core.service.MedicationUnitService;
import de.ollie.healthtracker.core.service.model.MedicationProduct;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJPanel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import javax.swing.JDesktopPane;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public class MedicationProductSelectJInternalFrame
	extends AbstractSelectJInternalFrame<MedicationProduct>
	implements SelectionPanelObserver {

	private static final String CLASS_NAME = "MedicationProduct";

	private final MedicationProductService medicationProductService;
	private final MedicationService medicationService;
	private final MedicationUnitService medicationUnitService;

	public MedicationProductSelectJInternalFrame(
		MedicationProductService medicationProductService,
		MedicationService medicationService,
		MedicationUnitService medicationUnitService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME, editDialogComponentFactory);
		this.medicationProductService = medicationProductService;
		this.medicationService = medicationService;
		this.medicationUnitService = medicationUnitService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<MedicationProduct> createSelectPanel() {
		return new MedicationProductSelectJPanel(
			medicationProductService,
			medicationService,
			medicationUnitService,
			CLASS_NAME,
			desktopPane,
			editDialogComponentFactory,
			this
		);
	}
}
