package uds.infiniquest.display;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.logging.*;
import javax.imageio.*;

/**
 *
 * @author UndeadScythes
 */
public class Background extends Layer {
    private BufferedImage image;

    public Background(final String image) {
        super(1, 1, 0 ,0);
        try {
            this.image = ImageIO.read(Background.class.getResource("/images/" + image));
        } catch (IOException ex) {
            Logger.getLogger(Background.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public final void draw(final Graphics2D g2d, final int x, final int y, final int w, final int h) {
        g2d.drawImage(image, x, y, w, h, null);
    }
}
