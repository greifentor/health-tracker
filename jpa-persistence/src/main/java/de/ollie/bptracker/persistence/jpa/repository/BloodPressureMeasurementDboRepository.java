package de.ollie.bptracker.persistence.jpa.repository;

import de.ollie.bptracker.persistence.jpa.dbo.BloodPressureMeasurementDbo;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodPressureMeasurementDboRepository extends JpaRepository<BloodPressureMeasurementDbo, UUID> {}
