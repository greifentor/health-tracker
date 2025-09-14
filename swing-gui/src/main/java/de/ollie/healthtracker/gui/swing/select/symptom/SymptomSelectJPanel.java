package de.ollie.healthtracker.gui.swing.select.symptom;

import de.ollie.baselib.util.DateTimeUtil;
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

	public SymptomSelectJPanel(
		SymptomService symptomService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className + "s", editDialogComponentFactory, observer);
		this.symptomService = symptomService;
		updateTableSelection();
	}

	@Override
	protected List<Symptom> getObjectsToSelect() {
		return symptomService != null
			? symptomService
				.listSymptoms()
				.stream()
				.sorted((d0, d1) -> d1.getDateOfRecording().compareTo(d0.getDateOfRecording()))
				.toList()
			: List.of();
	}

	@Override
	protected AbstractSelectionTableModel<Symptom> createSelectionModel() {
		return new AbstractSelectionTableModel<Symptom>(getObjectsToSelect(), "DateOfRecording", "Description") {
			@Override
			protected Object getColumnValueFor(Symptom t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> DateTimeUtil.DE_DATE_FORMAT.format(t.getDateOfRecording());
					case 1 -> t.getDescription();
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(Symptom selected) {
		new SymptomEditJInternalFrame(selected, getClassName(), getEditDialogComponentFactory(), this, getDesktopPane());
	}

	@Override
	protected Symptom createNewObject() {
		return symptomService.createSymptom("?", LocalDate.now());
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
