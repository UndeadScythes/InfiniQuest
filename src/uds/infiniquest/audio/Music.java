package uds.infiniquest.audio;

import java.io.*;
import java.util.logging.*;
import javax.sound.sampled.*;

/**
 *
 * @author UndeadScythes
 */
public class Music {
    private Clip clip;

    public Music(final String track, final float volume) {
        try {
            clip = AudioSystem.getClip();
            AudioInputStream ais;
            ais = AudioSystem.getAudioInputStream(Music.class.getResource("/music/" + track));
            clip.open(ais);
            changeVolume(volume);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Music.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Music.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Music.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Music(final String path) {
        this(path, 100);
    }

    public final void play() {
        clip.start();
    }

    public final void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public final void stop() {
        clip.stop();
    }

    public final void changeVolume(final float volume) {
        final FloatControl gain = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        gain.setValue(20 * (float)Math.log10(volume / 100));
    }
}
