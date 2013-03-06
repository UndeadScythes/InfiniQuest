package openables;

import audio.Music;
import entities.Player;
import game.Game;
import geometry.Vector2;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import menus.Menu;

/**
 *
 * @author UndeadScythes
 */
public class Scroll extends Openable {
    private BufferedImage scrollTop;
    private BufferedImage scrollMiddle;
    private BufferedImage scrollBottom;
    private final float y;
    private final int width;
    private final int height;
    private final long time;
    private double stage = 1;

    public Scroll(final Game game, final float y, final int width, final long time, final Menu menu) {
        super(menu, game);
        this.y = y;
        this.width = width;
        this.height = width / 96 * 17;
        this.time = time;
        try {
            scrollTop = ImageIO.read(new File("src/images/scrolltop.png"));
            scrollMiddle = ImageIO.read(new File("src/images/scroll.png"));
            scrollBottom = ImageIO.read(new File("src/images/scrollbtm.png"));
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void open() {
        new Music("paper2.wav").play();
        super.open();
    }

    @Override
    public void close() {
        new Music("paper2.wav").play();
        super.open();
    }

    @Override
    public void reOpen() {
        new Music("paper2.wav").play();
        super.reOpen();
    }

    @Override
    public void tick(final long time) {
        if(time > this.time) {
            done();
        } else {
            switch(getState()) {
                case OPENING:
                    stage = 1 - ((double)time / (double)this.time);
                    break;
                case CLOSING:
                    stage = (double)time / (double)this.time;
                    break;
            }
        }
    }

    @Override
    public void draw(final Graphics2D g2d, final Vector2 d) {
        int y1 = (int)(d.getY() * y);
        int y2 = y1 + height;
        int y3 = d.getIY() - height - 20;
        int y4 = y3 + height;
        final int cy = (y3 - y2) / 2;
        y1 += stage * cy;
        y2 += stage * cy;
        y3 -= stage * cy;
        y4 -= stage * cy;

        final int x1 = (d.getIX() - width) / 2;
        final int x2 = (d.getIX() - width) / 2 + width;

        final int my = (int)(100 / (float)width * (y3 - y2));

        g2d.drawImage(scrollBottom, x1, y3, x2, y4, 0, 0, 96, 17, null);
        g2d.drawImage(scrollTop, x1, y1, x2, y2, 0, 0, 96, 17, null);
        if(my > 0) {
            g2d.drawImage(scrollMiddle, x1, y2, x2, y3, 0, 0, 96, my, null);
        }
    }
}
