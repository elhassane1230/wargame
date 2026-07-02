package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import controleur.LogiqueJeu;

public class IHMPlateau extends JPanel {
	private Polygon pol;
	static final int COTE = 30;
	private Graphics2D graphics2d;
	private BufferedImage image;
	private Graphics graph;
	private Rectangle r;

	public static Polygon getPolygon(final int x, final int y, final int cote) {
		int haut = cote / 2;
		int larg = (int) (cote * (Math.sqrt(3) / 2));
		Polygon p = new Polygon();
		p.addPoint(x, y + haut);
		p.addPoint(x + larg, y);
		p.addPoint(x + 2 * larg, y + haut);
		p.addPoint(x + 2 * larg, y + (int) (1.5 * cote));
		p.addPoint(x + larg, y + 2 * cote);
		p.addPoint(x, y + (int) (1.5 * cote));

		return p;
	}

	@Override
	public void paint(final Graphics graph) {
		Polygon hexagone = getPolygon(0, 0, COTE);
		r = hexagone.getBounds(); 
		graph.setColor(Color.BLACK);
		super.paint(graph);
		graphics2d = (Graphics2D) graph;
		this.graph = graph;
		image = null;
		BufferedImage imageMap = null;

		try {
			imageMap = ImageIO.read(new File("assets/images/Map7.jpeg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		setPreferredSize(new Dimension(imageMap.getWidth(), imageMap.getHeight()));
		setBackground(Color.WHITE);

		graphics2d.drawImage(imageMap, 0, 0, 1040, 580, this);
		graphics2d.setColor(Color.green);

		for (ArrayList<Integer> listeUnite : LogiqueJeu.getInfoUnite()) {
			try {
				switch (listeUnite.get(1)) {
				case 1:
					image = ImageIO.read(new File(
							"assets/images/guerrier" + listeUnite.get(0) + ".png"));
					break;
				case 2:
					image = ImageIO.read(
							new File("assets/images/mage" + listeUnite.get(0) + ".png"));
					break;
				case 3:
					image = ImageIO.read(
							new File("assets/images/archer" + listeUnite.get(0) + ".png"));
					break;
				case 4:
					image = ImageIO.read(new File(
							"assets/images/Soigneuse" + listeUnite.get(0) + ".png"));
					break;
				case 5:
					image = ImageIO.read(new File(
							"assets/images/chevalier" + listeUnite.get(0) + ".png"));
					break;
				default:
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (image != null) {
				graphics2d = (Graphics2D) graph;
				if (listeUnite.get(2) % 2 == 0) {
					if (listeUnite.get(1) == 2) {
						graphics2d.drawImage(image, listeUnite.get(3) * r.width + 5, (int) (listeUnite.get(2) * COTE * 1.5),
								this);
					} else {
						graphics2d.drawImage(image, listeUnite.get(3) * r.width + 5, (int) (listeUnite.get(2) * COTE * 1.5) + 7,
								this);
					}
				} else {
					if (listeUnite.get(1) == 2) {
						graphics2d.drawImage(image, listeUnite.get(3) * r.width + r.width / 2 + 5,
								(int) (listeUnite.get(2) * COTE * 1.5 + 0.5), this);
					} else {
						graphics2d.drawImage(image, listeUnite.get(3) * r.width + r.width / 2 + 5,
								(int) (listeUnite.get(2) * COTE * 1.5 + 0.5) + 7, this);
					}
				}
				repaint();
			}
		}
		Polygon poly = null;


		if (LogiqueJeu.getDeplacementPossible() != null) {
			for (ArrayList<Integer> deplacementPossible : LogiqueJeu.getDeplacementPossible()) {
				if (deplacementPossible.get(0) % 2 == 0) {
					poly = getPolygon(deplacementPossible.get(1) * r.width,
							(int) (deplacementPossible.get(0) * COTE * 1.5), COTE);
				} else {
					poly = getPolygon(deplacementPossible.get(1) * r.width + r.width / 2,
							(int) (deplacementPossible.get(0) * COTE * 1.5 + 0.5), COTE);
				}
				if (poly != null) {
					graphics2d.setColor(new Color(26, 255, 26, 150));
					graphics2d.fill(poly);
					graphics2d.setColor(new Color(255, 255, 255));
					graphics2d.draw(poly);
				}
			}
		}
		if (LogiqueJeu.getActionPossible() != null) {
			for (ArrayList<Object> actionPossible : LogiqueJeu.getActionPossible()) {
				if (((int) actionPossible.get(0)) % 2 == 0) {
					poly = getPolygon(((int) actionPossible.get(1)) * r.width,
							(int) (((int) actionPossible.get(0)) * COTE * 1.5), COTE);
				} else {
					poly = getPolygon(((int) actionPossible.get(1)) * r.width + r.width / 2,
							(int) (((int) actionPossible.get(0)) * COTE * 1.5 + 0.5), COTE);
				}
				if (poly != null) {
					if ((String) actionPossible.get(2) != "allie") {
						graphics2d.setColor(new Color(255, 0, 0, 150));
						graphics2d.fill(poly);
					}
				}
			}
		}
	}
}
