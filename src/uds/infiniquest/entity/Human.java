package uds.infiniquest.entity;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.logging.*;
import javax.imageio.*;
import uds.infiniquest.geometry.*;
import uds.infiniquest.geometry.Vector2.Dir;
import uds.infiniquest.settings.*;
import uds.infiniquest.settings.ControlSettings.ContolSetting;
import uds.infiniquest.world.*;

/**
 *
 * @author UndeadScythes
 */
public class Human extends Controllable {
    public enum Gender {
        MALE,
        FEMALE;
    }

    private static BufferedImage male;
    private static BufferedImage female;

    private final static int X = 32;
    private final static int Y = 48;

    private final Gender gender;

    public Human(final double x, final double y, final Gender gender) {
        super(x, y, 0.005);
        this.gender = gender;
    }

    @Override
    public void passInputPressed(final int keyEvent) {
        if(keyEvent == ControlSettings.get(ContolSetting.UP)) {
            addMomentum(Dir.UP.getVector2());
        } else if(keyEvent == ControlSettings.get(ContolSetting.LEFT)) {
            addMomentum(Dir.LEFT.getVector2());
        } else if(keyEvent == ControlSettings.get(ContolSetting.DOWN)) {
            addMomentum(Dir.DOWN.getVector2());
        } else if(keyEvent == ControlSettings.get(ContolSetting.RIGHT)) {
            addMomentum(Dir.RIGHT.getVector2());
        }
    }

    @Override
    public void passInputReleased(final int keyEvent) {
        if(keyEvent == ControlSettings.get(ContolSetting.UP)) {
            addMomentum(Dir.DOWN.getVector2());
        } else if(keyEvent == ControlSettings.get(ContolSetting.LEFT)) {
            addMomentum(Dir.RIGHT.getVector2());
        } else if(keyEvent == ControlSettings.get(ContolSetting.DOWN)) {
            addMomentum(Dir.UP.getVector2());
        } else if(keyEvent == ControlSettings.get(ContolSetting.RIGHT)) {
            addMomentum(Dir.LEFT.getVector2());
        }
    }

    @Override
    public void draw(final Graphics2D g2d, final int x, final int y, final float h) {
        final int cx = x + (int)(getPosition().getFX() * World.T);
        final int cy = y - (int)((getPosition().getFY() - 1) * World.T);
        final Composite c = g2d.getComposite();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2d.fillOval(cx - X / 3, cy - 10, 2 * X / 3, 8);
        g2d.setComposite(c);
        final int step = getMomentum().isZero() ? 0 : (int)(System.currentTimeMillis() / 150) % 4;
        final Vector2 lastDir = getLastDir();
        int dir = 0;
        if(lastDir.getX() < 0) {
            dir = 1;
        } else if(lastDir.getX() > 0) {
            dir = 2;
        } else if(lastDir.getY() > 0) {
            dir = 3;
        }
        if(gender == Gender.MALE) {
            g2d.drawImage(male, cx - X / 2, cy - Y - 2, cx + X / 2, cy - 2, 0 + X * step, 0 + Y * dir, X + X * step, Y + Y * dir, null);
        } else {
            g2d.drawImage(female, cx - X / 2, cy - Y - 2, cx + X / 2, cy - 2, 0 + X * step, 0 + Y * dir, X + X * step, Y + Y * dir, null);
        }
    }

    public static void loadImages() {
        try {
            male = ImageIO.read(Human.class.getResource("/images/playerm.png"));
            female = ImageIO.read(Human.class.getResource("/images/playerf.png"));
        } catch (IOException ex) {
            Logger.getLogger(Human.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
