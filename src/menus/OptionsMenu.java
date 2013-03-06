package menus;

import audio.Volumes;
import audio.Volumes.Line;
import controls.Controls;
import controls.Controls.Control;
import game.Game;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author UndeadScythes
 */
public class OptionsMenu extends Menu {
    private final Game game;

    public OptionsMenu(final float y, final Game game) {
        super(new String[]{"Controls", "Audio", "Back"}, new Font("Serif", Font.PLAIN, 50), Color.RED, Color.BLACK, y, game);
        this.game = game;
    }

    @Override
    public void select() {
        good();
        switch(getSelection()) {
            case 0:
                final ArrayList<String> controlList = new ArrayList<String>();
                for(Control control : Control.values()) {
                    controlList.add(control.toString() + " - " + KeyEvent.getKeyText(Controls.get(control)));
                }
                controlList.add("Reset To Default");
                controlList.add("Back");
                final ControlMenu c = new ControlMenu(controlList.toArray(new String[controlList.size()]), 0.5f, game, 0);
                subMenu(c);
                break;
            case 1:
                final ArrayList<String> lineList = new ArrayList<String>();
                for(Line line : Line.values()) {
                    lineList.add(line.toString() + " - " + Volumes.get(line));
                }
                lineList.add("Back");
                final AudioMenu a = new AudioMenu(lineList.toArray(new String[lineList.size()]), 0.5f, game, 0);
                subMenu(a);
                break;
            case 2:
                final MainMenu m = new MainMenu(0.5f, game);
                subMenu(m);
                break;
        }
    }
}
