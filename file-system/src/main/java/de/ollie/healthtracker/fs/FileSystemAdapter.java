package de.ollie.healthtracker.fs;

import de.ollie.healthtracker.core.service.exception.FileSystemAccessException;
import de.ollie.healthtracker.core.service.port.fs.FileSystemPort;
import jakarta.inject.Named;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Named
class FileSystemAdapter implements FileSystemPort {

	static final String MSG_EXCEPTION = "error while accessing file system";

	@Override
	public void writeBinary(String fileName, byte[] binary) {
		try {
			Files.write(
				Path.of(fileName),
				binary,
				StandardOpenOption.WRITE,
				StandardOpenOption.TRUNCATE_EXISTING,
				StandardOpenOption.CREATE
			);
		} catch (Exception e) {
			throw new FileSystemAccessException(MSG_EXCEPTION, e);
		}
	}
}
