package audio;

import java.util.HashMap;

/**
 *
 * @author UndeadScythes
 */
public final class Volumes {
    public enum Line {
        MUSIC(0, "Music", 30),
        EFFECTS(1, "Effects", 80),
        VOICE(2, "Voices", 100),
        AMBIANCE(3, "Ambiance", 75);

        private int order, def;
        private String name;

        private Line(final int order, final String name, final int def) {
            this.order = order;
            this.name = name;
            this.def = def;
        }

        @Override
        public String toString() {
            return name;
        }

        public static Line getLine(final int line) {
            for(Line l : Line.values()) {
                if(l.order == line) {
                    return l;
                }
            }
            return null;
        }
    }

    private Volumes() {}

    private static HashMap<Line, Integer> volumes = new HashMap<Line, Integer>();

    public static void init() {
        for(Line line : Line.values()) {
            volumes.put(line, line.def);
        }
    }

    public static int get(final Line line) {
        return volumes.get(line);
    }

    public static boolean increase(final int line) {
        final Line l = Line.getLine(line);
        final int cVol = volumes.get(l);
        if(cVol < 100) {
            volumes.put(l, cVol + 1);
            return true;
        }
        return false;
    }

    public static boolean decrease(final int line) {
        final Line l = Line.getLine(line);
        final int cVol = volumes.get(l);
        if(cVol > 0) {
            volumes.put(l, cVol - 1);
            return true;
        }
        return false;
    }
}
