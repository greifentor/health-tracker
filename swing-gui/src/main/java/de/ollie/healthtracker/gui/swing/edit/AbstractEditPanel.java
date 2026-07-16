package de.ollie.healthtracker.gui.swing.edit;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.gui.swing.Editor;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

public abstract class AbstractEditPanel<T> extends JPanel implements Editor<T> {

	protected T toEdit;

	public AbstractEditPanel(T toEdit, Map<String, ItemProvider<?>> itemProviders) {
		super(new BorderLayout(HGAP, VGAP));
		this.toEdit = toEdit;
		setBorder(new EmptyBorder(VGAP, HGAP, VGAP, HGAP));
		JPanel psub = new JPanel(new BorderLayout(HGAP, VGAP));
		psub.add(createLabelPanel(), BorderLayout.WEST);
		psub.add(createComponentPanel(toEdit, itemProviders), BorderLayout.CENTER);
		JPanel additionalPanel = createAdditionalPanel(toEdit, itemProviders);
		if (additionalPanel != null) {
			add(additionalPanel, BorderLayout.CENTER);
		}
		add(psub, BorderLayout.NORTH);
	}

	protected JPanel createAdditionalPanel(T toEdit, Map<String, ItemProvider<?>> itemProviders) {
		return null;
	}

	protected abstract JPanel createLabelPanel();

	protected JPanel createLabelSubPanel(String... labels) {
		JPanel p = new JPanel(new GridLayout(labels.length, 1, HGAP, VGAP));
		for (String label : labels) {
			p.add(new JLabel(label));
		}
		return p;
	}

	protected abstract JPanel createComponentPanel(T toEdit, Map<String, ItemProvider<?>> itemProviders);

	/**
	 * Creates a {@link JSpinner} for editing a {@link BigDecimal} value. The spinner steps by {@code step} within
	 * [{@code min}, {@code max}], displays {@code fractionDigits} decimal places and additionally lets the '+' / '-' keys
	 * step the value. A {@code null} value starts at {@code min}; an out-of-range value is clamped into the range.
	 */
	protected JSpinner createDecimalSpinner(BigDecimal value, double min, double max, double step, int fractionDigits) {
		double start = value == null ? min : value.doubleValue();
		start = Math.max(min, Math.min(max, start));
		JSpinner spinner = new JSpinner(new SpinnerNumberModel(start, min, max, step));
		spinner.setEditor(
			new JSpinner.NumberEditor(spinner, fractionDigits <= 0 ? "0" : "0." + "0".repeat(fractionDigits))
		);
		installStepKeyBindings(spinner);
		return spinner;
	}

	/** Reads the current value of a spinner created by {@link #createDecimalSpinner} back into a {@link BigDecimal}. */
	protected BigDecimal decimalValueOf(JSpinner spinner) {
		return BigDecimal.valueOf(((Number) spinner.getValue()).doubleValue());
	}

	/** Binds the '+' / '-' keys of the spinner's editor to step the value up / down by the model step. */
	private void installStepKeyBindings(JSpinner spinner) {
		if (!(spinner.getEditor() instanceof JSpinner.DefaultEditor editor)) {
			return;
		}
		JFormattedTextField field = editor.getTextField();
		InputMap inputMap = field.getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap actionMap = field.getActionMap();
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
}
