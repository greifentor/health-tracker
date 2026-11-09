package de.ollie.healthtracker.gui.swing.select.longtermsymptom;

import de.ollie.healthtracker.core.service.BodyPartService;
import de.ollie.healthtracker.core.service.LongTermSymptomService;
import de.ollie.healthtracker.core.service.model.LongTermSymptom;
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
public class LongTermSymptomSelectJInternalFrame
	extends AbstractSelectJInternalFrame<LongTermSymptom>
	implements SelectionPanelObserver {

	private static final String CLASS_NAME = "LongTermSymptom";

	private final LongTermSymptomService longTermSymptomService;
	private final BodyPartService bodyPartService;

	public LongTermSymptomSelectJInternalFrame(
		LongTermSymptomService longTermSymptomService,
		BodyPartService bodyPartService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME, editDialogComponentFactory);
		this.longTermSymptomService = longTermSymptomService;
		this.bodyPartService = bodyPartService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<LongTermSymptom> createSelectPanel() {
		return new LongTermSymptomSelectJPanel(
			longTermSymptomService,
			bodyPartService,
			CLASS_NAME,
			desktopPane,
			editDialogComponentFactory,
			this
		);
	}
}
