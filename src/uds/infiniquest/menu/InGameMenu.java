package uds.infiniquest.menu;

import java.awt.*;
import java.io.*;
import uds.infiniquest.main.*;

/**
 *
 * @author UndeadScythes
 */
public class InGameMenu extends GameMenu {
    public InGameMenu() {
        super(new String[]{"Save", "Quit"}, Color.RED, Color.BLACK);
    }

    @Override
    public GameMenu select() throws IOException {
        if(getSelection() == 0) {
            InfiniQuest.saveGame();
        } else if(getSelection() == 1) {
            InfiniQuest.quitGame();
        }
        return null;
    }
}
