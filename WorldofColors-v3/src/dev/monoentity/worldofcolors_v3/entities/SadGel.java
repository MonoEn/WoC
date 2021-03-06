package dev.monoentity.worldofcolors_v3.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.monoentity.worldofcolors_v3.Handy;
import dev.monoentity.worldofcolors_v3.gfx.Assets;

public class SadGel extends Moving {

	private BufferedImage currentSprite;
	boolean firstHit = false;
	boolean hit = false;
	int hurtDelay = 0;
	
	public SadGel(Handy hand, float x, float y) {
		super(hand, x, y, Assets.TILESIZE);
		
		currentSprite = Assets.sadLeft[0];
		
		xa = -1;
		health = 5;
		
		
		rbounds.x = 0;
		rbounds.y = 0;
		rbounds.width = 64;
		rbounds.height = 64;
		
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
		
		walking = true;
		
	}

	@Override
	public void update() {
		if(hand.getPlayer().isFrozen()){
			xa = 0;
		}
		else{
			xa = -1f;
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
		g.drawImage(currentSprite, (int)(x-hand.getMShift().getxShift()), (int)(y-hand.getMShift().getyShift()), s, s, null);
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
