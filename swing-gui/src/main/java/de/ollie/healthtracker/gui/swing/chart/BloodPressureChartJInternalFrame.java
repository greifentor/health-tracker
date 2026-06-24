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
 * An internal frame that displays a {@link BloodPressureChartJComponent} for the daily blood pressure values of a month
 * passed to its constructor. A close button in the bottom-right corner disposes the frame.
 */
public class BloodPressureChartJInternalFrame extends JInternalFrame {

	public BloodPressureChartJInternalFrame(
		JDesktopPane desktopPane,
		List<BloodPressureChartData> data,
		int daysInMonth
	) {
		super("Blood Pressure Chart", true, true, true, true);
		BloodPressureChartJComponent chart = new BloodPressureChartJComponent();
		chart.setData(data, daysInMonth);
		JButton buttonClose = new JButton("Close");
		buttonClose.addActionListener(e -> dispose());
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, HGAP, VGAP));
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
