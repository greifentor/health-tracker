package de.ollie.healthtracker.gui.swing.medicationproduct;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * An internal frame that lists all currently expired medication products. The contents can be refreshed via
 * {@link #setWarnings(List)} so the overview stays in sync whenever medication product data changes.
 */
public class ExpiredMedicationProductJInternalFrame extends JInternalFrame {

	private final DefaultTableModel tableModel;

	public ExpiredMedicationProductJInternalFrame(JDesktopPane desktopPane, List<String> warnings) {
		super("Abgelaufene Medication Products", true, true, true, true);
		tableModel =
			new DefaultTableModel(new Object[] { "Abgelaufene Medication Products" }, 0) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
		JTable table = new JTable(tableModel);
		table.setRowSelectionAllowed(false);
		setWarnings(warnings);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JButton buttonClose = new JButton("Close");
		buttonClose.addActionListener(e -> dispose());
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, HGAP, VGAP));
		buttonPanel.add(buttonClose);
		JPanel contentPanel = new JPanel(new BorderLayout(HGAP, VGAP));
		contentPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		contentPanel.add(buttonPanel, BorderLayout.SOUTH);
		setContentPane(contentPanel);
		setBounds(10, 10, 420, 300);
		desktopPane.add(this);
		try {
			setSelected(true);
		} catch (java.beans.PropertyVetoException e) {
			e.printStackTrace();
		}
		setVisible(true);
	}

	public void setWarnings(List<String> warnings) {
		tableModel.setRowCount(0);
		warnings.forEach(warning -> tableModel.addRow(new Object[] { warning }));
	}
}
