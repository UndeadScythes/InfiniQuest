package graphics;

import controls.Controlable;
import game.Clockable;

/**
 *
 * @author UndeadScythes
 */
public abstract class Cutscene implements Clockable, Controlable {
    @Override
    public void inputReleased(final int keyCode) {}
}
