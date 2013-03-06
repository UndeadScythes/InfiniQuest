package menus;

import controls.Controls;
import controls.Controls.Control;
import game.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

/**
 *
 * @author UndeadScythes
 */
public class ControlMenu extends Menu {
    private final Game game;

    private boolean change;

    public ControlMenu(final String[] items, final float y, final Game game, final int selection) {
        super(items, new Font("Serif", Font.PLAIN, 50), Color.RED, Color.BLACK, y, game);
        this.game = game;
        setSelection(selection);
    }

    @Override
    public void inputPressed(final int keyCode) {
        if(change) {
            if(Controls.set(getSelection(), keyCode)) {
                good();
                final Control c = Control.getControl(getSelection());
                changeItem(getSelection(), c.toString() + " - " + KeyEvent.getKeyText(Controls.get(c)));
                change = false;
            } else {
                bad();
            }
        } else {
            super.inputPressed(keyCode);
        }
    }

    @Override
    public void select() {
        if(getSelection() == Control.values().length) {
            good();
            Controls.init();
            final int cCount = Control.values().length;
            for(int i = 0; i < cCount; i++) {
                final Control c = Control.getControl(i);
                changeItem(i, c.toString() + " - " + KeyEvent.getKeyText(Controls.get(c)));
            }
            changeItem(cCount, "Reset To Default");
            changeItem(cCount + 1, "Back");
        } else if(getSelection() == Control.values().length + 1) {
            good();
            final OptionsMenu o = new OptionsMenu(0.5f, game);
            subMenu(o);
        } else {
            good();
            changeItem(getSelection(), Control.getControl(getSelection()).toString() + " - ?");
            change = true;
        }
    }
}
