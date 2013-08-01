package uds.infiniquest.menu;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import uds.infiniquest.settings.*;
import uds.infiniquest.settings.ControlSettings.ContolSetting;

/**
 *
 * @author UndeadScythes
 */
public class ControlsMenu extends GameMenu {
    private boolean change;

    public ControlsMenu() {
        super(new String[0], Color.RED, Color.BLACK);
        getControls();
    }

    @Override
    public GameMenu passInputPressed(final int keyCode) {
        if(change) {
            if(ControlSettings.set(getSelection(), keyCode)) {
                good();
                final ContolSetting c = ContolSetting.getControl(getSelection());
                changeItem(getSelection(), c.toString() + " - " + KeyEvent.getKeyText(ControlSettings.get(c)));
                change = false;
            } else {
                bad();
            }
            return null;
        } else {
            return super.passInputPressed(keyCode);
        }
    }

    @Override
    public GameMenu select() {
        if(getSelection() == ContolSetting.values().length) {
            good();
            final int cCount = ContolSetting.values().length;
            for(int i = 0; i < cCount; i++) {
                final ContolSetting c = ContolSetting.getControl(i);
                changeItem(i, c.toString() + " - " + KeyEvent.getKeyText(ControlSettings.get(c)));
            }
            changeItem(cCount, "Reset To Default");
            changeItem(cCount + 1, "Back");
        } else if(getSelection() == ContolSetting.values().length + 1) {
            good();
            ControlSettings.save();
            return new OptionsMenu(0);
        } else {
            good();
            changeItem(getSelection(), ContolSetting.getControl(getSelection()).toString() + " - ?");
            change = true;
        }
        return null;
    }

    private void getControls() {
        final ArrayList<String> controlList = new ArrayList<String>();
        for(ContolSetting control : ContolSetting.values()) {
            controlList.add(control.toString() + " - " + KeyEvent.getKeyText(ControlSettings.get(control)));
        }
        controlList.add("Reset To Default");
        controlList.add("Back");
        setItems(controlList.toArray(new String[controlList.size()]));
    }
}
