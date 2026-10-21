package de.ollie.healthtracker.gui.swing.chart.nutrition;

import static de.ollie.healthtracker.gui.swing.Constants.HGAP;
import static de.ollie.healthtracker.gui.swing.Constants.VGAP;

import de.ollie.healthtracker.gui.swing.event.MeatConsumptionChangeNotifier;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import java.util.function.Supplier;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 * An internal frame that displays a {@link NutritionChartJComponent} for the number of months selected in the frame's month
 * spinner (ending with the current month). The chart is (re)loaded from {@code dataSupplier} when the frame opens, whenever
 * the selected number of months changes and whenever the {@link MeatConsumptionChangeNotifier} signals a change, so it
 * stays in sync with the meat consumption data. The change listener is removed again when the frame is closed.
 */
public class NutritionChartJInternalFrame extends JInternalFrame {

	private static final int MIN_MONTHS = 1;
	private static final int MAX_MONTHS = 12;

	public NutritionChartJInternalFrame(
		JDesktopPane desktopPane,
		Supplier<List<NutritionChartData>> dataSupplier,
		int months,
		MeatConsumptionChangeNotifier changeNotifier
	) {
		super("Nutrition Chart", true, true, true, true);
		NutritionChartJComponent chart = new NutritionChartJComponent();
		JSpinner monthsSpinner = new JSpinner(new SpinnerNumberModel(clampToRange(months), MIN_MONTHS, MAX_MONTHS, 1));
		Runnable reload = () -> chart.setData(dataSupplier.get(), ((Number) monthsSpinner.getValue()).intValue());
		monthsSpinner.addChangeListener(e -> reload.run());
		reload.run();
		changeNotifier.addListener(reload);
		addInternalFrameListener(
			new InternalFrameAdapter() {
				@Override
				public void internalFrameClosed(InternalFrameEvent e) {
					changeNotifier.removeListener(reload);
				}
			}
		);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JButton buttonClose = new JButton("Close");
		buttonClose.addActionListener(e -> dispose());
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, HGAP, VGAP));
		buttonPanel.add(new JLabel("Months:"));
		buttonPanel.add(monthsSpinner);
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

	private static int clampToRange(int months) {
		return Math.max(MIN_MONTHS, Math.min(MAX_MONTHS, months));
	}
}
