package de.ollie.healthtracker.gui.swing.edit;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.gui.swing.Editor;
import de.ollie.healthtracker.gui.swing.ItemProvider;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
}
