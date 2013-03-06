package audio;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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
            ais = AudioSystem.getAudioInputStream(new File("src/music/" + track));
            clip.open(ais);
            final FloatControl gain = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            gain.setValue(20 * (float)Math.log10(volume / 100));
            //gain.setValue(-((100 - (float)volume) / 100) * 80);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Music.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Music.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Music.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Music(final String path) {
        this(path, 100);
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
