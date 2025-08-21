package de.ollie.healthtracker.gui.swing;

import javax.swing.SwingUtilities;
import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Generated
@SpringBootApplication
@ComponentScan("de.ollie.healthtracker")
@EntityScan("de.ollie.healthtracker.persistence.jpa.dbo")
@EnableJpaRepositories(basePackages = "de.ollie.healthtracker.persistence.jpa.repository")
public class HealthTrackerSwingGUIStarter {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(HealthTrackerSwingGUIStarter.class);
		app.setHeadless(false); // GUI erlauben
		ConfigurableApplicationContext context = app.run(args);

		// GUI aus dem Spring Context holen
		HealthTrackerMainFrame mainFrame = context.getBean(HealthTrackerMainFrame.class);
		SwingUtilities.invokeLater(mainFrame::showFrame);
	}
}
