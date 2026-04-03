package de.ollie.healthtracker.gui.swing.select.generalbodypart;

import de.ollie.healthtracker.core.service.GeneralBodyPartService;
import de.ollie.healthtracker.core.service.model.GeneralBodyPart;
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
public class GeneralBodyPartSelectJInternalFrame
	extends AbstractSelectJInternalFrame<GeneralBodyPart>
	implements SelectionPanelObserver {

	private static final String CLASS_NAME = "GeneralBodyPart";

	private final GeneralBodyPartService generalBodyPartService;

	public GeneralBodyPartSelectJInternalFrame(
		GeneralBodyPartService generalBodyPartService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME + "s", editDialogComponentFactory);
		this.generalBodyPartService = generalBodyPartService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<GeneralBodyPart> createSelectPanel() {
		return new GeneralBodyPartSelectJPanel(
			generalBodyPartService,
			CLASS_NAME,
			desktopPane,
			editDialogComponentFactory,
			this
		);
	}
}
