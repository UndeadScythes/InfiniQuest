package uds.infiniquest.display;

import java.awt.*;
import uds.infiniquest.geometry.*;

/**
 *
 * @author UndeadScythes
 */
public abstract class Layer {
    private final double width, height, leftBorder, topBorder;

    public Layer(final double width, final double height, final double leftBorder, final double topBorder) {
        this.width = width;
        this.height = height;
        this.leftBorder = leftBorder;
        this.topBorder = topBorder;
    }

    public final void draw(final Graphics2D g2d, final Vector2 v) {
        final int w = (int)(v.getIX() * width);
        final int h = (int)(v.getIY() * height);
        draw(g2d, (int)((v.getIX() - w) * leftBorder), (int)((v.getIY() - h) * topBorder), w, h);
    }

    public abstract void draw(Graphics2D g2d, int x, int y, int w, int h);
}
