package de.ollie.healthtracker.gui.swing.select.alcoholproduct;

import de.ollie.healthtracker.core.service.AlcoholProductService;
import de.ollie.healthtracker.core.service.model.AlcoholProduct;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJPanel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import java.math.BigDecimal;
import java.util.UUID;
import javax.swing.JDesktopPane;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public class AlcoholProductSelectJInternalFrame
	extends AbstractSelectJInternalFrame<AlcoholProduct>
	implements SelectionPanelObserver {

	private static final String CLASS_NAME = "AlcoholProduct";

	private final AlcoholProductService alcoholProductService;

	public AlcoholProductSelectJInternalFrame(
		AlcoholProductService alcoholProductService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME, editDialogComponentFactory);
		this.alcoholProductService = alcoholProductService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<AlcoholProduct> createSelectPanel() {
		return new AlcoholProductSelectJPanel(
			alcoholProductService,
			CLASS_NAME,
			desktopPane,
			editDialogComponentFactory,
			this
		);
	}
}
