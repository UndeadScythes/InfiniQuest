package uds.infiniquest.settings;

import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;

/**
 *
 * @author UndeadScythes
 */
public final class ControlSettings {
    public enum ContolSetting {
        MENU(0, "Menu", KeyEvent.VK_ESCAPE),
        UP(1, "Move Up", KeyEvent.VK_W),
        LEFT(2, "Move Left", KeyEvent.VK_A),
        DOWN(3, "Move Down", KeyEvent.VK_S),
        RIGHT(4, "Move Right", KeyEvent.VK_D),
        ACTION(5, "Action", KeyEvent.VK_SPACE),
        ATTACK(6, "Attack", KeyEvent.VK_ENTER),
        MAGIC(7, "Magic", KeyEvent.VK_SHIFT),
        MAP(8, "Map", KeyEvent.VK_M),
        QUEST(9, "Quests", KeyEvent.VK_Q),
        BACKPACK(10, "Backpack", KeyEvent.VK_TAB),
        PAUSE(11, "Pause", KeyEvent.VK_P);

        private int order, def;
        private String name;

        private ContolSetting(final int order, final String name, final int def) {
            this.order = order;
            this.name = name;
            this.def = def;
        }

        @Override
        public String toString() {
            return name;
        }

        public static ContolSetting getControl(final int control) {
            for(ContolSetting c : ContolSetting.values()) {
                if(c.order == control) {
                    return c;
                }
            }
            return null;
        }
    }

    private static final String FILE = "controls.cfg";

    private static HashMap<ContolSetting, Integer> settings = new HashMap<ContolSetting, Integer>();

    private ControlSettings() {}

    public static void loadSettings() throws FileNotFoundException, IOException {
        final File file = new File(FILE);
        if(file.exists()) {
            final BufferedReader in = new BufferedReader(new FileReader(file));
            for(ContolSetting setting : ContolSetting.values()) {
                settings.put(setting, Integer.parseInt(in.readLine()));
            }
            in.close();
        } else {
            for(ContolSetting setting : ContolSetting.values()) {
                settings.put(setting, setting.def);
            }
        }
    }

    public static void save() {
        try {
            final BufferedWriter out = new BufferedWriter(new FileWriter(FILE));
            for(ContolSetting setting : ContolSetting.values()) {
                out.write(Integer.toString(settings.get(setting)));
                out.newLine();
            }
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(VideoSettings.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int get(final ContolSetting control) {
        return settings.get(control);
    }

    public static boolean set(final int control, final int key) {
        final ContolSetting c = ContolSetting.getControl(control);
        if(!settings.containsValue(key) || settings.get(c) == key) {
            settings.put(c, key);
            return true;
        }
        return false;
    }

    public static boolean hasKey(final int key) {
        return settings.containsValue(key);
    }
}
