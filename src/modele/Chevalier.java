package modele;

import controleur.LogiqueJeu;

public class Chevalier extends Unite {
   
    private static final int ATTAQUE = 10;
    private static final int DEFENSE = 8;
    private static final int PV = 30;
    private static final int DEPLACEMENT = 6;
    private static final int VISION = 10;
    private static final int PORTEE = 1;

    public Chevalier(final int equipe, final int x, final int y) {
        super(LogiqueJeu.CHEVALIER, ATTAQUE, DEFENSE, PV, DEPLACEMENT, VISION, PORTEE, x, y, equipe);
    }

}
