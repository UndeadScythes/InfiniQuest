package uds.infiniquest.settings;

import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 *
 * @author UndeadScythes
 */
public final class GameplaySettings {
    public enum GameplaySetting {
        GENDER(0, "Gender", true);

        private int order;
        private boolean def;
        private String name;

        private GameplaySetting(final int order, final String name, final boolean def) {
            this.order = order;
            this.name = name;
            this.def = def;
        }

        @Override
        public String toString() {
            return name;
        }

        public static GameplaySetting getSetting(final int line) {
            for(GameplaySetting s : GameplaySetting.values()) {
                if(s.order == line) {
                    return s;
                }
            }
            return null;
        }
    }

    private static final String FILE = "gameplay.cfg";

    private static HashMap<GameplaySetting, Boolean> settings = new HashMap<GameplaySetting, Boolean>();

    private GameplaySettings() {}

    public static void loadSettings() throws FileNotFoundException, IOException {
        final File file = new File(FILE);
        if(file.exists()) {
            final BufferedReader in = new BufferedReader(new FileReader(file));
            for(GameplaySetting setting : GameplaySetting.values()) {
                settings.put(setting, Boolean.parseBoolean(in.readLine()));
            }
            in.close();
        } else {
            for(GameplaySetting setting : GameplaySetting.values()) {
                settings.put(setting, setting.def);
            }
        }
    }

        public static void save() {
        try {
            final BufferedWriter out = new BufferedWriter(new FileWriter(FILE));
            for(GameplaySetting setting : GameplaySetting.values()) {
                out.write(Boolean.toString(settings.get(setting)));
                out.newLine();
            }
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(VideoSettings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean get(final GameplaySetting setting) {
        return settings.get(setting);
    }

    public static void toggle(final int setting) {
        final GameplaySetting s = GameplaySetting.getSetting(setting);
        final boolean set = settings.get(s);
        settings.put(s, set ^ true);
    }
}
