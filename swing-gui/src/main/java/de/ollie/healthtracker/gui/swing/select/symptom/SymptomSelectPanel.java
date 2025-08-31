package de.ollie.healthtracker.gui.swing.select.symptom;

import de.ollie.healthtracker.core.service.SymptomService;
import de.ollie.healthtracker.core.service.model.Symptom;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.symptom.SymptomEditInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectPanel;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectionTableModel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JDesktopPane;

public class SymptomSelectPanel extends AbstractSelectPanel<Symptom> implements SelectionPanelObserver {

	private final SymptomService symptomService;

	public SymptomSelectPanel(
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
		return new AbstractSelectionTableModel<Symptom>(getObjectsToSelect(), "Date", "Description") {
			@Override
			protected Object getColumnValueFor(Symptom t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> t.getDateOfRecording();
					case 1 -> t.getDescription();
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(Symptom selected) {
		new SymptomEditInternalFrame(selected, getClassName(), getEditDialogComponentFactory(), this, getDesktopPane());
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
