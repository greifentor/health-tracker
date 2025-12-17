package de.ollie.healthtracker.core.service.port.fs;

public interface FileSystemPort {
	void writeBinary(String fileName, byte[] binary);
}
