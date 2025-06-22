package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.BloodPressureMeasurementDbo;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodPressureMeasurementDboRepository extends JpaRepository<BloodPressureMeasurementDbo, UUID> {}
