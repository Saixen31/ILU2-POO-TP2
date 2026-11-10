package frontiere;

import controleur.ControlAcheterProduit;
import personnages.Gaulois;

public class BoundaryAcheterProduit {
	private ControlAcheterProduit controlAcheterProduit;

	public BoundaryAcheterProduit(ControlAcheterProduit controlAcheterProduit) {
		this.controlAcheterProduit = controlAcheterProduit;
	}

	public void acheterProduit(String nomAcheteur) {
		if (!controlAcheterProduit.verifierIdentite(nomAcheteur)) {
			System.out.println("Je suis désolé "+ nomAcheteur +" mais il faut être un habitant de notre village pour commercer ici.\n");
		}
		else {
			String produit = Clavier.entrerChaine("Quel produit voulez-vous acheter ?");
			Gaulois[] vendeur = controlAcheterProduit.trouverVendeurProduit(produit);
			if (vendeur == null) {
				System.out.println("Désolé, personne ne vend ce produit au marché.\n");
			}
			else {
				
				System.out.println("Chez quel commerçant voulez-vous acheter des "+ produit +" ?");
				for (int i = 0;i<vendeur.length;i++) {
					StringBuilder affichage = new StringBuilder();
					affichage.append((i+1));
					affichage.append(" - ");
					affichage.append(vendeur[i].getNom());
					System.out.println(affichage.toString());
				}
				
				int numVendeur = Clavier.entrerEntier("");
				while(numVendeur < 1 || numVendeur > vendeur.length) {
					numVendeur = Clavier.entrerEntier("Veuillez entrer un numéro de vendeur valide :");
				}
				
				System.out.println(nomAcheteur + " se déplace jusqu'à l'étal du vendeur "+ vendeur[numVendeur-1].getNom() +".");
				System.out.println("Bonjour " + nomAcheteur + ".");
				
				int quantite = Clavier.entrerEntier("Combien de "+ produit +" voulez-vous acheter ?");
				int quantiteAcheter = controlAcheterProduit.acheterVendeur(vendeur[numVendeur-1].getNom(), quantite);
				if (quantiteAcheter == 0) {
					System.out.println(nomAcheteur + " veut acheter "+ quantite +" "+ produit + ", malheureusement il n'y en a plus !");
				}
				else if (quantiteAcheter < quantite) {
					System.out.println(nomAcheteur + " veut acheter "+ quantite +" "+ produit + ", malheureusement "+ vendeur[numVendeur-1].getNom() 
							+" n'en a plus que "+ quantiteAcheter +". "+ nomAcheteur +" achète tout le stock de "+ vendeur[numVendeur-1].getNom() +".");
				}
				else {
					System.out.println(nomAcheteur + " achète "+ quantite +" "+ produit + " à "+ vendeur[numVendeur-1].getNom() +".");
				}
			}
		}
	}
}