package graphics;

import geometry.Vector2;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author UndeadScythes
 */
public class Text implements Drawable {
    private final String text;
    private final Font font;
    private final int y;

    public Text(final String text, final int y, final Font font) {
        this.text = text;
        this.y = y;
        this.font = font;
    }

    @Override
    public void draw(final Graphics2D g2d, final Vector2 d) {
        g2d.setFont(font);
        final int size = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        g2d.drawString(text, d.getIX() / 2 - size / 2, y);
    }

    public static void draw(final Graphics2D g2d, final Vector2 d, final String text, final int y, final Font font) {
        g2d.setFont(font);
        final int size = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        g2d.drawString(text, d.getIX() / 2 - size / 2, y);
    }
}

