package uds.infiniquest.settings;

import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 *
 * @author UndeadScythes
 */
public final class VideoSettings {
    public enum VideoSetting {
        MAXIMISED(0, "Maximised", false);

        private int order;
        private boolean def;
        private String name;

        private VideoSetting(final int order, final String name, final boolean def) {
            this.order = order;
            this.name = name;
            this.def = def;
        }

        @Override
        public String toString() {
            return name;
        }

        public static VideoSetting getSetting(final int line) {
            for(VideoSetting s : VideoSetting.values()) {
                if(s.order == line) {
                    return s;
                }
            }
            return null;
        }
    }

    private final static String FILE = "video.cfg";

    private static HashMap<VideoSetting, Boolean> settings = new HashMap<VideoSetting, Boolean>();

    private VideoSettings() {}

    public static void loadSettings() throws FileNotFoundException, IOException {
        final File file = new File(FILE);
        if(file.exists()) {
            final BufferedReader in = new BufferedReader(new FileReader(file));
            for(VideoSetting setting : VideoSetting.values()) {
                settings.put(setting, Boolean.parseBoolean(in.readLine()));
            }
            in.close();
        } else {
            for(VideoSetting setting : VideoSetting.values()) {
                settings.put(setting, setting.def);
            }
        }
    }

    public static void save() {
        try {
            final BufferedWriter out = new BufferedWriter(new FileWriter(FILE));
            for(VideoSetting setting : VideoSetting.values()) {
                out.write(Boolean.toString(settings.get(setting)));
            }
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(VideoSettings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean get(final VideoSetting setting) {
        return settings.get(setting);
    }

    public static void toggle(final int setting) {
        final VideoSetting s = VideoSetting.getSetting(setting);
        final boolean set = settings.get(s);
        settings.put(s, set ^ true);
    }
}
