package uds.infiniquest.main;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import uds.infiniquest.audio.*;
import uds.infiniquest.camera.*;
import uds.infiniquest.clock.*;
import uds.infiniquest.display.*;
import uds.infiniquest.display.holder.*;
import uds.infiniquest.entity.*;
import uds.infiniquest.entity.Human.Gender;
import uds.infiniquest.handler.*;
import uds.infiniquest.menu.*;
import uds.infiniquest.settings.AudioSettings.AudioSetting;
import uds.infiniquest.settings.GameplaySettings.GameplaySetting;
import uds.infiniquest.settings.*;
import uds.infiniquest.settings.VideoSettings.VideoSetting;
import uds.infiniquest.world.*;

/**
 *
 * @author UndeadScythes
 */
public final class InfiniQuest {
    private final static String NAME = "InfiniQuest";
    private final static String VERSION = "Dev 3.1.1";
    private final static int MIN_WIDTH = 640;
    private final static int MIN_HEIGHT = 480;
    private final static int FPS = 32;
    private final static int CPS = 100;

    private static Display display;
    private static Clock clock;
    private static Music music;
    private static Timer displayTimer;
    private static Timer clockTimer ;
    private static JFrame frame;

    private InfiniQuest() {}

    public static void main(final String[] args) throws FileNotFoundException, IOException {
        frame = new JFrame(NAME + " - Version " + VERSION);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(MIN_WIDTH, MIN_HEIGHT);
        frame.setLocationRelativeTo(null);
        display = new Display();
        Display.addLayer(new Background("loading.png"));
        final ProgressBar bar = new ProgressBar(0.9f, 0.1f, 0.5f, 0.9f, 10, 100, Color.GREEN);
        Display.addLayer(bar);
        displayTimer = new Timer(1000 / FPS, display);
        display.addKeyListener(display);
        display.setFocusable(true);
        frame.setContentPane(display);
        frame.setVisible(true);
        displayTimer.start();
        AudioSettings.loadSettings();
        ControlSettings.loadSettings();
        GameplaySettings.loadSettings();
        VideoSettings.loadSettings();
        bar.setValue(25);
        Tile.loadImages();
        Human.loadImages();
        bar.setValue(50);
        music = new Music("Deep Haze.wav");
        music.loop();
        bar.setValue(75);
        clock = new Clock();
        clockTimer = new Timer(1000 / CPS, clock);
        clockTimer.start();
        bar.setValue(100);
        Display.clear();
        frame.setMinimumSize(new Dimension(MIN_WIDTH ,MIN_HEIGHT));
        if(VideoSettings.get(VideoSetting.MAXIMISED)) {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        Display.addLayer(new Background("intro.png"));
        final Scroll s = new Scroll(new MainMenu(), 0.5f, 0.7f, 0.5f, 0.99f, 250);
        Display.addLayer(s);
        s.open();
    }

    public static void startMusic(final String s) {
        music.stop();
        music = new Music(s, AudioSettings.get(AudioSetting.MUSIC_VOLUME));
        music.loop();
    }

    public static void newGame() throws IOException {
        Display.clear();
        Clock.clear();
        if(GameplaySettings.get(GameplaySetting.GENDER)) {
            Display.addLayer(new DollyCam(new World(), new Human(.5, .5, Gender.MALE)));
        } else {
            Display.addLayer(new DollyCam(new World(), new Human(.5, .5, Gender.FEMALE)));
        }
        startMusic("Darkest Child.wav");
    }

    public static void saveGame() {
        System.out.println("-----TODO: Save Game");
    }

    public static void quitGame() {
        Display.clear();
        Clock.clear();
        Display.addLayer(new Background("intro.png"));
        final Scroll s = new Scroll(new MainMenu(), 0.5f, 0.7f, 0.5f, 0.99f, 250);
        Display.addLayer(s);
        s.open();
        startMusic("Deep Haze.wav");
    }

    public static void close() {
        frame.dispose();
        displayTimer.stop();
        clockTimer.stop();
        music.stop();
    }
}
