package modele;

import controleur.LogiqueJeu;
import java.awt.Point;

public class Humain extends Joueur {

    public Humain(final int numeroJoueur, final String pseudo) {
        super(numeroJoueur, pseudo);
    }

    @Override
    public void jouerTour() {
        int deplacable = 0;

        totality: do {
            boolean selected = false;
            do {
                deplacable = 0;
                selected = false;
                Point hexagone = new Point(-1, -1);
                do {
                    System.out.print(""); 
                    if (LogiqueJeu.getSkipFlag() || !LogiqueJeu.hasStarted()) {
                        LogiqueJeu.setSkipFlag(false);
                        break totality;
                    }
                    if (LogiqueJeu.getClicFlag()) {
                        hexagone = LogiqueJeu.getCoordHexaClicked();
                        LogiqueJeu.setClicFlag(false);
                    }
                } while (hexagone.x == -1 || hexagone.y == -1);
                for (Unite u : listeUnite) {
                    if (u.getX() == hexagone.x && u.getY() == hexagone.y) {
                        if (u.selected()) {
                            break totality;
                        }
                        selected = true;
                    }
                    int mindep = 10;
                    for (Hexagone h : LogiqueJeu.getMap()[u.getX()][u.getY()].getListeVoisin()) {
                        mindep = Math.min(mindep, h.getCoutDeDeplacement());
                    }
                    if (mindep <= u.getPtDeDeplacement() || !u.acted) {
                        deplacable++;
                    }
                }
            } while (!selected);
        } while (deplacable > 0);

        for (Unite unite : this.getListeUnite()) {
            unite.preparerPourProchainTour();
        }
    }
}
