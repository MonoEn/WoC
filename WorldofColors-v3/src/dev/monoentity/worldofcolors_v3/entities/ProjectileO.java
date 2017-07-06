package dev.monoentity.worldofcolors_v3.entities;

import java.awt.Graphics;

import dev.monoentity.worldofcolors_v3.Handy;
import dev.monoentity.worldofcolors_v3.gfx.Assets;

public class ProjectileO extends Projectile {
	
	public static final int FIREDELAY = 15;
	public static final int DEFAULTDMG = 1;

	public ProjectileO(Handy hand, float x, float y) {
		super(hand, x, y, Assets.PROJECTILEOSIZE);
		limit = hand.getMap().getWidth()-Assets.TILESIZE;
		dmg = DEFAULTDMG;
		firingDelay = FIREDELAY;
		velocity = 12;
		projectileSprite = Assets.po;
		xa = -5.5f + hand.getPlayer().getXa();
		boundsLeft.x = 0;
		boundsLeft.y = 0;
		boundsLeft.width = Assets.PROJECTILEOSIZE;
		boundsLeft.height = Assets.PROJECTILEOSIZE;
	}

	@Override
	public void update() {
		if(hand.getPlayer().isFrozen()){
			xa = 0;
		}
		else{
			xa = -5.5f + hand.getPlayer().getXa();
		}
		//System.out.println("Projectiles: " + hand.getMap().getEntitySys().getProjectiles().size());
		for(int i = 0; i < hand.getMap().getEntitySys().getProjectiles().size(); i++){
			Projectile pr = hand.getMap().getEntitySys().getProjectiles().get(i);
			if(pr.x <= hand.getMShift().getxShift()){
				pr.remove();
			}
		}
		move(xa , 0);
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(projectileSprite, (int)(x-hand.getMShift().getxShift()), (int)(y-hand.getMShift().getyShift()), s, s, null);
		
	}

	
	
}
