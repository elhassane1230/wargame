package controleur;

import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import modele.Hexagone;
import modele.Humain;
import modele.IA;
import modele.Joueur;
import modele.Unite;
import vue.IHMPlateau;
import vue.MainJFrame;

public final class LogiqueJeu {

	public static final int GUERRIER = 1;

	public static final int MAGE = 2;
	public static final int ARCHER = 3;
	public static final int PRETRE = 4;
	public static final int CHEVALIER = 5;
	public static final int PLAINE = 10;
	public static final int FORET = 11;
	public static final int VILLAGE = 12;
	public static final int LAC = 13;
	public static final int NEIGE = 14;
	public static final int MER = 15;
	public static final int DESERT = 16;
	public static final double SOIN = 0.1;
	public static final int MAPLIGNE = 12;
	public static final int MAPCOLONNE = 20;
	private static ArrayList<Joueur> listeJoueurs = new ArrayList<Joueur>();
	private static volatile boolean clicFlag = false;
	private static volatile boolean skipFlag = false;
	private static volatile boolean started = false;
	private static int turn = 1;
	private static Hexagone[][] map = new Hexagone[MAPLIGNE][MAPCOLONNE];
	private static MainJFrame frame;
	private static IHMPlateau plateau;
	private static ArrayList<ArrayList<Integer>> infoUnite = new ArrayList<ArrayList<Integer>>();
	private static HashMap<Hexagone, Integer> deplacementPossibleHash = new HashMap<Hexagone, Integer>();
	private static ArrayList<ArrayList<Integer>> deplacementPossible = new ArrayList<ArrayList<Integer>>();
	private static HashMap<Hexagone, String> actionPossibleHash = new HashMap<Hexagone, String>();
	private static ArrayList<ArrayList<Object>> actionPossible = new ArrayList<ArrayList<Object>>();
	private static String lastAttaque = new String();

	private LogiqueJeu() {

	}

	public static void start(final int nbJoueur, final int nbIA) {
		turn = 1;
		for (int i = 1; i <= nbJoueur; i++) {
			Humain joueur = new Humain(i, "Joueur " + i);
			listeJoueurs.add(joueur);
		}
		for (int i = nbJoueur + 1; i <= nbJoueur + nbIA; i++) {
			IA ia = new IA(i, "IA" + i);
			listeJoueurs.add(ia);
		}
		frame = new MainJFrame();
		plateau = new IHMPlateau();
		LogiqueJeu.initMap();
		frame.getFrame().setVisible(true);
		LogiqueJeu.affichageUnite();
		started = true;
	}

	public static void start() {
		frame = new MainJFrame();
		plateau = new IHMPlateau();
		frame.getFrame().setVisible(true);
		LogiqueJeu.initVoisins();
		LogiqueJeu.affichageUnite();
		started = true;
	}

	public static void kill() {
		started = false;
		frame.getFrame().dispose();
		for (Joueur j : LogiqueJeu.listeJoueurs) {
			j.getListeUnite().clear();
		}
		LogiqueJeu.listeJoueurs.clear();
		infoUnite.clear();
		deplacementPossibleHash.clear();
		deplacementPossible.clear();
		actionPossibleHash.clear();
		actionPossible.clear();
		frame = null;
		plateau = null;
	}

	public static void initMap() {
		List<List<Integer>> listeMap = new ArrayList<List<Integer>>();
		List<Integer> lignePossible = new ArrayList<Integer>();
		List<Integer> terrains = new ArrayList<Integer>();

		for (int i = 0; i < MAPLIGNE; i++) { // récupère toutes les positions possibles
			List<Integer> listeLigne = new ArrayList<Integer>();
			for (int j = 0; j < MAPCOLONNE; j++) {
				listeLigne.add(j);
			}
			listeMap.add(listeLigne);
			lignePossible.add(i);
		}

		for (int i = PLAINE; i <= DESERT; i++) { // récupère tous les terrains possibles
			terrains.add(i);
		}

		final int ligneZoneDepart = 7;
		final int colonneZoneDepart = 5;

		for (int ligne = 0; ligne < MAPLIGNE; ligne++) {
			for (int colonne = 0; colonne < MAPCOLONNE; colonne++) {
				final int nbVoisin = 6;

				for (int cpt = 0; cpt <= nbVoisin; cpt++) { // met le même terrain pour l'hexagone aléatoire et
															// ces voisins
					if (ligne >= 0 && colonne >= 0 && ligne < MAPLIGNE && colonne < MAPCOLONNE
							&& lignePossible.contains(ligne) && listeMap.get(ligne).contains(colonne)) {
						int terrainTmp = PLAINE;

						if ((ligne == 4 && colonne == 19) || (ligne == 5 && colonne == 19)
								|| (ligne == 6 && colonne == 19) || (ligne == 7 && colonne == 19)
								|| (ligne == 5 && colonne == 18) || (ligne == 5 && colonne == 17)
								|| (ligne == 6 && colonne == 17) || (ligne == 6 && colonne == 18)
								|| (ligne == 7 && colonne == 18) || (ligne == 7 && colonne == 17)
								|| (ligne == 8 && colonne == 19)

								|| (ligne == 4 && colonne == 0) || (ligne == 5 && colonne == 0)
								|| (ligne == 6 && colonne == 0) || (ligne == 7 && colonne == 0)
								|| (ligne == 5 && colonne == 1) || (ligne == 5 && colonne == 2)
								|| (ligne == 6 && colonne == 2) || (ligne == 6 && colonne == 1)
								|| (ligne == 7 && colonne == 1) || (ligne == 6 && colonne == 3)
								|| (ligne == 4 && colonne == 1) || (ligne == 8 && colonne == 0)

						) {
							terrainTmp = MER;
						}

						if ((ligne == 0 && colonne == 5) || (ligne == 0 && colonne == 6) || (ligne == 0 && colonne == 7)
								|| (ligne == 0 && colonne == 8) || (ligne == 0 && colonne == 9)
								|| (ligne == 0 && colonne == 10) || (ligne == 0 && colonne == 11)
								|| (ligne == 0 && colonne == 12) || (ligne == 0 && colonne == 13)
								|| (ligne == 1 && colonne == 6) || (ligne == 1 && colonne == 7)
								|| (ligne == 1 && colonne == 8) | (ligne == 1 && colonne == 9)
								|| (ligne == 1 && colonne == 10) || (ligne == 1 && colonne == 11)
								|| (ligne == 2 && colonne == 8) || (ligne == 2 && colonne == 9)
								|| (ligne == 2 && colonne == 10) || (ligne == 5 && colonne == 9)
								|| (ligne == 6 && colonne == 10) || (ligne == 6 && colonne == 9)

								|| (ligne == 11 && colonne == 5) || (ligne == 11 && colonne == 6)
								|| (ligne == 11 && colonne == 7) || (ligne == 11 && colonne == 8)
								|| (ligne == 11 && colonne == 9) || (ligne == 11 && colonne == 10)
								|| (ligne == 11 && colonne == 11) || (ligne == 11 && colonne == 12)

								|| (ligne == 10 && colonne == 7) || (ligne == 10 && colonne == 8)
								|| (ligne == 10 && colonne == 9) || (ligne == 10 && colonne == 10)
								|| (ligne == 10 && colonne == 11) || (ligne == 2 && colonne == 10)
								|| (ligne == 9 && colonne == 9) || (ligne == 9 && colonne == 8)

						) {

							terrainTmp = LAC;
						}

						if ((ligne == 3 && colonne == 19) || (ligne == 3 && colonne == 18)
								|| (ligne == 4 && colonne == 18) || (ligne == 4 && colonne == 17)
								|| (ligne == 5 && colonne == 15) || (ligne == 5 && colonne == 16)
								|| (ligne == 6 && colonne == 15) || (ligne == 6 && colonne == 14)
								|| (ligne == 7 && colonne == 15) || (ligne == 7 && colonne == 16)
								|| (ligne == 8 && colonne == 17) || (ligne == 8 && colonne == 18)
								|| (ligne == 9 && colonne == 19) || (ligne == 9 && colonne == 18)
								|| (ligne == 2 && colonne == 0) || (ligne == 2 && colonne == 1)
								|| (ligne == 3 && colonne == 1) || (ligne == 3 && colonne == 2)
								|| (ligne == 4 && colonne == 3) || (ligne == 4 && colonne == 4)
								|| (ligne == 5 && colonne == 3) || (ligne == 5 && colonne == 4)
								|| (ligne == 5 && colonne == 4) || (ligne == 6 && colonne == 5)
								|| (ligne == 9 && colonne == 0) || (ligne == 9 && colonne == 1)
								|| (ligne == 8 && colonne == 1) || (ligne == 8 && colonne == 2)
								|| (ligne == 7 && colonne == 2) || (ligne == 7 && colonne == 3)
								|| (ligne == 7 && colonne == 4) || (ligne == 6 && colonne == 4)
								|| (ligne == 3 && colonne == 0) || (ligne == 4 && colonne == 2)
								|| (ligne == 8 && colonne == 5) || (ligne == 10 && colonne == 0)
								|| (ligne == 6 && colonne == 16)) {

							terrainTmp = PLAINE;
						}

						if ((ligne == 11 && colonne == 4) || (ligne == 11 && colonne == 13)
								|| (ligne == 10 && colonne == 6) || (ligne == 10 && colonne == 12)
								|| (ligne == 9 && colonne == 7) || (ligne == 9 && colonne == 10)
								|| (ligne == 8 && colonne == 9)) {

							terrainTmp = NEIGE;
						}

						if ((ligne == 7 && colonne == 6) || (ligne == 7 && colonne == 7) || (ligne == 7 && colonne == 8)
								|| (ligne == 6 && colonne == 6) || (ligne == 6 && colonne == 7)
								|| (ligne == 6 && colonne == 8) || (ligne == 6 && colonne == 11)
								|| (ligne == 6 && colonne == 12) || (ligne == 6 && colonne == 13)
								|| (ligne == 5 && colonne == 6) || (ligne == 5 && colonne == 7)
								|| (ligne == 5 && colonne == 10) || (ligne == 5 && colonne == 11)
								|| (ligne == 5 && colonne == 12) || (ligne == 5 && colonne == 13)) {

							terrainTmp = FORET;
						}

						if ((ligne == 0 && colonne == 3) || (ligne == 0 && colonne == 4)
								|| (ligne == 0 && colonne == 14) || (ligne == 0 && colonne == 15)
								|| (ligne == 0 && colonne == 16) || (ligne == 1 && colonne == 3)
								|| (ligne == 1 && colonne == 4) || (ligne == 1 && colonne == 5)
								|| (ligne == 1 && colonne == 12) || (ligne == 1 && colonne == 13)
								|| (ligne == 1 && colonne == 14) || (ligne == 2 && colonne == 6)
								|| (ligne == 2 && colonne == 7) || (ligne == 2 && colonne == 11)
								|| (ligne == 2 && colonne == 12) || (ligne == 3 && colonne == 6)
								|| (ligne == 3 && colonne == 7) || (ligne == 3 && colonne == 8)
								|| (ligne == 3 && colonne == 9) || (ligne == 3 && colonne == 10)
								|| (ligne == 3 && colonne == 11) || (ligne == 4 && colonne == 7)
								|| (ligne == 4 && colonne == 8) || (ligne == 4 && colonne == 9)
								|| (ligne == 4 && colonne == 10)) {

							terrainTmp = DESERT;
						}

						if ((ligne == 2 && colonne == 5) || (ligne == 3 && colonne == 5)
								|| (ligne == 2 && colonne == 13) || (ligne == 9 && colonne == 6)
								|| (ligne == 9 && colonne == 14) || (ligne == 9 && colonne == 5)
								|| (ligne == 9 && colonne == 13) || (ligne == 8 && colonne == 6)
								|| (ligne == 8 && colonne == 14)) {

							terrainTmp = VILLAGE;
						}

						switch (terrainTmp) {
						case PLAINE:
							map[ligne][colonne] = new Hexagone(PLAINE, 0.1, 1, ligne, colonne);
							break;
						case FORET:
							map[ligne][colonne] = new Hexagone(FORET, 0.5, 2, ligne, colonne);
							break;
						case VILLAGE:
							map[ligne][colonne] = new Hexagone(VILLAGE, 0.3, 1, ligne, colonne);
							break;
						case LAC:
							map[ligne][colonne] = new Hexagone(LAC, 0.1, 2, ligne, colonne);
							break;
						case NEIGE:
							map[ligne][colonne] = new Hexagone(NEIGE, 0.7, 3, ligne, colonne);
							break;
						case MER:
							map[ligne][colonne] = new Hexagone(MER, 0, 0, ligne, colonne);
							break;
						case DESERT:
							map[ligne][colonne] = new Hexagone(DESERT, 0.1, 2, ligne, colonne);
							break;
						default:
							break;
						}

						if (colonne > 0 && listeMap.get(ligne).size() < colonne)
							listeMap.get(ligne).remove(colonne); // retire les coordonnées de la liste
						if (listeMap.get(ligne).isEmpty()) {
							lignePossible.remove(ligne);
						}
					}
				}

			}
		}
		initVoisins();
	}

	public static void initVoisins() {
		for (int i = 0; i < MAPLIGNE; i++) {
			for (int j = 0; j < MAPCOLONNE; j++) {
				map[i][j].initListeVoisin();
			}
		}
	}

	public static int getElementAleatoire(final List<Integer> liste) {
		Random alea = new Random();
		return liste.get(alea.nextInt(liste.size()));
	}

	public static void controlAffichageUnite() {
		infoUnite.clear();
		for (Joueur joueur : listeJoueurs) {
			for (Unite unite : joueur.getListeUnite()) {
				ArrayList<Integer> unites = new ArrayList<Integer>();
				unites.add(joueur.getNumeroJoueur());
				unites.add(unite.getTypeUnite());
				unites.add(unite.getX());
				unites.add(unite.getY());
				infoUnite.add(unites);
			}
		}
	}

	public static void controlSurligne(final HashMap<Hexagone, Integer> deplacementPossibleHash,
			final HashMap<Hexagone, String> actionPossibleHash) {

		deplacementPossible.clear();
		actionPossible.clear();

		for (Hexagone i : deplacementPossibleHash.keySet()) {
			ArrayList<Integer> deplacementP = new ArrayList<Integer>();
			deplacementP.add(i.getX());
			deplacementP.add(i.getY());
			deplacementPossible.add(deplacementP);
		}

		for (Hexagone i : actionPossibleHash.keySet()) {
			ArrayList<Object> actionP = new ArrayList<Object>();
			actionP.add(i.getX());
			actionP.add(i.getY());
			actionP.add(actionPossibleHash.get(i));
			actionPossible.add(actionP);
		}
	}

	public static void affichageDeplacementPossible() {
		controlAffichageUnite();
		controlSurligne(deplacementPossibleHash, actionPossibleHash);
		plateau.repaint();
	}

	public static void affichageUnite() {
		controlAffichageUnite();
		deplacementPossible.clear();
		actionPossible.clear();
		plateau.repaint();
	}

	public static Point getCoordHexaClicked() {
		Point mouse = new Point(-1, -1);
		int hX = -1;
		int hY = -1;
		mouse = LogiqueJeu.getFrame().getClicPos();
		int X = mouse.y;
		int Y = mouse.x;
		find: for (int ligne = 0; ligne < LogiqueJeu.MAPLIGNE; ligne++) {
			for (int colonne = 0; colonne < LogiqueJeu.MAPCOLONNE; colonne++) {
				int refX = 30 + 45 * ligne;
				int refY = 25 * (ligne & 1) + colonne * 50;
				if (Y >= refY && Y < refY + 50) {
					int dX = (int) (30 - 15.0 / 25.0 * (Math.abs(refY + 25 - Y)));
					if (X >= refX - dX && X <= refX + dX) {
						hX = ligne;
						hY = colonne;
						break find;
					}
				}
			}
		}
		return new Point(hX, hY);
	}

	public static void sauvegarderPartie(final String saveName) {
		final File saveFile = new File("./" + saveName);
		try {
			if (!saveFile.exists()) {
				System.out.println("Fichier inexistant");
				saveFile.createNewFile();
			}
			final FileWriter save = new FileWriter(saveFile);
			try {
				int x, y;
				for (y = 0; y < MAPLIGNE; y++) {
					for (x = 0; x < MAPCOLONNE; x++) {
						save.write(map[y][x].getType());
					}
				}
				save.write(listeJoueurs.size());
				for (Joueur joueur : listeJoueurs) {
					save.write(joueur.getPseudo().length());
					for (int i = 0; i < joueur.getPseudo().length(); i++) {
						save.write(joueur.getPseudo().charAt(i));
					}
					save.write(joueur.getListeUnite().size());
					for (Unite unite : joueur.getListeUnite()) {
						save.write(unite.getTypeUnite());
						save.write(unite.getAttaque());
						save.write(unite.getDefense());
						save.write(unite.getPv());
						save.write(unite.getPvMax());
						save.write(unite.getPtDeDeplacement());
						save.write(unite.getPtDeDeplacementMax());
						save.write(unite.getVision());
						save.write(unite.getPorte());
						save.write(unite.getX());
						save.write(unite.getY());
					}
				}
				save.write(turn);
			} finally {
				save.close();
			}
		} catch (IOException e) {
			System.out.println("Impossible de creer le fichier");
		}
	}

	public static ArrayList<String> getCaractUniteEnMouvement(final Point hexa) {

		ArrayList<String> listeCaractAffichage = new ArrayList<String>();
		listeCaractAffichage.clear();
		int px = (int) hexa.getX();
		int py = (int) hexa.getY();

		for (Joueur j : LogiqueJeu.getListeJoueurs()) {
			for (Unite u : j.getListeUnite()) {
				if (px == u.getX() && py == u.getY()) {
					listeCaractAffichage.add(String.valueOf(u.getPv()));
					listeCaractAffichage.add(String.valueOf(u.getPvMax()));
					listeCaractAffichage.add(String.valueOf(u.getDefense()));
					listeCaractAffichage.add(String.valueOf(u.getAttaque()));
					listeCaractAffichage.add(String.valueOf(u.getVision()));
					listeCaractAffichage.add(String.valueOf(u.getPtDeDeplacement()));
					listeCaractAffichage.add(String.valueOf(u.getTypeUnite()));
					break;
				}
			}
		}
		return listeCaractAffichage;

	}

	public static int getTurn() {
		return turn;
	}

	public static void nextTurn() {
		turn = turn % listeJoueurs.size() + 1;
	}

	public static boolean getClicFlag() {
		return clicFlag;
	}

	public static void setClicFlag(final boolean clicFlag) {
		LogiqueJeu.clicFlag = clicFlag;
	}

	public static void setSkipFlag(final boolean skipFlag) {
		LogiqueJeu.skipFlag = skipFlag;
	}

	public static boolean getSkipFlag() {
		return skipFlag;
	}

	public static void setStarted(final boolean started) {
		LogiqueJeu.started = started;
	}

	public static boolean hasStarted() {
		return started;
	}

	public static ArrayList<Joueur> getListeJoueurs() {
		return listeJoueurs;
	}

	public static Hexagone[][] getMap() {
		return map;
	}

	public static MainJFrame getFrame() {
		return frame;
	}

	public static IHMPlateau getPlateau() {
		return plateau;
	}

	public static void setPlateau(final IHMPlateau plateau) {
		LogiqueJeu.plateau = plateau;
	}

	public static ArrayList<ArrayList<Integer>> getInfoUnite() {
		return infoUnite;
	}

	public static ArrayList<ArrayList<Integer>> getDeplacementPossible() {
		return deplacementPossible;
	}

	public static ArrayList<ArrayList<Object>> getActionPossible() {
		return actionPossible;
	}

	public static void setDeplacementPossibleHash(final HashMap<Hexagone, Integer> deplacementPossibleHash) {
		LogiqueJeu.deplacementPossibleHash = deplacementPossibleHash;
	}

	public static void setActionPossibleHash(final HashMap<Hexagone, String> actionPossibleHash) {
		LogiqueJeu.actionPossibleHash = actionPossibleHash;
	}

	public static String getLastAttaque() {
		return lastAttaque;
	}

	public static void setLastAttaque(final String lastAttaque) {
		LogiqueJeu.lastAttaque = lastAttaque;
	}

}
