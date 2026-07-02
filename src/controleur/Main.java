package controleur;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import modele.Joueur;
import vue.MainJFrame;
import vue.MenuPrincipal;
import vue.VictoireFrame;

public class Main {

	static Joueur joueur;

	public static Joueur getJoueur() {
		return joueur;
	}

	public static void setJoueur(Joueur joueur) {
		Main.joueur = joueur;
	}

	public static void main(final String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		
		String soundName = "assets/musique/bgsound.wav";

		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
		AudioFormat format = audioInputStream.getFormat();
		Clip clip = AudioSystem.getClip();
		DataLine.Info infoMusique = new DataLine.Info(Clip.class, format);
		
		Line l = AudioSystem.getLine(infoMusique);
		
		if (!l.isOpen()) {
			clip.open(audioInputStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		}
		
		
	
		JLabel p = null;
		totality: do {
			MenuPrincipal menu = new MenuPrincipal();
			Joueur winner = null;
			Joueur J1 = new Joueur(1, "test");
			do {
				do {
					System.out.print("");
				} while (!LogiqueJeu.hasStarted());
				LogiqueJeu.affichageUnite();

				do {
					ArrayList<Joueur> perdant = new ArrayList<Joueur>();
					joueur = LogiqueJeu.getListeJoueurs().get(LogiqueJeu.getTurn() - 1); // tour des joueurs chacun leur tour
					if (p != null) {
						MainJFrame.frame.getContentPane().remove(p);
					}
					if (joueur.getPseudo().equalsIgnoreCase("Joueur 1")) {
						ImageIcon imageIcon = new ImageIcon("assets/images/j1.png"); // load the image to a
						Image image = imageIcon.getImage(); // transform it
						Image newimg = image.getScaledInstance(216, 120, java.awt.Image.SCALE_SMOOTH); // scale it the
						imageIcon = new ImageIcon(newimg); // transform it back

						p = new JLabel(imageIcon, JLabel.CENTER);
						p.setBounds(10, 610, 216, 120);
						MainJFrame.frame.getContentPane().add(p);
						MainJFrame.frame.repaint();
					} else if (joueur.getPseudo().equalsIgnoreCase("Joueur 2")) {
						ImageIcon imageIcon = new ImageIcon("assets/images/j2.png"); // load the image to a

						Image image = imageIcon.getImage(); // transform it
						Image newimg = image.getScaledInstance(216, 120, java.awt.Image.SCALE_SMOOTH); // scale it the
						imageIcon = new ImageIcon(newimg); // transform it back

						p = new JLabel(imageIcon, JLabel.CENTER);
						p.setBounds(10, 610, 216, 120);
						MainJFrame.frame.getContentPane().add(p);
						MainJFrame.frame.repaint();
					} else if (joueur.getPseudo().equalsIgnoreCase("Joueur 3")) {
						ImageIcon imageIcon = new ImageIcon("assets/images/j3.png"); // load the image to a
						Image image = imageIcon.getImage(); // transform it
						Image newimg = image.getScaledInstance(216, 120, java.awt.Image.SCALE_SMOOTH); // scale it the
						imageIcon = new ImageIcon(newimg); // transform it back

						p = new JLabel(imageIcon, JLabel.CENTER);
						p.setBounds(10, 610, 216, 120);
						MainJFrame.frame.getContentPane().add(p);
						MainJFrame.frame.repaint();
					} else if (joueur.getPseudo().equalsIgnoreCase("Joueur 4")) {
						ImageIcon imageIcon = new ImageIcon("assets/images/j4.png"); // load the image to a
						Image image = imageIcon.getImage(); // transform it
						Image newimg = image.getScaledInstance(216, 120, java.awt.Image.SCALE_SMOOTH); // scale it the
						imageIcon = new ImageIcon(newimg); // transform it back

						p = new JLabel(imageIcon, JLabel.CENTER);
						p.setBounds(10, 610, 216, 120);
						MainJFrame.frame.getContentPane().add(p);
						MainJFrame.frame.repaint();
						winner = J1;
						break;
					}

					joueur.jouerTour();
					if (!LogiqueJeu.hasStarted()) {
						// continue totality;
					}
					if (joueur.getListeUnite().size() == 0) { // mise en mémoire des perdants
						perdant.add(joueur);
					}
					if (!perdant.isEmpty()) { // on enlève les perdants de la liste
						LogiqueJeu.getListeJoueurs().removeAll(perdant);
					}
					LogiqueJeu.nextTurn();
				} while (LogiqueJeu.getListeJoueurs().size() > 1 && LogiqueJeu.hasStarted());
				LogiqueJeu.affichageUnite();
				winner = LogiqueJeu.getListeJoueurs().get(0);
			} while (winner == null);
			LogiqueJeu.kill();
			System.out.println("VICTOIRE !!!");
			MainJFrame.frame.dispose();
			VictoireFrame v = new VictoireFrame(winner);
		} while (true);
	}

}
