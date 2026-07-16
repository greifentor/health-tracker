package de.ollie.healthtracker.gui.swing.edit.bodytemperaturemeasurement;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.model.BodyTemperatureMeasurement;
import de.ollie.healthtracker.core.service.model.PointOfMeasurement;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditPanel;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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
public class BodyTemperatureMeasurementEditJPanel extends AbstractEditPanel<BodyTemperatureMeasurement> {

	public static final String POINT_OF_MEASUREMENT_ITEM_PROVIDER_ID = "point-of-measurement-item-provider";

	private JTextField textFieldDateOfRecording;
	private JTextField textFieldTimeOfRecording;
	private JSpinner spinnerCelsius;
	private JComboBox<PointOfMeasurement> comboBoxPointOfMeasurement;
	private JTextField textFieldComment;

	public BodyTemperatureMeasurementEditJPanel(
		BodyTemperatureMeasurement toEdit,
		Map<String, ItemProvider<?>> itemProviders
	) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		return createLabelSubPanel(
			"Date Of Recording:",
			"Time Of Recording:",
			"Celsius:",
			"Point Of Measurement:",
			"Comment:"
		);
	}

	@Override
	protected JPanel createComponentPanel(BodyTemperatureMeasurement toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(5, 1, HGAP, VGAP));
		textFieldDateOfRecording = new JTextField(DateTimeUtil.DE_DATE_FORMAT.format(toEdit.getDateOfRecording()), 40);
		p.add(textFieldDateOfRecording);
		textFieldTimeOfRecording = new JTextField(DateTimeUtil.DE_TIME_FORMAT.format(toEdit.getTimeOfRecording()), 40);
		p.add(textFieldTimeOfRecording);
		spinnerCelsius = createDecimalSpinner(toEdit.getCelsius(), 30, 45, 0.1, 1);
		p.add(spinnerCelsius);
		List<PointOfMeasurement> listPointOfMeasurement =
			((ItemProvider<PointOfMeasurement>) itemProviders.get(POINT_OF_MEASUREMENT_ITEM_PROVIDER_ID)).getItem();
		comboBoxPointOfMeasurement =
			new JComboBox<>(listPointOfMeasurement.toArray(new PointOfMeasurement[listPointOfMeasurement.size()]));
		comboBoxPointOfMeasurement.setSelectedItem(toEdit.getPointOfMeasurement());
		comboBoxPointOfMeasurement.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
			if (value != null) {
				return new JLabel(value.getName());
			}
			return new JLabel("-");
		});
		p.add(comboBoxPointOfMeasurement);
		textFieldComment = new JTextField(toEdit.getComment(), 40);
		p.add(textFieldComment);
		return p;
	}

	@Override
	public BodyTemperatureMeasurement getCurrentContent() {
		return new BodyTemperatureMeasurement()
			.setId(toEdit.getId())
			.setDateOfRecording(DateTimeUtil.dateFromString(textFieldDateOfRecording.getText()))
			.setTimeOfRecording(DateTimeUtil.timeFromString(textFieldTimeOfRecording.getText()))
			.setCelsius(decimalValueOf(spinnerCelsius))
			.setPointOfMeasurement(((PointOfMeasurement) comboBoxPointOfMeasurement.getSelectedItem()))
			.setComment(textFieldComment.getText().isEmpty() ? null : textFieldComment.getText());
	}
}
