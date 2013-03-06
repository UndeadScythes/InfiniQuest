package graphics;

import geometry.Vector2;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author UndeadScythes
 */
public class Background implements Drawable {
    private BufferedImage image;

    public Background(final String image) {
        try {
            this.image = ImageIO.read(new File("src/images/" + image));
        } catch (IOException ex) {
            Logger.getLogger(Background.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void draw(final Graphics2D g2d, final Vector2 d) {
        g2d.drawImage(image, 0, 0, d.getIX(), d.getIY(), null);
    }
}
