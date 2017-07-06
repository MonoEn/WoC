package dev.monoentity.worldofcolors_v3.entities;

import java.awt.Color;
import java.awt.Graphics;

import dev.monoentity.worldofcolors_v3.Handy;
import dev.monoentity.worldofcolors_v3.gfx.Assets;
import dev.monoentity.worldofcolors_v3.states.ShopState;
import dev.monoentity.worldofcolors_v3.states.State;

public class Unknown extends Moving {
	
	public Unknown(Handy hand, float x, float y) {
		super(hand, x, y, Assets.TILESIZE);

		rbounds.x = 36;
		rbounds.y = 10;
		rbounds.width = 16;
		rbounds.height = 45;

		boundsRight.x = 36;
		boundsRight.y = 10;
		boundsRight.width = 16;
		boundsRight.height = 45;

		boundsLeft.x = 18;
		boundsLeft.y = 10;
		boundsLeft.width = 18;
		boundsLeft.height = 45;

		boundsTop.x = 25;
		boundsTop.y = 5;
		boundsTop.width = 20;
		boundsTop.height = 12;

		boundsBottom.x = 25;
		boundsBottom.y = 49;
		boundsBottom.width = 20;
		boundsBottom.height = 12;
		
		xa = 2.5f;
		health = 10000;
	}
	
	@Override
	public void move(float xa, float ya){
		x+=xa;
					
	}

	@Override
	public void update() {
		
		
		move(xa, ya);
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillOval((int) (x - hand.getMShift().getxShift()), (int) (y - hand.getMShift().getyShift()), Assets.TILESIZE,
				Assets.TILESIZE);

	}

}
