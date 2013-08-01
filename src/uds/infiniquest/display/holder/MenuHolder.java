package uds.infiniquest.display.holder;

import java.io.*;
import uds.infiniquest.display.*;
import uds.infiniquest.handler.*;
import uds.infiniquest.menu.*;

/**
 *
 * @author UndeadScythes
 */
public abstract class MenuHolder extends Holder implements Handler {
    private GameMenu menu;

    @SuppressWarnings("LeakingThisInConstructor")
    public MenuHolder(final GameMenu menu, final double x, final double y, final double l, final double t) {
        super(menu, x, y, l, t);
        this.menu = menu;
        Display.addHandler(this);
    }

    @Override
    public void inputPressed(final int keyCode) throws IOException {
        final GameMenu newMenu = passInputPressed(keyCode);
        if(newMenu != null) {
            this.menu = newMenu;
            setLayer(newMenu);
            reOpen();
        }
    }

    @Override
    public void inputReleased(final int keyCode) {
     final GameMenu newMenu = passInputReleased(keyCode);
        if(newMenu != null) {
            this.menu = newMenu;
            setLayer(newMenu);
            reOpen();
        }
    }

    public GameMenu passInputPressed(final int keyCode) {
        return menu.passInputPressed(keyCode);
    }

    public GameMenu passInputReleased(final int keyCode) {
        return menu.passInputReleased(keyCode);
    }
}
