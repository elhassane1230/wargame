package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PaintImage extends JPanel {
    private static BufferedImage image;

    public PaintImage(final String path) {
        super();
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setImage(final String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(final Graphics g) {
        Color coul = new Color(255, 255, 255);
        g.drawImage(image, 0, 0, coul, null);
        repaint();
    }

}
