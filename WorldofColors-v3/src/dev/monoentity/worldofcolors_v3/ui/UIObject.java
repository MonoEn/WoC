package dev.monoentity.worldofcolors_v3.ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import dev.monoentity.worldofcolors_v3.Handy;
import dev.monoentity.worldofcolors_v3.input.MouseIn;

public abstract class UIObject {

	protected Handy hand;
	protected float x, y;
	protected int width, height;
	protected Rectangle box;
	protected boolean hoveringOver = false;
	protected MouseIn mi;
	
	public UIObject(Handy hand, float ux, float uy, int uWidth, int uHeight, MouseIn mouseIn){
		this.hand = hand;
		x = ux;
		y = uy;
		width = uWidth;
		height = uHeight;
		mi = mouseIn;
		box = new Rectangle((int) ux, (int) uy, uWidth, uHeight);
	}
	
	public abstract void update();
	public abstract void render(Graphics g);
	
	public abstract void onClick();

	public void onMouseMove(MouseEvent evt){
		if(box.contains(evt.getX(), evt.getY()))
			setHoveringOver(true);
		else setHoveringOver(false);
	}
	
	public void onMouseRelease(MouseEvent evt){
		if(isHoveringOver()&&evt.getButton() == MouseEvent.BUTTON1){
				onClick();
		}
	}
	
	//GETTERS&SETTERS
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isHoveringOver() {
		return hoveringOver;
	}

	public void setHoveringOver(boolean hoveringOver) {
		this.hoveringOver = hoveringOver;
	}
	
	
	
}
