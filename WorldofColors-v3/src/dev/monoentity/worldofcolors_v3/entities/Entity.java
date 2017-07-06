package dev.monoentity.worldofcolors_v3.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import dev.monoentity.worldofcolors_v3.Handy;

public abstract class Entity {

	protected float x, y, gravity, velocity;
	protected boolean cubeGel = false, boxBird = false, sadGel = false;
	protected int s;
	protected Handy hand;
	protected Rectangle rbounds, boundsRight, boundsBottom, boundsTop, boundsLeft;
	protected boolean removed = false;

	public Entity(Handy hand, float x, float y, int size) {
		this.hand = hand;
		this.x = x;
		this.y = y;
		s = size;

		boundsRight = new Rectangle(0, 0, size, size);
		boundsBottom = new Rectangle(0, 0, size, size);
		boundsTop = new Rectangle(0, 0, size, size);
		boundsLeft = new Rectangle(0, 0, size, size);

		rbounds = new Rectangle(0, 0, size, size);
	}

	public Rectangle getColliderR(float xs, float ys) {
		return new Rectangle((int) (x + rbounds.x + xs), (int) (y + rbounds.y + ys), (int) rbounds.width,
				(int) rbounds.height);
	}

	public boolean checkCollider(float xs, float ys) {
		for (Entity e : hand.getMap().getEntitySys().getEntities()) {
			if (e.equals(this))
				continue;
			if (e.equals(hand.getMap().getEntitySys().getP()))
				continue;
			if(e instanceof MiniUnknown || e instanceof Unknown)
				continue;
			if (this instanceof CubeGel) {
				if (e instanceof BoxBird || e instanceof SadGel || e instanceof CubeGel || e instanceof BlackBird) {
					continue;
				}
			}
			if (this instanceof BoxBird) {
				if (e instanceof CubeGel || e instanceof SadGel || e instanceof BoxBird || e instanceof BlackBird) {
					continue;
				}
			}
			if (this instanceof SadGel) {
				if (e instanceof CubeGel || e instanceof BoxBird || e instanceof SadGel || e instanceof BlackBird) {
					continue;
				}
			}
			if (this instanceof DropGel) {
				if (e instanceof AngryGel || e instanceof BlackBird || e instanceof DropGel) {
					continue;
				}
			}
			if (this instanceof AngryGel) {
				if (e instanceof AngryGel || e instanceof BlackBird || e instanceof DropGel) {
					continue;
				}
			}
			if (this instanceof BlackBird) {
				if (e instanceof AngryGel || e instanceof BlackBird || e instanceof DropGel || e instanceof CubeGel
						|| e instanceof SadGel || e instanceof BoxBird) {
					continue;
				}
			}
			if (e.getColliderR(0f, 0f).intersects(getColliderR(xs, ys))) {
				return true;
			}
		}
		return false;
	}

	public boolean checkHit(float xs, float ys) {
		for (Projectile pr : hand.getMap().getEntitySys().getProjectiles()) {
			if (pr.equals(this))
				continue;
			if (pr instanceof ProjectileO)
				continue;
			for (int i = 0; i < hand.getMap().getEntitySys().getEntities().size(); i++) {
				Entity e = hand.getMap().getEntitySys().getEntities().get(i);
				if (pr.getColliderR(0f, 0f).intersects(getColliderR(xs, ys))) {
					return true;
				}

			}

		}
		return false;
	}

	public void remove() {
		removed = true;
	}

	public abstract void update();

	public abstract void render(Graphics g);

	// GETTERS & SETTERS
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

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	public float getVelocity() {
		return velocity;
	}

	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}

	public int getSize() {
		return s;
	}

	public void setSize(int size) {
		this.s = size;
	}

	public Rectangle getRbounds() {
		return rbounds;
	}

	public void setRbounds(Rectangle rbounds) {
		this.rbounds = rbounds;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

}
