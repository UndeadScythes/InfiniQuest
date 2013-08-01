package uds.infiniquest.entity;

/**
 *
 * @author UndeadScythes
 */
public abstract class Controllable extends Entity {
    private final double speed;

    public Controllable(final double x, final double y, final double speed) {
        super(x, y);
        this.speed = speed;
    }

    @Override
    public void clock(final long interval) {
        move(interval * speed);
    }

    public abstract void passInputPressed(int keyEvent);

    public abstract void passInputReleased(int keyEvent);
}
