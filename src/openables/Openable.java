package openables;

import game.Clockable;
import game.Game;
import graphics.Drawable;
import menus.Menu;

/**
 *
 * @author UndeadScythes
 */
public abstract class Openable implements Clockable, Drawable {
    public enum State {
        OPENING,
        CLOSING,
        STATIC;
    }

    private final Game game;

    private Menu menu;
    private long start;
    private State state = State.STATIC;
    private boolean reOpen;

    public Openable(final Menu menu, final Game game) {
        this.menu = menu;
        this.game = game;
    }

    @Override
    public void clock() {
        if(state != State.STATIC) {
            tick(System.currentTimeMillis() - start);
        }
    }

    public void open() {
        start = System.currentTimeMillis();
        state = State.OPENING;
    }

    public void close() {
        start = System.currentTimeMillis();
        state = State.CLOSING;
    }

    public State getState() {
        return state;
    }

    public void done() {
        if(reOpen && state == State.CLOSING) {
            start = System.currentTimeMillis();
            state = State.OPENING;
        } else {
            reOpen = false;
            state = State.STATIC;
            game.addLayer(menu);
            game.addPawn(menu);
            menu.addOpenable(this);
        }
    }

    public void setMenu(final Menu menu) {
        this.menu = menu;
    }

    public void reOpen() {
        start = System.currentTimeMillis();
        reOpen = true;
        state = State.CLOSING;
    }

    public abstract void tick(final long time);
}
