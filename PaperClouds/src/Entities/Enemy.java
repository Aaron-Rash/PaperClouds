package Entities;

import java.awt.Rectangle;
import Main.GamePanel;
import Main.ResourceManager;
import java.util.Random;

public class Enemy extends Entity{
	
	private GamePanel gamePanel;
	private Random random = new Random();
	
	public Enemy(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		width = random.nextInt(25,50) * GamePanel.scale; //Breaks pixel perfect scaling but adds much needed enemy variety
		height = width; //Fine as long as sprites are square (32x32)
		sprite = ResourceManager.baddie;
		x = random.nextInt(GamePanel.gameWidth-width);
		y = (random.nextInt(400)*-1) - height; //Random height just above the screen (random can't be negative)
		speed *= random.nextBoolean()? 1:-1; //Randomly decide if the enemies go left or right when created
		hitbox = makeHitbox();
	}
	
	public void update() {
		if (isAlive) {
			updatePosition();
			updateHitbox();
			checkCollision();
		}
	}

	private void checkCollision() {
		for(Bullet b: gamePanel.bullets) {
			if(b.hitbox.intersects(hitbox)) { //Intersects is of the rectangle class, much simpler than comparing all the x & y values
				b.isAlive = false;
				isAlive = false;
				gamePanel.score +=5;
				System.out.println(gamePanel.score);
			}
		}
		if (gamePanel.player.hitbox.intersects(hitbox)) {
			gamePanel.player.isAlive = false;
		}
		
	}

	private void updatePosition() {
		
		float increase = 0.3f; //Gets faster each bounce
		if(x+width >= GamePanel.gameWidth) { //bounce off right wall
			speed *= -1;
			speed-=increase;
		}
		if(x < 0) { //bounce off left wall
			speed *= -1;
			speed+=increase;
		}
		x+=speed/2;
		y+= 2f;
	}
	
}
