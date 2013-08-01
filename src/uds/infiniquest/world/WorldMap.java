package uds.infiniquest.world;

import uds.infiniquest.geometry.*;
import uds.infiniquest.world.Tile.Decor;
import uds.infiniquest.world.Tile.Terrain;

/**
 *
 * @author UndeadScythes
 */
class WorldMap {
    private final int seed;

    public WorldMap(final int seed) {
        this.seed = seed;
    }

    public Tile getTile(final int x, final int y) {
        float noise = (float)Noise.getPerlin2(seed, x , y, 7, 2) / 64 - 0.05f;
        if(noise < -1) {
            noise = -1;
        }
        if(noise > 1) {
            noise = 1;
        }
        if(noise < 0) {
            return new Tile((noise + 1) / 2, Terrain.WATER, Decor.NONE, (int)Noise.pseudoNoise(seed, x, y) % 3);
        } else if(noise < 0.1) {
            return new Tile((noise + 1) / 2, Terrain.SAND, Decor.NONE, (int)Noise.pseudoNoise(seed, x, y) % 3);
        } else if(noise < 0.5) {
            return new Tile((noise + 1) / 2, Terrain.GRASS, Decor.NONE, (int)Noise.pseudoNoise(seed, x, y) % 3);
        } else {
            return new Tile((noise + 1) / 2, Terrain.DIRT, Decor.NONE, (int)Noise.pseudoNoise(seed, x, y) % 3);
        }
    }
}
