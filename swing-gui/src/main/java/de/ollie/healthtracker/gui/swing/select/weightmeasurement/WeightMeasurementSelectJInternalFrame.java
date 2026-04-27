package de.ollie.healthtracker.gui.swing.select.weightmeasurement;

import de.ollie.healthtracker.core.service.WeightMeasurementService;
import de.ollie.healthtracker.core.service.model.WeightMeasurement;
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
public class WeightMeasurementSelectJInternalFrame
	extends AbstractSelectJInternalFrame<WeightMeasurement>
	implements SelectionPanelObserver {

	private static final String CLASS_NAME = "WeightMeasurement";

	private final WeightMeasurementService weightMeasurementService;

	public WeightMeasurementSelectJInternalFrame(
		WeightMeasurementService weightMeasurementService,
		JDesktopPane desktopPane,
		EditDialogComponentFactory editDialogComponentFactory
	) {
		super(desktopPane, CLASS_NAME, editDialogComponentFactory);
		this.weightMeasurementService = weightMeasurementService;
		finishConstruct();
	}

	@Override
	protected AbstractSelectJPanel<WeightMeasurement> createSelectPanel() {
		return new WeightMeasurementSelectJPanel(
			weightMeasurementService,
			CLASS_NAME,
			desktopPane,
			editDialogComponentFactory,
			this
		);
	}
}
