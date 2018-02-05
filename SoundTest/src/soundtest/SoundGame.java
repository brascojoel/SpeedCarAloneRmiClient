package soundtest;


import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brasc
 */
public class SoundGame {
    public  static final SoundGame started =  new SoundGame("./Sound/cargeardown.wav");
   /* public  static final SoundGame brake =  new SoundGame("CarCrash.wav");
    public  static final SoundGame left =  new SoundGame("TireSKID.wav");
    public  static final SoundGame right =  new SoundGame("TireSKID.wav");
    public  static final SoundGame accelerate=  new SoundGame("gt40takingOff.wav");*/
   
   // Nested class for specifying volume
   public static enum Volume {
      MUTE, LOW, MEDIUM, HIGH
   }
   
   public static Volume volume = Volume.LOW;
   
   // Each sound effect has its own clip, loaded with its own sound file.
   private Clip clip;
   
   // Constructor to construct each element of the enum with its own sound file.
    public SoundGame(String soundFileName) {
      try {
         // Use URL (instead of File) to read from disk and JAR.
         URL url = this.getClass().getClassLoader().getResource(soundFileName);
         // Set up an audio input stream piped from the sound file.
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
         // Get a clip resource.
         clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         clip.open(audioInputStream);
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }
   
   // Play or Re-play the sound effect from the beginning, by rewinding.
   public void play() {
      if (volume != Volume.MUTE) {
         if (clip.isRunning())
            clip.stop();   // Stop the player if it is still running
         clip.setFramePosition(0); // rewind to the beginning
         clip.start(); // Start playing
          try {
              Thread.sleep(1000);
          } catch (InterruptedException ex) {
              Logger.getLogger(SoundGame.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
   }
   
   // Optional static method to pre-load all the sound files.
   /*static void init() {
      values(); // calls the constructor for all the elements
   }*/
    
    
}