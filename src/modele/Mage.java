package modele;

import controleur.LogiqueJeu;

public class Mage extends Unite {
    private static final int ATTAQUE = 13;
    private static final int DEFENSE = 7;
    private static final int PV = 25;
    private static final int DEPLACEMENT = 5;
    private static final int VISION = 7;
    private static final int PORTEE = 2;

    public Mage(final int equipe, final int x, final int y) {
        super(LogiqueJeu.MAGE, ATTAQUE, DEFENSE, PV, DEPLACEMENT, VISION, PORTEE, x, y, equipe);
    }

}
