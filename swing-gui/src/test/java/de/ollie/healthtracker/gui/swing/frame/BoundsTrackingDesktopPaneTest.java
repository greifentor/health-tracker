package de.ollie.healthtracker.gui.swing.frame;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.awt.Component;
import java.awt.Rectangle;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BoundsTrackingDesktopPaneTest {

	@Mock
	private InternalFrameBoundsManager boundsManager;

	@InjectMocks
	private BoundsTrackingDesktopPane unitUnderTest;

	@Test
	void handsAnAddedInternalFrameToTheManagerKeyedByItsClassNameWithItsBoundsAsDefault() {
		// Prepare
		JInternalFrame frame = new JInternalFrame();
		frame.setBounds(10, 20, 700, 480);
		// Run
		unitUnderTest.add(frame);
		// Check
		verify(boundsManager).applyBounds(frame, unitUnderTest, "JInternalFrame", new Rectangle(10, 20, 700, 480));
	}

	@Test
	void doesNotCallTheManagerForNonInternalFrameComponents() {
		// Prepare
		JPanel panel = new JPanel();
		// Run
		unitUnderTest.add(panel);
		// Check
		verifyNoInteractions(boundsManager);
	}

	@Test
	void returnsTheAddedComponent() {
		// Prepare
		JInternalFrame frame = new JInternalFrame();
		// Run
		Component result = unitUnderTest.add(frame);
		// Check
		assertSame(frame, result);
	}
}
