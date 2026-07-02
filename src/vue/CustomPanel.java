package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class CustomPanel extends JPanel {
    private Image image = null;
    private int w;
    private int h;

    public CustomPanel(final Image image) {
        this.image = image;
        this.w = image.getWidth(this) / 2;
        this.h = image.getHeight(this) / 2;
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(0, 128, 128));
        if (image != null) {
            int x = this.getParent().getWidth() / 2 - w;
            int y = this.getParent().getHeight() / 2 - h;
            g.drawImage(image, x, y, this);
        }
    }
}
