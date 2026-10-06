package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.MeatConsumptionDbo;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeatConsumptionDboRepository extends JpaRepository<MeatConsumptionDbo, UUID> {
	@Query("SELECT dbo FROM MeatConsumptionDbo dbo ORDER BY dbo.dateOfRecording DESC")
	List<MeatConsumptionDbo> findAllOrdered();

	@Query(
		"SELECT COUNT(dbo) > 0 FROM MeatConsumptionDbo dbo WHERE dbo.dateOfRecording = :dateOfRecording AND dbo.meatProduct.id = :meatProduct AND dbo.units = :units"
	)
	boolean isDuplicate(LocalDate dateOfRecording, UUID meatProduct, BigDecimal units);

	@Query("SELECT dbo FROM MeatConsumptionDbo dbo WHERE dbo.dateOfRecording >= :from ORDER BY dbo.dateOfRecording DESC")
	List<MeatConsumptionDbo> findAllSince(LocalDate from);

	/**
	 * Returns the consumptions recorded within the last {@code days} days
	 * (dateOfRecording on or after today - days).
	 */
	default List<MeatConsumptionDbo> findAllOfLastDays(int days) {
		return findAllSince(LocalDate.now().minusDays(days));
	}
}
