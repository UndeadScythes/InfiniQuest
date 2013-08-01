package uds.infiniquest.world;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.logging.*;
import javax.imageio.*;

/**
 *
 * @author UndeadScythes
 */
public class Tile {
    public enum Terrain {
        GRASS,
        SAND,
        WATER,
        DIRT,
        LEAVES;
    }

    public enum Decor {
        NONE,
        ROCK,
        SPAWN,
        TREE,
        CHEST;
    }

    public final static int TERRAIN_TEX = 64;
    private final static int SMALL_TEX = 16;
    private final static int LARGE_TEX = 64;

    private static BufferedImage grass;
    private static BufferedImage sand;
    private static BufferedImage water;
    private static BufferedImage dirt;
    private static BufferedImage leaves;

    private static BufferedImage rock;
    private static BufferedImage spawn;
    private static BufferedImage tree;
    private static BufferedImage chest;

    private final float height;
    private final Terrain type;
    private final Decor deco;
    private final int rand;

    public Tile(final float height, final Terrain type, final Decor deco, final int rand) {
        this.height = height;
        this.type = type;
        this.deco = deco;
        this.rand = rand;
    }

    public final void drawTile(final Graphics2D g2d, final int x, final int y, final int t) {
        /*g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, TERRAIN_TEX, TERRAIN_TEX);*/
        switch(type) {
            case GRASS:
                g2d.drawImage(grass, x, y, x + t, y + t, 0, 0, TERRAIN_TEX, TERRAIN_TEX, null);
                break;
            case SAND:
                g2d.drawImage(sand, x, y, x + t, y + t, 0, 0, TERRAIN_TEX, TERRAIN_TEX, null);
                break;
            case WATER:
                final Composite c = g2d.getComposite();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, height * 2));
                g2d.drawImage(water, x, y, x + t, y + t, 0, 0, TERRAIN_TEX, TERRAIN_TEX, null);
                g2d.setComposite(c);
                break;
            case DIRT:
                g2d.drawImage(dirt, x, y, x + t, y + t, 0, 0, TERRAIN_TEX, TERRAIN_TEX, null);
                break;
            case LEAVES:
                g2d.drawImage(leaves, x, y, x + t, y + t, 0, 0, TERRAIN_TEX, TERRAIN_TEX, null);
                break;
        }
    }

    public final void drawDeco(final Graphics2D g2d, final int x, final int y) {
        /*if(deco == Decor.ROCK) {
            g2d.setColor(Color.RED);
            g2d.fillRect(x, y, TERRAIN_TEX, TERRAIN_TEX);
        }*/
        switch(deco) {
            case ROCK:
                g2d.drawImage(rock, x, y, x + SMALL_TEX, y + SMALL_TEX, 0, 0, SMALL_TEX, SMALL_TEX, null);
                break;
            case SPAWN:
                g2d.drawImage(spawn, x, y, x + SMALL_TEX, y + SMALL_TEX, 0, 0, SMALL_TEX, SMALL_TEX, null);
                break;
            case TREE:
                g2d.drawImage(tree, x + LARGE_TEX / 2 - LARGE_TEX / 2, y + LARGE_TEX - LARGE_TEX, x + LARGE_TEX / 2 + LARGE_TEX / 2, y + LARGE_TEX, 0, 0, LARGE_TEX, LARGE_TEX, null);
                break;
            case CHEST:
                g2d.drawImage(chest, x, y, x + SMALL_TEX, y + SMALL_TEX, 0, 0, SMALL_TEX, SMALL_TEX, null);
                break;
        }
    }

    public final float getHeight() {
        return height;
    }

    public static void loadImages() {
        try {
            grass = ImageIO.read(Tile.class.getResource("/images/grass.png"));
            sand = ImageIO.read(Tile.class.getResource("/images/sand.png"));
            water = ImageIO.read(Tile.class.getResource("/images/water.png"));
            dirt = ImageIO.read(Tile.class.getResource("/images/dirt.png"));
            leaves = ImageIO.read(Tile.class.getResource("/images/village.png"));
            rock = ImageIO.read(Tile.class.getResource("/images/rock.png"));
            spawn = ImageIO.read(Tile.class.getResource("/images/spawn.png"));
            tree = ImageIO.read(Tile.class.getResource("/images/tree.png"));
            chest = ImageIO.read(Tile.class.getResource("/images/chest.png"));
        } catch (IOException ex) {
            Logger.getLogger(Tile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
