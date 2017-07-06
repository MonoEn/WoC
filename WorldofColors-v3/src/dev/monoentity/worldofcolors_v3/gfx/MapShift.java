package dev.monoentity.worldofcolors_v3.gfx;

import dev.monoentity.worldofcolors_v3.Handy;
import dev.monoentity.worldofcolors_v3.entities.Entity;

public class MapShift {

	private float xShift, yShift;
	private Handy hand;
	
	public MapShift(Handy hand, int xs, int ys){
		this.hand = hand;
		xShift = xs;
		yShift = ys;
	}
	
	public void centerOn(Entity e){
		xShift = e.getX() - hand.getWidth()/8;
		yShift = e.getY() - hand.getHeight()+(hand.getHeight()/3);
	}
	
	public void shift(float xDisplace, float yDisplace){
		xShift += xDisplace;
		yShift += yDisplace;
	}

	public float getxShift() {
		return xShift;
	}

	public void setShift(float xs, float ys) {
		this.xShift = xs;
		this.yShift = ys;
	}
	
	public void setxShift(float xs){
		this.xShift = xs;
	}

	public float getyShift() {
		return yShift;
	}

	
	
}
