package de.ollie.healthtracker.gui.swing.select.alcoholconsumption;

import de.ollie.healthtracker.core.service.AlcoholConsumptionService;
import de.ollie.healthtracker.core.service.AlcoholProductService;
import de.ollie.healthtracker.core.service.model.AlcoholConsumption;
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
public class AlcoholConsumptionSelectJInternalFrame
	extends AbstractSelectJInternalFrame<AlcoholConsumption>
	implements SelectionPanelObserver {

	private static final String CLASS_NAME = "AlcoholConsumption";

	private final AlcoholConsumptionService alcoholConsumptionService;
	private final AlcoholProductService alcoholProductService;

	public AlcoholConsumptionSelectJInternalFrame(
		AlcoholConsumptionService alcoholConsumptionService,
		AlcoholProductService alcoholProductService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME, editDialogComponentFactory);
		this.alcoholConsumptionService = alcoholConsumptionService;
		this.alcoholProductService = alcoholProductService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<AlcoholConsumption> createSelectPanel() {
		return new AlcoholConsumptionSelectJPanel(
			alcoholConsumptionService,
			alcoholProductService,
			CLASS_NAME,
			desktopPane,
			editDialogComponentFactory,
			this
		);
	}
}
