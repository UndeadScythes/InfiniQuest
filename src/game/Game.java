package game;

import audio.Music;
import audio.Volumes;
import audio.Volumes.Line;
import controls.Controlable;
import controls.Controls;
import geometry.Map;
import geometry.Vector2;
import graphics.Background;
import graphics.Drawable;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import menus.MainMenu;
import openables.Scroll;

/**
 *
 * @author UndeadScythes
 */
public class Game extends JPanel implements ActionListener, KeyListener {
    private final static int FPS = 32;
    private final static int FCOUNT = 1000 / FPS;

    private final Timer timer;
    private final ArrayList<Integer> downedKeys = new ArrayList<Integer>();
    private final ArrayList<Drawable> layers = new ArrayList<Drawable>();
    private final ArrayList<Controlable> pawns = new ArrayList<Controlable>();
    private final ArrayList<Clockable> clocks = new ArrayList<Clockable>();

    private boolean init = true;
    private long frameTime = System.currentTimeMillis();
    private Music music;

    public Game() {
        Volumes.init();
        Controls.init();
        timer = new Timer(10, this);
        timer.setInitialDelay(100);
        timer.start();
    }

    @Override
    public void paint(final Graphics g) {
        if(init) {
            init = false;
            layers.add(new Background("intro.png"));
            final MainMenu m = new MainMenu(0.5f, this);
            final Scroll s = new Scroll(this, 0.3f, 550, 500, m);
            m.addOpenable(s);
            layers.add(s);
            clocks.add(s);
            s.open();
            music = new Music("Deep Haze.wav", Volumes.get(Line.MUSIC));
            music.loop();
        }
        for(Clockable c : clocks) {
            c.clock();
        }
        if(System.currentTimeMillis() - frameTime > FCOUNT) {
            final Vector2 v = new Vector2(getWidth(), getHeight());
            for(Drawable drawable : layers) {
                drawable.draw((Graphics2D)g, v);
            }
            frameTime = System.currentTimeMillis();
        }
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        repaint();
    }

    @Override
    public void keyTyped(final KeyEvent e) {}

    @Override
    public void keyPressed(final KeyEvent e) {
        final int k = e.getKeyCode();
        if(!downedKeys.contains(k)) {
            downedKeys.add(k);
            for(Controlable c : pawns) {
                c.inputPressed(k);
            }
        }
    }

    @Override
    public void keyReleased(final KeyEvent e) {
        final int k = e.getKeyCode();
        for(Controlable c : pawns) {
            c.inputReleased(k);
        }
        downedKeys.remove(Integer.valueOf(k));
    }

    public void clearAll() {
        layers.clear();
        pawns.clear();
        clocks.clear();
    }

    public void addLayer(final Drawable d) {
        layers.add(d);
    }

    public void addPawn(final Controlable c) {
        pawns.add(c);
    }

    public void addClock(final Clockable c) {
        clocks.add(c);
    }

    public void removeLayer(final Drawable d) {
        layers.remove(d);
    }

    public void removePawn(final Controlable c) {
        pawns.remove(c);
    }

    public void removeClock(final Clockable c) {
        clocks.remove(c);
    }

    public void startMusic(final String track) {
        music.stop();
        music = new Music(track, Volumes.get(Line.MUSIC));
        music.loop();
    }

    public static void main(final String[] args) {
        final JFrame frame = new JFrame("QuestPlane");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 480);
        frame.setMinimumSize(new Dimension(640 ,480));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        final Game game = new Game();
        game.addKeyListener(game);
        frame.setContentPane(game);
        game.setFocusable(true);
        frame.setVisible(true);
    }

}
