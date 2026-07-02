package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import controleur.LogiqueJeu;

public class MenuPrincipal {

	private JFrame frame;

	public MenuPrincipal() {
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

		BufferedImage imagefond = null;
		try {
			imagefond = ImageIO.read(new File("assets/images/back.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		CustomPanel panel = new CustomPanel(imagefond.getScaledInstance(1000, 650, imagefond.SCALE_SMOOTH));
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);
		panel.setAlignmentY(Component.CENTER_ALIGNMENT);
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton jvj = new JButton("Joueur VS Joueur");
		jvj.setBounds(145, 161, 272, 36);
		jvj.setBackground(new Color(0, 204, 153));
		jvj.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.WHITE));
		jvj.setPreferredSize(new Dimension(200, 40));
		jvj.setAlignmentX(Component.CENTER_ALIGNMENT);
		jvj.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {

				JFrame frame2 = new JFrame();
				frame2.setBounds(100, 100, 350, 430);

				frame2.setLocationRelativeTo(null);
				frame2.setVisible(true);

				ImageIcon imageIcon = new ImageIcon("assets/images/miniMenu_2.jpg"); // load the image to a
				Image image = imageIcon.getImage(); // transform it
				Image newimg = image.getScaledInstance(380, 420, java.awt.Image.SCALE_SMOOTH); // scale it the smooth
				imageIcon = new ImageIcon(newimg); // transform it back

				JLabel panel2 = new JLabel(imageIcon);
				frame2.getContentPane().add(panel2, BorderLayout.CENTER);
				panel2.setLayout(null);

				JLabel lblNewLabel_1 = new JLabel("Nombre joueurs  ");
				lblNewLabel_1.setBounds(100, 80, 200, 50);

				lblNewLabel_1.setFont(new Font("Serif", Font.BOLD, 20));
				panel2.add(lblNewLabel_1);

				JLabel lblNewLabel2 = new JLabel("");
				lblNewLabel2.setVerticalAlignment(SwingConstants.TOP);
				lblNewLabel2.setBounds(167, 83, 249, 131);
				panel2.add(lblNewLabel2);

				JButton btnNewButton_2 = new JButton("2 Joueurs");
				btnNewButton_2.setBounds(70, 130, 200, 40);
				btnNewButton_2.setBackground(new Color(0, 204, 153));
				btnNewButton_2.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.WHITE));
				panel2.add(btnNewButton_2);
				btnNewButton_2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frame2.dispose();
						frame.dispose();
						LogiqueJeu.start(2, 0);
						LogiqueJeu.setStarted(true);
					}

				});

				JButton btnNewButton_3 = new JButton("3 Joueurs");
				btnNewButton_3.setBounds(70, 180, 200, 40);
				btnNewButton_3.setBackground(new Color(0, 204, 153));
				btnNewButton_3.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.WHITE));
				panel2.add(btnNewButton_3);
				btnNewButton_3.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frame2.dispose();
						frame.dispose();
						LogiqueJeu.start(3, 0);
						LogiqueJeu.setStarted(true);
					}

				});

				JButton btnNewButton_4 = new JButton("4 Joueurs");
				btnNewButton_4.setBounds(70, 230, 200, 40);
				btnNewButton_4.setBackground(new Color(0, 204, 153));
				btnNewButton_4.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.WHITE));
				panel2.add(btnNewButton_4);
				btnNewButton_4.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frame2.dispose();
						frame.dispose();
						LogiqueJeu.start(4, 0);
						LogiqueJeu.setStarted(true);
					}

				});

				frame2.setVisible(true);

			}
		});

		Integer[] items3 = { 1, 2, 3, 4 };
		JComboBox<Integer> liste_3 = new JComboBox<Integer>(items3);
		liste_3.setBounds(145, 161, 272, 36);
		liste_3.setPreferredSize(new Dimension(80, 40));
		liste_3.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton jva = new JButton("Joueur Vs IA");
		jva.setBounds(145, 161, 272, 36);
		jva.setBackground(new Color(0, 204, 153));
		jva.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.WHITE));
		jva.setPreferredSize(new Dimension(200, 40));
		jva.setAlignmentX(Component.CENTER_ALIGNMENT);
		jva.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {

				JFrame frame2 = new JFrame();
				frame2.setBounds(100, 100, 350, 430);

				frame2.setLocationRelativeTo(null);
				frame2.setVisible(true);

				ImageIcon imageIcon = new ImageIcon("assets/images/miniMenu_2.1.jpg"); // load the image to
																									// a
				Image image = imageIcon.getImage(); // transform it
				Image newimg = image.getScaledInstance(380, 420, java.awt.Image.SCALE_SMOOTH); // scale it the smooth
				imageIcon = new ImageIcon(newimg); // transform it back
				JLabel panel2 = new JLabel(imageIcon);
				frame2.getContentPane().add(panel2, BorderLayout.CENTER);
				panel2.setLayout(null);

				JLabel lblNewLabel_1 = new JLabel("Nombre IA  ");
				lblNewLabel_1.setBounds(120, 35, 200, 50);
				lblNewLabel_1.setFont(new Font("Serif", Font.BOLD, 20));
				panel2.add(lblNewLabel_1);

				JLabel lblNewLabel2 = new JLabel("");
				lblNewLabel2.setVerticalAlignment(SwingConstants.TOP);
				lblNewLabel2.setBounds(167, 13, 249, 131);
				panel2.add(lblNewLabel2);

				JButton btnNewButton_2 = new JButton("1 HUMAIN vs 3 IA");
				btnNewButton_2.setBounds(70, 80, 200, 40);
				btnNewButton_2.setBackground(new Color(0, 204, 153));
				btnNewButton_2.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.WHITE));
				panel2.add(btnNewButton_2);
				btnNewButton_2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frame2.dispose();
						frame.dispose();
						LogiqueJeu.start(1, 3);
						LogiqueJeu.setStarted(true);

					}

				});

				JButton btnNewButton_3 = new JButton("2 HUMAIN vs 2 IA");
				btnNewButton_3.setBounds(70, 130, 200, 40);
				btnNewButton_3.setBackground(new Color(0, 204, 153));
				btnNewButton_3.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.WHITE));
				panel2.add(btnNewButton_3);
				btnNewButton_3.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frame2.dispose();
						frame.dispose();
						LogiqueJeu.start(2, 2);
						LogiqueJeu.setStarted(true);
					}

				});

				JButton btnNewButton_4 = new JButton("3 HUMAIN vs 1 IA");
				btnNewButton_4.setBounds(70, 180, 200, 40);
				btnNewButton_4.setBackground(new Color(0, 204, 153));
				btnNewButton_4.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.WHITE));
				panel2.add(btnNewButton_4);
				btnNewButton_4.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frame2.dispose();
						frame.dispose();
						LogiqueJeu.start(3, 1);
						LogiqueJeu.setStarted(true);
					}

				});

				JButton btnNewButton_5 = new JButton("1 HUMAIN vs 1 IA");
				btnNewButton_5.setBounds(70, 230, 200, 40);
				btnNewButton_5.setBackground(new Color(0, 204, 153));
				btnNewButton_5.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.WHITE));
				panel2.add(btnNewButton_5);
				btnNewButton_5.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frame2.dispose();
						frame.dispose();
						LogiqueJeu.start(1, 1);
						LogiqueJeu.setStarted(true);
					}

				});

				JButton btnNewButton_6 = new JButton("2 HUMAIN vs 1 IA");
				btnNewButton_6.setBounds(70, 280, 200, 40);
				btnNewButton_6.setBackground(new Color(0, 204, 153));
				btnNewButton_6.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.WHITE));
				panel2.add(btnNewButton_6);
				btnNewButton_6.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frame2.dispose();
						frame.dispose();
						LogiqueJeu.start(2, 1);
						LogiqueJeu.setStarted(true);
					}

				});

				frame2.setVisible(true);

			}
		});

		JButton cp = new JButton("Charger une partie");
		cp.setBounds(145, 161, 272, 36);
		cp.setBackground(new Color(0, 204, 153));
		cp.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.WHITE));
		cp.setPreferredSize(new Dimension(200, 40));
		cp.setAlignmentX(Component.CENTER_ALIGNMENT);
		cp.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				
			}
		});

		JButton aide = new JButton("<html><font color=\"white\">Aide</font></html>");
		aide.setBounds(145, 161, 272, 36);
		Font font = new Font("Courier", Font.BOLD, 18);
		aide.setFont(font);
		aide.setBackground(new Color(0, 204, 153));
		aide.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.WHITE));
		aide.setBackground(new Color(153, 0, 0));
		aide.setPreferredSize(new Dimension(90, 30));
		aide.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				Aide regle = new Aide();
			}
		});
		aide.setAlignmentX(Component.CENTER_ALIGNMENT);

		sl_panel.putConstraint(SpringLayout.NORTH, jvj, 270, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, jvj, 400, SpringLayout.WEST, panel);

		sl_panel.putConstraint(SpringLayout.WEST, jva, 0, SpringLayout.WEST, jvj);
		sl_panel.putConstraint(SpringLayout.SOUTH, jva, 100, SpringLayout.NORTH, jvj);

		sl_panel.putConstraint(SpringLayout.NORTH, liste_3, 0, SpringLayout.NORTH, jva);
		sl_panel.putConstraint(SpringLayout.WEST, liste_3, 420, SpringLayout.WEST, jva);

		sl_panel.putConstraint(SpringLayout.WEST, cp, 0, SpringLayout.WEST, jva);
		sl_panel.putConstraint(SpringLayout.SOUTH, cp, 100, SpringLayout.NORTH, jva);

		sl_panel.putConstraint(SpringLayout.WEST, aide, 900, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.NORTH, aide, 4, SpringLayout.NORTH, panel);

		panel.add(jva);
		panel.add(jvj);
		panel.add(cp);
		panel.add(aide);

		frame.add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
