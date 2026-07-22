package de.ollie.healthtracker.gui.swing.frame;

import jakarta.inject.Named;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;

/**
 * {@link FrameBoundsStore} backed by a properties file. Its directory is configurable via the
 * {@code app.frame-bounds.directory} property (default {@code ~/.health-tracker}); the file itself is
 * {@value #FILE_NAME}. Each entry maps a frame key to {@code "x,y,width,height"}.
 */
@Named
public class FileFrameBoundsStore implements FrameBoundsStore {

	private static final String FILE_NAME = "frame-bounds.properties";

	private final Path file;

	public FileFrameBoundsStore(@Value("${app.frame-bounds.directory:${user.home}/.health-tracker}") String directory) {
		this.file = Path.of(directory, FILE_NAME);
	}

	@Override
	public Map<String, Rectangle> load() {
		Map<String, Rectangle> result = new HashMap<>();
		if (!Files.isRegularFile(file)) {
			return result;
		}
		Properties properties = new Properties();
		try (InputStream in = Files.newInputStream(file)) {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
			return result;
		}
		properties.forEach((key, value) -> {
			Rectangle bounds = parse((String) value);
			if (bounds != null) {
				result.put((String) key, bounds);
			}
		});
		return result;
	}

	@Override
	public void save(Map<String, Rectangle> boundsByKey) {
		Properties properties = new Properties();
		boundsByKey.forEach((key, r) -> properties.setProperty(key, r.x + "," + r.y + "," + r.width + "," + r.height));
		try {
			Files.createDirectories(file.getParent());
			try (OutputStream out = Files.newOutputStream(file)) {
				properties.store(out, "Health-Tracker internal frame bounds");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Rectangle parse(String value) {
		if (value == null) {
			return null;
		}
		String[] parts = value.split(",");
		if (parts.length != 4) {
			return null;
		}
		try {
			return new Rectangle(
				Integer.parseInt(parts[0].trim()),
				Integer.parseInt(parts[1].trim()),
				Integer.parseInt(parts[2].trim()),
				Integer.parseInt(parts[3].trim())
			);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
