package villagegaulois;

import exceptions.VillageSansChefException;
import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
		if(chef == null) {
			throw new VillageSansChefException("Il n'y a pas de chef");
		}
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	private static class Marche {
		private Etal[] etals;

		private Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for (int i = 0; i < etals.length; i++) {
				etals[i] = new Etal();
			}
		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe())
					return i;
			}
			return -1;
		}

		private Etal[] trouverEtals(String produit) {
			Etal[] produitEtals;
			int nbEtal = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit))
					nbEtal++;
			}
			produitEtals = new Etal[nbEtal];
			for (int i = 0, j = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					produitEtals[j] = etals[i];
					j++;
				}
			}
			return produitEtals;
		}

		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}

		private String afficherMarche() {
			int nbEtalVide = 0;
			StringBuilder chaine = new StringBuilder();
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
				} else {
					nbEtalVide++;
				}
			}
			chaine.append("Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n");
			return chaine.toString();
		}
	}

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + "\n");
		int indiceEtal = marche.trouverEtalLibre();
		marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
		chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " a l'etal n°" + (indiceEtal+1) + "\n");
		return chaine.toString();
	}

public String rechercherVendeursProduit(String produit) {
	StringBuilder chaine = new StringBuilder();
	Etal[] etals = marche.trouverEtals(produit);
	if(etals.length == 1) {
		chaine.append(etals[0].getVendeur().getNom() + " vend des " + produit + " au marche.\n");
	}
	if (etals.length > 1) {
		chaine.append("Les vendeurs qui proposent des " + produit + " sont :\n");
		for (int i = 0; i < etals.length; i++) {
			chaine.append("- " + etals[i].getVendeur().getNom()+"\n");
		}
	}
	else {
		chaine.append("Il n'y a pas de vendeur qui propose des " + produit + " au marche.\n");
	}
	return chaine.toString();
}

public Etal rechercherEtal(Gaulois vendeur) {
	return marche.trouverVendeur(vendeur);
}

public String partirVendeur(Gaulois vendeur) {
	return rechercherEtal(vendeur).libererEtal();
}

public String afficherMarche() {
	return marche.afficherMarche();
}










}