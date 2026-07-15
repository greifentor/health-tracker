package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.BodyTemperatureMeasurementDbo;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyTemperatureMeasurementDboRepository extends JpaRepository<BodyTemperatureMeasurementDbo, UUID> {
	@Query(
		"SELECT dbo FROM BodyTemperatureMeasurementDbo dbo ORDER BY dbo.dateOfRecording DESC, dbo.timeOfRecording DESC"
	)
	List<BodyTemperatureMeasurementDbo> findAllOrdered();

	@Query(
		"SELECT dbo FROM BodyTemperatureMeasurementDbo dbo WHERE dbo.dateOfRecording >= :from ORDER BY dbo.dateOfRecording DESC, dbo.timeOfRecording DESC"
	)
	List<BodyTemperatureMeasurementDbo> findAllSince(LocalDate from);

	/** Returns the measurements recorded within the last {@code days} days (dateOfRecording on or after today - days). */
	default List<BodyTemperatureMeasurementDbo> findAllOfLastDays(int days) {
		return findAllSince(LocalDate.now().minusDays(days));
	}
}
