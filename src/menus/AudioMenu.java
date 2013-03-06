package menus;

import audio.Volumes;
import audio.Volumes.Line;
import game.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

/**
 *
 * @author UndeadScythes
 */
public class AudioMenu extends Menu {
    private final Game game;


    public AudioMenu(final String[] items, final float y, final Game game, final int selection) {
        super(items, new Font("Serif", Font.PLAIN, 50), Color.RED, Color.BLACK, y, game);
        this.game = game;
        setSelection(selection);
    }

    @Override
    public void inputPressed(final int keyCode) {
        if(keyCode == KeyEvent.VK_RIGHT) {
            if(Volumes.increase(getSelection())) {
                good();
                final Line l = Line.getLine(getSelection());
                changeItem(getSelection(), l.toString() + " - " + Volumes.get(l));
            } else {
                bad();
            }
        } else if(keyCode == KeyEvent.VK_LEFT) {
            if(Volumes.decrease(getSelection())) {
                good();
                final Line l = Line.getLine(getSelection());
                changeItem(getSelection(), l.toString() + " - " + Volumes.get(l));
            } else {
                bad();
            }
        }
        super.inputPressed(keyCode);
    }

    @Override
    public void select() {
        if(getSelection() == Line.values().length) {
            good();
            final OptionsMenu o = new OptionsMenu(0.5f, game);
            subMenu(o);
        } else {
            bad();
        }
    }
}
