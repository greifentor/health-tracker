package de.ollie.healthtracker.gui.swing.select.meatconsumption;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.MeatConsumptionService;
import de.ollie.healthtracker.core.service.model.MeatConsumption;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.edit.meatconsumption.MeatConsumptionEditJInternalFrame;
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
public class MeatConsumptionSelectJPanel
	extends AbstractSelectJPanel<MeatConsumption>
	implements SelectionPanelObserver {

	private final MeatConsumptionService meatConsumptionService;

	public MeatConsumptionSelectJPanel(
		MeatConsumptionService meatConsumptionService,
		String className,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory,
		SelectionPanelObserver observer
	) {
		super(desktopPane, className + "s", editDialogComponentFactory, observer);
		this.meatConsumptionService = meatConsumptionService;
		updateTableSelection();
	}

	@Override
	protected List<MeatConsumption> getObjectsToSelect() {
		return meatConsumptionService != null
			? meatConsumptionService
				.listMeatConsumptions()
				.stream()
				.sorted((d0, d1) -> d1.getDateOfRecording().compareTo(d0.getDateOfRecording()))
				.toList()
			: List.of();
	}

	@Override
	protected AbstractSelectionTableModel<MeatConsumption> createSelectionModel() {
		return new AbstractSelectionTableModel<MeatConsumption>(getObjectsToSelect(), "Date Of Recording", "Description") {
			@Override
			protected Object getColumnValueFor(MeatConsumption t, int columnIndex) {
				return switch (columnIndex) {
					case 0 -> DateTimeUtil.DE_DATE_FORMAT.format(t.getDateOfRecording());
					case 1 -> t.getDescription();
					default -> null;
				};
			}
		};
	}

	@Override
	protected void createEditInternalFrame(MeatConsumption selected) {
		new MeatConsumptionEditJInternalFrame(selected, getEditDialogComponentFactory(), this, getDesktopPane());
	}

	@Override
	protected MeatConsumption createNewObject() {
		return new MeatConsumption().setId(UUID.randomUUID()).setDateOfRecording(LocalDate.now()).setDescription("");
	}

	@Override
	protected void delete(MeatConsumption toDelete) {
		meatConsumptionService.deleteMeatConsumption(toDelete.getId());
	}

	@Override
	protected void save(MeatConsumption toSave) {
		meatConsumptionService.updateMeatConsumption(toSave);
	}
}
