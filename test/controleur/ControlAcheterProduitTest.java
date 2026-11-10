package controleur;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import personnages.Chef;
import personnages.Gaulois;
import villagegaulois.Village;

class ControlAcheterProduitTest {
	private Village village;
	private Chef abraracourcix;
	private Gaulois bonemine;
	private Gaulois asterix;
	private ControlVerifierIdentite controlVerifierIdentite;
	private ControlTrouverEtalVendeur controlTrouverEtalVendeur;
	private ControlAcheterProduit controlAcheterProduit;

	@BeforeEach
	public void initialiserSituation() {
		village = new Village("le village des irréductibles", 10, 5);
		abraracourcix = new Chef("Abraracourcix", 10, village);
		village.setChef(abraracourcix);

		bonemine = new Gaulois("Bonemine", 10);
		asterix = new Gaulois("Astérix", 8);
		village.ajouterHabitant(bonemine);
		village.ajouterHabitant(asterix);
		village.installerVendeur(bonemine, "fleurs", 10);

		controlVerifierIdentite = new ControlVerifierIdentite(village);
		controlTrouverEtalVendeur = new ControlTrouverEtalVendeur(village);
		controlAcheterProduit = new ControlAcheterProduit(controlVerifierIdentite, controlTrouverEtalVendeur, village);
	}

	@Test
	void testControlAcheterProduit() {
		assertNotNull(controlAcheterProduit, "Constructeur ne renvoie pas null");
	}

	@Test
	void testVerifierIdentite() {
		assertTrue(controlAcheterProduit.verifierIdentite("Astérix"));
		assertFalse(controlAcheterProduit.verifierIdentite("Inconnu"));
	}

	@Test
	void testRechercherVendeursProduit() {
		// Produit non vendu sur le marché -> renvoie null
		assertNull(controlAcheterProduit.rechercherVendeursProduit("poissons"));

		// Produit vendu par Bonemine
		Gaulois[] vendeurs = controlAcheterProduit.rechercherVendeursProduit("fleurs");
		assertNotNull(vendeurs);
		assertEquals(1, vendeurs.length);
		assertEquals(bonemine, vendeurs[0]);
	}

	@Test
	void testAcheterProduit() {
		// Achat de 3 fleurs sur 10
		int quantiteAchetee = controlAcheterProduit.acheterProduit("Bonemine", 3);
		assertEquals(3, quantiteAchetee);

		// Achat de 15 fleurs alors qu'il n'en reste que 7 -> plafonné à 7
		int quantiteRestante = controlAcheterProduit.acheterProduit("Bonemine", 15);
		assertEquals(7, quantiteRestante);

		// Étal désormais vide -> renvoie 0
		assertEquals(0, controlAcheterProduit.acheterProduit("Bonemine", 2));
	}
}