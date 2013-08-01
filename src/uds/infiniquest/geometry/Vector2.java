package uds.infiniquest.geometry;

/**
 *
 * @author UndeadScythes
 */
public class Vector2 {
    public enum Dir {
        UP(new Vector2(0, 1)),
        LEFT(new Vector2(-1, 0)),
        DOWN(new Vector2(0, -1)),
        RIGHT(new Vector2(1, 0));

        private Vector2 dir;

        Dir(final Vector2 dir) {
            this.dir = dir;
        }

        public Vector2 getVector2() {
            return dir;
        }
    }

    private double x, y;

    public Vector2() {
        x = 0;
        y = 0;
    }

    public Vector2(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getIX() {
        return (int)Math.floor(x);
    }

    public int getIY() {
        return (int)Math.floor(y);
    }

    public double getFX() {
        return x - (int)Math.floor(x);
    }

    public double getFY() {
        return y - (int)Math.floor(y);
    }

    public void setX(final double x) {
        this.x = x;
    }

    public void setY(final double y) {
        this.y = y;
    }

    public Vector2 add(final Vector2 v) {
        x += v.getX();
        y += v.getY();
        return this;
    }

    public Vector2 multiply(final double m) {
        x *= m;
        y *= m;
        return this;
    }

    public boolean equals(final Vector2 v) {
        return v.getX() == x && v.getY() == y;
    }

    public boolean isZero() {
        return x == 0 && y == 0;
    }

    public Vector2 normalise() {
        final double d = Math.sqrt(x * x + y * y);
        if(d > 0) {
            x /= d;
            y /= d;
        }
        return this;
    }

    public int getDir() {
        if(x < 0) {
            return 1;
        } else if(x > 0) {
            return 2;
        } else if(y < 0) {
            return 0;
        } else {
            return 3;
        }
    }

    public Vector2 copy() {
        return new Vector2(x, y);
    }

    public double distanceSq(final Vector2 v) {
        final double dx = v.getX() - x;
        final double dy = v.getY() - y;
        return dx * dx + dy * dy;
    }
}
