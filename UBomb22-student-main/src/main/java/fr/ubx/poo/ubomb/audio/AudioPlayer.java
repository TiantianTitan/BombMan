package fr.ubx.poo.ubomb.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;


import javax.sound.sampled.*;

public class AudioPlayer {
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


//
//public class AudioPlayer {
//
//    public MediaPlayer backgroundMusicPlayer;
//
//    public void playSound(String soundFileName) {
//        URL resource = getClass().getResource("/audio/" + soundFileName);
//        Media media = new Media(resource.toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.play();
//    }
//
//    public void playBackgroundMusic() {
//        try {
//            URL resource = getClass().getResource("/audio/background.mp3");
//            if (resource == null) {
//                throw new FileNotFoundException("Background music file not found");
//            }
//            Media media = new Media(resource.toString());
//            backgroundMusicPlayer = new MediaPlayer(media);
//            backgroundMusicPlayer.setOnError(() -> {
//                System.err.println("Failed to play media. Error: " + backgroundMusicPlayer.getError().getMessage());
//            });
//            backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
//            backgroundMusicPlayer.play();
//        } catch (Exception e) {
//            System.err.println("Error playing background music:");
//            e.printStackTrace();
//        }
////        try {
////            URL resource = getClass().getResource("/audio/background.mp3");
////            if (resource == null) {
////                System.err.println("Audio file not found!");
////                return;
////            }
////            Media media = new Media(resource.toString());
////            if (backgroundMusicPlayer != null) {
////                backgroundMusicPlayer.stop();
////            }
////            backgroundMusicPlayer = new MediaPlayer(media);
////            backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop playback
////            backgroundMusicPlayer.play();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//    }
//
//
//    public void stopBackgroundMusic() {
//        if (backgroundMusicPlayer != null) {
//            backgroundMusicPlayer.stop();
//        }
//    }
//}
//
