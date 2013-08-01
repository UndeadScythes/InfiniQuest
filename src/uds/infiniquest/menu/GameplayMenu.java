package uds.infiniquest.menu;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import uds.infiniquest.settings.ControlSettings.ContolSetting;
import uds.infiniquest.settings.*;
import uds.infiniquest.settings.GameplaySettings.GameplaySetting;

/**
 *
 * @author UndeadScythes
 */
public class GameplayMenu extends GameMenu {
    public GameplayMenu() {
        super(new String[0], Color.RED, Color.BLACK);
        getGameplay();
    }

    @Override
    public GameMenu passInputPressed(final int keyCode) {
        if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_LEFT || keyCode == ControlSettings.get(ContolSetting.LEFT) || keyCode == ControlSettings.get(ContolSetting.RIGHT)) {
            GameplaySettings.toggle(getSelection());
            good();
            final GameplaySetting l = GameplaySetting.getSetting(getSelection());
            if(l == GameplaySetting.GENDER) {
                changeItem(getSelection(), l.toString() + " - " + (GameplaySettings.get(l) ? "Male" : "Female"));
            }

        }
        return super.passInputPressed(keyCode);
    }

    @Override
    public GameMenu select() {
        if(getSelection() == GameplaySetting.values().length) {
            good();
            GameplaySettings.save();
            return new OptionsMenu(1);

        } else {
            bad();
        }
        return null;
    }

    private void getGameplay() {
        final ArrayList<String> settingList = new ArrayList<String>();
        for(GameplaySetting setting : GameplaySetting.values()) {
            if(setting == GameplaySetting.GENDER) {
                settingList.add(setting.toString() + " - " + (GameplaySettings.get(setting) ? "Male" : "Female"));
            }
        }
        settingList.add("Back");
        setItems(settingList.toArray(new String[settingList.size()]));
    }
}
