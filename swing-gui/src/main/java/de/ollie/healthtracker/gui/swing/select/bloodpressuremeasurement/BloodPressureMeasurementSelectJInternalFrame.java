package de.ollie.healthtracker.gui.swing.select.bloodpressuremeasurement;

import de.ollie.healthtracker.core.service.BloodPressureMeasurementService;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJPanel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import javax.swing.JDesktopPane;

public class BloodPressureMeasurementSelectJInternalFrame
	extends AbstractSelectJInternalFrame<BloodPressureMeasurement>
	implements SelectionPanelObserver {

	private static final String CLASS_NAME = "Blood Pressure Measurement";

	private final BloodPressureMeasurementService bloodPressureMeasurementService;

	public BloodPressureMeasurementSelectJInternalFrame(
		BloodPressureMeasurementService bloodPressureMeasurementService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME + "s", editDialogComponentFactory);
		this.bloodPressureMeasurementService = bloodPressureMeasurementService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<BloodPressureMeasurement> createSelectPanel() {
		return new BloodPressureMeasurementSelectJPanel(
			bloodPressureMeasurementService,
			CLASS_NAME,
			desktopPane,
			editDialogComponentFactory,
			this
		);
	}
}
