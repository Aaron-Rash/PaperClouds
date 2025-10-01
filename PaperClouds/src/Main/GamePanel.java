package Main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.sound.sampled.Clip;
import javax.swing.JPanel;

import Entities.Background;
import Entities.Bullet;
import Entities.Enemy;
import Entities.Player;
import Input.InputMap;


public class GamePanel extends JPanel {

	private static int tileSize = 32;
	private static int columns  = 9; //3:4 aspect ratio, feels like a vertical shooter
	private static int rows = 12; //9 sprites wide, 12 tall
	public static int scale = 2; //Game looks tiny rendered at 288x384 on a higher res like 1920x1080
	private static int tileSizeScaled = tileSize * scale;
	public static int gameWidth = tileSizeScaled * columns;
	public static int gameHeight = tileSizeScaled * rows;
	
	public Player player;
	
	Background bgFar = new Background(ResourceManager.bgFar,1);
	Background bgMid = new Background(ResourceManager.bgMid,2);
	Background bgNear = new Background(ResourceManager.bgNear,3);
	

	public ArrayList<Bullet> bullets = new ArrayList<>();
	public ArrayList<Enemy> enemies = new ArrayList<>();
	
	long lastCheck = System.currentTimeMillis();
	long frequency = 0;
	long time = 5000;
	int enemyCount = 1;
	public Integer score = 0;
	
	Font levelFont = new Font("LevelFont",Font.PLAIN,16*scale);
	Font scoreFont = new Font("scoreFont",Font.PLAIN,32*scale);
	
	public GamePanel() {
		setFocusable(true);
		setPanelSize();
		setDoubleBuffered(true);
		enemies.add(new Enemy(this));
	}

	private void setPanelSize() {
		Dimension size = new Dimension(gameWidth,gameHeight);
		setPreferredSize(size);
	}
	
	//Try to update logic and draw logic separately
	public void updateGame(){ 

		if(player.isAlive) {
			player.update();
			addEnemy(); //Stop adding enemies when you die
		}
		
		for(Bullet b : bullets)
			b.update();
		
		for (Enemy e : enemies)
			e.update();
		
		backgroundUpdate();
		musicUpdate();
		
		dispose();
	}
	
	private void musicUpdate() {
		if(player.isAlive) {
			ResourceManager.gameOver.stop();
			ResourceManager.gameOver.setFramePosition(0); //Sets audio file back to beginning
			
			ResourceManager.song.start(); 
			ResourceManager.song.loop(Clip.LOOP_CONTINUOUSLY);
		}
		else {
			ResourceManager.song.stop();
			ResourceManager.song.setFramePosition(0);
			ResourceManager.gameOver.start();
		}
		
	}

	private void backgroundUpdate() {
		bgFar.update();
		bgMid.update();
		bgNear.update();
	}

	//Draw logic
	public void paintComponent(Graphics g) { //Called on repaint
		super.paintComponent(g);
		drawBackground(g); //You gotta draw it first, so its on the bottom
		drawBullets(g);
		drawEnemies(g);
		drawPlayer(g);
		drawScoreBoard(g);
	}
	
	
	private void drawScoreBoard(Graphics g) {
		if(player.isAlive) {
			g.setFont(levelFont);
			g.drawString("Level: " + enemyCount, 0, levelFont.getSize());
		}
		else {
			g.setFont(scoreFont);
			g.drawString("Your score is: "+ score.toString(), 0, gameHeight/2);
			g.drawString("Press enter to retry", 0, gameHeight/2 + 32*scale);
		}
	}

	private void drawPlayer(Graphics g) {
		if(player.isAlive) {
			//g.drawRect(player.hitbox.x, player.hitbox.y, player.hitbox.width, player.hitbox.height);
			g.drawImage(player.sprite, (int)player.x, (int)player.y, player.height, player.width, null);
		}
	}

	private void drawEnemies(Graphics g) {
		for (Enemy e: enemies) {
			//g.drawRect(e.hitbox.x, e.hitbox.y, e.hitbox.width, e.hitbox.height);
			g.drawImage(e.sprite,(int)e.x,(int)e.y,e.width,e.height,null);
		}
	}

	private void drawBullets(Graphics g) {
		for (Bullet b: bullets) {
			//g.drawRect(b.hitbox.x+b.xOffset, b.hitbox.y+b.yOffset, b.hitbox.width-b.xOffset, b.hitbox.height-b.yOffset);
			g.drawImage(ResourceManager.bullet,(int)b.x,(int)b.y,b.width,b.height,null);
		}
	}
	
	//Scrolling background + parallax
	private void drawBackground(Graphics g) {
		//Correct order is necessary for it to work
		g.drawImage(bgFar.sprite,(int)bgFar.x,(int)bgFar.y,gameWidth,gameHeight,null);
		g.drawImage(bgFar.sprite,(int)bgFar.x,(int)bgFar.y-gameHeight,gameWidth,gameHeight,null); //Need a copy of each, offset by height, for loop purposes
							
		g.drawImage(bgMid.sprite,(int)bgMid.x,(int)bgMid.y,gameWidth,gameHeight,null);
		g.drawImage(bgMid.sprite,(int)bgMid.x,(int)bgMid.y-gameHeight,gameWidth,gameHeight,null);
			
		g.drawImage(bgNear.sprite,(int)bgNear.x,(int)bgNear.y,gameWidth,gameHeight,null);
		g.drawImage(bgNear.sprite,(int)bgNear.x,(int)bgNear.y-gameHeight,gameWidth,gameHeight,null);
	}
		
	public void addEnemy() { //Add a new enemy at increasingly fast intervals
		if(System.currentTimeMillis() - lastCheck >= time) { //Starts by adding a new one every five seconds
			lastCheck = System.currentTimeMillis();
			
			makeEnemy(enemyCount); //Generate enemies
			
			time -=1000; //Shortens the time by half a second
			if(time < 1000) { //If it gets too fast (because eventually it'll be every game tick (200 a second)
				enemyCount ++; //Increase amount of enemies at once (2 then 3 then 4 etc)
				time = 5000; //reset timer
			}

		}
	}

	private void makeEnemy(int count) {
		for (int i = 0; i<count; i++) {
			enemies.add(new Enemy(this));
		}
	}

	private void dispose() {
		bullets.removeIf(b -> !b.isAlive); //Way cleaner than an iterator
		enemies.removeIf(e -> !e.isAlive || e.y > gameHeight); 
		
	}
	
	public void restart() {
		score = 0;
		enemyCount = 1;
		time = 5000;
		player.initPlayer();
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setInputHandler(InputMap inputMap) {
		addKeyListener(inputMap);
	}

}
