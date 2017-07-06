package dev.monoentity.worldofcolors_v3.entities;

import java.awt.Color;
import java.awt.Graphics;

import dev.monoentity.worldofcolors_v3.Handy;
import dev.monoentity.worldofcolors_v3.gfx.Assets;

public class MiniUnknown extends Moving {

	public MiniUnknown(Handy hand, float x, float y, int size) {
		super(hand, x, y, size);

		rbounds.x = 0;
		rbounds.y = 0;
		rbounds.width = 32;
		rbounds.height = 32;

		boundsRight.x = 0;
		boundsRight.y = 0;
		boundsRight.width = 0;
		boundsRight.height = 0;

		boundsLeft.x = 0;
		boundsLeft.y = 0;
		boundsLeft.width = 0;
		boundsLeft.height = 0;

		boundsTop.x = 0;
		boundsTop.y = 0;
		boundsTop.width = 0;
		boundsTop.height = 0;

		boundsBottom.x = 0;
		boundsBottom.y = 0;
		boundsBottom.width = 0;
		boundsBottom.height = 0;

		xa = -2;
		health = 10000;
	}

	@Override
	public void move(float xa, float ya) {
		x += xa;

	}

	@Override
	public void update() {
		if(hand.getPlayer().isFrozen()){
			xa = 0;
		}
		else{
			xa = -2f;
		}
		move(xa, ya);

	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillOval((int) (x - hand.getMShift().getxShift()), (int) (y - hand.getMShift().getyShift()), s, s);
		g.drawRect((int)(rbounds.x + x - hand.getMShift().getxShift()), (int)(rbounds.y + y - hand.getMShift().getyShift()), rbounds.width, rbounds.height);
	}
}
