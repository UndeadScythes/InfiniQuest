package uds.infiniquest.world;

import java.awt.*;
import java.util.*;
import uds.infiniquest.clock.*;
import uds.infiniquest.entity.*;
import uds.infiniquest.geometry.*;

/**
 *
 * @author UndeadScythes
 */
public class World implements GameProcess {
    public final static int T = 32;

    private final ArrayList<Entity> entities = new ArrayList<Entity>();
    private WorldMap map;

    public World(final int seed) {
        map = new WorldMap(seed) {};
    }

    public World() {
        this((int)System.currentTimeMillis());
    }

    public void addEntity(final Entity entity) {
        entities.add(entity);
    }

    @Override
    public void clock(final long interval) {
        for(final Entity entity : entities) {
            entity.clock(interval);
        }
    }

    public void draw(final Graphics2D g2d, final int x, final int y, final int w, final int h, final Vector2 o) {
        final int tw = w / T;
        final int th = h / T;
        final int bw = (w / 2) % T - (int)((o.getX() - o.getIX()) * T);
        final int bh = (h / 2) % T + (int)((o.getY() - o.getIY() - 1) * T);
        final int x1 = o.getIX() - tw / 2;
        final int y1 = -o.getIY() - th / 2;
        for(int iy =  -1; iy < th + 1; iy++) {
            for(int ix = -1; ix < tw + 1; ix++) {
                map.getTile(x1 + ix, - y1 - iy).drawTile(g2d, bw + ix * T, bh + iy * T, T);
            }
            for(int ix = -1; ix < tw + 1; ix++) {
                map.getTile(x1 + ix, - y1 - iy).drawDeco(g2d, bw + ix * T, bh + iy * T);
            }
            for(Entity entity : entities) {
                if(-entity.getPosition().getIY() - y1 == iy) {
                    final int ix = entity.getPosition().getIX() - x1;
                    entity.draw(g2d, bw + ix * T, bh + iy * T, map.getTile(x1 + ix, y1 + iy).getHeight());
                }
            }
        }
    }
}
