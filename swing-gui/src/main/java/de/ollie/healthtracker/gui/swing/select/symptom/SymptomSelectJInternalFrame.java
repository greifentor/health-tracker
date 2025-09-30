package de.ollie.healthtracker.gui.swing.select.symptom;

import de.ollie.healthtracker.core.service.BodyPartService;
import de.ollie.healthtracker.core.service.SymptomService;
import de.ollie.healthtracker.core.service.model.Symptom;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJPanel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import javax.swing.JDesktopPane;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public class SymptomSelectJInternalFrame
	extends AbstractSelectJInternalFrame<Symptom>
	implements SelectionPanelObserver {

	private static final String CLASS_NAME = "Symptom";

	private final SymptomService symptomService;
	private final BodyPartService bodyPartService;

	public SymptomSelectJInternalFrame(
		SymptomService symptomService,
		BodyPartService bodyPartService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME + "s", editDialogComponentFactory);
		this.symptomService = symptomService;
		this.bodyPartService = bodyPartService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<Symptom> createSelectPanel() {
		return new SymptomSelectJPanel(
			symptomService,
			bodyPartService,
			CLASS_NAME,
			desktopPane,
			editDialogComponentFactory,
			this
		);
	}
}
