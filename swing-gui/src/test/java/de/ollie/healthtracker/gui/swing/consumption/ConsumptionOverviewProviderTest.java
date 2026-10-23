package de.ollie.healthtracker.gui.swing.consumption;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import de.ollie.healthtracker.core.service.AlcoholConsumptionService;
import de.ollie.healthtracker.core.service.MeatConsumptionService;
import de.ollie.healthtracker.core.service.model.AlcoholConsumption;
import de.ollie.healthtracker.core.service.model.AlcoholProduct;
import de.ollie.healthtracker.core.service.model.AlcoholType;
import de.ollie.healthtracker.core.service.model.MeatCategory;
import de.ollie.healthtracker.core.service.model.MeatConsumption;
import de.ollie.healthtracker.core.service.model.MeatProduct;
import de.ollie.healthtracker.core.service.model.MeatType;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ConsumptionOverviewProviderTest {

	@Mock
	private MeatConsumptionService meatConsumptionService;

	@Mock
	private AlcoholConsumptionService alcoholConsumptionService;

	@InjectMocks
	private ConsumptionOverviewProvider unitUnderTest;

	@BeforeEach
	void setUp() {
		when(meatConsumptionService.listMeatConsumptions()).thenReturn(List.of());
		when(alcoholConsumptionService.listAlcoholConsumptions()).thenReturn(List.of());
	}

	private MeatConsumption meat(String typeName, MeatCategory category, int amountInGr, double units) {
		return new MeatConsumption()
			.setUnits(BigDecimal.valueOf(units))
			.setMeatProduct(
				new MeatProduct().setAmountInGr(amountInGr).setMeatType(new MeatType().setCategory(category).setName(typeName))
			);
	}

	private AlcoholConsumption alcohol(String typeName, double liter, double percentVol) {
		return new AlcoholConsumption()
			.setLiter(BigDecimal.valueOf(liter))
			.setAlcoholProduct(
				new AlcoholProduct()
					.setPercentVol(BigDecimal.valueOf(percentVol))
					.setAlcoholType(new AlcoholType().setName(typeName))
			);
	}

	private ConsumptionOverviewRow rowLabeled(List<ConsumptionOverviewRow> rows, String label) {
		return rows.stream().filter(row -> row.label().equals(label)).findFirst().orElseThrow();
	}

	@Test
	void sumsTheMeatConsumptionInGrams() {
		// Prepare
		when(meatConsumptionService.listMeatConsumptions())
			.thenReturn(List.of(meat("Rind", MeatCategory.MEAT, 200, 2.0), meat("Schwein", MeatCategory.MEAT, 100, 1.0)));
		// Run
		List<ConsumptionOverviewRow> result = unitUnderTest.create();
		// Check
		assertEquals("0,500 kg", rowLabeled(result, "Fleisch").amount());
	}

	@Test
	void listsTheMeatTypesAsSubPositions() {
		// Prepare
		when(meatConsumptionService.listMeatConsumptions()).thenReturn(List.of(meat("Rind", MeatCategory.MEAT, 200, 2.0)));
		// Run
		List<ConsumptionOverviewRow> result = unitUnderTest.create();
		// Check
		assertEquals("0,400 kg", rowLabeled(result, "Rind").amount());
	}

	@Test
	void marksMeatTypeRowsAsSubPositions() {
		// Prepare
		when(meatConsumptionService.listMeatConsumptions()).thenReturn(List.of(meat("Rind", MeatCategory.MEAT, 200, 2.0)));
		// Run
		List<ConsumptionOverviewRow> result = unitUnderTest.create();
		// Check
		assertTrue(rowLabeled(result, "Rind").subPosition());
	}

	@Test
	void countsFishSeparatelyFromMeat() {
		// Prepare
		when(meatConsumptionService.listMeatConsumptions()).thenReturn(List.of(meat("Lachs", MeatCategory.FISH, 150, 2.0)));
		// Run
		List<ConsumptionOverviewRow> result = unitUnderTest.create();
		// Check
		assertEquals("0,300 kg", rowLabeled(result, "Fisch").amount());
	}

	@Test
	void sumsThePureAlcoholInLitres() {
		// Prepare
		when(alcoholConsumptionService.listAlcoholConsumptions())
			.thenReturn(List.of(alcohol("Bier", 1.0, 50.0), alcohol("Wein", 2.0, 5.0)));
		// Run
		List<ConsumptionOverviewRow> result = unitUnderTest.create();
		// Check
		assertEquals("0,60 l", rowLabeled(result, "Alkohol").amount());
	}

	@Test
	void listsTheAlcoholTypesAsSubPositions() {
		// Prepare
		when(alcoholConsumptionService.listAlcoholConsumptions()).thenReturn(List.of(alcohol("Bier", 1.0, 50.0)));
		// Run
		List<ConsumptionOverviewRow> result = unitUnderTest.create();
		// Check
		assertEquals("0,50 l", rowLabeled(result, "Bier").amount());
	}

	@Test
	void marksAlcoholTypeRowsAsSubPositions() {
		// Prepare
		when(alcoholConsumptionService.listAlcoholConsumptions()).thenReturn(List.of(alcohol("Bier", 1.0, 50.0)));
		// Run
		List<ConsumptionOverviewRow> result = unitUnderTest.create();
		// Check
		assertTrue(rowLabeled(result, "Bier").subPosition());
	}
}
