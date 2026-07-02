package modele;

import controleur.LogiqueJeu;

public class Guerrier extends Unite {
    private static final int ATTAQUE = 8;
    private static final int DEFENSE = 12;
    private static final int PV = 35;
    private static final int DEPLACEMENT = 4;
    private static final int VISION = 9;
    private static final int PORTEE = 1;

    public Guerrier(final int equipe, final int x, final int y) {
        super(LogiqueJeu.GUERRIER, ATTAQUE, DEFENSE, PV, DEPLACEMENT, VISION, PORTEE, x, y, equipe);
    }

}
