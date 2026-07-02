package modele;

import java.util.ArrayList;

import controleur.LogiqueJeu;

public class Hexagone {

	private int type;
    private double bonusDefense;
    private int coutDeDeplacement;
    private int x;
    private int y;
    private ArrayList<Hexagone> listeVoisin = new ArrayList<Hexagone>();

    public Hexagone(final int type, final double bonusDefense, final int coutDeDeplacement, final int x, final int y) {
        this.type = type;
        this.bonusDefense = bonusDefense;
        this.coutDeDeplacement = coutDeDeplacement;
        this.x = x;
        this.y = y;
    }

    public void ajoutHexagoneVoisin(final Hexagone h) {
        listeVoisin.add(h);
    }

    public void initListeVoisin() {
        if (x % 2 == 0) {
            if (x + 1 < LogiqueJeu.MAPLIGNE) {
                this.ajoutHexagoneVoisin(LogiqueJeu.getMap()[x + 1][y]);
            }
            if (y + 1 < LogiqueJeu.MAPCOLONNE) {
                this.ajoutHexagoneVoisin(LogiqueJeu.getMap()[x][y + 1]);
            }
            if (x + 1 < LogiqueJeu.MAPLIGNE && y - 1 >= 0) {
                this.ajoutHexagoneVoisin(LogiqueJeu.getMap()[x + 1][y - 1]);
            }
            if (x - 1 >= 0) {
                this.ajoutHexagoneVoisin(LogiqueJeu.getMap()[x - 1][y]);
            }
            if (x - 1 >= 0 && y - 1 >= 0) {
                this.ajoutHexagoneVoisin(LogiqueJeu.getMap()[x - 1][y - 1]);
            }
            if (y - 1 >= 0) {
                this.ajoutHexagoneVoisin(LogiqueJeu.getMap()[x][y - 1]);
            }
        } else {
            if (x + 1 < LogiqueJeu.MAPLIGNE) {
                this.ajoutHexagoneVoisin(LogiqueJeu.getMap()[x + 1][y]);
            }
            if (x + 1 < LogiqueJeu.MAPLIGNE && y + 1 < LogiqueJeu.MAPCOLONNE) {
                this.ajoutHexagoneVoisin(LogiqueJeu.getMap()[x + 1][y + 1]);
            }
            if (y + 1 < LogiqueJeu.MAPCOLONNE) {
                this.ajoutHexagoneVoisin(LogiqueJeu.getMap()[x][y + 1]);
            }
            if (x - 1 >= 0) {
                this.ajoutHexagoneVoisin(LogiqueJeu.getMap()[x - 1][y]);
            }
            if (y - 1 >= 0) {
                this.ajoutHexagoneVoisin(LogiqueJeu.getMap()[x][y - 1]);
            }
            if (x - 1 >= 0 && y + 1 < LogiqueJeu.MAPCOLONNE) {
                this.ajoutHexagoneVoisin(LogiqueJeu.getMap()[x - 1][y + 1]);
            }
        }
    }
    public int getDistanceBetweenTwoPosition(final Hexagone h2) {

        double a = (double) this.x;
        double b = (double) this.y;
        double a2 = (double) h2.getX();
        double b2 = (double) h2.getY();

        double t1 = Math.abs((a - b / 2) - (a2 - b2 / 2));
        double t2 = Math.abs(b - b2);
        double t3 = Math.abs((a + b / 2) - (a2 + b2 / 2));

        double m = Math.max(Math.max(t1, t2), t3);

        int resultat = 0;

        final double arrondi = 0.5;

        if (((a == 0 && b == 0) && (a2 % 2 == 1 && b2 % 2 == 1))
                || ((a % 2 == 1 && b % 2 == 1) && (a2 == 0 && b2 == 0))
                || Math.abs(a - a2) == 1 && Math.abs(b - b2) == 1) { // si un des paramètres est 0,0 et que
                                                                           // l'autre a deux coordoonées impaires
            resultat = (int) Math.abs((m - arrondi)); // on arrondit à l'inférieur
        } else {
            resultat = (int) Math.abs((m + arrondi)); // on arrondit au supérieur
        }
        return resultat;
    }

    public int getType() {
        return type;
    }

    public double getBonusDefense() {
        return bonusDefense;
    }

    public int getCoutDeDeplacement() {
        return coutDeDeplacement;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ArrayList<Hexagone> getListeVoisin() {
        return listeVoisin;
    }
}
