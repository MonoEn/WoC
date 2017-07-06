package dev.monoentity.worldofcolors_v3.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.monoentity.worldofcolors_v3.Handy;
import dev.monoentity.worldofcolors_v3.gfx.Assets;

public class DropGel extends Moving {

	private BufferedImage currentSprite;
	boolean firstHit = false;
	boolean hit = false;
	int hurtDelay = 0;

	public DropGel(Handy hand, float x, float y) {
		super(hand, x, y, Assets.TILESIZE * 2);

		currentSprite = Assets.dropLeft[0];

		xa = 0;
		health = 1000;

		rbounds.x = 17;
		rbounds.y = 17;
		rbounds.width = 94;
		rbounds.height = 94;
		boundsRight.x = 95;
		boundsRight.y = 40;
		boundsRight.width = 16;
		boundsRight.height = 48;
		boundsTop.x = 40;
		boundsTop.y = 17;
		boundsTop.width = 48;
		boundsTop.height = 16;
		boundsLeft.x = 17;
		boundsLeft.y = 40;
		boundsLeft.width = 16;
		boundsLeft.height = 48;
		boundsBottom.x = 40;
		boundsBottom.y = 94;
		boundsBottom.width = 48;
		boundsBottom.height = 16;

		walking = true;

	}

	private int time = 0;

	@Override
	public void update() {
		if(hand.getPlayer().isFrozen()){
			xa = 0;
		}
		else{
			xa = 0;
		}
		time++;
		if (time > 1000) {
			time = 0;
		}

		if (time % 180 == 0)
			health = 1000;

		if (health >= 996) {
			rbounds.x = 17;
			rbounds.y = 17;
			rbounds.width = 94;
			rbounds.height = 94;
			boundsRight.x = 95;
			boundsRight.y = 40;
			boundsRight.width = 16;
			boundsRight.height = 48;
			boundsTop.x = 40;
			boundsTop.y = 17;
			boundsTop.width = 48;
			boundsTop.height = 16;
			boundsLeft.x = 17;
			boundsLeft.y = 40;
			boundsLeft.width = 16;
			boundsLeft.height = 48;
			boundsBottom.x = 40;
			boundsBottom.y = 94;
			boundsBottom.width = 48;
			boundsBottom.height = 16;
			currentSprite = Assets.dropLeft[0];
		}
		if (health <= 995) {
			rbounds.x = 32;
			rbounds.y = 32;
			rbounds.width = 64;
			rbounds.height = 64;
			boundsRight.x = 80;
			boundsRight.y = 40;
			boundsRight.width = 16;
			boundsRight.height = 48;
			boundsTop.x = 40;
			boundsTop.y = 32;
			boundsTop.width = 48;
			boundsTop.height = 16;
			boundsLeft.x = 32;
			boundsLeft.y = 40;
			boundsLeft.width = 16;
			boundsLeft.height = 48;
			boundsBottom.x = 40;
			boundsBottom.y = 80;
			boundsBottom.width = 48;
			boundsBottom.height = 16;
			currentSprite = Assets.dropLeft[1];
		}

		move(xa, ya);
		checkHurt();
		checkDead();
		updateOnScreen();
		// System.out.println("Health: " + health);
	}

	public void updateOnScreen() {
		for (int i = 0; i < hand.getMap().getEntitySys().getEntities().size(); i++) {
			Entity e = hand.getMap().getEntitySys().getEntities().get(i);
			if (e instanceof BoxBird) {
				if (e.x + s <= hand.getMShift().getxShift()) {
					e.remove();
				}
			}

		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(currentSprite, (int) (x - hand.getMShift().getxShift()), (int) (y - hand.getMShift().getyShift()),
				s, s, null);

		/*
		 * g.setColor(Color.BLUE); g.drawRect((int) (boundsRight.x + x -
		 * hand.getMShift().getxShift()), (int) (boundsRight.y + y -
		 * hand.getMShift().getyShift()), (int) boundsRight.width, (int)
		 * boundsRight.height); g.setColor(Color.RED); g.drawRect((int)
		 * (boundsLeft.x + x - hand.getMShift().getxShift()), (int)
		 * (boundsLeft.y + y - hand.getMShift().getyShift()), (int)
		 * boundsLeft.width, (int) boundsLeft.height); g.setColor(Color.YELLOW);
		 * g.drawRect((int) (boundsTop.x + x - hand.getMShift().getxShift()),
		 * (int) (boundsTop.y + y - hand.getMShift().getyShift()), (int)
		 * boundsTop.width, (int) boundsTop.height); g.setColor(Color.GREEN);
		 * g.drawRect((int) (boundsBottom.x + x - hand.getMShift().getxShift()),
		 * (int) (boundsBottom.y + y - hand.getMShift().getyShift()), (int)
		 * boundsBottom.width, (int) boundsBottom.height);
		 * g.setColor(Color.BLACK);
		 * g.drawRect((int)(rbounds.x+x-hand.getMShift().getxShift()),
		 * (int)(rbounds.y+y-hand.getMShift().getyShift()), rbounds.width,
		 * rbounds.height);
		 */
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public BufferedImage getCurrentSprite() {
		return currentSprite;
	}

	public void setCurrentSprite(BufferedImage currentSprite) {
		this.currentSprite = currentSprite;
	}
	
	

}
