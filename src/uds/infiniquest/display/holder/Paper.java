package uds.infiniquest.display.holder;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.logging.*;
import javax.imageio.*;
import uds.infiniquest.audio.*;
import uds.infiniquest.display.*;

/**
 *
 * @author UndeadScythes
 */
public class Paper extends Holder {
    private final long time;

    private BufferedImage paper;
    private double stage = 1;

    public Paper(final Layer layer, final double w, final double h, final double l, final double t, final long time) {
        super(layer, w, h, l, t);
        this.time = time;
        try {
            paper = ImageIO.read(Paper.class.getResource("/images/paper.png"));
        } catch (IOException ex) {
            Logger.getLogger(Paper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void openSound() {
        new Music("paper.wav").play();
    }

    @Override
    public void closeSound() {
        new Music("paper.wav").play();
    }

    @Override
    public void tick(final long time) {
        if(getState() == State.OPENING) {
            stage = 1 - ((double)time / (double)this.time);
        } else if(getState() == State.CLOSING || getState() == State.REMOVING) {
            stage = (double)time / (double)this.time;
        }
        if(stage > 1) {
            stage = 1;
        }
        if(stage < 0) {
            stage = 0;
        }
        if(time > this.time) {
            if(getState() == State.OPENING) {
                stage = 0;
            } else if(getState() == State.CLOSING || getState() == State.REMOVING) {
                stage = 1;
            }
            done();
        }
    }

    @Override
    public void drawMe(final Graphics2D g2d, final int x, final int y, final int w, final int h) {
        g2d.drawImage(paper, (int)(x + w / 2 - (w / 2 * (1 - stage))), y, x + w / 2, y + h, 0, 0, 373, 600, null);
        g2d.drawImage(paper, x + w / 2, y, (int)(x + w / 2 + (w / 2 * (1 - stage))), y + h, 373, 0, 746, 600, null);
    }

    @Override
    public void drawLayer(final Graphics2D g2d, final int x, final int y, final int w, final int h) {
        getLayer().draw(g2d, (int)(x + w * 0.05), (int)(y + h * 0.05), (int)(w * 0.9), (int)(h * 0.9));
    }
}
