package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class Aide {

	private JFrame frame;

	public Aide() {
		initialize();
	}

	private void initialize() {

		Dimension size = new Dimension(1000, 650);
		frame = new JFrame("Wargame");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(size);
		frame.setMaximumSize(size);
		frame.setMinimumSize(size);

		JLabel label = new JLabel();
		JLabel labelP1 = new JLabel();
		JLabel labelP2 = new JLabel();
		JLabel labelP3 = new JLabel();
		JLabel labelP4 = new JLabel();
		JLabel labelP5 = new JLabel();
		JLabel labelTerrain = new JLabel();

		label.setText("<html><h1>1 Le jeu</h1><br>"
				+ "Un wargame (jeu de guerre) est un jeu permettant à un ou plusieurs joueurs de simuler des batailles.<br> Un wargame est"
				+ "composé d'un système de jeu (règles) et de scénarios. Dans un wargame tactique, un <br>scénario représente une bataille"
				+ "où évoluent des unités d'au maximum quelques dizaines de soldats (compagnie). <br>L'univers dans lequel le jeu se déroule"
				+ "est libre. On peut citer par exemple : historique (Mémoire 44, <br>War in the east), fantastique (Battlelore, Battle for"
				+ "Wesnoth), science-_ction (Full metal planet, Crimson _eld..)</html>");
		label.setFont(new Font("Serif", Font.BOLD, 17));
		label.setForeground(Color.white);
		labelP1.setText("<html><h1>2 Unités</h1>"
				+ "Chaque unité possède un type (infanterie légère, infanterie lourde, . . .).Le jeu devra proposer au <br>moins cinq types"
				+ "différents d'unité dont au minimum une disposant de capacité d'attaque à distance..<br></html>");

		labelP1.setFont(new Font("Serif", Font.BOLD, 17));
		labelP1.setForeground(Color.white);

		labelP2.setText("<html><h1>3 Conditions de victoire</h1><br>"
				+ "Dans un scénario, chaque adversaire peut avoir des conditions de victoire différentes. <br>Deux conditions de victoire sont"
				+ "possibles :<br>"
				+ "- Destruction complète de l'armée adverse : cela représente la réussite de l'attaque d'un <br>objectif, ou - Atteindre"
				+ "un numéro de tour sans avoir été détruit, i.e. Après un nombre donné <br>de tours, un joueur possède encore au"
				+ "moins une unité : cela correspond à la réussite de la défense d'un objectif. </html>");

		labelP2.setFont(new Font("Serif", Font.BOLD, 17));
		labelP2.setForeground(Color.white);

		JButton exit = new JButton("Retour au Menu");
		exit.setBackground(new Color(0, 204, 153));
		exit.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.WHITE));
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				frame.dispose();
			}
		});

		ImageIcon imageIcon = new ImageIcon("assets/images/Regles.png"); // load the image to a
		Image image = imageIcon.getImage(); // transform it
		Image newimg = image.getScaledInstance(1000, 650, java.awt.Image.SCALE_SMOOTH); // scale it the smooth
		imageIcon = new ImageIcon(newimg); // transform it back

		JLabel panel = new JLabel(imageIcon);
		panel.setBackground(new Color(200, 153, 255));

		SpringLayout sl_panel = new SpringLayout();
		panel.setPreferredSize(new Dimension(1000, 650));
		panel.setLayout(sl_panel);
		panel.setAlignmentY(Component.CENTER_ALIGNMENT);
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);

		sl_panel.putConstraint(SpringLayout.NORTH, label, 50, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, label, 150, SpringLayout.WEST, panel);

		sl_panel.putConstraint(SpringLayout.WEST, labelP1, 0, SpringLayout.WEST, label);
		sl_panel.putConstraint(SpringLayout.SOUTH, labelP1, 300, SpringLayout.NORTH, label);

		sl_panel.putConstraint(SpringLayout.WEST, labelP2, 0, SpringLayout.WEST, labelP1);
		sl_panel.putConstraint(SpringLayout.SOUTH, labelP2, 300, SpringLayout.NORTH, labelP1);

		sl_panel.putConstraint(SpringLayout.WEST, labelP3, 0, SpringLayout.WEST, labelP2);
		sl_panel.putConstraint(SpringLayout.SOUTH, labelP3, 200, SpringLayout.NORTH, labelP2);

		sl_panel.putConstraint(SpringLayout.WEST, labelP4, 0, SpringLayout.WEST, labelP3);
		sl_panel.putConstraint(SpringLayout.SOUTH, labelP4, 200, SpringLayout.NORTH, labelP3);

		sl_panel.putConstraint(SpringLayout.WEST, labelP5, 0, SpringLayout.WEST, labelP4);
		sl_panel.putConstraint(SpringLayout.SOUTH, labelP5, 200, SpringLayout.NORTH, labelP4);

		sl_panel.putConstraint(SpringLayout.WEST, labelTerrain, 0, SpringLayout.WEST, labelP5);
		sl_panel.putConstraint(SpringLayout.SOUTH, labelTerrain, 300, SpringLayout.NORTH, labelP5);

		sl_panel.putConstraint(SpringLayout.WEST, exit, 870, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.NORTH, exit, 4, SpringLayout.NORTH, panel);

		panel.add(label);
		panel.add(labelP1);
		panel.add(labelP2);
		panel.add(labelP3);
		panel.add(labelP4);
		panel.add(labelP5);
		panel.add(labelTerrain);
		panel.add(exit);

		frame.add(panel); // add scrollpane to frame

		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

}
