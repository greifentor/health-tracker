package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.BloodPressureMeasurementDbo;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodPressureMeasurementDboRepository extends JpaRepository<BloodPressureMeasurementDbo, UUID> {}
