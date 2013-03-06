package entities;

import audio.Music;
import audio.Volumes;
import controls.Controlable;
import controls.Controls;
import controls.Controls.Control;
import game.Clockable;
import geometry.Vector2;
import geometry.Vector2.Dir;
import graphics.Drawable;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author UndeadScythes
 */
public class Player implements Clockable, Controlable, Drawable, Entity {
    private static BufferedImage player;

    private String name = "stranger";
    private final Vector2 p = new Vector2();
    private final Vector2 m = new Vector2();
    private int health = 100;
    private int dir = 0;
    private Music footsteps;

    public Player() {
        if(player == null) {
            try {
                player = ImageIO.read(new File("src/images/player.png"));
            } catch (IOException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        footsteps = new Music("grass.wav", Volumes.get(Volumes.Line.EFFECTS));
    }

    @Override
    public void draw(final Graphics2D g2d, final Vector2 d) {
        final int w = d.getIX() / 2;
        final int h = d.getIY() / 2;
        int step;
        if(m.equals(new Vector2())) {
            step = 0;
        } else {
            step = (int)((System.currentTimeMillis() / 150) % 4);
        }
        final Composite c = g2d.getComposite();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2d.fillOval(w - 12, h + 15, 24, 10);
        g2d.setComposite(c);
        g2d.drawImage(player, w - 16, h - 24, w + 16, h + 24, step * 32, dir * 48, step * 32 + 32, dir * 48 + 48, null);
    }

    public void move(final Dir dir) {
        m.add(dir.getVector2());
        if(!m.equals(new Vector2())) {
            this.dir = m.getDir();
            footsteps.loop();
        } else {
            footsteps.stop();
        }
    }

    @Override
    public Vector2 getPosition() {
        return p;
    }

    public void move(final double interval) {
        p.add(m.copy().normalise().multiply(interval));
    }

    @Override
    public void clock() {
        move(2);
    }

    @Override
    public void inputPressed(final int keyCode) {
        if(keyCode == Controls.get(Control.MoveUp)) {
            move(Dir.UP);
        } else if(keyCode == Controls.get(Control.MoveLeft)) {
            move(Dir.LEFT);
        } else if(keyCode == Controls.get(Control.MoveDown)) {
            move(Dir.DOWN);
        } else if(keyCode == Controls.get(Control.MoveRight)) {
            move(Dir.RIGHT);
        } else if(keyCode == Controls.get(Control.Action)) {
            System.out.println("Insert player action start here...");
        } else if(keyCode == Controls.get(Control.Menu)) {
            System.out.println("Insert in game menu here...");
        }
    }

    @Override
    public void inputReleased(final int keyCode) {
        if(keyCode == Controls.get(Control.MoveUp)) {
            move(Dir.DOWN);
        } else if(keyCode == Controls.get(Control.MoveLeft)) {
            move(Dir.RIGHT);
        } else if(keyCode == Controls.get(Control.MoveDown)) {
            move(Dir.UP);
        } else if(keyCode == Controls.get(Control.MoveRight)) {
            move(Dir.LEFT);
        }
    }

    @Override
    public void nudgeX(final double nudge) {
        p.setX(p.getX() + nudge);
    }
}
