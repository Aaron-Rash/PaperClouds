package Entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Main.GamePanel;

public abstract class Entity { //Abstract basically means you don't make objects from this class, only extend

	protected boolean up,left,down,right;
	public float x = 100;
	public float y = 100;
	public int speed = 2 * GamePanel.scale;
	public int width = (32 * GamePanel.scale);
	public int height = (32 * GamePanel.scale);
	
	public Rectangle hitbox ;
	public BufferedImage sprite;
	
	public boolean isAlive = true;

	public Entity() {
		
	}

	protected Rectangle makeHitbox() { //The property that determines if an object collides with another
		hitbox = new Rectangle((int)x, (int)y, width, height);	
		return hitbox;
		
	}
	protected void updateHitbox() {
		hitbox.x = (int)x;
		hitbox.y = (int)y;
	}
	
}
