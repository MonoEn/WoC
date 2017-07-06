package dev.monoentity.worldofcolors_v3.entities;

import java.awt.Color;

import dev.monoentity.worldofcolors_v3.Handy;
import dev.monoentity.worldofcolors_v3.gfx.Assets;
import dev.monoentity.worldofcolors_v3.items.Item;
import dev.monoentity.worldofcolors_v3.sfx.Sound;
import dev.monoentity.worldofcolors_v3.states.GameState;

public abstract class Moving extends Entity {

	public int health;
	protected float velocity, xa, ya;
	protected boolean walking = false;
	public static int wat;
	public Projectile pr;
	protected boolean colliding = false;
	protected boolean grounded = true;
	protected boolean hitHead = false;
	protected int hurtAmt;
	protected boolean hit = false;

	protected boolean firstHit = false;
	protected int hurtDelay = 0;
	protected boolean hurt = false;

	// UPGRADES
	protected int dmgUp = 0;
	protected int chanceDrop = 0;

	public Moving(Handy hand, float x, float y, int size) {
		super(hand, x, y, size);
	}

	protected void shoot(float x, float y) {
		Projectile pr = new ProjectileP(hand, x, y);
		hand.getMap().getEntitySys().addProjectile(pr);
	}

	public void move(float xa, float ya) {
		if (!checkCollider(xa, 0f)) {
			if (xa < 0) {
				int tx = (int) Math.round(x + xa + boundsLeft.x) / Assets.TILESIZE;
				if (!isCollideTile(tx, (int) (y + boundsLeft.y) / Assets.TILESIZE)
						&& !isCollideTile(tx, (int) (y + boundsLeft.y + boundsLeft.height) / Assets.TILESIZE)) {

					x += xa;
				} else
					x = (float) (tx * Assets.TILESIZE + Assets.TILESIZE - boundsLeft.x);

			}
		}
		if (!checkCollider(xa, 0f)) {
			if (xa > 0) {
				int tx = (int) Math.round(x + xa + boundsRight.x + boundsRight.width) / Assets.TILESIZE;
				if (!isCollideTile(tx, (int) (y + boundsRight.y) / Assets.TILESIZE)
						&& !isCollideTile(tx, (int) (y + boundsRight.y + boundsRight.height) / Assets.TILESIZE)) {

					x += xa;

				} else {

					x = (float) (tx * Assets.TILESIZE - boundsRight.x - boundsRight.width - 1);
				}
			}
		}

		if (!checkCollider(0f, ya)) {
			if (ya > 0) {
				int ty = (int) Math.round(y + ya + boundsBottom.y + boundsBottom.height) / Assets.TILESIZE;
				if (!isCollideTile((int) (x + boundsBottom.x) / Assets.TILESIZE, ty)
						&& !isCollideTile((int) (x + boundsBottom.x + boundsBottom.width) / Assets.TILESIZE, ty)) {
					y += ya;
					grounded = false;
				} else {
					y = (float) (ty * Assets.TILESIZE - boundsBottom.height - boundsBottom.y - 1);
					grounded = true;
				}

			}

		}
		if (!checkCollider(0f, ya)) {
			if (ya < 0) {
				int ty = (int) Math.round(y + ya + boundsTop.y) / Assets.TILESIZE;
				if (!isCollideTile((int) (x + boundsTop.x) / Assets.TILESIZE, ty)
						&& !isCollideTile((int) (x + boundsTop.x + boundsTop.width) / Assets.TILESIZE, ty)) {

					y += ya;
				} else {
					y = (float) (ty * Assets.TILESIZE + Assets.TILESIZE - boundsTop.y);
				}
			}
		}

		if (grounded) {
			hitHead = false;
		}

		// System.out.println("Grounded: " + grounded);
		// System.out.println("HitHead: " + hitHead);
		// System.out.println(hand.getMap().getEntitySys().getProjectiles().size());

	}

	protected void checkHurt() {
		hurtAmt = ProjectileP.DEFAULTDMG + hand.getGame().getShop().getDmgUp();
		for (int i = 0; i < hand.getMap().getEntitySys().getProjectiles().size(); i++) {
			Projectile pr = hand.getMap().getEntitySys().getProjectiles().get(i);
			for (int i2 = 0; i2 < hand.getMap().getEntitySys().getEntities().size(); i2++) {
				Entity e = hand.getMap().getEntitySys().getEntities().get(i2);
				if (e instanceof CubeGel) {
					CubeGel c = (CubeGel) e;
					// System.out.println("CubeGel Health: " + c.health);

					if ((c).checkHit(xa, 0f) && pr.checkCollider(xa, 0f)) {
						Sound.cubegel.play();
						(c).firstHit = true;
						(c).hit = true;
						(c).hurtDelay = 0;
						Particle part = new Particle(hand, (int) pr.x, (int) pr.y, 100, 10, Color.MAGENTA);
						hand.getMap().getEntitySys().addParticle(part);
						part.setT(0);
						part.setPartSpeed(0);

						pr.remove();
					}
					if ((c).checkHit(0f, ya) && pr.checkCollider(0f, ya)) {
						Sound.cubegel.play();
						(c).firstHit = true;
						((c)).hit = true;
						((c)).hurtDelay = 0;
						Particle part = new Particle(hand, (int) pr.x, (int) pr.y, 100, 10, Color.MAGENTA);
						hand.getMap().getEntitySys().addParticle(part);
						part.setT(0);
						part.setPartSpeed(0);

						pr.remove();
					}

					if (((c)).hit && c.firstHit) {
						(c).firstHit = false;

					}

					if ((c).hit) {
						((c)).hurtDelay++;
						if (((c)).hurtDelay % 10 == 0) {
							((c)).health -= hurtAmt;
							(c).hit = false;
						}
					}

				} else if (e instanceof BoxBird) {
					BoxBird b = (BoxBird) e;
					// System.out.println("BoxBird Health: " + b.health);
					if ((b).checkHit(xa, 0f) && pr.checkCollider(xa, 0f)) {
						Sound.bird.play();
						(b).firstHit = true;
						(b).hit = true;
						(b).hurtDelay = 0;
						Particle part = new Particle(hand, (int) pr.x, (int) pr.y, 100, 10, Color.WHITE);
						hand.getMap().getEntitySys().addParticle(part);
						part.setT(0);
						part.setPartSpeed(0);

						pr.remove();
					}
					if ((b).checkHit(0f, ya) && pr.checkCollider(0f, ya)) {
						Sound.bird.play();
						(b).firstHit = true;
						(b).hit = true;
						(b).hurtDelay = 0;
						Particle part = new Particle(hand, (int) pr.x, (int) pr.y, 100, 10, Color.WHITE);
						hand.getMap().getEntitySys().addParticle(part);
						part.setT(0);
						part.setPartSpeed(0);

						pr.remove();
					}

					if ((b).hit && b.firstHit) {

						(b).firstHit = false;

					}

					if ((b).hit) {
						(b).hurtDelay++;
						if (((b)).hurtDelay % 10 == 0) {
							((b)).health -= hurtAmt;
							(b).hit = false;
						}
					}

				} else if (e instanceof SadGel) {
					SadGel s = (SadGel) e;
					// System.out.println("SadGel Health: " + (s).health);
					if ((s).checkHit(xa, 0f) && pr.checkCollider(xa, 0f)) {
						Sound.sadgel.play();
						(s).firstHit = true;
						(s).hit = true;
						(s).hurtDelay = 0;
						Particle part = new Particle(hand, (int) pr.x, (int) pr.y, 100, 10, Color.CYAN);
						hand.getMap().getEntitySys().addParticle(part);
						part.setT(0);
						part.setPartSpeed(0);

						pr.remove();
					}
					if ((s).checkHit(0f, ya) && pr.checkCollider(0f, ya)) {
						Sound.sadgel.play();
						(s).firstHit = true;
						(s).hit = true;
						(s).hurtDelay = 0;
						Particle part = new Particle(hand, (int) pr.x, (int) pr.y, 100, 10, Color.CYAN);
						hand.getMap().getEntitySys().addParticle(part);
						part.setT(0);
						part.setPartSpeed(0);

						pr.remove();
					}

					if ((s).hit && (s).firstHit) {
						(s).firstHit = false;
					}

					if ((s).hit) {
						((s)).hurtDelay++;
						if ((s).hurtDelay % 10 == 0) {
							(s).health -= hurtAmt;
							(s).hit = false;
						}
					}
				}
				if (e instanceof DropGel) {
					DropGel d = (DropGel) e;
					// System.out.println("CubeGel Health: " + c.health);

					if ((d).checkHit(xa, 0f) && pr.checkCollider(xa, 0f)) {
						Sound.cubegel.play();
						(d).firstHit = true;
						(d).hit = true;
						(d).hurtDelay = 0;
						Particle part = new Particle(hand, (int) pr.x, (int) pr.y, 100, 5, Color.GRAY);
						hand.getMap().getEntitySys().addParticle(part);
						part.setT(0);
						part.setPartSpeed(0);
						(d).health -= hurtAmt;

						pr.remove();
					}
					if ((d).checkHit(0f, ya) && pr.checkCollider(0f, ya)) {
						Sound.cubegel.play();
						(d).firstHit = true;
						((d)).hit = true;
						((d)).hurtDelay = 0;
						Particle part = new Particle(hand, (int) pr.x, (int) pr.y, 100, 5, Color.GRAY);
						hand.getMap().getEntitySys().addParticle(part);
						part.setT(0);
						part.setPartSpeed(0);

						pr.remove();
					}

					if (((d)).hit && d.firstHit) {
						(d).firstHit = false;

					}

					if ((d).hit) {
						((d)).hurtDelay++;
						if (((d)).hurtDelay % 60 == 0 && (d).health >= 999) {
							((d)).health -= hurtAmt;
							(d).hit = false;
						}
					}

				}
				if (e instanceof AngryGel) {
					AngryGel a = (AngryGel) e;
					// System.out.println("CubeGel Health: " + c.health);

					if ((a).checkHit(xa, 0f) && pr.checkCollider(xa, 0f)) {
						Sound.sadgel.play();
						(a).firstHit = true;
						(a).hit = true;
						(a).hurtDelay = 0;
						Particle part = new Particle(hand, (int) pr.x, (int) pr.y, 100, 5, Color.red);
						hand.getMap().getEntitySys().addParticle(part);
						part.setT(0);
						part.setPartSpeed(0);

						pr.remove();
					}
					if ((a).checkHit(0f, ya) && pr.checkCollider(0f, ya)) {
						Sound.sadgel.play();
						(a).firstHit = true;
						((a)).hit = true;
						((a)).hurtDelay = 0;
						Particle part = new Particle(hand, (int) pr.x, (int) pr.y, 100, 5, Color.red);
						hand.getMap().getEntitySys().addParticle(part);
						part.setT(0);
						part.setPartSpeed(0);

						pr.remove();
					}

					if (((a)).hit && a.firstHit) {
						(a).firstHit = false;

					}

					if ((a).hit) {
						((a)).hurtDelay++;
						if (((a)).hurtDelay % 60 == 0) {
							((a)).health -= hurtAmt;
							(a).hit = false;
						}
					}

				}
				if (e instanceof BlackBird) {
					BlackBird bb = (BlackBird) e;
					// System.out.println("CubeGel Health: " + c.health);

					if ((bb).checkHit(xa, 0f) && pr.checkCollider(xa, 0f)) {
						Sound.cubegel.play();
						(bb).firstHit = true;
						(bb).hit = true;
						(bb).hurtDelay = 0;
						Particle part = new Particle(hand, (int) pr.x, (int) pr.y, 100, 5, Color.DARK_GRAY);
						hand.getMap().getEntitySys().addParticle(part);
						part.setT(0);
						part.setPartSpeed(0);

						pr.remove();
					}
					if ((bb).checkHit(0f, ya) && pr.checkCollider(0f, ya)) {
						Sound.cubegel.play();
						(bb).firstHit = true;
						((bb)).hit = true;
						((bb)).hurtDelay = 0;
						Particle part = new Particle(hand, (int) pr.x, (int) pr.y, 100, 5, Color.DARK_GRAY);
						hand.getMap().getEntitySys().addParticle(part);
						part.setT(0);
						part.setPartSpeed(0);

						pr.remove();
					}

					if (((bb)).hit && bb.firstHit) {
						(bb).firstHit = false;

					}

					if ((bb).hit) {
						((bb)).hurtDelay++;
						if (((bb)).hurtDelay % 60 == 0) {
							((bb)).health -= hurtAmt;
							(bb).hit = false;
						}
					}

				}
				
				
				
				
				EntitySystem es = hand.getMap().getEntitySys();
				if (e instanceof CubeGel) {
					CubeGel c = (CubeGel) e;
					if ((c).health <= 0) {
						chanceDrop++;
						if (!hand.getPlayer().aCoinPickup || !hand.getPlayer().firstTime) {
							if (chanceDrop % 1 == 0) {
								hand.getMap().getIs().addItem(Item.aCoin.createItem((int) c.x, (int) c.y));
							}
						}
						es.remove(c);
					}
				}
				if (e instanceof BoxBird) {
					BoxBird b = (BoxBird) e;
					if ((b).health <= 0) {
						chanceDrop++;
						if (!hand.getPlayer().aCoinPickup || !hand.getPlayer().firstTime) {
							if (chanceDrop % 1 == 0) {
								hand.getMap().getIs().addItem(Item.aCoin.createItem((int) b.x, (int) b.y));
							}
						}
						es.remove(b);
					}
				}
				if (e instanceof SadGel) {
					SadGel s = (SadGel) e;
					if ((s).health <= 0) {
						chanceDrop++;
						if (!hand.getPlayer().aCoinPickup || !hand.getPlayer().firstTime) {
							if (chanceDrop % 1 == 0) {
								hand.getMap().getIs().addItem(Item.aCoin.createItem((int) s.x, (int) s.y));
							}
						}
						es.remove(s);
					}
				}
				if (e instanceof DropGel) {
					DropGel d = (DropGel) e;
					if ((d).health <= 0) {
						chanceDrop++;
						if (!hand.getPlayer().aCoinPickup || !hand.getPlayer().firstTime) {
							if (chanceDrop % 1 == 0) {
								hand.getMap().getIs().addItem(Item.aCoin.createItem((int) d.x, (int) d.y));
							}
						}
						es.remove(d);
					}
				}
				if (e instanceof AngryGel) {
					AngryGel a = (AngryGel) e;
					if ((a).health <= 0) {
						chanceDrop++;
						if (!hand.getPlayer().aCoinPickup || !hand.getPlayer().firstTime) {
							if (chanceDrop % 1 == 0) {
								hand.getMap().getIs().addItem(Item.aCoin.createItem((int) a.x, (int) a.y));
							}
						}
						es.remove(a);
					}
				}
				if (e instanceof BlackBird) {
					BlackBird bb = (BlackBird) e;
					if ((bb).health <= 0) {
						chanceDrop++;
						if (!hand.getPlayer().aCoinPickup || !hand.getPlayer().firstTime) {
							if (chanceDrop % 1 == 0) {
								hand.getMap().getIs().addItem(Item.aCoin.createItem((int) bb.x, (int) bb.y));
							}
						}
						es.remove(bb);
					}
				}

			}
		}

	}

	protected void checkDead() {

	}

	protected boolean isCollideTile(int x, int y) {
		if (x < 0 || x >= hand.getMap().getWidth() / Assets.TILESIZE || y < 0 || y >= hand.getMap().getHeight())
			return false;
		return hand.getMap().getTile(x, y).isSolid();

	}

	protected boolean isHarmTile(int x, int y) {
		if (x < 0 || x >= hand.getMap().getWidth() * hand.getK()

				|| y < 0 || y >= hand.getMap().getHeight())
			return false;
		return hand.getMap().getTile(x, y).isHarmful();
	}

	public float getXa() {
		return xa;
	}

	public void setXa(float xa) {
		this.xa = xa;
	}

	public float getYa() {
		return ya;
	}

	public void setYa(float ya) {
		this.ya = ya;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

}
