package uds.infiniquest.display.holder;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.logging.*;
import javax.imageio.*;
import uds.infiniquest.audio.*;
import uds.infiniquest.menu.*;

/**
 *
 * @author UndeadScythes
 */
public class Scroll extends MenuHolder {
    private final static int SCROLL_WIDTH = 96;
    private final static int SCROLL_HEIGHT = 17;
    private final static float RATIO = SCROLL_HEIGHT / (float)SCROLL_WIDTH;

    private BufferedImage scroll;
    private final long time;
    private double stage = 1;

    public Scroll(final GameMenu layer, final double w, final double h, final double l, final double t, final long time) {
        super(layer, w, h, l, t);
        this.time = time;
        try {
            scroll = ImageIO.read(Scroll.class.getResource("/images/scroll.png"));
        } catch (IOException ex) {
            Logger.getLogger(Scroll.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void openSound() {
        new Music("scroll.wav").play();
    }

    @Override
    public void closeSound() {
        new Music("scroll.wav").play();
    }

    @Override
    public void tick(final long time) {
        if(getState() == State.OPENING || getState() == State.REOPEN2) {
            stage = 1 - ((double)time / (double)this.time);
        } else if(getState() == State.CLOSING || getState() == State.REOPEN1 || getState() == State.REMOVING) {
            stage = (double)time / (double)this.time;
        }
        if(stage > 1) {
            stage = 1;
        }
        if(stage < 0) {
            stage = 0;
        }
        if(time > this.time) {
            if(getState() == State.OPENING || getState() == State.REOPEN2) {
                stage = 0;
            } else if(getState() == State.CLOSING || getState() == State.REOPEN1 || getState() == State.REMOVING) {
                stage = 1;
            }
            done();
        }
    }

    @Override
    public void drawMe(final Graphics2D g2d, final int x, final int y, final int w, final int h) {
        final int sh = (int)(w * RATIO);
        final int step = (int)((h - 2 * sh) * stage * 0.5);
        final int m = h - 2 * sh - 2 * step;
        g2d.drawImage(scroll, x, y + step, x + w, y + sh + step, 0, 0, SCROLL_WIDTH, SCROLL_HEIGHT, null);
        g2d.drawImage(scroll, x, y + h - sh - step, x + w, y + h - step, 0, 498 - SCROLL_HEIGHT, SCROLL_WIDTH, 498, null);
        if(m > 0) {
            g2d.drawImage(scroll, x, y + sh + step, x + w, y + h - sh - step, 0, SCROLL_HEIGHT, SCROLL_WIDTH, (int)(m * RATIO) + SCROLL_HEIGHT, null);
        }
    }

    @Override
    public void drawLayer(final Graphics2D g2d, final int x, final int y, final int w, final int h) {
        final int l = (int)(x * 1.4);
        final int t = (int)(w * RATIO * 1.1);
        getLayer().draw(g2d, l, y + t , w - 2 * (l - x), h - 2 * t);
    }
}
