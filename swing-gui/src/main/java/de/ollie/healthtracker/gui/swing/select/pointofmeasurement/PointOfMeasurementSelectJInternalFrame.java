package de.ollie.healthtracker.gui.swing.select.pointofmeasurement;

import de.ollie.healthtracker.core.service.PointOfMeasurementService;
import de.ollie.healthtracker.core.service.model.PointOfMeasurement;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJPanel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import java.math.BigDecimal;
import java.util.UUID;
import javax.swing.JDesktopPane;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public class PointOfMeasurementSelectJInternalFrame
	extends AbstractSelectJInternalFrame<PointOfMeasurement>
	implements SelectionPanelObserver {

	private static final String CLASS_NAME = "PointOfMeasurement";

	private final PointOfMeasurementService pointOfMeasurementService;

	public PointOfMeasurementSelectJInternalFrame(
		PointOfMeasurementService pointOfMeasurementService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME, editDialogComponentFactory);
		this.pointOfMeasurementService = pointOfMeasurementService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<PointOfMeasurement> createSelectPanel() {
		return new PointOfMeasurementSelectJPanel(
			pointOfMeasurementService,
			CLASS_NAME,
			desktopPane,
			editDialogComponentFactory,
			this
		);
	}
}
