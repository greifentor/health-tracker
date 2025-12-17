package de.ollie.healthtracker.gui.swing.external.viewer.pdf;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.port.fs.FileSystemPort;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class ExternalPdfViewerStarter {

	private final ExternalPdfViewerConfiguration configuration;
	private final FileSystemPort fileSystemPort;
	private final ProcessBuilderFactory processBuilderFactory;

	public void show(byte[] pdf) {
		ensure(pdf != null, "pdf cannot be null!");
		fileSystemPort.writeBinary(configuration.getPdfTmpFilename(), pdf);
		try {
			ProcessBuilder pb = processBuilderFactory.create(
				configuration.getPdfViewerCallApplication(),
				configuration.getPdfViewerCallParameters().replace("%TEMP_PDF%", configuration.getPdfTmpFilename())
			);
			pb.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/*
				ProcessBuilder pb = new ProcessBuilder(
					configuration.getPdfViewerCallApplication(),
					configuration.getPdfViewerCallParameters().replace("%TEMP_PDF%", configuration.getPdfTmpFilename())
				);
				pb.start();

	 */

}
