package de.ollie.healthtracker.gui.swing.edit.longtermsymptom;

import de.ollie.healthtracker.core.service.LongTermSymptomService;
import de.ollie.healthtracker.core.service.model.BodyPart;
import de.ollie.healthtracker.core.service.model.LongTermSymptom;
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
public class LongTermSymptomEditJInternalFrame extends AbstractEditJInternalFrame<LongTermSymptom> {

	public LongTermSymptomEditJInternalFrame(
		LongTermSymptom toEdit,
		ItemProvider<BodyPart> bodyParts,
		EditDialogComponentFactory editDialogComponentFactory,
		Observer<LongTermSymptom> observer,
		JDesktopPane desktopPane
	) {
		super(
			desktopPane,
			"Long Term Symptom",
			toEdit,
			editDialogComponentFactory,
			observer,
			Map.of(LongTermSymptomEditJPanel.BODY_PART_ITEM_PROVIDER_ID, bodyParts)
		);
	}

	@Override
	protected JPanel createEditorPanel(LongTermSymptom toEdit, Map<String, ItemProvider<?>> itemProviders) {
		editPanel = new LongTermSymptomEditJPanel(toEdit, itemProviders);
		return editPanel;
	}
}
