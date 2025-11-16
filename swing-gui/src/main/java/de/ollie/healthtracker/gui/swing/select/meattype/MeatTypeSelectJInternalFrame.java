package de.ollie.healthtracker.gui.swing.select.meattype;

import de.ollie.healthtracker.core.service.MeatTypeService;
import de.ollie.healthtracker.core.service.model.MeatType;
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
public class MeatTypeSelectJInternalFrame
	extends AbstractSelectJInternalFrame<MeatType>
	implements SelectionPanelObserver {

	private static final String CLASS_NAME = "MeatType";

	private final MeatTypeService meatTypeService;

	public MeatTypeSelectJInternalFrame(
		MeatTypeService meatTypeService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME + "s", editDialogComponentFactory);
		this.meatTypeService = meatTypeService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<MeatType> createSelectPanel() {
		return new MeatTypeSelectJPanel(meatTypeService, CLASS_NAME, desktopPane, editDialogComponentFactory, this);
	}
}
