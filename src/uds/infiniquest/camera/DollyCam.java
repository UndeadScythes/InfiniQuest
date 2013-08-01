package uds.infiniquest.camera;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import uds.infiniquest.clock.*;
import uds.infiniquest.display.*;
import uds.infiniquest.display.holder.*;
import uds.infiniquest.entity.*;
import uds.infiniquest.handler.*;
import uds.infiniquest.menu.*;
import uds.infiniquest.settings.*;
import uds.infiniquest.settings.ControlSettings.ContolSetting;
import uds.infiniquest.world.*;

/**
 *
 * @author UndeadScythes
 */
public class DollyCam extends Layer implements Handler {
    private final World world;
    private final Controllable anchor;

    private boolean paused = false;
    private boolean debug = false;

    private Holder overlay;

    @SuppressWarnings("LeakingThisInConstructor")
    public DollyCam(final World world, final Controllable anchor) {
        super(1, 1, 0, 0);
        this.world = world;
        this.anchor = anchor;
        world.addEntity(anchor);
        Clock.addProcess(world);
        Display.addHandler(this);
    }

    @Override
    public void draw(final Graphics2D g2d, final int x, final int y, final int w, final int h) {
        world.draw(g2d, x, y, w, h, anchor.getPosition());
        if(debug) {
            g2d.setColor(Color.BLACK);
            g2d.drawLine(x + w / 2, y, x + w / 2, y + h);
            g2d.drawLine(x, y + h / 2, x + w, y + h / 2);
            final DecimalFormat df = new DecimalFormat("#.##");
            g2d.drawString(Double.valueOf(df.format(anchor.getPosition().getX())) + ", " + Double.valueOf(df.format(anchor.getPosition().getY())), 5, 15);
        }
    }

    @Override
    public void inputPressed(final int keyCode) throws IOException {
        if(keyCode == ControlSettings.get(ContolSetting.MENU)) {
            if(paused) {
                paused = false;
                overlay.remove();
                Clock.addProcess(world);
            } else {
                overlay = new Scroll(new InGameMenu(), 0.7, 0.9, 0.5, 0.5, 250);
                overlay.open();
                Display.addLayer(overlay);
                paused = true;
                Clock.removeProcess(world);
            }
        } else if(keyCode == KeyEvent.VK_F1) {
            debug ^= true;
        } else if(keyCode == ControlSettings.get(ContolSetting.MAP)) {
            if(paused) {
                paused = false;
                overlay.remove();
                Clock.addProcess(world);
            } else {
                overlay = new Paper(new Background("loading.png"), 0.7, 0.9, 0.5, 0.5, 250);
                overlay.open();
                Display.addLayer(overlay);
                paused = true;
                Clock.removeProcess(world);
            }
        }
        if(!paused) {
            anchor.passInputPressed(keyCode);
        }
    }

    @Override
    public void inputReleased(final int keyCode) {
        if(!paused) {
            anchor.passInputReleased(keyCode);
        }
    }
}
