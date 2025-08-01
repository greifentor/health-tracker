package de.ollie.healthtracker.shell;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;

@Configuration
class HealthtrackerPromptProvider implements PromptProvider {

	static final String PROMPT = "ht>";
	static final AttributedStyle STYLE = AttributedStyle.DEFAULT.bold().foreground(AttributedStyle.YELLOW);

	@Override
	public AttributedString getPrompt() {
		return new AttributedString(PROMPT, STYLE);
	}
}
