package vue;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import modele.Joueur;

public class VictoireFrame {

	public VictoireFrame(Joueur winner) {
		JFrame frame = new JFrame();
		frame.setBounds(0, 0, 1000, 650);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		System.out.println("OUIII");
		ImageIcon imageIcon2 = new ImageIcon("assets/images/victoire.png"); // load the image to a
		Image image2 = imageIcon2.getImage(); // transform it
		Image newimg2 = image2.getScaledInstance(1000, 650, java.awt.Image.SCALE_SMOOTH); // scale it the smooth
		imageIcon2 = new ImageIcon(newimg2); // transform it back

		JLabel p2 = new JLabel(imageIcon2, JLabel.CENTER);
		p2.setBounds(0, 0, 1000, 650);
		frame.getContentPane().add(p2);
		frame.getContentPane().setBackground(new Color(34, 115, 119));
		
		Font fontT = new Font("serif", Font.BOLD, 45);
		
		JLabel lblPointDeDeplacement = new JLabel("<html><font color=\"#993366\">" + winner.getPseudo() + "</font></html>");
		lblPointDeDeplacement.setBounds(430, 20, 300, 50);
		lblPointDeDeplacement.setFont(fontT);
		frame.getContentPane().add(lblPointDeDeplacement);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
}
