package entities;

import geometry.Vector2;

/**
 *
 * @author UndeadScythes
 */
public interface Entity {
    Vector2 getPosition();
    void nudgeX(final double nudge);
}
