package generators;

import geometry.Noise;

/**
 *
 * @author UndeadScythes
 */
public class Tree {
    public static int spawn(final float p, final int seed, final int x, final int y) {
        if(Noise.getPerlin2(seed + 1, x, y, 5, 3) / 64 > 0.3  && Noise.pseudoNoise(seed + 1, x, y) > 0.6 && p > 0.1) {
            return (Math.abs(x * y * seed) % 3) + 1;
        }
        return 0;
    }
}
