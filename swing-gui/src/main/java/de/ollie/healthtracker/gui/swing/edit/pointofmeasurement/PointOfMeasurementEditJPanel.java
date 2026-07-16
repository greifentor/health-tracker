package de.ollie.healthtracker.gui.swing.edit.pointofmeasurement;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.core.service.model.PointOfMeasurement;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditPanel;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public class PointOfMeasurementEditJPanel extends AbstractEditPanel<PointOfMeasurement> {

	private JTextField textFieldName;
	private JSpinner spinnerRegularMaxCelsius;
	private JSpinner spinnerRegularMinCelsius;

	public PointOfMeasurementEditJPanel(PointOfMeasurement toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		return createLabelSubPanel("Name:", "Regular Max Celsius:", "Regular Min Celsius:");
	}

	@Override
	protected JPanel createComponentPanel(PointOfMeasurement toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(3, 1, HGAP, VGAP));
		textFieldName = new JTextField(toEdit.getName(), 40);
		p.add(textFieldName);
		spinnerRegularMaxCelsius = createDecimalSpinner(toEdit.getRegularMaxCelsius(), 30, 45, 0.1, 1);
		p.add(spinnerRegularMaxCelsius);
		spinnerRegularMinCelsius = createDecimalSpinner(toEdit.getRegularMinCelsius(), 30, 45, 0.1, 1);
		p.add(spinnerRegularMinCelsius);
		return p;
	}

	@Override
	public PointOfMeasurement getCurrentContent() {
		return new PointOfMeasurement()
			.setId(toEdit.getId())
			.setName(textFieldName.getText())
			.setRegularMaxCelsius(decimalValueOf(spinnerRegularMaxCelsius))
			.setRegularMinCelsius(decimalValueOf(spinnerRegularMinCelsius));
	}
}
