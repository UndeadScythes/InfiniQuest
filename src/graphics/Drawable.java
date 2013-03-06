package graphics;

import geometry.Vector2;
import java.awt.Graphics2D;

/**
 *
 * @author UndeadScythes
 */
public interface Drawable {
    void draw(final Graphics2D g2d, final Vector2 d);
}
