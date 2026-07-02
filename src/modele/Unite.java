package modele;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import controleur.LogiqueJeu;

public class Unite {
	protected int typeUnite;
	protected int attaque;
	protected int defense;
	protected int pv;
	protected int pvMax;
	protected int ptDeDeplacement;
	protected int ptDeDeplacementMax;
	protected int vision;
	protected int porte;
	protected int x;
	protected int y;
	protected int equipe;
	protected boolean acted;

	public Unite(final int typeUnite, final int attaque, final int defense, final int pv, final int ptDeDeplacement,
			final int vision, final int porte, final int x, final int y, final int equipe) {
		this.typeUnite = typeUnite;
		this.attaque = attaque;
		this.defense = defense;
		this.pv = pv;
		this.pvMax = pv;
		this.ptDeDeplacement = ptDeDeplacement;
		this.ptDeDeplacementMax = ptDeDeplacement;
		this.vision = vision;
		this.porte = porte;
		this.x = x;
		this.y = y;
		this.equipe = equipe;
	}

	public Unite(final int typeUnite, final int attaque, final int defense, final int pv, final int pvMax,
			final int ptDeDeplacement, final int ptDeDeplacementMax, final int vision, final int porte, final int x,
			final int y, final int equipe) {
		this.typeUnite = typeUnite;
		this.attaque = attaque;
		this.defense = defense;
		this.pv = pv;
		this.pvMax = pvMax;
		this.ptDeDeplacement = ptDeDeplacement;
		this.ptDeDeplacementMax = ptDeDeplacementMax;
		this.vision = vision;
		this.porte = porte;
		this.x = x;
		this.y = y;
		this.equipe = equipe;
	}

	public boolean selected() {
		HashMap<Hexagone, Integer> deplacementPossible = calculDeplacementPossible();
		HashMap<Hexagone, String> action = actionPossible();

		LogiqueJeu.setDeplacementPossibleHash(deplacementPossible);
		LogiqueJeu.setActionPossibleHash(action);
		LogiqueJeu.affichageDeplacementPossible();
		Point hexagone = new Point(-2, -2);
		do {
			System.out.print("");
			if (LogiqueJeu.getSkipFlag() || !LogiqueJeu.hasStarted()) {
				LogiqueJeu.setSkipFlag(false);
				return true;
			}
			if (LogiqueJeu.getClicFlag()) {
				hexagone = LogiqueJeu.getCoordHexaClicked();
				LogiqueJeu.setClicFlag(false);
			}
		} while (hexagone.x == -2 || hexagone.y == -2);

		if (secondClic(hexagone.x, hexagone.y, deplacementPossible)) {
			return true;
		}
		return false;
	}

	public boolean secondClic(final int xHexa, final int yHexa, final HashMap<Hexagone, Integer> deplacementPossible) {
		if (xHexa >= 0 && yHexa >= 0) {
			Hexagone hexaVisee = LogiqueJeu.getMap()[xHexa][yHexa];
			Hexagone hexaDeLunite = LogiqueJeu.getMap()[this.x][this.y];

			if (deplacementPossible.containsKey(LogiqueJeu.getMap()[xHexa][yHexa])) {
				this.x = xHexa;
				this.y = yHexa;
				this.ptDeDeplacement = deplacementPossible.get(hexaVisee);
			} else {
				tests: for (Joueur j : LogiqueJeu.getListeJoueurs()) {
					for (Unite u : j.getListeUnite()) {

						if (hexaVisee.getX() == u.getX() && hexaVisee.getY() == u.getY()) {

							if (u.getTeamUnite() == this.getTeamUnite()) {
								if (typeUnite == LogiqueJeu.PRETRE && !acted
										&& hexaDeLunite.getDistanceBetweenTwoPosition(hexaVisee) <= this.porte) {
									int pdvAvSoin = u.getPv();
									((Soigneuse) (this)).soigner(u);
									LogiqueJeu.setLastAttaque("Le prêtre a soigné de: " + (pdvAvSoin - u.getPv()));
								} else {
									return u.selected(); // changement unité
								}
							} else {
								if (hexaDeLunite.getDistanceBetweenTwoPosition(hexaVisee) <= this.porte && !acted) {
									int pdvAvAtt = u.getPv();
									attaquer(u);
									LogiqueJeu.setLastAttaque("L'unité du joueur: " + j.getPseudo() + " a subi: "
											+ (pdvAvAtt - u.getPv()));
									break tests;
								}
							}
						}
					}
				}
			}
		}
		LogiqueJeu.affichageUnite();
		return false;
	}

	public CleMap<Hexagone, Integer> calculDeplacementPossible() {
		Hexagone h = LogiqueJeu.getMap()[x][y]; // hexagone où se situe l'unite
		CleMap<Hexagone, Integer> deplacementPossible = new CleMap<Hexagone, Integer>();
		CleMap<Hexagone, Integer> pointAExplorer = new CleMap<Hexagone, Integer>(); // couple hexagone / point de
																					// déplacement restant
		Joueur joueurCourant = this.getPlayerWhoControlMe();

		Hexagone hexagoneCourant = h;

		pointAExplorer.put(hexagoneCourant, ptDeDeplacement);

		if (ptDeDeplacement != 0) { // si jamais un jour on donne la possibilité d'enlever des points de

			while (!pointAExplorer.isEmpty()) {

				hexagoneCourant = (Hexagone) pointAExplorer.getFirstKey(); // on récupère le premier élément de la
																			// liste

				for (Hexagone v : hexagoneCourant.getListeVoisin()) { // on parcourt la liste de ses voisins

					boolean libre = true;
					test: for (Joueur j : LogiqueJeu.getListeJoueurs()) {
						for (Unite u : j.getListeUnite()) {
							if (u.getX() == v.getX() && u.getY() == v.getY()) {
								libre = false;
								break test;
							}
						}
					}

					if (v.getType() != LogiqueJeu.MER && libre) {

						if (deplacementPossible.containsKey(v)) {

							if (pointAExplorer.get(hexagoneCourant) - v.getCoutDeDeplacement() > deplacementPossible
									.get(v)) {

								deplacementPossible.replace(v,
										pointAExplorer.get(hexagoneCourant) - v.getCoutDeDeplacement());
								if (pointAExplorer.containsKey(v)) {
									pointAExplorer.replace(v,
											pointAExplorer.get(hexagoneCourant) - v.getCoutDeDeplacement());
								} else {
									pointAExplorer.put(v,
											pointAExplorer.get(hexagoneCourant) - v.getCoutDeDeplacement());
								}
							}
						} else if (pointAExplorer.containsKey(v)) { // si il est déja en attente d'exploration

							if (pointAExplorer.get(hexagoneCourant) - v.getCoutDeDeplacement() > pointAExplorer
									.get(v)) {
								pointAExplorer.replace(v,
										pointAExplorer.get(hexagoneCourant) - v.getCoutDeDeplacement());

							}
						} else if (v.getCoutDeDeplacement() <= pointAExplorer.get(hexagoneCourant)) {
							pointAExplorer.put(v, pointAExplorer.get(hexagoneCourant) - v.getCoutDeDeplacement());

						}

					}
				} // fin du parcours des voisins
				if (!deplacementPossible.containsKey(hexagoneCourant)) {
					deplacementPossible.put(hexagoneCourant, pointAExplorer.get(hexagoneCourant));
				}
				pointAExplorer.remove(hexagoneCourant);

			} // fin de la boucle principal
		}
		return deplacementPossible;
	}

	public Joueur getPlayerWhoControlMe() {

		for (Joueur j : LogiqueJeu.getListeJoueurs()) {
			if (j.getNumeroJoueur() == this.equipe) {
				return j;
			}
		}
		return null;
	}

	public CleMap<Hexagone, String> actionPossible() {
		Hexagone h = LogiqueJeu.getMap()[x][y]; // hexagone ou se situe l'unite
		CleMap<Hexagone, String> actionPossible = new CleMap<Hexagone, String>();
		CleMap<Hexagone, Integer> aExplorer = new CleMap<Hexagone, Integer>(); // couple hexagone/ point de
																				// deplacement restant
		Hexagone hexagoneCourant = h;
		aExplorer.put(hexagoneCourant, 0);

		while (!aExplorer.isEmpty()) {

			hexagoneCourant = (Hexagone) aExplorer.getFirstKey(); // on récupére le premier element de la liste
			for (Hexagone v : hexagoneCourant.getListeVoisin()) { // on parcourt la liste de ses voisins
				if (!actionPossible.containsKey(v) && !aExplorer.containsKey(v)
						&& aExplorer.get(hexagoneCourant) + 1 <= porte) {
					aExplorer.put(v, aExplorer.get(hexagoneCourant) + 1);
				}

			} // fin du parcours des voisins
			if (!actionPossible.containsKey(hexagoneCourant)) {
				for (Joueur j : LogiqueJeu.getListeJoueurs()) {
					for (Unite u : j.getListeUnite()) {
						if (u.getX() == hexagoneCourant.getX() && u.getY() == hexagoneCourant.getY()) {
							if (u.getTeamUnite() == this.getTeamUnite()) {
								if (this.getTypeUnite() == LogiqueJeu.PRETRE && u != this && !acted) {
									actionPossible.put(hexagoneCourant, "allie");
								}
							} else if (!acted) {
								actionPossible.put(hexagoneCourant, "ennemi");
							}
						}
					}
				}
			}
			aExplorer.remove(hexagoneCourant);

		} // fin de la boucle principale
		return actionPossible;
	}

	public ArrayList<Hexagone> vision() {
		Hexagone h = LogiqueJeu.getMap()[x][y]; // hexagone où se situe l'unite
		ArrayList<Hexagone> nofog = new ArrayList<Hexagone>();
		CleMap<Hexagone, Integer> aExplorer = new CleMap<Hexagone, Integer>(); // couple hexagone/ point de
																				// deplacement restant
		Hexagone hexagoneCourant = h;
		aExplorer.put(hexagoneCourant, 0);

		while (!aExplorer.isEmpty()) {

			hexagoneCourant = (Hexagone) aExplorer.getFirstKey(); // on récupère le premier élément de la liste
			for (Hexagone v : hexagoneCourant.getListeVoisin()) { // on parcourt la liste de ses voisins
				if (!aExplorer.containsKey(v) && aExplorer.get(hexagoneCourant) + 1 <= vision) {
					aExplorer.put(v, aExplorer.get(hexagoneCourant) + 1);
				}

			} // fin du parcours des voisins
			if (!nofog.contains(hexagoneCourant)) {
				nofog.add(hexagoneCourant);
			}
			aExplorer.remove(hexagoneCourant);

		} // fin de la boucle principale
		return nofog;
	}

	public void attaquer(final Unite unite) {
		if (unite.calculDegats(attaque) && porte > 1) {
			x = unite.getX();
			y = unite.getY();
		}
		acted = true;
		ptDeDeplacement = 0;
	}

	public boolean calculDegats(final int attaque) {
		final double borneInf = 0.5;
		final double borneSup = 1.5;
		double bonusDefense = LogiqueJeu.getMap()[x][y].getBonusDefense();
		double degats = (attaque - (defense * (1 + bonusDefense))) * getDoubleAleaBorne(borneInf, borneSup);
		if (degats > 0) {
			pv -= (int) degats;
			if (pv <= 0) {
				for (Joueur joueur : LogiqueJeu.getListeJoueurs()) {
					if (joueur.getListeUnite().remove(this)) { // supprime l'unité morte
						return true;
					}
				}
			}
		}
		return false;
	}

	public void soin(final double taux) {
		if (this.ptDeDeplacement == this.ptDeDeplacementMax) {
			this.pv += (int) this.pvMax * taux;
			if (this.pv > this.pvMax) {
				this.pv = this.pvMax;
			}
		}
	}

	public double getDoubleAleaBorne(final double min, final double max) {
		double res = (Math.random() * ((max - min) + 1)) + min;
		return res;
	}

	public void soigner(final Unite unite) {

	}

	public void preparerPourProchainTour() {
		soin(LogiqueJeu.SOIN);
		this.ptDeDeplacement = this.ptDeDeplacementMax;
		acted = false;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(final int defense) {
		this.defense = defense;
	}

	public int getPv() {
		return pv;
	}

	public void setPv(final int pv) {
		this.pv = pv;
	}

	public int getPvMax() {
		return pvMax;
	}

	public int getPtDeDeplacement() {
		return ptDeDeplacement;
	}

	public void setPtDeDeplacement(final int ptDeDeplacement) {
		this.ptDeDeplacement = ptDeDeplacement;
	}

	public int getPtDeDeplacementMax() {
		return ptDeDeplacementMax;
	}

	public int getX() {
		return x;
	}

	public void setX(final int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(final int y) {
		this.y = y;
	}

	public int getAttaque() {
		return attaque;
	}

	public void setAttaque(final int attaque) {
		this.attaque = attaque;
	}

	public int getVision() {
		return vision;
	}

	public void setVision(final int vision) {
		this.vision = vision;
	}

	public int getPorte() {
		return porte;
	}

	public void setPorte(final int porte) {
		this.porte = porte;
	}

	public int getTypeUnite() {
		return typeUnite;
	}

	public void setTypeUnite(final int typeUnite) {
		this.typeUnite = typeUnite;
	}

	public int getTeamUnite() {
		return equipe;
	}

	public void setEquipe(final int equipe) {
		this.equipe = equipe;
	}

}
