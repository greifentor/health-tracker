package de.ollie.healthtracker.gui.swing.select.longtermsymptom;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.BodyPartService;
import de.ollie.healthtracker.core.service.LongTermSymptomService;
import de.ollie.healthtracker.core.service.model.LongTermSymptom;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.longtermsymptom.LongTermSymptomEditJInternalFrame;
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
public class LongTermSymptomSelectJPanel
	extends AbstractSelectJPanel<LongTermSymptom>
	implements SelectionPanelObserver {

	private final LongTermSymptomService longTermSymptomService;
	private final BodyPartService bodyPartService;

	public LongTermSymptomSelectJPanel(
		LongTermSymptomService longTermSymptomService,
		BodyPartService bodyPartService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className + "s", editDialogComponentFactory, observer);
		this.longTermSymptomService = longTermSymptomService;
		this.bodyPartService = bodyPartService;
		updateTableSelection();
	}

	@Override
	protected List<LongTermSymptom> getObjectsToSelect() {
		return longTermSymptomService != null ? longTermSymptomService.listLongTermSymptoms().stream().toList() : List.of();
	}

	@Override
	protected AbstractSelectionTableModel<LongTermSymptom> createSelectionModel() {
		return new AbstractSelectionTableModel<LongTermSymptom>(
			getObjectsToSelect(),
			"Start Date",
			"End Date",
			"Body Part",
			"Description",
			"Unverified"
		) {
			@Override
			protected Object getColumnValueFor(LongTermSymptom t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> DateTimeUtil.DE_DATE_FORMAT.format(t.getStartDate());
					case 1 -> DateTimeUtil.DE_DATE_FORMAT.format(t.getEndDate());
					case 2 -> (t.getBodyPart() != null ? t.getBodyPart().getName() : "-");
					case 3 -> t.getDescription();
					case 4 -> t.isUnverified();
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(LongTermSymptom selected) {
		new LongTermSymptomEditJInternalFrame(
			selected,
			() -> bodyPartService.listBodyParts(),
			getEditDialogComponentFactory(),
			this,
			getDesktopPane()
		);
	}

	@Override
	protected LongTermSymptom createNewObject() {
		return new LongTermSymptom()
			.setId(UUID.randomUUID())
			.setStartDate(LocalDate.now())
			.setEndDate(LocalDate.now())
			.setBodyPart(null)
			.setDescription("")
			.setUnverified(false);
	}

	@Override
	protected void delete(LongTermSymptom toDelete) {
		longTermSymptomService.deleteLongTermSymptom(toDelete.getId());
	}

	@Override
	protected void save(LongTermSymptom toSave) {
		longTermSymptomService.updateLongTermSymptom(toSave);
	}
}
