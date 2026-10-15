package controleur;

import villagegaulois.Etal;
import personnages.Gaulois;
import villagegaulois.Village;

public class ControlTrouverEtalVendeur {
	private Village village;

	public ControlTrouverEtalVendeur(Village village) {
		this.village = village;
	}

	public Etal trouverEtalVendeur(String nomVendeur) {
		Etal etal = null;
		Gaulois gaulois = village.trouverHabitant(nomVendeur);
		if (gaulois != null) {
			etal = village.rechercherEtal(gaulois);
		}
		return etal;
	}
}
