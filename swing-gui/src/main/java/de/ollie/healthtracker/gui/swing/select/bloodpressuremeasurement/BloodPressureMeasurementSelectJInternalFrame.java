package de.ollie.healthtracker.gui.swing.select.bloodpressuremeasurement;

import de.ollie.healthtracker.core.service.BloodPressureMeasurementService;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectPanel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import javax.swing.JDesktopPane;

public class BloodPressureMeasurementSelectJInternalFrame
	extends AbstractSelectJInternalFrame<BloodPressureMeasurement>
	implements SelectionPanelObserver {

	private final BloodPressureMeasurementService bloodPressureMeasurementService;

	public BloodPressureMeasurementSelectJInternalFrame(
		BloodPressureMeasurementService bloodPressureMeasurementService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, "Blood Pressure Measurements", editDialogComponentFactory);
		this.bloodPressureMeasurementService = bloodPressureMeasurementService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectPanel<BloodPressureMeasurement> createSelectPanel() {
		return new BloodPressureMeasurementSelectPanel(
			bloodPressureMeasurementService,
			desktopPane,
			editDialogComponentFactory,
			this
		);
	}
}
