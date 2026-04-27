package de.ollie.healthtracker.gui.swing.select.meatproduct;

import de.ollie.healthtracker.core.service.MeatProductService;
import de.ollie.healthtracker.core.service.MeatTypeService;
import de.ollie.healthtracker.core.service.model.MeatProduct;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJPanel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import java.util.UUID;
import javax.swing.JDesktopPane;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public class MeatProductSelectJInternalFrame
	extends AbstractSelectJInternalFrame<MeatProduct>
	implements SelectionPanelObserver {

	private static final String CLASS_NAME = "MeatProduct";

	private final MeatProductService meatProductService;
	private final MeatTypeService meatTypeService;

	public MeatProductSelectJInternalFrame(
		MeatProductService meatProductService,
		MeatTypeService meatTypeService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME, editDialogComponentFactory);
		this.meatProductService = meatProductService;
		this.meatTypeService = meatTypeService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<MeatProduct> createSelectPanel() {
		return new MeatProductSelectJPanel(
			meatProductService,
			meatTypeService,
			CLASS_NAME,
			desktopPane,
			editDialogComponentFactory,
			this
		);
	}
}
