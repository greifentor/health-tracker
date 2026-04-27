package de.ollie.healthtracker.gui.swing.select.meatconsumption;

import de.ollie.healthtracker.core.service.MeatConsumptionService;
import de.ollie.healthtracker.core.service.MeatProductService;
import de.ollie.healthtracker.core.service.model.MeatConsumption;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJPanel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
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
public class MeatConsumptionSelectJInternalFrame
	extends AbstractSelectJInternalFrame<MeatConsumption>
	implements SelectionPanelObserver {

	private static final String CLASS_NAME = "MeatConsumption";

	private final MeatConsumptionService meatConsumptionService;
	private final MeatProductService meatProductService;

	public MeatConsumptionSelectJInternalFrame(
		MeatConsumptionService meatConsumptionService,
		MeatProductService meatProductService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME, editDialogComponentFactory);
		this.meatConsumptionService = meatConsumptionService;
		this.meatProductService = meatProductService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<MeatConsumption> createSelectPanel() {
		return new MeatConsumptionSelectJPanel(
			meatConsumptionService,
			meatProductService,
			CLASS_NAME,
			desktopPane,
			editDialogComponentFactory,
			this
		);
	}
}
