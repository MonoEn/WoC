package dev.monoentity.worldofcolors_v3.entities;

import java.awt.image.BufferedImage;

import dev.monoentity.worldofcolors_v3.Handy;
import dev.monoentity.worldofcolors_v3.gfx.Assets;
import dev.monoentity.worldofcolors_v3.states.GameState;

public abstract class Projectile extends Entity {

	public boolean shooting = true;

	protected final float x0, y0;
	protected float xa, ya;
	protected float velocity, firingDelay, limit;
	protected int dmg;
	
	protected BufferedImage projectileSprite;
	protected float distance;

	public Projectile(Handy hand, float x, float y, int s) {
		super(hand, x, y, s);
		x0 = x;
		y0 = y;
		this.x = x;
		this.y = y;
		boundsLeft.x = 0;
		boundsLeft.y = 0;
		boundsLeft.width = Assets.PROJECTILEOSIZE;
		boundsLeft.height = Assets.PROJECTILEOSIZE;
	}

	protected void move(float xa, float ya) {
		// System.out.println("BulletX: " + x);
		x += xa;
		y += ya;
		
		if (calcDistance() > limit)
			remove();
	}
	
	protected boolean isCollideTile(int x, int y) {
		if (x < 0 || x >= hand.getMap().getWidth() || y < 0
				|| y >= hand.getMap().getHeight())
			return false;
		return hand.getMap().getTile(x, y).isSolid();

	}

	private float calcDistance() {
		float d = 0;
		d = (float) Math.sqrt(Math.abs(((x0 - x) * (x0 - x)) + ((y0 - y) * (y0 - y))));
		return d;
	}

	public int getDmg() {
		return dmg;
	}

	public void setDmg(int dmg) {
		this.dmg = dmg;
	}
	
	

}
