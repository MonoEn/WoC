package dev.monoentity.worldofcolors_v3.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.monoentity.worldofcolors_v3.Handy;
import dev.monoentity.worldofcolors_v3.gfx.Animations;

public abstract class Enemy extends Moving {
	
	private BufferedImage currentSprite;
	private Animations animLeft;
	boolean firstHit = false;
	boolean hit = false;
	int hurtDelay = 0;
	
	public Enemy(Handy hand, float x, float y, int size) {
		super(hand, x, y, size);
		// TODO Auto-generated constructor stub
	}
}
