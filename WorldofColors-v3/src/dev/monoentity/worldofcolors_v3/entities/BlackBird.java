package dev.monoentity.worldofcolors_v3.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.monoentity.worldofcolors_v3.Handy;
import dev.monoentity.worldofcolors_v3.gfx.Animations;
import dev.monoentity.worldofcolors_v3.gfx.Assets;

public class BlackBird extends Moving {

	private BufferedImage currentSprite;
	private Animations animLeft;
	boolean firstHit = false;
	boolean hit = false;
	int hurtDelay = 0;
	
	public BlackBird(Handy hand, float x, float y) {
		super(hand, x, y, Assets.TILESIZE);
		animLeft = new Animations(500, Assets.blackbirdLeft);
		currentSprite = Assets.blackbirdLeft[0];
		
		xa = -2.5f;
		walking = true;
		health = 6;
		
		rbounds.x = 16;
		rbounds.y = 16;
		rbounds.width = 32;
		rbounds.height = 32;

		boundsRight.x = 48;
		boundsRight.y = 8;
		boundsRight.width = 16;
		boundsRight.height = 48;

		boundsTop.x = 8;
		boundsTop.y = 0;
		boundsTop.width = 48;
		boundsTop.height = 16;

		boundsLeft.x = 0;
		boundsLeft.y = 8;
		boundsLeft.width = 16;
		boundsLeft.height = 48;

		boundsBottom.x = 8;
		boundsBottom.y = 49;
		boundsBottom.width = 48;
		boundsBottom.height = 16;
	}

	@Override
	public void update() {
		if(hand.getPlayer().isFrozen()){
			xa = 0;
			ya = 0;
		}
		else{
			if(y + (s/2) < hand.getPlayer().getY() + (hand.getPlayer().getSize()/2))
				ya = 1;
			if(y + (s/2) > hand.getPlayer().getY() + (hand.getPlayer().getSize()/2))
				ya = -1;
			xa = -2.5f;
		}
		//System.out.println("BoxBird: " + health);
		animLeft.update();
		if (walking) {
			currentSprite = animLeft.getCurrentFrame();
		}
		
		move(xa, ya);
		checkHurt();
		checkDead();
		updateOnScreen();
		//System.out.println("Health: " + health);
	}

	public void updateOnScreen(){
		for(int i = 0; i < hand.getMap().getEntitySys().getEntities().size(); i++){
			Entity e = hand.getMap().getEntitySys().getEntities().get(i);
			if(e instanceof BoxBird){
				if ( e.x +s<= hand.getMShift().getxShift()){
					e.remove();
				}
			}
				
		}
		
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(currentSprite, (int)(x - hand.getMShift().getxShift()), (int) (y - hand.getMShift().getyShift()), s, s, null);
		/*g.setColor(Color.BLUE);
		g.drawRect((int) (boundsRight.x + x - hand.getMShift().getxShift()),
		 (int) (boundsRight.y + y - hand.getMShift().getyShift()), (int)
		 boundsRight.width,
		 (int) boundsRight.height);
		 g.setColor(Color.RED);
		 g.drawRect((int) (boundsLeft.x + x - hand.getMShift().getxShift()),
		 (int) (boundsLeft.y + y - hand.getMShift().getyShift()), (int)
		 boundsLeft.width,
		 (int) boundsLeft.height);
		 g.setColor(Color.YELLOW);
		 g.drawRect((int) (boundsTop.x + x - hand.getMShift().getxShift()),
		 (int) (boundsTop.y + y - hand.getMShift().getyShift()), (int)
		 boundsTop.width, (int) boundsTop.height);
		 g.setColor(Color.GREEN);
		 g.drawRect((int) (boundsBottom.x + x - hand.getMShift().getxShift()),
		 (int) (boundsBottom.y + y - hand.getMShift().getyShift()), (int)
		 boundsBottom.width,
		 (int) boundsBottom.height);*/
	}

	
}
