package de.ollie.healthtracker.shell;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jline.utils.AttributedString;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HealthtrackerPromptProviderTest {

	@InjectMocks
	private HealthtrackerPromptProvider unitUnderTest;

	@Nested
	class getPrompt {

		@Test
		void returnsTheCorrectPrompt() {
			assertEquals(
				new AttributedString(HealthtrackerPromptProvider.PROMPT, HealthtrackerPromptProvider.STYLE),
				unitUnderTest.getPrompt()
			);
		}
	}
}
