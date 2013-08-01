package uds.infiniquest.settings;

import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 *
 * @author UndeadScythes
 */
public final class AudioSettings {
    public enum AudioSetting {
        MUSIC_VOLUME(0, "Music", 30),
        EFFECTS(1, "Effects", 80),
        VOICE(2, "Voices", 100),
        AMBIANCE(3, "Ambiance", 75);

        private int order, def;
        private String name;

        private AudioSetting(final int order, final String name, final int def) {
            this.order = order;
            this.name = name;
            this.def = def;
        }

        @Override
        public String toString() {
            return name;
        }

        public static AudioSetting getLine(final int line) {
            for(AudioSetting l : AudioSetting.values()) {
                if(l.order == line) {
                    return l;
                }
            }
            return null;
        }
    }
    
    private static final String FILE = "audio.cfg";

    private static HashMap<AudioSetting, Integer> settings = new HashMap<AudioSetting, Integer>();

    private AudioSettings() {}

    public static void loadSettings() throws FileNotFoundException, IOException {
        final File file = new File(FILE);
        if(file.exists()) {
            final BufferedReader in = new BufferedReader(new FileReader(file));
            for(AudioSetting line : AudioSetting.values()) {
                settings.put(line, Integer.parseInt(in.readLine()));
            }
            in.close();
        } else {
            for(AudioSetting line : AudioSetting.values()) {
                settings.put(line, line.def);
            }
        }
    }

    public static void save() {
        try {
            final BufferedWriter out = new BufferedWriter(new FileWriter(FILE));
            for(AudioSetting setting : AudioSetting.values()) {
                out.write(Integer.toString(settings.get(setting)));
                out.newLine();
            }
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(VideoSettings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int get(final AudioSetting line) {
        return settings.get(line);
    }

    public static boolean increase(final int line) {
        final AudioSetting l = AudioSetting.getLine(line);
        final int cVol = settings.get(l);
        if(cVol < 100) {
            settings.put(l, cVol + 1);
            return true;
        }
        return false;
    }

    public static boolean decrease(final int line) {
        final AudioSetting l = AudioSetting.getLine(line);
        final int cVol = settings.get(l);
        if(cVol > 0) {
            settings.put(l, cVol - 1);
            return true;
        }
        return false;
    }
}
