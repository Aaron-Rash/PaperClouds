package Entities;

import java.awt.Rectangle;

import Main.GamePanel;
import Main.ResourceManager;

public class Bullet extends Entity{
	
	public int speed  = 5;
	public int range = 400;
	public int traveledDistance = 0;
	// All my sprites are 32x32, but bullet is much smaller, it doesn't show since its a transparent PNG.
	//so it needs to offset the hit box by the empty pixels in the drawing (11 blank pixels above and below)
	public int xOffset = 13 * GamePanel.scale;
	public int yOffset = 11 * GamePanel.scale;
	
	
	public Bullet(int x, int y) {
		this.x = x;
		this.y = y;
		sprite = ResourceManager.bullet;
		hitbox = new Rectangle(x+xOffset,y+yOffset,width-xOffset,height-yOffset);
	}

	public void update() {
		updatePosition();
		updateHitbox();
	}
	
	private void updatePosition() {

		if(y<0) //if the bullet leaves the top of the screen (0) get rid of it
			isAlive = false;
		y-= speed;
		
		//Might use this in the future
				//v
		
//		if (traveledDistance > range) {
//			isAlive = false;
//		}
//		y -= speed;
//		traveledDistance+=speed;
		
	}
}
