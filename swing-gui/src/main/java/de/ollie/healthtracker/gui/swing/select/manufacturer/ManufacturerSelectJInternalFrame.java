package de.ollie.healthtracker.gui.swing.select.manufacturer;

import de.ollie.healthtracker.core.service.ManufacturerService;
import de.ollie.healthtracker.core.service.model.Manufacturer;
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
public class ManufacturerSelectJInternalFrame
	extends AbstractSelectJInternalFrame<Manufacturer>
	implements SelectionPanelObserver {

	private static final String CLASS_NAME = "Manufacturer";

	private final ManufacturerService manufacturerService;

	public ManufacturerSelectJInternalFrame(
		ManufacturerService manufacturerService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME + "s", editDialogComponentFactory);
		this.manufacturerService = manufacturerService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<Manufacturer> createSelectPanel() {
		return new ManufacturerSelectJPanel(manufacturerService, CLASS_NAME, desktopPane, editDialogComponentFactory, this);
	}
}
