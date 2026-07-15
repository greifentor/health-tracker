package de.ollie.healthtracker.gui.swing.select.bodytemperaturemeasurement;

import de.ollie.healthtracker.core.service.BodyTemperatureMeasurementService;
import de.ollie.healthtracker.core.service.model.BodyTemperatureMeasurement;
import de.ollie.healthtracker.gui.swing.EditDialogComponentFactory;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJInternalFrame;
import de.ollie.healthtracker.gui.swing.select.AbstractSelectJPanel;
import de.ollie.healthtracker.gui.swing.select.SelectionPanelObserver;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import javax.swing.JDesktopPane;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public class BodyTemperatureMeasurementSelectJInternalFrame
	extends AbstractSelectJInternalFrame<BodyTemperatureMeasurement>
	implements SelectionPanelObserver {

	private static final String CLASS_NAME = "BodyTemperatureMeasurement";

	private final BodyTemperatureMeasurementService bodyTemperatureMeasurementService;

	public BodyTemperatureMeasurementSelectJInternalFrame(
		BodyTemperatureMeasurementService bodyTemperatureMeasurementService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME, editDialogComponentFactory);
		this.bodyTemperatureMeasurementService = bodyTemperatureMeasurementService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<BodyTemperatureMeasurement> createSelectPanel() {
		return new BodyTemperatureMeasurementSelectJPanel(
			bodyTemperatureMeasurementService,
			CLASS_NAME,
			desktopPane,
			editDialogComponentFactory,
			this
		);
	}
}
