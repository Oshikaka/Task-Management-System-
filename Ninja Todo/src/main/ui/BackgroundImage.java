package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BackgroundImage extends JComponent {
    private BufferedImage image;

    public BackgroundImage(BufferedImage image) {
        this.image = image;
    }

    // MODIFIES: this
    // EFFECTS:  pain image component
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}