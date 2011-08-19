package org.crusty.g2103;

import java.io.*;

import javax.sound.sampled.*;

public class Sound {
    public static class Clips {
        public Clip[] clips;
        private int p;
        private int count;
        
        public Clips(byte[] buffer, int count) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
            if (buffer == null) return;
            
            clips = new Clip[count];
            this.count = count;
            for (int i = 0; i < count; i++) {
                clips[i] = AudioSystem.getClip();
                clips[i].open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(buffer)));
            }
        }
        
        public void play() {
            if (clips == null) {
            	System.out.println("Sound doesn't exist.");
            	return;
            }

            clips[p].stop();
            clips[p].setFramePosition(0);
            clips[p].start();
            p++;
            if (p >= count) p = 0;
            
        }
    }
    
    public static Clips endGame = load("endGame.wav", 1);
    public static Clips death = load("death.wav", 1);
    
    public static Clips t0 = load("toneset/0.wav", 1);
    public static Clips t1 = load("toneset/1.wav", 1);
    public static Clips t2 = load("toneset/2.wav", 1);
    public static Clips t3 = load("toneset/3.wav", 1);
    public static Clips t4 = load("toneset/4.wav", 1);
    public static Clips t5 = load("toneset/5.wav", 1);
    
    public static Clips t6 = load("toneset/6.wav", 1);
    public static Clips t7 = load("toneset/7.wav", 1);
    public static Clips t8 = load("toneset/8.wav", 1);
    public static Clips t9 = load("toneset/9.wav", 1);
    public static Clips t10 = load("toneset/10.wav", 1);
    
    public static Clips t11 = load("toneset/11.wav", 1);
    public static Clips t12 = load("toneset/12.wav", 1);
    public static Clips t13 = load("toneset/13.wav", 1);
    

    private static Clips load(String name, int count) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataInputStream dis = new DataInputStream(ClassLoader.getSystemResourceAsStream(name));
            // Game.class.getResourceAsStream(name)
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = dis.read(buffer)) >= 0) {
                baos.write(buffer, 0, read);
            }
            dis.close();
            
            byte[] data = baos.toByteArray();
            return new Clips(data, count);
        } catch (Exception e) {
            try {
            	System.out.println("Clip load fail: " + name);
                return new Clips(null, 0);
            } catch (Exception ee) {
                return null;
            }
        }
    }

    public static void touch() {
    }
}
