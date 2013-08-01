package uds.infiniquest.display.holder;

import java.awt.*;
import uds.infiniquest.clock.*;
import uds.infiniquest.display.*;

/**
 *
 * @author UndeadScythes
 */
public abstract class Holder extends Layer implements GameProcess {
    public enum State {
        OPENING,
        OPEN,
        CLOSING,
        CLOSED,
        REOPEN1,
        REOPEN2,
        REMOVING;
    }

    private Layer layer;
    private long start;
    private State state;

    public Holder(final Layer layer, final double w, final double h, final double left, final double top) {
        super(w, h, left, top);
        this.layer = layer;
    }

    @Override
    public final void clock(final long interval) {
        if(state != State.CLOSED && state != State.OPEN) {
            tick(System.currentTimeMillis() - start);
        }
    }

    public final void open() {
        start = System.currentTimeMillis();
        state = State.OPENING;
        Clock.addProcess(this);
        openSound();
    }

    public final void close() {
        start = System.currentTimeMillis();
        state = State.CLOSING;
        Clock.addProcess(this);
        closeSound();
    }

    public final void remove() {
        start =  System.currentTimeMillis();
        state = State.REMOVING;
        Clock.addProcess(this);
        closeSound();
    }

    public void reOpen() {
        start = System.currentTimeMillis();
        state = State.REOPEN1;
        Clock.addProcess(this);
        closeSound();
    }

    public final State getState() {
        return state;
    }

    public final Layer getLayer() {
        return layer;
    }

    public final void done() {
        if(state == State.REOPEN1) {
            open();
        } else if(state == State.OPENING) {
            state = State.OPEN;
            Clock.removeProcess(this);
        } else if(state == State.CLOSING) {
            state = State.CLOSED;
            Clock.removeProcess(this);
        } else if(state == State.REMOVING) {
            Clock.removeProcess(this);
            Display.removeLayer(this);
        }
    }

    public final void setLayer(final Layer layer) {
        this.layer = layer;
    }

    @Override
    public final void draw(final Graphics2D g2d, final int x, final int y, final int w, final int h) {
        drawMe(g2d, x, y, w, h);
        if(state == State.OPEN) {
            drawLayer(g2d, x, y, w, h);
        }
    }

    public abstract void tick(long time);

    public abstract void drawMe(Graphics2D g2d, int x, int y, int w, int h);

    public abstract void drawLayer(Graphics2D g2d, int x, int y, int w, int h);

    public abstract void openSound();

    public abstract void closeSound();
}
