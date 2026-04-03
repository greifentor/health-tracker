package de.ollie.healthtracker.gui.swing.select.meatconsumption;

import de.ollie.healthtracker.core.service.MeatConsumptionService;
import de.ollie.healthtracker.core.service.MeatTypeService;
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
	private final MeatTypeService meatTypeService;

	public MeatConsumptionSelectJInternalFrame(
		MeatConsumptionService meatConsumptionService,
		MeatTypeService meatTypeService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME + "s", editDialogComponentFactory);
		this.meatConsumptionService = meatConsumptionService;
		this.meatTypeService = meatTypeService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<MeatConsumption> createSelectPanel() {
		return new MeatConsumptionSelectJPanel(
			meatConsumptionService,
			meatTypeService,
			CLASS_NAME,
			desktopPane,
			editDialogComponentFactory,
			this
		);
	}
}
