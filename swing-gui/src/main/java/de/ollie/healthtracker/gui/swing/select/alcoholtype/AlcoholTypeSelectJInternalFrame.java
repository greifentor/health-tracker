package de.ollie.healthtracker.gui.swing.select.alcoholtype;

import de.ollie.healthtracker.core.service.AlcoholTypeService;
import de.ollie.healthtracker.core.service.model.AlcoholType;
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
public class AlcoholTypeSelectJInternalFrame
	extends AbstractSelectJInternalFrame<AlcoholType>
	implements SelectionPanelObserver {

	private static final String CLASS_NAME = "AlcoholType";

	private final AlcoholTypeService alcoholTypeService;

	public AlcoholTypeSelectJInternalFrame(
		AlcoholTypeService alcoholTypeService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME, editDialogComponentFactory);
		this.alcoholTypeService = alcoholTypeService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<AlcoholType> createSelectPanel() {
		return new AlcoholTypeSelectJPanel(alcoholTypeService, CLASS_NAME, desktopPane, editDialogComponentFactory, this);
	}
}
