package fr.sorbonne.cpa.bombman.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;


import javax.sound.sampled.*;

public class AudioPlayer {
    /**
     * Plays a sound from the specified file name with given volume and looping settings.
     *
     * @param soundFileName the file name of the sound to play.
     * @param volume the volume level from 0.0 (silent) to 1.0 (maximum).
     * @param loop whether the sound should loop continuously.
     */
    public void playSound(String soundFileName, float volume,boolean loop) {
        new Thread(() -> {
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                        getClass().getResourceAsStream("/audio/" + soundFileName)
                );

                if (audioStream.getFormat().getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
                    AudioFormat baseFormat = audioStream.getFormat();
                    AudioFormat decodedFormat = new AudioFormat(
                            AudioFormat.Encoding.PCM_SIGNED,
                            baseFormat.getSampleRate(),
                            16,
                            baseFormat.getChannels(),
                            baseFormat.getChannels() * 2,
                            baseFormat.getSampleRate(),
                            false
                    );
                    audioStream = AudioSystem.getAudioInputStream(decodedFormat, audioStream);
                }

                DataLine.Info info = new DataLine.Info(Clip.class, audioStream.getFormat());
                Clip clip = (Clip) AudioSystem.getLine(info);
                clip.open(audioStream);
                if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    // 将音量转换为分贝
                    float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
                    gainControl.setValue(dB);
                }

                clip.start();
                if(loop) clip.loop(Clip.LOOP_CONTINUOUSLY);
                else clip.loop(0);

            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }).start();
    }
}


