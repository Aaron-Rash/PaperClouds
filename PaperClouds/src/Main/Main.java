//Aaron Rash
//CPT-236
//Final Project
//"Paper Clouds"

/* Game handles the game loop
 * GamePanel is a jPanel for displaying the game contents
 * GameWindow is a jFrame to hold the panel
 * 
 * InputMap handles inputs with KeyListener
 * ResourceManager initializes the images and sounds
 * 
 * Player/Enemy/Bullet/Background are of my Entity class, which defines shared properties between them
 * 
 * All images were drawn by me in Aseprite
 * All music was written by me in Beepbox
 * 
 */


package Main;

public class Main {

	public static void main(String[] args) {
		new Game();
	}

}
