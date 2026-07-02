package modele;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Joueur {
    protected int numeroJoueur;
    protected String pseudo;
    protected ArrayList<Unite> listeUnite = new ArrayList<Unite>();

    public Joueur(final int numeroJoueur, final String pseudo) {
        this.numeroJoueur = numeroJoueur;
        this.pseudo = pseudo;

        if (numeroJoueur == 1) {
            listeUnite.add(new Archer(1, 0, 2));
            listeUnite.add(new Mage(1, 1, 1));
            listeUnite.add(new Guerrier(1, 2, 1));
            listeUnite.add(new Chevalier(1, 3, 0));
            listeUnite.add(new Soigneuse(1, 3, 1));
        } else if (numeroJoueur == 2) {
            listeUnite.add(new Archer(2, 11, 16));
            listeUnite.add(new Mage(2, 10, 17));
            listeUnite.add(new Guerrier(2, 9, 17));
            listeUnite.add(new Chevalier(2, 8, 18));
            listeUnite.add(new Soigneuse(2, 8, 17));
        } else if (numeroJoueur == 3) {
            listeUnite.add(new Archer(3, 11, 2));
            listeUnite.add(new Mage(3, 10, 2));
            listeUnite.add(new Guerrier(3, 9, 1));
            listeUnite.add(new Chevalier(3, 8, 1));
            listeUnite.add(new Soigneuse(3, 8, 3));
        } else {
            listeUnite.add(new Archer(4, 0, 16));
            listeUnite.add(new Mage(4, 1, 16));
            listeUnite.add(new Guerrier(4, 2, 17));
            listeUnite.add(new Chevalier(4, 3, 17));
            listeUnite.add(new Soigneuse(4, 4, 17));
      }
  }

    public void jouerTour() {

    }

    public int getNumeroJoueur() {
        return numeroJoueur;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setListeUnite(final ArrayList<Unite> listeUnite) {
        this.listeUnite = listeUnite;
    }

    public ArrayList<Unite> getListeUnite() {
        return listeUnite;
    }
}
