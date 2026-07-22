package de.ollie.healthtracker.gui.swing.frame;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class InternalFrameBoundsManagerTest {

	private static final String KEY = "SomeChartJInternalFrame";
	private static final String OTHER_KEY = "AnotherJInternalFrame";
	private static final Dimension DESKTOP = new Dimension(1000, 800);
	private static final Rectangle DEFAULT_BOUNDS = new Rectangle(10, 10, 700, 480);

	private final InMemoryFrameBoundsStore store = new InMemoryFrameBoundsStore();

	private InternalFrameBoundsManager managerWithStored(String key, Rectangle stored) {
		store.saved.put(key, stored);
		return new InternalFrameBoundsManager(store);
	}

	private JDesktopPane desktopPane() {
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setSize(DESKTOP);
		return desktopPane;
	}

	private void fireMoved(JInternalFrame frame) {
		for (ComponentListener listener : frame.getComponentListeners()) {
			listener.componentMoved(new ComponentEvent(frame, ComponentEvent.COMPONENT_MOVED));
		}
	}

	private void fireResized(JInternalFrame frame) {
		for (ComponentListener listener : frame.getComponentListeners()) {
			listener.componentResized(new ComponentEvent(frame, ComponentEvent.COMPONENT_RESIZED));
		}
	}

	private void fireClosing(JInternalFrame frame) {
		for (InternalFrameListener listener : frame.getInternalFrameListeners()) {
			listener.internalFrameClosing(new InternalFrameEvent(frame, InternalFrameEvent.INTERNAL_FRAME_CLOSING));
		}
	}

	private void fireClosed(JInternalFrame frame) {
		for (InternalFrameListener listener : frame.getInternalFrameListeners()) {
			listener.internalFrameClosed(new InternalFrameEvent(frame, InternalFrameEvent.INTERNAL_FRAME_CLOSED));
		}
	}

	@Nested
	class ComputeBounds {

		private final InternalFrameBoundsManager unitUnderTest = new InternalFrameBoundsManager(store);

		@Test
		void returnsTheDefaultBoundsWhenNothingIsStored() {
			// Run
			Rectangle result = unitUnderTest.computeBounds(null, DESKTOP, DEFAULT_BOUNDS);
			// Check
			assertEquals(DEFAULT_BOUNDS, result);
		}

		@Test
		void returnsTheStoredBoundsWhenTheyAreFullyVisible() {
			// Run
			Rectangle result = unitUnderTest.computeBounds(new Rectangle(100, 50, 600, 400), DESKTOP, DEFAULT_BOUNDS);
			// Check
			assertEquals(new Rectangle(100, 50, 600, 400), result);
		}

		@Test
		void treatsTheRightAndBottomEdgeAsStillVisible() {
			// Run
			Rectangle result = unitUnderTest.computeBounds(new Rectangle(400, 400, 600, 400), DESKTOP, DEFAULT_BOUNDS);
			// Check
			assertEquals(new Rectangle(400, 400, 600, 400), result);
		}

		@Test
		void centersTheFrameWhenTheStoredBoundsExtendBeyondTheRightOrBottom() {
			// Run
			Rectangle result = unitUnderTest.computeBounds(new Rectangle(900, 700, 600, 400), DESKTOP, DEFAULT_BOUNDS);
			// Check
			assertEquals(new Rectangle(200, 200, 600, 400), result);
		}

		@Test
		void centersTheFrameWhenTheStoredOriginIsNegative() {
			// Run
			Rectangle result = unitUnderTest.computeBounds(new Rectangle(-10, 50, 600, 400), DESKTOP, DEFAULT_BOUNDS);
			// Check
			assertEquals(new Rectangle(200, 200, 600, 400), result);
		}

		@Test
		void clampsTheWidthToTheDesktopWidth() {
			// Run
			Rectangle result = unitUnderTest.computeBounds(new Rectangle(0, 0, 2000, 400), DESKTOP, DEFAULT_BOUNDS);
			// Check
			assertEquals(new Rectangle(0, 0, 1000, 400), result);
		}

		@Test
		void clampsTheHeightToTheDesktopHeight() {
			// Run
			Rectangle result = unitUnderTest.computeBounds(new Rectangle(0, 0, 600, 2000), DESKTOP, DEFAULT_BOUNDS);
			// Check
			assertEquals(new Rectangle(0, 0, 600, 800), result);
		}
	}

	@Nested
	class ApplyBounds {

		@Test
		void placesTheFrameAtTheStoredBoundsWhenVisible() {
			// Prepare
			InternalFrameBoundsManager unitUnderTest = managerWithStored(KEY, new Rectangle(100, 50, 600, 400));
			JInternalFrame frame = new JInternalFrame();
			// Run
			unitUnderTest.applyBounds(frame, desktopPane(), KEY, DEFAULT_BOUNDS);
			// Check
			assertEquals(new Rectangle(100, 50, 600, 400), frame.getBounds());
		}

		@Test
		void placesTheFrameAtTheDefaultBoundsWhenNothingIsStored() {
			// Prepare
			InternalFrameBoundsManager unitUnderTest = new InternalFrameBoundsManager(store);
			JInternalFrame frame = new JInternalFrame();
			// Run
			unitUnderTest.applyBounds(frame, desktopPane(), KEY, DEFAULT_BOUNDS);
			// Check
			assertEquals(DEFAULT_BOUNDS, frame.getBounds());
		}

		@Test
		void centersTheFrameWhenTheStoredBoundsAreOffscreen() {
			// Prepare
			InternalFrameBoundsManager unitUnderTest = managerWithStored(KEY, new Rectangle(900, 700, 600, 400));
			JInternalFrame frame = new JInternalFrame();
			// Run
			unitUnderTest.applyBounds(frame, desktopPane(), KEY, DEFAULT_BOUNDS);
			// Check
			assertEquals(new Rectangle(200, 200, 600, 400), frame.getBounds());
		}

		@Test
		void remembersTheAppliedBoundsImmediately() {
			// Prepare
			InternalFrameBoundsManager unitUnderTest = new InternalFrameBoundsManager(store);
			JInternalFrame frame = new JInternalFrame();
			unitUnderTest.applyBounds(frame, desktopPane(), KEY, DEFAULT_BOUNDS);
			// Run
			unitUnderTest.save();
			// Check
			assertEquals(DEFAULT_BOUNDS, store.saved.get(KEY));
		}
	}

	@Nested
	class Tracking {

		private final InternalFrameBoundsManager unitUnderTest = new InternalFrameBoundsManager(store);

		@Test
		void remembersTheBoundsAfterTheFrameHasBeenMoved() {
			// Prepare
			JInternalFrame frame = new JInternalFrame();
			unitUnderTest.applyBounds(frame, desktopPane(), KEY, DEFAULT_BOUNDS);
			frame.setBounds(200, 150, 700, 480);
			fireMoved(frame);
			// Run
			unitUnderTest.save();
			// Check
			assertEquals(new Rectangle(200, 150, 700, 480), store.saved.get(KEY));
		}

		@Test
		void remembersTheBoundsAfterTheFrameHasBeenResized() {
			// Prepare
			JInternalFrame frame = new JInternalFrame();
			unitUnderTest.applyBounds(frame, desktopPane(), KEY, DEFAULT_BOUNDS);
			frame.setBounds(100, 100, 500, 300);
			fireResized(frame);
			// Run
			unitUnderTest.save();
			// Check
			assertEquals(new Rectangle(100, 100, 500, 300), store.saved.get(KEY));
		}

		@Test
		void remembersTheBoundsWhenTheFrameIsClosedViaTheCloseButton() {
			// Prepare
			JInternalFrame frame = new JInternalFrame();
			unitUnderTest.applyBounds(frame, desktopPane(), KEY, DEFAULT_BOUNDS);
			frame.setBounds(300, 250, 700, 480);
			fireClosing(frame);
			// Run
			unitUnderTest.save();
			// Check
			assertEquals(new Rectangle(300, 250, 700, 480), store.saved.get(KEY));
		}

		@Test
		void remembersTheBoundsWhenTheFrameIsDisposed() {
			// Prepare
			JInternalFrame frame = new JInternalFrame();
			unitUnderTest.applyBounds(frame, desktopPane(), KEY, DEFAULT_BOUNDS);
			frame.setBounds(350, 275, 640, 360);
			fireClosed(frame);
			// Run
			unitUnderTest.save();
			// Check
			assertEquals(new Rectangle(350, 275, 640, 360), store.saved.get(KEY));
		}
	}

	@Nested
	class SaveAndReload {

		@Test
		void remembersTheBoundsOfSeveralFramesIndependently() {
			// Prepare
			InternalFrameBoundsManager unitUnderTest = new InternalFrameBoundsManager(store);
			unitUnderTest.applyBounds(new JInternalFrame(), desktopPane(), KEY, DEFAULT_BOUNDS);
			unitUnderTest.applyBounds(new JInternalFrame(), desktopPane(), OTHER_KEY, DEFAULT_BOUNDS);
			// Run
			unitUnderTest.save();
			// Check
			assertEquals(Set.of(KEY, OTHER_KEY), store.saved.keySet());
		}

		@Test
		void reappliesTheSavedBoundsThroughANewlyLoadedManager() {
			// Prepare
			InternalFrameBoundsManager saving = new InternalFrameBoundsManager(store);
			JInternalFrame frame = new JInternalFrame();
			saving.applyBounds(frame, desktopPane(), KEY, DEFAULT_BOUNDS);
			frame.setBounds(250, 150, 700, 480);
			fireMoved(frame);
			saving.save();
			InternalFrameBoundsManager reloaded = new InternalFrameBoundsManager(store);
			JInternalFrame reopenedFrame = new JInternalFrame();
			// Run
			reloaded.applyBounds(reopenedFrame, desktopPane(), KEY, DEFAULT_BOUNDS);
			// Check
			assertEquals(new Rectangle(250, 150, 700, 480), reopenedFrame.getBounds());
		}
	}

	private JInternalFrame visibleFrameAt(JDesktopPane desktopPane, int x, int y) {
		JInternalFrame frame = new JInternalFrame();
		frame.setBounds(x, y, 600, 400);
		frame.setVisible(true);
		desktopPane.add(frame);
		return frame;
	}

	@Nested
	class AvoidsOverlap {

		@Test
		void offsetsTheNewFrameWhenAnotherFrameOccupiesTheSamePosition() {
			// Prepare
			InternalFrameBoundsManager unitUnderTest = managerWithStored(KEY, new Rectangle(100, 50, 600, 400));
			JDesktopPane desktopPane = desktopPane();
			visibleFrameAt(desktopPane, 100, 50);
			JInternalFrame frame = new JInternalFrame();
			// Run
			unitUnderTest.applyBounds(frame, desktopPane, KEY, DEFAULT_BOUNDS);
			// Check
			assertEquals(new Rectangle(120, 70, 600, 400), frame.getBounds());
		}

		@Test
		void doesNotOffsetWhenNoOtherFrameIsAtThatPosition() {
			// Prepare
			InternalFrameBoundsManager unitUnderTest = managerWithStored(KEY, new Rectangle(100, 50, 600, 400));
			JDesktopPane desktopPane = desktopPane();
			visibleFrameAt(desktopPane, 0, 0);
			JInternalFrame frame = new JInternalFrame();
			// Run
			unitUnderTest.applyBounds(frame, desktopPane, KEY, DEFAULT_BOUNDS);
			// Check
			assertEquals(new Rectangle(100, 50, 600, 400), frame.getBounds());
		}

		@Test
		void cascadesPastSeveralFramesAtTheSamePosition() {
			// Prepare
			InternalFrameBoundsManager unitUnderTest = managerWithStored(KEY, new Rectangle(100, 50, 600, 400));
			JDesktopPane desktopPane = desktopPane();
			visibleFrameAt(desktopPane, 100, 50);
			visibleFrameAt(desktopPane, 120, 70);
			JInternalFrame frame = new JInternalFrame();
			// Run
			unitUnderTest.applyBounds(frame, desktopPane, KEY, DEFAULT_BOUNDS);
			// Check
			assertEquals(new Rectangle(140, 90, 600, 400), frame.getBounds());
		}

		@Test
		void usesTheConfiguredOffset() {
			// Prepare
			store.saved.put(KEY, new Rectangle(100, 50, 600, 400));
			InternalFrameBoundsManager unitUnderTest = new InternalFrameBoundsManager(store, 50);
			JDesktopPane desktopPane = desktopPane();
			visibleFrameAt(desktopPane, 100, 50);
			JInternalFrame frame = new JInternalFrame();
			// Run
			unitUnderTest.applyBounds(frame, desktopPane, KEY, DEFAULT_BOUNDS);
			// Check
			assertEquals(new Rectangle(150, 100, 600, 400), frame.getBounds());
		}
	}

	@Nested
	class HasStoredBounds {

		@Test
		void returnsTrueWhenTheKeyWasStoredAtConstruction() {
			// Prepare
			InternalFrameBoundsManager unitUnderTest = managerWithStored(KEY, new Rectangle(100, 50, 600, 400));
			// Run
			boolean result = unitUnderTest.hasStoredBounds(KEY);
			// Check
			assertEquals(true, result);
		}

		@Test
		void returnsFalseForAnUnknownKey() {
			// Prepare
			InternalFrameBoundsManager unitUnderTest = new InternalFrameBoundsManager(store);
			// Run
			boolean result = unitUnderTest.hasStoredBounds(KEY);
			// Check
			assertEquals(false, result);
		}

		@Test
		void returnsFalseForBoundsOnlyRecordedDuringTheCurrentSession() {
			// Prepare
			InternalFrameBoundsManager unitUnderTest = new InternalFrameBoundsManager(store);
			unitUnderTest.applyBounds(new JInternalFrame(), desktopPane(), KEY, DEFAULT_BOUNDS);
			// Run
			boolean result = unitUnderTest.hasStoredBounds(KEY);
			// Check
			assertEquals(false, result);
		}
	}

	private static class InMemoryFrameBoundsStore implements FrameBoundsStore {

		private final Map<String, Rectangle> saved = new HashMap<>();

		@Override
		public Map<String, Rectangle> load() {
			return new HashMap<>(saved);
		}

		@Override
		public void save(Map<String, Rectangle> boundsByKey) {
			saved.clear();
			boundsByKey.forEach((key, r) -> saved.put(key, new Rectangle(r)));
		}
	}
}
