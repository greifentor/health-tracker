package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.AlcoholConsumptionDbo;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AlcoholConsumptionDboRepository extends JpaRepository<AlcoholConsumptionDbo, UUID> {
	@Query("SELECT dbo FROM AlcoholConsumptionDbo dbo ORDER BY dbo.date DESC")
	List<AlcoholConsumptionDbo> findAllOrdered();

	@Query(
		"SELECT COUNT(dbo) > 0 FROM AlcoholConsumptionDbo dbo WHERE dbo.date = :date AND dbo.alcoholProduct.id = :alcoholProduct AND dbo.liter = :liter"
	)
	boolean isDuplicate(LocalDate date, UUID alcoholProduct, BigDecimal liter);

	@Query("SELECT dbo FROM AlcoholConsumptionDbo dbo WHERE dbo.date >= :from ORDER BY dbo.date DESC")
	List<AlcoholConsumptionDbo> findAllSince(LocalDate from);

	/** Returns the consumptions recorded within the last {@code days} days (date on or after today - days). */
	default List<AlcoholConsumptionDbo> findAllOfLastDays(int days) {
		return findAllSince(LocalDate.now().minusDays(days));
	}
}
