package menus;

import audio.Music;
import audio.Volumes;
import audio.Volumes.Line;
import controls.Controlable;
import game.Game;
import geometry.Vector2;
import graphics.Drawable;
import graphics.Text;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import openables.Openable;

/**
 *
 * @author UndeadScythes
 */
public abstract class Menu implements Controlable, Drawable {
    private final Font font;
    private final float y;
    private final Color on, off;
    private final Game game;


    private String[] items;
    private int selection = 0;
    private Openable openable;

    public Menu(final String[] items, final Font font, final Color on, final Color off, final float y, final Game game) {
        this.items = items.clone();
        this.font = font;
        this.y = y;
        this.on = on;
        this.off = off;
        this.game = game;
    }

    @Override
    public void draw(final Graphics2D g2d, final Vector2 d) {
        g2d.setFont(font);
        g2d.setColor(off);
        for(int i = 0; i < items.length; i++) {
            if(i != selection) {
                Text.draw(g2d, d, items[i], (int)(d.getY() * y) + i * (font.getSize() + 10), font);
            } else {
                g2d.setColor(on);
                Text.draw(g2d, d, items[i], (int)(d.getY() * y) + i * (font.getSize() + 10), font);
                g2d.setColor(off);
            }
        }
    }

    @Override
    public void inputPressed(final int keyCode) {
        if(keyCode == KeyEvent.VK_UP) {
            if(selection > 0) {
                selection--;
                good();
            } else {
                bad();
            }
        } else if(keyCode == KeyEvent.VK_DOWN) {
            if(selection < items.length - 1) {
                selection++;
                good();
            } else {
                bad();
            }
        } else if(keyCode == KeyEvent.VK_ENTER || keyCode == KeyEvent.VK_SPACE) {
            select();
        }
    }

    @Override
    public void inputReleased(final int keyCode) {}

    public void good() {
        new Music("click.wav", Volumes.get(Line.EFFECTS)).play();
    }

    public void bad() {
        new Music("error.wav", Volumes.get(Line.EFFECTS)).play();
    }

    public int getSelection() {
        return selection;
    }

    public void setSelection(final int selection) {
        this.selection = selection;
    }

    public void changeItem(final int item, final String text) {
        items[item] = text;
    }

    public void addOpenable(final Openable openable) {
        this.openable = openable;
    }

    public void subMenu(final Menu menu) {
        if(openable != null) {
            openable.setMenu(menu);
            openable.reOpen();
        }
        game.removePawn(this);
        game.removeLayer(this);
    }

    public abstract void select();
}
