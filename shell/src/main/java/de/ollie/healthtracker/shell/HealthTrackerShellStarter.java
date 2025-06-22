package de.ollie.healthtracker.shell;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Generated
@SpringBootApplication
@ComponentScan("de.ollie.healthtracker")
@EntityScan("de.ollie.healthtracker.persistence.jpa.dbo")
@EnableJpaRepositories(basePackages = "de.ollie.healthtracker.persistence.jpa.repository")
public class HealthTrackerShellStarter {

	public static void main(String[] args) {
		SpringApplication.run(HealthTrackerShellStarter.class, args);
	}
}
