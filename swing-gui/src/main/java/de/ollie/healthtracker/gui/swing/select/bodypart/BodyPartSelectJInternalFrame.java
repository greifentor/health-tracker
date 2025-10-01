package de.ollie.healthtracker.gui.swing.select.bodypart;

import de.ollie.healthtracker.core.service.BodyPartService;
import de.ollie.healthtracker.core.service.GeneralBodyPartService;
import de.ollie.healthtracker.core.service.model.BodyPart;
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
public class BodyPartSelectJInternalFrame
	extends AbstractSelectJInternalFrame<BodyPart>
	implements SelectionPanelObserver {

	private static final String CLASS_NAME = "BodyPart";

	private final BodyPartService bodyPartService;
	private final GeneralBodyPartService generalBodyPartService;

	public BodyPartSelectJInternalFrame(
		BodyPartService bodyPartService,
		GeneralBodyPartService generalBodyPartService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME + "s", editDialogComponentFactory);
		this.bodyPartService = bodyPartService;
		this.generalBodyPartService = generalBodyPartService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<BodyPart> createSelectPanel() {
		return new BodyPartSelectJPanel(
			bodyPartService,
			generalBodyPartService,
			CLASS_NAME,
			desktopPane,
			editDialogComponentFactory,
			this
		);
	}
}
