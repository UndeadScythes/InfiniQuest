package uds.infiniquest.menu;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import uds.infiniquest.settings.*;
import uds.infiniquest.settings.VideoSettings.VideoSetting;

/**
 *
 * @author UndeadScythes
 */
public class VideoMenu extends GameMenu {
    public VideoMenu() {
        super(new String[0], Color.RED, Color.BLACK);
        getGameplaySettings();
    }

    @Override
    public GameMenu passInputPressed(final int keyCode) {
        if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_LEFT) {
            VideoSettings.toggle(getSelection());
            good();
            final VideoSetting l = VideoSetting.getSetting(getSelection());
            if(l == VideoSetting.MAXIMISED) {
                changeItem(getSelection(), l.toString() + " - " + (VideoSettings.get(l) ? "True" : "False"));
            }
        }
        return super.passInputPressed(keyCode);
    }

    @Override
    public GameMenu select() {
        if(getSelection() == VideoSetting.values().length) {
            good();
            VideoSettings.save();
            return new OptionsMenu(2);
        } else {
            bad();
        }
        return null;
    }

    private void getGameplaySettings() {
        final ArrayList<String> settingList = new ArrayList<String>();
        for(VideoSetting setting : VideoSetting.values()) {
            if(setting == VideoSetting.MAXIMISED) {
                settingList.add(setting.toString() + " - " + (VideoSettings.get(setting) ? "True" : "False"));
            }
        }
        settingList.add("Back");
        setItems(settingList.toArray(new String[settingList.size()]));
    }
}
