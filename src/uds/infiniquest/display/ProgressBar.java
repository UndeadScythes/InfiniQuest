package uds.infiniquest.display;

import java.awt.*;

/**
 *
 * @author UndeadScythes
 */
public class ProgressBar extends Layer {
    private final Color color;
    private final int max;
    private final int thickness = 3;

    private int value;

    public ProgressBar(final float w, final float h, final float l, final float t, final int value, final int max, final Color color) {
        super(w, h, l, t);
        this.color = color;
        this.value = value;
        this.max = max;
    }

    public void setValue(final int value) {
        this.value = value;
    }

    @Override
    public void draw(final Graphics2D g2d, final int x, final int y, final int w, final int h) {
        g2d.setColor(Color.BLACK);
        for(int i = 0; i < thickness; i++) {
            g2d.drawRect(x + i, y + i, w - 2 * i, h - 2 * i);
        }
        g2d.setColor(color);
        g2d.fillRect(x + thickness, y + thickness, (int)((w - 2 * thickness) * value / (float)max), h - 2 * thickness);
    }
}
