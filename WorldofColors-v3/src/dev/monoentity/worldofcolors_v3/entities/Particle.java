package dev.monoentity.worldofcolors_v3.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import dev.monoentity.worldofcolors_v3.Handy;
import dev.monoentity.worldofcolors_v3.gfx.Assets;

public class Particle extends Entity {

	private Color c;
	private int distance;
	private int duration = 100;
	protected float x0, y0, xx, yy, xa, ya, partSpeed;
	private Random rnd = new Random();
	private int t = 0;

	public Particle(Handy hand, float x, float y, int dist, Color color) {
		super(hand, x, y, 3);
		createParticle(hand, x, y, dist, color);
		this.x = x;
		this.y = y;
		x0 = x;
		y0 = y;
		this.xa = (float) rnd.nextGaussian();
		this.ya = (float) rnd.nextGaussian();
	}

	public Particle(Handy hand, float x, float y, int dist, int amt, Color color) {
		super(hand, x, y, 3);
		createParticle(hand, x, y, dist, c);
		for (int i = 0; i < amt - 1; i++) {
			hand.getMap().getEntitySys().addParticle(new Particle(hand, x, y, dist, color));
		}
		hand.getMap().getEntitySys().addParticle(this);

	}

	private void createParticle(Handy hand, float x, float y, int dist, Color color) {
		c = color;
		distance = dist;
		partSpeed = 0.02f;
	}

	private float calcDistance() {
		float d = 0;
		d = (float) Math.sqrt(Math.abs(((x0 - x) * (x0 - x)) + ((y0 - y) * (y0 - y))));
		return d;
	}

	@Override
	public void update() {
		// System.out.println("ParticleSpeed: " + xa);
		t++;
		if (t > 1000) {
			t = 0;
		}
		
		

		ya += partSpeed;

		// System.out.println("Duration: " + during);
		for (int i = 0; i < hand.getMap().getEntitySys().getParticles().size(); i++) {
			Particle part = hand.getMap().getEntitySys().getParticles().get(i);
			if(part.t > duration){
				part.remove();
			}
			if (part.calcDistance() > distance) {
				part.remove();
			}
			if(part.x+part.s >= hand.getMap().getWidth()|| part.x < 0 || part.y+part.s >=hand.getMap().getHeight()-128 || part.y < 0){
				part.remove();
			}
		}

		move(xa, ya);

	}

	public void move(float xa, float ya) {
		
			if (xa < 0) {
				int tx = (int) Math.round(x + xa) / Assets.TILESIZE;
				if (!isCollideTile(tx, (int) (y) / Assets.TILESIZE)
						&& !isCollideTile(tx, (int) (y + s) / Assets.TILESIZE)) {

					x += xa;
				} else
					x = (float) (tx * Assets.TILESIZE + Assets.TILESIZE - boundsLeft.x);

			}
		
		
			if (xa > 0) {
				int tx = (int) Math.round(x + xa + s) / Assets.TILESIZE;
				if (!isCollideTile(tx, (int) (y) / Assets.TILESIZE)
						&& !isCollideTile(tx, (int) (y + s) / Assets.TILESIZE)) {

					x += xa;

				} else {

					x = (float) (tx * Assets.TILESIZE - boundsRight.x - boundsRight.width - 1);
				}
			}
		

		
			if (ya > 0) {
				int ty = (int) Math.round(y + ya + s) / Assets.TILESIZE;
				if (!isCollideTile((int) (x) / Assets.TILESIZE, ty)
						&& !isCollideTile((int) (x + s) / Assets.TILESIZE, ty)) {
					y += ya;

				} else {
					y = (float) (ty * Assets.TILESIZE - boundsBottom.height - boundsBottom.y - 1);

				}

			}

			if (ya < 0) {
				int ty = (int) Math.round(y + ya) / Assets.TILESIZE;
				if (!isCollideTile((int) (x) / Assets.TILESIZE, ty)
						&& !isCollideTile((int) (x + s) / Assets.TILESIZE, ty)) {

					y += ya;
				} else {
					y = (float) (ty * Assets.TILESIZE + Assets.TILESIZE - boundsTop.y);
				}
			}

		

		// System.out.println("Grounded: " + grounded);
		// System.out.println("HitHead: " + hitHead);
		// System.out.println(hand.getMap().getEntitySys().getProjectiles().size());

	}

	protected boolean isCollideTile(int x, int y) {
		if (x < 0|| x >= hand.getMap().getWidth() || y < 1
				|| y >= hand.getMap().getHeight())
			return false;
		return hand.getMap().getTile(x, y).isSolid();

	}

	@Override
	public void render(Graphics g) {
		g.setColor(c);
		g.fillRect((int) (x - hand.getMShift().getxShift()), (int) (y - hand.getMShift().getyShift()), s, s);

	}

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}

	public int getDuring() {
		return distance;
	}

	public void setDuring(int during) {
		this.distance = during;
	}

	public double getXa() {
		return xa;
	}

	public void setXa(float xa) {
		this.xa = xa;
	}

	public double getYa() {
		return ya;
	}

	public void setYa(float ya) {
		this.ya = ya;
	}

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public double getPartSpeed() {
		return partSpeed;
	}

	public void setPartSpeed(float partSpeed) {
		this.partSpeed = partSpeed;
	}

}
