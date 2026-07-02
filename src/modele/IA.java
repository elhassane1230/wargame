package modele;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import controleur.LogiqueJeu;

public class IA extends Joueur {

    public IA(final int numeroJoueur, final String pseudo) {
        super(numeroJoueur, pseudo);
    }

    @Override
    public void jouerTour() {
        for (Unite unite : this.getListeUnite()) {
            CleMap<Hexagone, Integer> deplacementPossible = unite.calculDeplacementPossible();
            Iterator<Map.Entry<Hexagone, Integer>> iterator = deplacementPossible.entrySet().iterator();

            recherche: while (iterator.hasNext()) {
                Map.Entry<Hexagone, Integer> mapEntry = iterator.next();
                Hexagone hexagone = mapEntry.getKey();

                for (Joueur joueur : LogiqueJeu.getListeJoueurs()) {
                    if (joueur != this) {
                        for (Unite uniteAdverse : joueur.getListeUnite()) {
                            if (unite.getTypeUnite() != LogiqueJeu.PRETRE && hexagone.getDistanceBetweenTwoPosition(
                                    LogiqueJeu.getMap()[uniteAdverse.getX()][uniteAdverse.getY()]) <= unite.getPorte()) {
                                unite.setX(hexagone.getX());
                                unite.setY(hexagone.getY());
                                unite.setPtDeDeplacement((Integer) mapEntry.getValue());
                                LogiqueJeu.affichageUnite();

                                unite.attaquer(uniteAdverse);

                                deplacementPossible = unite.calculDeplacementPossible();

                                break recherche;
                            }
                        }
                    }
                }
            }

            if (unite.getTypeUnite() == LogiqueJeu.PRETRE) {
                for (Unite uniteAlliee : listeUnite) {
                    if (uniteAlliee != unite && uniteAlliee.getPv() != uniteAlliee.getPvMax()
                            && LogiqueJeu.getMap()[unite.getX()][unite.getY()].getDistanceBetweenTwoPosition(
                                    LogiqueJeu.getMap()[uniteAlliee.getX()][uniteAlliee.getY()]) <= unite.getPorte()) {
                        unite.soigner(uniteAlliee);
                    }
                }
            }

            final double pourcentagePV = 0.75;

            if (!deplacementPossible.isEmpty() && unite.getPv() >= (int) (pourcentagePV * unite.getPvMax())) {

                Object[] tabDeplacementPossible = deplacementPossible.keySet().toArray();
                Object key = tabDeplacementPossible[new Random().nextInt(tabDeplacementPossible.length)];
                Hexagone hexagone = (Hexagone) key;
                unite.setX(hexagone.getX());
                unite.setY(hexagone.getY());
                unite.setPtDeDeplacement((Integer) deplacementPossible.get(key));
                LogiqueJeu.affichageUnite();
            }

        }

        for (Unite unite : this.getListeUnite()) {
            unite.preparerPourProchainTour();
        }
    }
}
