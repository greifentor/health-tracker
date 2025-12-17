package de.ollie.healthtracker.gui.swing.external.viewer.pdf;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.port.fs.FileSystemPort;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExternalPdfViewerStarterTest {

	@Mock
	private ExternalPdfViewerConfiguration configuration;

	@Mock
	private FileSystemPort fileSystemPort;

	@Mock
	private ProcessBuilderFactory processBuilderFactory;

	@InjectMocks
	private ExternalPdfViewerStarter unitUnderTest;

	@Nested
	class show_ByteArr {

		private static final String PDF_TMP_FILE_NAME = "pdf-tmp-file-name";
		private static final String PDF_VIEWER_CALL_APPLICATION = "pdf-viewer-call-application";
		private static final String PDF_VIEWER_CALL_PARAMETERS = "pdf-viewer-call-parameters%TEMP_PDF%";

		private byte[] pdf = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };

		@Mock
		private ProcessBuilder processBuilder;

		@Test
		void throwsAnException_passingANullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.show(null));
		}

		@Test
		void happyRun() throws Exception {
			// Prepare
			when(configuration.getPdfTmpFilename()).thenReturn(PDF_TMP_FILE_NAME);
			when(configuration.getPdfViewerCallApplication()).thenReturn(PDF_VIEWER_CALL_APPLICATION);
			when(configuration.getPdfViewerCallParameters()).thenReturn(PDF_VIEWER_CALL_PARAMETERS);
			when(
				processBuilderFactory.create(
					PDF_VIEWER_CALL_APPLICATION,
					PDF_VIEWER_CALL_PARAMETERS.replace("%TEMP_PDF%", PDF_TMP_FILE_NAME)
				)
			)
				.thenReturn(processBuilder);
			// Run
			unitUnderTest.show(pdf);
			// Check
			verify(fileSystemPort, times(1)).writeBinary(PDF_TMP_FILE_NAME, pdf);
			verify(processBuilder, times(1)).start();
		}
	}
}
