package uds.infiniquest.menu;

import java.awt.*;

/**
 *
 * @author UndeadScythes
 */
public class OptionsMenu extends GameMenu {
    public OptionsMenu() {
        super(new String[]{"Controls", "Gameplay", "Video", "Audio", "Back"}, Color.RED, Color.BLACK);
    }

    public OptionsMenu(final int selection) {
        super(new String[]{"Controls", "Gameplay", "Video", "Audio", "Back"}, Color.RED, Color.BLACK, selection);
    }

    @Override
    public GameMenu select() {
        good();
        switch(getSelection()) {
            case 0:
                return new ControlsMenu();
            case 1:
                return new GameplayMenu();
            case 2:
                return new VideoMenu();
            case 3:
                return new AudioMenu();
            case 4:
                return new MainMenu(2);
        }
        return null;
    }
}
