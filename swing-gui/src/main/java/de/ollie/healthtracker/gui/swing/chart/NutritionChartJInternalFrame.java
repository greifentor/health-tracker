package de.ollie.healthtracker.gui.swing.chart;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

/**
 * An internal frame that displays a {@link NutritionChartJComponent} for the list of {@link NutritionChartData} objects
 * passed to its constructor.
 */
public class NutritionChartJInternalFrame extends JInternalFrame {

	public NutritionChartJInternalFrame(JDesktopPane desktopPane, List<NutritionChartData> data) {
		super("Nutrition Chart", true, true, true, true);
		NutritionChartJComponent chart = new NutritionChartJComponent();
		chart.setData(data);
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, HGAP, VGAP));
		JButton buttonClose = new JButton("Close");
		buttonClose.addActionListener(e -> dispose());
		buttonPanel.add(buttonClose);
		JPanel contentPanel = new JPanel(new BorderLayout(HGAP, VGAP));
		contentPanel.add(chart, BorderLayout.CENTER);
		contentPanel.add(buttonPanel, BorderLayout.SOUTH);
		setContentPane(contentPanel);
		setBounds(10, 10, 700, 480);
		desktopPane.add(this);
		try {
			setSelected(true);
		} catch (java.beans.PropertyVetoException e) {
			e.printStackTrace();
		}
		setVisible(true);
	}
}
