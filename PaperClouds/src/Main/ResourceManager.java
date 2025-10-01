package Main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ResourceManager {
	
	//Images
	public static BufferedImage baddie;
	public static BufferedImage plane;

	public static BufferedImage bgFar;
	public static BufferedImage bgMid;
	public static BufferedImage bgNear;
	
	public static BufferedImage bullet;
	
	//Sounds
	public static Clip song;
	public static Clip gameOver;
	
	

	//Set them in advance, because reading them each time is surprisingly performance taxing;
	static {
		try {
			baddie = ImageIO.read(ResourceManager.class.getResourceAsStream("/baddie.png"));
			plane = ImageIO.read(ResourceManager.class.getResourceAsStream("/plane.png"));
			
			bgFar = ImageIO.read(ResourceManager.class.getResourceAsStream("/clouds_far.png"));
			bgMid = ImageIO.read(ResourceManager.class.getResourceAsStream("/clouds_mid.png"));
			bgNear = ImageIO.read(ResourceManager.class.getResourceAsStream("/clouds_near.png"));
			
			bullet = ImageIO.read(ResourceManager.class.getResourceAsStream("/bullet.png"));
			
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(ResourceManager.class.getResource("/main_theme.wav"));
			AudioInputStream audio2 = AudioSystem.getAudioInputStream(ResourceManager.class.getResource("/game_over_theme.wav"));
			
			song = AudioSystem.getClip();
			song.open(audioStream);
			
			gameOver = AudioSystem.getClip();
			gameOver.open(audio2);
			
		}catch(IOException e) {
			e.printStackTrace();
		}catch(IllegalArgumentException e) {
			System.err.println("Could not load image");
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			 //TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
