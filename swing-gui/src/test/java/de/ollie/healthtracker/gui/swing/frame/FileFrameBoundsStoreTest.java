package de.ollie.healthtracker.gui.swing.frame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Rectangle;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileFrameBoundsStoreTest {

	@TempDir
	private Path directory;

	@Test
	void loadReturnsAnEmptyMapWhenNoFileExists() {
		// Prepare
		FileFrameBoundsStore unitUnderTest = new FileFrameBoundsStore(directory.toString());
		// Run
		Map<String, Rectangle> result = unitUnderTest.load();
		// Check
		assertTrue(result.isEmpty());
	}

	@Test
	void loadReturnsTheBoundsThatWereSaved() {
		// Prepare
		new FileFrameBoundsStore(directory.toString()).save(Map.of("SomeFrame", new Rectangle(10, 20, 300, 400)));
		FileFrameBoundsStore unitUnderTest = new FileFrameBoundsStore(directory.toString());
		// Run
		Map<String, Rectangle> result = unitUnderTest.load();
		// Check
		assertEquals(new Rectangle(10, 20, 300, 400), result.get("SomeFrame"));
	}

	@Test
	void createsTheConfiguredDirectoryWhenSaving() {
		// Prepare
		Path nestedDirectory = directory.resolve("nested");
		FileFrameBoundsStore unitUnderTest = new FileFrameBoundsStore(nestedDirectory.toString());
		// Run
		unitUnderTest.save(Map.of("SomeFrame", new Rectangle(1, 2, 3, 4)));
		// Check
		assertTrue(Files.isRegularFile(nestedDirectory.resolve("frame-bounds.properties")));
	}
}
