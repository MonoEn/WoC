package dev.monoentity.worldofcolors_v3.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import dev.monoentity.worldofcolors_v3.Handy;
import dev.monoentity.worldofcolors_v3.gfx.Assets;
import dev.monoentity.worldofcolors_v3.items.Item;
import dev.monoentity.worldofcolors_v3.sfx.Sound;

public class ProjectileP extends Projectile {

	public static final int FIREDELAY = 45;
	public static final int DEFAULTDMG = 1;
	private int t = 0;
	private Random rand = new Random();

	public ProjectileP(Handy hand, float x, float y) {
		super(hand, x, y, Assets.PROJECTILEOSIZE);
		limit = 900;
		dmg = DEFAULTDMG;
		firingDelay = FIREDELAY;
		velocity = 12;
		projectileSprite = Assets.pp;
		xa = 6;

		rbounds.x = 0;
		rbounds.y = 0;
		rbounds.width = Assets.PROJECTILEOSIZE;
		rbounds.height = Assets.PROJECTILEOSIZE;
	}

	@Override
	public void update() {
		t = rand.nextInt(4);
		//hand.getMap().getTile(tx, (int) (y + boundsLeft.y) / Assets.TILESIZE).update();
		System.out.println("t: " + t);
		for (int i = 0; i < hand.getMap().getEntitySys().getProjectiles().size(); i++) {
			Projectile pr = hand.getMap().getEntitySys().getProjectiles().get(i);
			if (pr instanceof ProjectileP) {
				ProjectileP pp = (ProjectileP) pr;
				int tx = (int) Math.round(x + xa + boundsLeft.x) / Assets.TILESIZE;
				if (pp.isCollideTile(tx, (int) (y + boundsLeft.y) / Assets.TILESIZE)
						&& pp.isCollideTile(tx, (int) (y + boundsLeft.y + boundsLeft.height) / Assets.TILESIZE)) {
					Sound.brick.play();
					hand.getMap().getEntitySys().removeProjectile(this);
					Particle part = new Particle(hand, (int) x, (int) y, 100, 10, Color.GRAY);
					hand.getMap().getEntitySys().addParticle(part);
					part.setT(0);
					part.setPartSpeed(0);
					/*if (hand.getMap().getTile(tx, (int) (y + boundsLeft.y) / Assets.TILESIZE).isBreakable()) {
	
						if(t % 2 == 0){
							hand.getMap().getIs().addItem(Item.aCoin.createItem((int)(x), (int)(y - 128)));
						}
						if (t % 3 == 0)
							hand.getMap().getIs().addItem(Item.cCoin.createItem((int) (x), (int) (y - 128)));
					}*/
				}

			}
		}
		for (int i = 0; i < hand.getMap().getEntitySys().getProjectiles().size(); i++) {
			Projectile pr = hand.getMap().getEntitySys().getProjectiles().get(i);
			if (pr.x + s >= hand.getMap().getWidth() - (Assets.TILESIZE * 2)) {
				hand.getMap().getEntitySys().removeProjectile(pr);
			}
			if (pr.y + s >= hand.getMap().getHeight() - 1) {
				hand.getMap().getEntitySys().removeProjectile(pr);
			}
		}
		move(xa, 0);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(projectileSprite, (int) (x - hand.getMShift().getxShift()),
				(int) (y - hand.getMShift().getyShift()), s, s, null);
		// g.drawRect((int)(rbounds.x + x - hand.getMShift().getxShift()),
		// (int)(rbounds.y + y - hand.getMShift().getyShift()), rbounds.width,
		// rbounds.height);
	}

}
