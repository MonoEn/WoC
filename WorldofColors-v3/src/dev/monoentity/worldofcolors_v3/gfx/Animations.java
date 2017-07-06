package dev.monoentity.worldofcolors_v3.gfx;

import java.awt.image.BufferedImage;

public class Animations {
	
	private int speed, index;
	private BufferedImage[] frames;
	private double time, lastTime;
	
	public Animations(int s, BufferedImage[] animFrames){
		speed = s;
		frames = animFrames;
		index = 0;
		time = 0;
		lastTime = System.currentTimeMillis();
	}
	
	public void update(){
		time += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(time > speed){
			index++;
			time = 0;
			if(index >= frames.length){
				index = 0;
			}
		}
	}
	
	public BufferedImage getCurrentFrame(){
		return frames[index];
	}
	
}
