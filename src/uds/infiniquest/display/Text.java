package uds.infiniquest.display;

import java.awt.*;
import uds.infiniquest.geometry.*;

/**
 *
 * @author UndeadScythes
 */
public class Text extends Layer {
    private final String text;
    private final Font font;
    private final int y;

    public Text(final String text, final int y, final Font font) {
        super(1, 1, 0 ,0);
        this.text = text;
        this.y = y;
        this.font = font;
    }

    @Override
    public void draw(final Graphics2D g2d, final int X, final int Y, final int w, final int h) {
        g2d.setFont(font);
        final int size = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        g2d.drawString(text, w / 2 - size / 2, y);
    }

    public static void draw(final Graphics2D g2d, final Vector2 d, final String text, final int y, final Font font) {
        g2d.setFont(font);
        final int size = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        g2d.drawString(text, d.getIX() / 2 - size / 2, y);
    }

    public static void center(final Graphics2D g2d, final String text, final int x, final int y) {
        final int size = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        g2d.drawString(text, x - size / 2, y);
    }
}

