package uds.infiniquest.menu;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.logging.*;
import uds.infiniquest.audio.*;
import uds.infiniquest.display.*;
import uds.infiniquest.settings.*;
import uds.infiniquest.settings.AudioSettings.AudioSetting;
import uds.infiniquest.settings.ControlSettings.ContolSetting;

/**
 *
 * @author UndeadScythes
 */
public abstract class GameMenu extends Layer {
    private static final float MENU_FONT_SIZE = 0.08f;

    private final String fontFamily;
    private final int fontStyle;
    private final float fontSize;
    private final Color on, off;

    private String[] items;
    private int selection;

    private int start = 0;

    @SuppressWarnings("LeakingThisInConstructor")
    public GameMenu(final String[] items, final String fontFamily, final int fontStyle, final float fontSize, final Color on, final Color off, final int selection) {
        super(1, 1, 0, 0);
        this.items = items.clone();
        this.fontFamily = fontFamily;
        this.fontStyle = fontStyle;
        this.fontSize = fontSize;
        this.on = on;
        this.off = off;
        this.selection = selection;
    }

    public GameMenu(final String[] items, final Color on, final Color off) {
        this(items, "Serif", Font.BOLD, MENU_FONT_SIZE, on, off, 0);
    }

    public GameMenu(final String[] items, final Color on, final Color off, final int selection) {
        this(items, "Serif", Font.BOLD, MENU_FONT_SIZE, on, off, selection);
    }

    @Override
    public void draw(final Graphics2D g2d, final int x, final int y, final int width, final int height) {
        final Font font = new Font(fontFamily, fontStyle, (int)(width * fontSize * 1.5));
        g2d.setFont(font);
        g2d.setColor(off);
        final int textHeight = (int)g2d.getFontMetrics().getStringBounds("Test.", g2d).getHeight();
        int fit = height / textHeight;
        if(fit > items.length) {
            fit = items.length;
        }
        if(selection > start + fit - 1) {
            start++;
        } else if(selection < start) {
            start--;
        }
        for(int i = start; i < start + fit; i++) {
            if(i != selection) {
                Text.center(g2d, items[i], x + width / 2, y + (i - start + 1) * (font.getSize()) + (int)((i - start) * width * 0.03));
            } else {
                g2d.setColor(on);
                Text.center(g2d, items[i], x + width / 2, y + (i - start + 1) * (font.getSize()) + (int)((i - start) * width * 0.03));
                g2d.setColor(off);
            }
        }
    }

    public GameMenu passInputPressed(final int keyCode) {
        if(keyCode == KeyEvent.VK_UP || keyCode == ControlSettings.get(ContolSetting.UP)) {
            if(selection > 0) {
                selection--;
                good();
            } else {
                bad();
            }
        } else if(keyCode == KeyEvent.VK_DOWN || keyCode == ControlSettings.get(ContolSetting.DOWN)) {
            if(selection < items.length - 1) {
                selection++;
                good();
            } else {
                bad();
            }
        } else if(keyCode == KeyEvent.VK_ENTER || keyCode == KeyEvent.VK_SPACE || keyCode == ControlSettings.get(ContolSetting.ACTION)) {
            try {
                return select();
            } catch (Exception ex) {
                Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public GameMenu passInputReleased(final int keyCode) {
        return null;
    }

    public final void good() {
        new Music("click.wav", AudioSettings.get(AudioSetting.EFFECTS)).play();
    }

    public final void bad() {
        new Music("error.wav", AudioSettings.get(AudioSetting.EFFECTS)).play();
    }

    public final int getSelection() {
        return selection;
    }

    public final void setSelection(final int selection) {
        this.selection = selection;
    }

    public final void changeItem(final int item, final String text) {
        items[item] = text;
    }

    public final void setItems(final String[] items) {
        this.items = items.clone();
    }

    public abstract GameMenu select() throws IOException;
}
