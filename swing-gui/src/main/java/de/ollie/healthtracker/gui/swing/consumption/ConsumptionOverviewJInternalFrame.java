package de.ollie.healthtracker.gui.swing.consumption;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * An internal frame that lists the consumption of meat, fish (both in grams, each broken down into its meat types as
 * indented sub-positions) and alcohol (in litres of pure alcohol), as provided by a {@link ConsumptionOverviewProvider}.
 * The overview is loaded from the provider when the frame opens.
 */
public class ConsumptionOverviewJInternalFrame extends JInternalFrame {

	private static final String SUB_POSITION_INDENT = "    ";

	public ConsumptionOverviewJInternalFrame(JDesktopPane desktopPane, ConsumptionOverviewProvider provider) {
		super("Consumption Overview", true, true, true, true);
		DefaultTableModel tableModel = new DefaultTableModel(new Object[] { "Position", "Verbrauch" }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		provider
			.create()
			.forEach(row ->
				tableModel.addRow(
					new Object[] { row.subPosition() ? SUB_POSITION_INDENT + row.label() : row.label(), row.amount() }
				)
			);
		JTable table = new JTable(tableModel);
		table.setRowSelectionAllowed(false);
		DefaultTableCellRenderer rightAlignedRenderer = new DefaultTableCellRenderer();
		rightAlignedRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		table.getColumnModel().getColumn(1).setCellRenderer(rightAlignedRenderer);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JButton buttonClose = new JButton("Close");
		buttonClose.addActionListener(e -> dispose());
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, HGAP, VGAP));
		buttonPanel.add(buttonClose);
		JPanel contentPanel = new JPanel(new BorderLayout(HGAP, VGAP));
		contentPanel.add(new JScrollPane(table), BorderLayout.CENTER);
		contentPanel.add(buttonPanel, BorderLayout.SOUTH);
		setContentPane(contentPanel);
		setBounds(10, 10, 420, 400);
		desktopPane.add(this);
		try {
			setSelected(true);
		} catch (java.beans.PropertyVetoException e) {
			e.printStackTrace();
		}
		setVisible(true);
	}
}
