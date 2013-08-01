package uds.infiniquest.menu;

import java.awt.*;
import java.io.*;
import uds.infiniquest.clock.*;
import uds.infiniquest.main.*;

/**
 *
 * @author UndeadScythes
 */
public class MainMenu extends GameMenu implements GameProcess {
    private long time = System.currentTimeMillis();

    public MainMenu() {
        super(new String[]{"New Game", "Continue", "Options", "Quit"}, Color.RED, Color.BLACK);
    }
    public MainMenu(final int selection) {
        super(new String[]{"New Game", "Continue", "Options", "Quit"}, Color.RED, Color.BLACK, selection);
    }

    @Override
    public GameMenu passInputPressed(final int keyCode) {
        time = System.currentTimeMillis();
        return super.passInputPressed(keyCode);
    }

    @Override
    public GameMenu select() throws IOException {
        good();
        switch(getSelection()) {
            case 0:
                InfiniQuest.newGame();
                break;
            case 1:
                System.out.println("Insert continue game code here...");
                break;
            case 2:
                Clock.removeProcess(this);
                return new OptionsMenu();
            case 3:
                InfiniQuest.close();
                break;
        }
        return null;
    }

    @Override
    public void clock(final long interval) {
        if(System.currentTimeMillis() - time > 20000) {
            System.out.println("Insert cutscene replay here...");
        }
    }
}
