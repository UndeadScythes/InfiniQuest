package generators;

import geometry.Noise;

/**
 *
 * @author UndeadScythes
 */
public class Item {
    public static int spawn(final float p, final int seed, final int x, final int y) {
        if(Math.abs(Noise.pseudoNoise(seed + 1, x, y)) < 0.001 && p < 0.1 && p > 0) {
            return (Math.abs(x * y * seed) % 3) + 1;
        }
        return 0;
    }
}
