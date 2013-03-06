package geometry;

import entities.Entity;
import generators.Item;
import generators.Tree;
import graphics.Drawable;
import java.awt.Color;
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
public class Map implements Drawable {
    public final static int RES = 32;
    public final static int TEX = 64;
    public final static float P = 2;
    public final static int N = 7;

    private static BufferedImage terrain;
    private static BufferedImage water;
    private static BufferedImage scenery;
    private static BufferedImage trees;

    private final Entity anchor;
    private final int seed = (int)System.currentTimeMillis();

    public Map(final Entity anchor) {
        this.anchor = anchor;
        try {
            terrain = ImageIO.read(new File("src/images/terrain.png"));
            water = ImageIO.read(new File("src/images/water.png"));
            scenery = ImageIO.read(new File("src/images/scenery.png"));
            trees = ImageIO.read(new File("src/images/trees.png"));
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void draw(final Graphics2D g2d, final Vector2 d) {
        final int dx = d.getIX() / RES + 2;
        final int dy = d.getIY() / RES + 2;
        final int ix = anchor.getPosition().getIX() + RES / 2;
        final int iy = anchor.getPosition().getIY() - 8;
        final int px = ix / RES + 1;
        final int py = iy / RES - 1;
        final int fx = ix % RES;
        final int fy = iy % RES;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, d.getIX(), d.getIY());
        for(int y = -1; y <= dy; y++) {
            for(int x = -1; x <= dx; x++) {
                int tx = (x + px - dx / 2);
                int ty = (y - py - dy / 2);
                int t = 0;
                int x1 = x * RES - fx;
                int y1 = y * RES + fy;
                int x2 = x * RES - fx + RES;
                int y2 = y * RES + fy + RES;
                float p = (float)Noise.getPerlin2(seed, tx , ty, N, P) / 64 - 0.05f;
                if(p < 0.1) {
                    t = 2;
                }
                if(p < 0) {
                    g2d.drawImage(water, x1, y1, x2, y2, 0, 0, TEX, TEX, null);
                } else {
                    g2d.drawImage(terrain, x1, y1, x2, y2, TEX * t, 0, TEX * t + TEX, TEX, null);
                }
                final int item = Item.spawn(p, seed, tx, ty);
                if(item > 0) {
                    g2d.drawImage(scenery, x1, y1, x2, y2, TEX * (item - 1), 0, TEX * item, TEX, null);
                }
                final int tree = Tree.spawn(p, seed, tx, ty);
                if(tree > 0) {
                    g2d.drawImage(trees, x1 - 110, y1 - 140, x1, y1, 110 * (tree - 1), 0, 110 * tree, 140, null);
                }
                if(x == dx / 2 && y == dy / 2) {
                    ((Drawable)anchor).draw(g2d, d);
                }
            }
        }
    }

    public void findSpot() {
        int ix = anchor.getPosition().getIX() + RES / 2;
        int iy = anchor.getPosition().getIY() - 8;
        int px = ix / RES + 1;
        int py = iy / RES - 1;
        double n = Noise.getPerlin2(seed, px, py, N, P) / 64 - 0.05f;
        while(n < 0 || n > 0.1) {
            anchor.nudgeX(RES);
            ix = anchor.getPosition().getIX() + RES / 2;
            iy = anchor.getPosition().getIY() - 8;
            px = ix / RES + 1;
            py = iy / RES - 1;
            n = Noise.getPerlin2(seed, px, py, N, P) / 64 - 0.05f;
        }
    }
}
