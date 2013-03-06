package controls;

import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 *
 * @author UndeadScythes
 */
public final class Controls {
    public enum Control {
        Menu(0, "Menu", KeyEvent.VK_ESCAPE),
        MoveUp(1, "Move Up", KeyEvent.VK_W),
        MoveLeft(2, "Move Left", KeyEvent.VK_A),
        MoveDown(3, "Move Down", KeyEvent.VK_S),
        MoveRight(4, "Move Right", KeyEvent.VK_D),
        Action(5, "Action", KeyEvent.VK_SPACE);

        private int order, def;
        private String name;

        private Control(final int order, final String name, final int def) {
            this.order = order;
            this.name = name;
            this.def = def;
        }

        @Override
        public String toString() {
            return name;
        }

        public static Control getControl(final int control) {
            for(Control c : Control.values()) {
                if(c.order == control) {
                    return c;
                }
            }
            return null;
        }
    }

    private static HashMap<Control, Integer> controls = new HashMap<Control, Integer>();

    private Controls() {}

    public static void init() {
        for(Control control : Control.values()) {
            controls.put(control, control.def);
        }
    }

    public static int get(final Control control) {
        return controls.get(control);
    }

    public static boolean set(final int control, final int key) {
        final Control c = Control.getControl(control);
        if(!controls.containsValue(key) || controls.get(c) == key) {
            controls.put(c, key);
            return true;
        }
        return false;
    }

    public static boolean hasKey(final int key) {
        return controls.containsValue(key);
    }
}
