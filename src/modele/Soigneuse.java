package modele;

import controleur.LogiqueJeu;

public class Soigneuse extends Unite {
    private static final int ATTAQUE = 5;
    private static final int DEFENSE = 8;
    private static final int PV = 25;
    private static final int DEPLACEMENT = 5;
    private static final int VISION = 9;
    private static final int PORTEE = 1;
    private static final double SOIN = 0.3;

    public Soigneuse(final int equipe, final int x, final int y) {
        super(LogiqueJeu.PRETRE, ATTAQUE, DEFENSE, PV, DEPLACEMENT, VISION, PORTEE, x, y, equipe);
    }

    @Override
    public void soigner(final Unite unite) {
        unite.pv += (int) unite.pvMax * SOIN;
        if (unite.pv > unite.pvMax) {
            unite.pv = unite.pvMax;
        }
        acted = true;
        ptDeDeplacement = 0;
    }
}
