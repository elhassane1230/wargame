package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controleur.LogiqueJeu;
import controleur.Main;

public class MainJFrame {
	public static JFrame frame;
	private Point mouse = new Point(-1, -1);
	private ArrayList<JLabel> listeCaractAffichage = new ArrayList<JLabel>();
	private PaintImage image = new PaintImage("assets/images/j1.png");
	private JLabel labelLastAtt;

	public MainJFrame() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1058, 950);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		LogiqueJeu.setPlateau(new IHMPlateau());
		LogiqueJeu.getPlateau().setBounds(0, 21, 1048, 591);
		LogiqueJeu.getPlateau().addMouseListener(new MouseListener() {

			public void mouseClicked(final MouseEvent e) {
			}

			public void mousePressed(final MouseEvent e) {
				mouse = e.getPoint();
				LogiqueJeu.setClicFlag(true);
				System.out.println();
				ArrayList<String> caracteristiquesUnite = LogiqueJeu
						.getCaractUniteEnMouvement(LogiqueJeu.getCoordHexaClicked());
				if (!caracteristiquesUnite.isEmpty()) {
					listeCaractAffichage.get(0).setText(caracteristiquesUnite.get(0));
					listeCaractAffichage.get(1).setText(caracteristiquesUnite.get(5));

					switch (Integer.parseInt(caracteristiquesUnite.get(6))) {
					case LogiqueJeu.ARCHER:
						image = new PaintImage("assets/images/GrandArcher.png");
						break;
					case LogiqueJeu.CHEVALIER:
						image = new PaintImage("assets/images/GrandChevalier.png");
						break;
					case LogiqueJeu.GUERRIER:
						image = new PaintImage("assets/images/GrandGuerrier.png");
						break;
					case LogiqueJeu.PRETRE:
						image = new PaintImage("assets/images/GrandPretre.png");
						break;
					case LogiqueJeu.MAGE:
						image = new PaintImage("assets/images/GrandMage.png");
						break;
					default:
						break;
					}
				}
			}

			public void mouseReleased(final MouseEvent e) {
			}

			public void mouseEntered(final MouseEvent e) {
			}

			public void mouseExited(final MouseEvent e) {
			}

		});
		frame.getContentPane().add(LogiqueJeu.getPlateau());

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(360, 0, 300, 21);
		frame.getContentPane().add(menuBar);

		JMenu mnFichier = new JMenu("<html><h2>Bienvenue dans le jeu Wargame</h2></html>");

		menuBar.add(mnFichier);

		JButton mntmEnregistrer = new JButton("<html><font>Enregistrer</font></html>");
		mntmEnregistrer.setBounds(20, 850, 150, 40);
		Font font = new Font("serif", Font.BOLD, 18);
		mntmEnregistrer.setBackground(new Color(71, 209, 71));
		mntmEnregistrer.setFont(font);
		mntmEnregistrer.setPreferredSize(new Dimension(90, 30));

		mntmEnregistrer.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				JFileChooser choix = new JFileChooser();
				choix.setCurrentDirectory(new File(
						"." + System.getProperty("file.separator") + "saves" + System.getProperty("file.separator")));
				int retour = choix.showSaveDialog(frame);
				if (retour == JFileChooser.APPROVE_OPTION) {
					LogiqueJeu.sauvegarderPartie("." + System.getProperty("file.separator") + "saves"
							+ System.getProperty("file.separator") + choix.getSelectedFile().getName());
					JOptionPane.showMessageDialog(null, "Partie enregistrée", "Sauvegarde",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		frame.add(mntmEnregistrer);

		JButton mntmRetourMenu = new JButton("<html><font>Quitter</font></html>");
		mntmRetourMenu.setBounds(870, 850, 150, 40);
		mntmRetourMenu.setFont(font);
		mntmRetourMenu.setBackground(new Color(204, 0, 0));
		mntmRetourMenu.setPreferredSize(new Dimension(90, 30));

		mntmRetourMenu.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				LogiqueJeu.kill();
			}
		});
		frame.add(mntmRetourMenu);

		JButton btnNewButton = new JButton("<html><font>Fin de tour</html>");
		btnNewButton.setBounds(400, 810, 250, 50);
		Font f = new Font("serif", Font.BOLD, 35);
		btnNewButton.setFont(f);
		btnNewButton.setBackground(new Color(255, 255, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				LogiqueJeu.setSkipFlag(true);
			}
		});
		frame.add(btnNewButton);

		Font fontT = new Font("serif", Font.BOLD, 18);

		JPanel panel = new JPanel();
		panel.setBounds(1048, 21, 236, 591);
		frame.getContentPane().add(panel);

		JLabel lblPointDeVie = new JLabel("PV restant : ");
		lblPointDeVie.setBounds(10, 730, 116, 16);
		lblPointDeVie.setFont(fontT);
		frame.getContentPane().add(lblPointDeVie);

		JLabel lblPointDeDeplacement = new JLabel("Point PM : ");
		lblPointDeDeplacement.setBounds(10, 760, 116, 16);
		lblPointDeDeplacement.setFont(fontT);
		frame.getContentPane().add(lblPointDeDeplacement);

		JLabel lblNewLabelAffichagePvRest = new JLabel("-");
		lblNewLabelAffichagePvRest.setBounds(110, 730, 50, 16);
		lblNewLabelAffichagePvRest.setFont(fontT);
		frame.getContentPane().add(lblNewLabelAffichagePvRest);
		listeCaractAffichage.add(lblNewLabelAffichagePvRest);

		JLabel labelAffichagePtDeDeplacement = new JLabel("-");
		labelAffichagePtDeDeplacement.setBounds(110, 760, 50, 16);
		labelAffichagePtDeDeplacement.setFont(fontT);
		frame.getContentPane().add(labelAffichagePtDeDeplacement);
		listeCaractAffichage.add(labelAffichagePtDeDeplacement);

		image.setBounds(10, 610, 216, 120);
		panel.add(image);

		ImageIcon imageIcon2 = new ImageIcon("assets/images/listeUnites.png"); // load the image to a
		Image image2 = imageIcon2.getImage(); // transform it
		Image newimg2 = image2.getScaledInstance(800, 190, java.awt.Image.SCALE_SMOOTH); // scale it the smooth
		imageIcon2 = new ImageIcon(newimg2); // transform it back

		JLabel p2 = new JLabel(imageIcon2, JLabel.CENTER);
		p2.setBounds(236, 610, 800, 190);
		frame.getContentPane().add(p2);
		frame.getContentPane().setBackground(new Color(34, 115, 119));

		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
	}

	public JFrame getFrame() {
		return frame;
	}

	public Point getClicPos() {
		return mouse;
	}
}
