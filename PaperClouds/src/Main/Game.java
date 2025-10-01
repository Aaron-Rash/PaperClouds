package Main;

import Entities.Player;
import Input.InputMap;

public class Game implements Runnable {
	
	private GamePanel gamePanel;
	private Thread gameThread;
	private Player player;
	
	public Game() {
		
		gamePanel = new GamePanel();
		player = new Player(gamePanel);
		
			gamePanel.setPlayer(player);
			gamePanel.setInputHandler(new InputMap(player));
		
		new GameWindow(gamePanel);
		gamePanel.requestFocusInWindow();
		
		startGameLoop();
	}
	
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public Player getPlayer() {
		return player;
	}
	public GamePanel getGamePanel() {
		return gamePanel;
	}


	@Override
	public void run() {
		//Game loop runs as long as game is active
		/*
		 * You want certain logic to operate on a set schedule, not just as fast as possible
		 * its based on a timer, so that the game functions the same on every machine, fast and slow, and shouldn't fluctuate
		 * the game updates the logic at a steady 200 times per second
		 * the game draws the graphics independently at 120 frames per second
		 */
		double timePerFrame = 1000000000 / 120; //Length of each frame (120 FPS)
		double timePerUpdate = 1000000000 / 200;
		
		long previousTime  = System.nanoTime();
		
		double deltaF  = 0; //Delta just means time (or difference in times)
		double deltaU = 0;
		
		int frames = 0; //For counting purposes
		int updates = 0;
				
		long lastCheck = System.currentTimeMillis();
		
		while(true) { //Infinite loop
			
			long now = System.nanoTime();
			deltaF += (now - previousTime)/timePerFrame;
			deltaU += (now - previousTime)/timePerUpdate;
			previousTime = now;
			
			
			if(deltaU >= 1) { //Update each interval
				Update();
				deltaU --; //remove 1 from delta, because sometimes it'll be more or less than 1, this carries lost time to the next loop
				updates++;
			}
			if(deltaF >= 1) { //Separate loop for FPS
				Repaint();
				deltaF --;
				frames ++;
			}
			
			//FPS / UPS counter
			if(System.currentTimeMillis() - lastCheck >= 1000) { //Check every second (1000 millisecond)
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " " + "Updates: " + updates);
				updates = 0; //reset so it isn't cumulative
				frames = 0;
			}
		}
	}





	private void Update() {
		gamePanel.updateGame();
	}
	
	private void Repaint() {
		gamePanel.repaint();
	}
	
}
