package Entities;

import java.awt.image.BufferedImage;

import Main.GamePanel;

public class Background extends Entity{ //Might not fit as entity but works for now
	
	public Background(BufferedImage img, int speed) {
		sprite = img;
		x=0;
		y=0;
		this.speed = speed;
	}
	
	public void update() {
		y+=speed;
		if(y>GamePanel.gameHeight) 
			y=0; //Reset position
	}
	
}
