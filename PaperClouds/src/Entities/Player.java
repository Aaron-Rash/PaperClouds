package Entities;

import Main.GamePanel;
import Main.ResourceManager;

public class Player extends Entity{

	private boolean attacking;
	public GamePanel gamePanel;
	
	public Player(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	
		initPlayer();
	}
	
	public void initPlayer() {
		x = (GamePanel.gameWidth - width)/2; //start in bottom center
		y = GamePanel.gameHeight - height;
		sprite = ResourceManager.plane;
		hitbox = makeHitbox();
		isAlive = true;
	}

	public void update() { 
		if (isAlive) {
			updatePosition();		
			updateHitbox();
		}

	}
	
	private void updatePosition() {
		if(up && !down && y >0) { //check if up & down aren't pressed at same time & not out of bounds
			y -= speed;
		}
		else if(down && !up && y + height < GamePanel.gameHeight) {
			y += speed;
		}
		
		if(left && !right && x >0) { //Left and right checked separately, so diagonal movement possible;
			x -= speed;
		}
		else if(right && !left && x + width < GamePanel.gameWidth) {
			x += speed;
		}
	}
		
	public void attack() {
		if (isAlive) {
			Bullet bullet = new Bullet((int)x,(int)y);
			gamePanel.bullets.add(bullet);
		}
	}


	//Getters & setters
	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isAttacking() {
		return attacking;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}


}
