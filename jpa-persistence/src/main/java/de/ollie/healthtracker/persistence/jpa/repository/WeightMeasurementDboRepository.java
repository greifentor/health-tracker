package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.WeightMeasurementDbo;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WeightMeasurementDboRepository extends JpaRepository<WeightMeasurementDbo, UUID> {
	@Query("SELECT dbo FROM WeightMeasurementDbo dbo ORDER BY dbo.dateOfRecording DESC, dbo.timeOfRecording DESC")
	List<WeightMeasurementDbo> findAllOrdered();

	@Query(
		"SELECT dbo FROM WeightMeasurementDbo dbo WHERE dbo.dateOfRecording >= :from ORDER BY dbo.dateOfRecording DESC, dbo.timeOfRecording DESC"
	)
	List<WeightMeasurementDbo> findAllSince(LocalDate from);

	/** Returns the measurements recorded within the last {@code days} days (dateOfRecording on or after today - days). */
	default List<WeightMeasurementDbo> findAllOfLastDays(int days) {
		return findAllSince(LocalDate.now().minusDays(days));
	}
}
