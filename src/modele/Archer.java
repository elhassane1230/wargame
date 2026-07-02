package modele;

import controleur.LogiqueJeu;

public class Archer extends Unite {
    private static final int ATTAQUE = 15;
    private static final int DEFENSE = 5;
    private static final int PV = 20;
    private static final int DEPLACEMENT = 4;
    private static final int VISION = 6;
    private static final int PORTEE = 3;

    public Archer(final int equipe, final int x, final int y) {
        super(LogiqueJeu.ARCHER, ATTAQUE, DEFENSE, PV, DEPLACEMENT, VISION, PORTEE, x, y, equipe);
    }

}
