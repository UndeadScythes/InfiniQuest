package uds.infiniquest.menu;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import uds.infiniquest.clock.*;
import uds.infiniquest.settings.AudioSettings.AudioSetting;
import uds.infiniquest.settings.*;
import uds.infiniquest.settings.ControlSettings.ContolSetting;

/**
 *
 * @author UndeadScythes
 */
public class AudioMenu extends GameMenu implements GameProcess {
    private int change = 0;
    private long time;

    @SuppressWarnings("LeakingThisInConstructor")
    public AudioMenu() {
        super(new String[0], Color.RED, Color.BLACK);
        getAudio();
        Clock.addProcess(this);
    }

    @Override
    public GameMenu passInputPressed(final int keyCode) {
        if(keyCode == KeyEvent.VK_RIGHT || keyCode == ControlSettings.get(ContolSetting.RIGHT)) {
            change = 1;
        } else if(keyCode == KeyEvent.VK_LEFT || keyCode == ControlSettings.get(ContolSetting.LEFT)) {
            change = -1;
        }
        return super.passInputPressed(keyCode);
    }

    @Override
    public GameMenu passInputReleased(final int keyCode) {
        change = 0;
        return super.passInputReleased(keyCode);
    }

    @Override
    public GameMenu select() {
        if(getSelection() == AudioSetting.values().length) {
            good();
            AudioSettings.save();
            Clock.removeProcess(this);
            return new OptionsMenu(3);
        } else {
            bad();
        }
        return null;
    }

    private void getAudio() {
        final ArrayList<String> lineList = new ArrayList<String>();
        for(AudioSetting line : AudioSetting.values()) {
            lineList.add(line.toString() + " - " + AudioSettings.get(line));
        }
        lineList.add("Back");
        setItems(lineList.toArray(new String[lineList.size()]));
    }

    @Override
    public void clock(final long interval) {
        time += interval;
        if(time > 100) {
            time = 0;
            if(change == 1) {
                if(AudioSettings.increase(getSelection())) {
                    good();
                    final AudioSetting l = AudioSetting.getLine(getSelection());
                    changeItem(getSelection(), l.toString() + " - " + AudioSettings.get(l));
                } else {
                    bad();
                }
            } else if(change == -1) {
                if(AudioSettings.decrease(getSelection())) {
                    good();
                    final AudioSetting l = AudioSetting.getLine(getSelection());
                    changeItem(getSelection(), l.toString() + " - " + AudioSettings.get(l));
                } else {
                    bad();
                }
            }
        }
    }
}
