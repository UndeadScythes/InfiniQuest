package geometry;

/**
 *
 * @author UndeadScythes
 */
public class Vector2 {
    public enum Dir {
        UP(new Vector2(0, 1), 3),
        LEFT(new Vector2(-1, 0), 1),
        DOWN(new Vector2(0, -1), 0),
        RIGHT(new Vector2(1, 0), 2);

        private Vector2 dir;
        private int code;

        Dir(final Vector2 dir, final int code) {
            this.dir = dir;
            this.code = code;
        }

        public Vector2 getVector2() {
            return dir;
        }

        public int getCode() {
            return code;
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
        return (int)x;
    }

    public int getIY() {
        return (int)y;
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
}
