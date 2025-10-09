package de.ollie.healthtracker.gui.swing.select.symptom;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.BodyPartService;
import de.ollie.healthtracker.core.service.SymptomService;
import de.ollie.healthtracker.core.service.model.Symptom;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.symptom.SymptomEditJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJPanel;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectionTableModel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import javax.swing.JDesktopPane;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public class SymptomSelectJPanel extends AbstractSelectJPanel<Symptom> implements SelectionPanelObserver {

	private final SymptomService symptomService;
	private final BodyPartService bodyPartService;

	public SymptomSelectJPanel(
		SymptomService symptomService,
		BodyPartService bodyPartService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className + "s", editDialogComponentFactory, observer);
		this.symptomService = symptomService;
		this.bodyPartService = bodyPartService;
		updateTableSelection();
	}

	@Override
	protected List<Symptom> getObjectsToSelect() {
		return symptomService != null ? symptomService.listSymptoms().stream().toList() : List.of();
	}

	@Override
	protected AbstractSelectionTableModel<Symptom> createSelectionModel() {
		return new AbstractSelectionTableModel<Symptom>(
			getObjectsToSelect(),
			"Date Of Recording",
			"Description",
			"Body Part"
		) {
			@Override
			protected Object getColumnValueFor(Symptom t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> DateTimeUtil.DE_DATE_FORMAT.format(t.getDateOfRecording());
					case 1 -> t.getDescription();
					case 2 -> (t.getBodyPart() != null ? t.getBodyPart().getName() : "-");
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(Symptom selected) {
		new SymptomEditJInternalFrame(
			selected,
			() -> bodyPartService.listBodyParts(),
			getEditDialogComponentFactory(),
			this,
			getDesktopPane()
		);
	}

	@Override
	protected Symptom createNewObject() {
		return new Symptom()
			.setId(UUID.randomUUID())
			.setDescription("")
			.setDateOfRecording(LocalDate.now())
			.setBodyPart(null);
	}

	@Override
	protected void delete(Symptom toDelete) {
		symptomService.deleteSymptom(toDelete.getId());
	}

	@Override
	protected void save(Symptom toSave) {
		symptomService.updateSymptom(toSave);
	}
}
