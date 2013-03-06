package geometry;

/**
 *
 * @author UndeadScythes
 */
public final class Noise {
    public static int hash32(final int key) {
        int output = ~key + (key << 15);
        output ^= (key >>> 12);
        output += (key << 2);
        output ^= (key >>> 4);
        output *= 2057;
        output ^= (key >>> 16);
        return output;
    }

    public static double pseudoNoise(final int i, final double x, final double y) {
        return (double)hash32(i + hash32((int)x + hash32((int)y))) / (double)(Integer.MAX_VALUE);
    }

    public static double smoothNoise(final int i, final double x, final double y) {
        final double corners = (pseudoNoise(i, x - 1, y - 1) + pseudoNoise(i, x + 1, y - 1) + pseudoNoise(i, x - 1, y + 1) + pseudoNoise(i, x + 1, y + 1)) / 16;
        final double sides = (pseudoNoise(i, x - 1, y) + pseudoNoise(i, x + 1, y) + pseudoNoise(i, x, y - 1) + pseudoNoise(i, x, y + 1)) / 8;
        final double center = pseudoNoise(i, x, y) / 4;
        return corners + sides + center;
    }

    public static double interpolate(final double a, final double b, final double x) {
        return x < 0 ? b * (1 + x) - a * x : a * (1 - x) + b * x;
    }

    public static double interpolatedNoise(final int i, final double x, final double y) {
        final int iX = (int)(x < 0 ? x - 1 : x);
        final int iY = (int)(y < 0 ? y - 1 : y);
        final double fX = x - iX;
        final double fY = y - iY;

        final double v1 = smoothNoise(i, iX, iY);
        final double v2 = smoothNoise(i, iX + 1, iY);
        final double v3 = smoothNoise(i, iX, iY + 1);
        final double v4 = smoothNoise(i, iX + 1, iY + 1);

        final double i1 = interpolate(v1, v2, fX);
        final double i2 = interpolate(v3, v4, fX);

        return interpolate(i1, i2, fY);
    }

    public static double getPerlin2(final int seed, final int x, final int y, final int n, final double p) {
        double total = 0;
        for(int i = 0; i < n; i++) {
            final double f = Math.pow(2, i);
            final double a = Math.pow(p, i);
            total += interpolatedNoise(i + seed, x / f, y / f) * a;
        }
        return total;
    }

    private Noise() {}
}
