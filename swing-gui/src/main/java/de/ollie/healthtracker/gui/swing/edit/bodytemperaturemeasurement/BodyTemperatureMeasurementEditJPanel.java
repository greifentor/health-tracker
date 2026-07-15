package de.ollie.healthtracker.gui.swing.edit.bodytemperaturemeasurement;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.model.BodyTemperatureMeasurement;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import de.ollie.healthtracker.gui.swing.edit.AbstractEditPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;

public class BodyTemperatureMeasurementEditJPanel extends AbstractEditPanel<BodyTemperatureMeasurement> {

	private JTextField textFieldDateOfRecording;
	private JTextField textFieldTimeOfRecording;
	private JSpinner spinnerCelsius;
	private JTextField textFieldComment;

	public BodyTemperatureMeasurementEditJPanel(
		BodyTemperatureMeasurement toEdit,
		Map<String, ItemProvider<?>> itemProviders
	) {
		super(toEdit, itemProviders);
	}

	@Override
	protected JPanel createLabelPanel() {
		return createLabelSubPanel("Date Of Recording:", "Time Of Recording:", "Celsius:", "Comment:");
	}

	@Override
	protected JPanel createComponentPanel(BodyTemperatureMeasurement toEdit, Map<String, ItemProvider<?>> itemProviders) {
		JPanel p = new JPanel(new GridLayout(4, 1, HGAP, VGAP));
		textFieldDateOfRecording = new JTextField(DateTimeUtil.DE_DATE_FORMAT.format(toEdit.getDateOfRecording()), 40);
		p.add(textFieldDateOfRecording);
		textFieldTimeOfRecording = new JTextField(DateTimeUtil.DE_TIME_FORMAT.format(toEdit.getTimeOfRecording()), 40);
		p.add(textFieldTimeOfRecording);
		spinnerCelsius = new JSpinner(new SpinnerNumberModel(toEdit.getCelsius().doubleValue(), 30, 45, 0.1));
		JSpinner.NumberEditor celsiusEditor = new JSpinner.NumberEditor(spinnerCelsius, "0.0");
		spinnerCelsius.setEditor(celsiusEditor);
		installStepKeyBindings(spinnerCelsius, celsiusEditor.getTextField());
		p.add(spinnerCelsius);
		textFieldComment = new JTextField(toEdit.getComment(), 40);
		p.add(textFieldComment);
		return p;
	}

	/** Binds the '+' / '-' keys to step the spinner up / down by its model step (0.1 °C). */
	private void installStepKeyBindings(JSpinner spinner, JFormattedTextField editorField) {
		InputMap inputMap = editorField.getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap actionMap = editorField.getActionMap();
		inputMap.put(KeyStroke.getKeyStroke('+'), "stepUp");
		inputMap.put(KeyStroke.getKeyStroke('-'), "stepDown");
		actionMap.put(
			"stepUp",
			new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Object next = spinner.getNextValue();
					if (next != null) {
						spinner.setValue(next);
					}
				}
			}
		);
		actionMap.put(
			"stepDown",
			new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Object previous = spinner.getPreviousValue();
					if (previous != null) {
						spinner.setValue(previous);
					}
				}
			}
		);
	}

	@Override
	public BodyTemperatureMeasurement getCurrentContent() {
		return new BodyTemperatureMeasurement()
			.setId(toEdit.getId())
			.setDateOfRecording(DateTimeUtil.dateFromString(textFieldDateOfRecording.getText()))
			.setTimeOfRecording(DateTimeUtil.timeFromString(textFieldTimeOfRecording.getText()))
			.setCelsius(new BigDecimal(((Number) spinnerCelsius.getValue()).doubleValue()))
			.setComment(textFieldComment.getText().isEmpty() ? null : textFieldComment.getText());
	}
}
