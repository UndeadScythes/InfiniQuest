package menus;

import entities.Player;
import game.Clockable;
import game.Game;
import geometry.Map;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author UndeadScythes
 */
public class MainMenu extends Menu implements Clockable {
    private long time = System.currentTimeMillis();

    private final Game game;

    public MainMenu(final float y, final Game game) {
        super(new String[]{"New Game", "Continue", "Options", "Quit"}, new Font("Serif", Font.PLAIN, 50), Color.RED, Color.BLACK, y, game);
        this.game = game;
    }

    @Override
    public void inputPressed(final int keyCode) {
        time = System.currentTimeMillis();
        super.inputPressed(keyCode);
    }

    @Override
    public void select() {
        good();
        switch(getSelection()) {
            case 0:
                game.clearAll();
                final Player player = new Player();
                final Map m = new Map(player);
                m.findSpot();
                game.addLayer(m);
                game.addPawn(player);
                game.addClock(player);
                game.startMusic("Darkest Child.wav");
                break;
            case 1:
                System.out.println("Insert continue game code here...");
                break;
            case 2:
                subMenu(new OptionsMenu(0.5f, game));
                game.removeClock(this);
                break;
            case 3:
                System.exit(0);
                break;
        }
    }

    @Override
    public void clock() {
        if(System.currentTimeMillis() - time > 20000) {
            System.out.println("Insert cutscene replay here...");
        }
    }
}
