package main;
import java.io.*;
import javax.sound.sampled.*;

public class SoundThread extends Thread {
	private String filename;
	
	public SoundThread(String filename) {
		this.filename = filename;
	}
	
	

	// Statements defined in run() method are executes as a thread
	public void run() {
			try {
				AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename).getAbsoluteFile()); //(this.getClass().getResource(filename));
				Clip clip = AudioSystem.getClip(); // obtains clip that can be used to play back audio stream
			    clip.open(stream);
			    clip.start();
			}
			catch (Exception e) {
			    e.printStackTrace();
			    System.exit(1);
			}
			/*
			try {
				Thread.sleep(1000);
				} catch (InterruptedException e) {
				    e.printStackTrace();
				}
				*/
	}

}
