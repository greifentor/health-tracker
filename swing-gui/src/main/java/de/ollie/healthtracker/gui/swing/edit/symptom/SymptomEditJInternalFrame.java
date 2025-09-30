package de.ollie.healthtracker.gui.swing.edit.symptom;

import de.ollie.healthtracker.core.service.SymptomService;
import de.ollie.healthtracker.core.service.model.BodyPart;
import de.ollie.healthtracker.core.service.model.Symptom;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditJInternalFrame;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public class SymptomEditJInternalFrame extends AbstractEditJInternalFrame<Symptom> {

	public SymptomEditJInternalFrame(
		Symptom toEdit,
		ItemProvider<BodyPart> BodyParts,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<Symptom> observer,
		JDesktopPane desktopPane
	) {
		super(
			desktopPane,
			"Symptom",
			toEdit,
			editDialogComponentFactory,
			observer,
			Map.of(SymptomEditJPanel.BODY_PART_ITEM_PROVIDER_ID, BodyParts)
		);
	}

	@Override
	protected JPanel createEditorPanel(Symptom toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new SymptomEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
