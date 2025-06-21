package de.ollie.healthtracker.shell;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@Generated
@SpringBootApplication
@ComponentScan("de.ollie.healthtracker")
public class HealthTrackerShellStarter {

	public static void main(String[] args) {
		SpringApplication.run(HealthTrackerShellStarter.class, args);
	}
}
