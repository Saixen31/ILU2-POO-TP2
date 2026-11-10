package controleur;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import personnages.Chef;
import villagegaulois.Village;

class ControlEmmenagerTest {

	private ControlEmmenager ce;

	@BeforeEach
	void setup() {
		Village village = new Village("LeVillage", 5, 1);
		village.setChef(new Chef("Lechef", 1, village));
		ce = new ControlEmmenager(village);
	}

	@Test
	void testSansHabitant() {
		assertFalse(ce.isHabitant("Asterix"));
	}

	@Test
	void testChefHabitant() {
		assertTrue(ce.isHabitant("Lechef"));
	}

	private ControlEmmenager setupVillageSansChef() {
		Village village = new Village("LeVillage", 5, 1);
		return new ControlEmmenager(village);
	}

	@Test
	void testErreurVillageSansChef() {
		ControlEmmenager ceSansChef = setupVillageSansChef();
		int ok = 0;
		try {
			ceSansChef.isHabitant("Asterix");
		} catch (NullPointerException npe) {
			ok = 1;
		}
		assertEquals(1, ok);
	}

	@Test
	void testExceptionVillageSansChef() {
		ControlEmmenager ceSansChef = setupVillageSansChef();
		assertThrows(NullPointerException.class, () -> ceSansChef.isHabitant("Asterix")); // fonction anonyme, en ILU3

	}

	@Test
	void testGauloisEmmenager() {
		assertFalse(ce.isHabitant("Asterix"));
		ce.ajouterGaulois("Asterix", 1);
		assertTrue(ce.isHabitant("Asterix"));
	}

	@Test
	void testAjouterDruide() {
		assertFalse(ce.isHabitant("Panoramix"));
		ce.ajouterDruide("Panoramix", 1, 1, 2);
		assertTrue(ce.isHabitant("Panoramix"));

	}

	@Test
	void testAjouterPleinVillageois() {
		for (int i = 0; i < 10; i++) {
			ce.ajouterGaulois("G" + i, 5);
		}

		for (int i = 0; i < 5; i++) {
			assertTrue(ce.isHabitant("G" + i));
			assertFalse(ce.isHabitant("G" + (i + 5)));
		}
	}

}