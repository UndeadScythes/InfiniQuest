package uds.infiniquest.entity;

import java.awt.*;
import uds.infiniquest.clock.*;
import uds.infiniquest.geometry.*;

/**
 *
 * @author UndeadScythes
 */
public abstract class Entity implements GameProcess {
    private final Vector2 momentum = new Vector2();

    private Vector2 lastDir = new Vector2();

    private final Vector2 position;

    public Entity(final double x, final double y) {
        position = new Vector2(x, y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getMomentum() {
        return momentum.copy();
    }

    public void addMomentum(final Vector2 v) {
        momentum.add(v);
        if(!momentum.isZero()) {
            lastDir = momentum.copy();
        }
    }

    public Vector2 getLastDir() {
        return lastDir;
    }

    public void move(final double interval) {
        position.add(momentum.copy().normalise().multiply(interval));
    }

    public abstract void draw(Graphics2D g2d, int x, int y, float h);
}
