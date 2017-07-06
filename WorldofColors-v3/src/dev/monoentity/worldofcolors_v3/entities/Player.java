package dev.monoentity.worldofcolors_v3.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import dev.monoentity.worldofcolors_v3.Handy;
import dev.monoentity.worldofcolors_v3.gfx.Assets;
import dev.monoentity.worldofcolors_v3.input.KeyIn;
import dev.monoentity.worldofcolors_v3.items.Item;
import dev.monoentity.worldofcolors_v3.sfx.Sound;
import dev.monoentity.worldofcolors_v3.states.ShopState;
import dev.monoentity.worldofcolors_v3.states.State;
import dev.monoentity.worldofcolors_v3.tiles.Tile;

public class Player extends Moving {

	public final int MAXHEALTH = 3;
	public final int DEFAULTJUMP = -11;
	public boolean END = false;
	public boolean CLEAR = false;
	public static int spawnX = 220, spawnY = 322;
	private BufferedImage currentSprite;
	private KeyIn ki;

	private boolean jumping = false, hurt = false, firstHit = false;
	private int anim;
	private int t;
	private double v0;
	private int hurtDelay;
	private int fireDelay;
	public boolean firstTime = true;
	public boolean firstPickup = false;
	public boolean cpickup = false, apickup = false, spickup = false;
	public int pickupCooldown;
	public int ccoins;
	public int acoins;
	public boolean aCoinPickup = false;
	public boolean cCoinPickup = false;

	public Player(Handy hand, float x, float y, KeyIn keyIn) {
		super(hand, x, y, Assets.TILESIZE);
		ki = keyIn;
		currentSprite = Assets.pR2;

		anim = 0;
		t = 0;
		fireDelay = 0;
		hurtDelay = 0;
		pickupCooldown = 0;
		ccoins = 0;
		acoins = 0;

		rbounds.x = 18;
		rbounds.y = 4;
		rbounds.width = 36;
		rbounds.height = 56;

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
		v0 = DEFAULTJUMP;
		gravity = 0.2f;

		health = MAXHEALTH;
		fireDelay = ProjectileP.FIREDELAY;
		grounded = true;
	}

	@Override
	public void update() {

		// System.out.println("firstTime: " + firstTime);
		t++;
		if (t > 1000) {
			t = 0;
		}

		if (!firstTime && t % 360 == 0) {
			xa += 0.1f;
		}

		checkInput();
		updateAnimation();
		updatePickUp();
		checkAction();
		checkHurt();
		checkWin();
		checkLose();

		move(xa, ya);
	}

	public void updatePickUp() {
		// System.out.println(ccoins);
		for (int i = 0; i < hand.getMap().getIs().getItems().size(); i++) {
			Item item = hand.getMap().getIs().getItems().get(i);
			if (item.getName() == "CCoin") {
				if (item.getColliderE().intersects(getColliderR(xa, 0f))) {
					firstPickup = true;
					cpickup = true;
					pickupCooldown = 0;
				}
				if (item.getColliderE().intersects(getColliderR(0f, ya))) {
					firstPickup = true;
					cpickup = true;
					pickupCooldown = 0;

				}

				if (cpickup && firstPickup) {
					firstPickup = false;
					cCoinPickup = true;
					Sound.coin.play();
					hand.getMap().getIs().removeItem(item);
					ccoins++;
				}
				if (cpickup) {
					pickupCooldown++;
					if (pickupCooldown % 60 == 0) {
						cpickup = false;
					}
				}
			}
			if (item.getName() == "ACoin") {
				if (item.getColliderE().intersects(getColliderR(xa, 0f))) {
					firstPickup = true;
					apickup = true;
					pickupCooldown = 0;
				}
				if (item.getColliderE().intersects(getColliderR(0f, ya))) {
					firstPickup = true;
					apickup = true;
					pickupCooldown = 0;

				}

				if (apickup && firstPickup) {
					firstPickup = false;
					aCoinPickup = true;
					Sound.coin.play();
					hand.getMap().getIs().removeItem(item);
					acoins++;
				}
				if (apickup) {
					pickupCooldown++;
					if (pickupCooldown % 60 == 0) {
						apickup = false;
					}
				}
			}

			if (item.getName() == "Cold Stop" && item.getName() != "CCoin" && item.getName() != "ACoin") {
				if (item.getColliderE().intersects(getColliderR(xa, 0f))) {
					spickup = true;
					pickupCooldown = 0;
				}
				if (item.getColliderE().intersects(getColliderR(0f, ya))) {
					spickup = true;
					pickupCooldown = 0;

				}

				if (spickup && !frozen && hand.getGame().getShop().isFREEZESKILL()) {
					hand.getMap().getIs().removeItem(item);
					frozen = true;
					freezeTime = 0;
					freezeChance++;
				}
				if (spickup) {
					pickupCooldown++;
					if (pickupCooldown % 60 == 0) {
						spickup = false;
					}
				}
			}
		}
	}

	

	@Override
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

					x = (float) (tx * Assets.TILESIZE - rbounds.x - rbounds.width - 1);
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
					if (hand.getMap().getTile((int) (x + boundsBottom.x) / Assets.TILESIZE, ty).equals(Tile.pink)) {
						v0 = DEFAULTJUMP - hand.getGame().getShop().getJumpUp() - 2;
						jump();
						Sound.jump.play();
						jumping = true;
						grounded = false;
					} else {
						v0 = DEFAULTJUMP - hand.getGame().getShop().getJumpUp();
						y = (float) (ty * Assets.TILESIZE - boundsBottom.height - boundsBottom.y - 1);
						grounded = true;
					}
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
			jumping = false;
		}
		if (checkCollider(0f, ya) && ya < 0) {

			int ty = (int) Math.round(y + ya + rbounds.y) / Assets.TILESIZE;
			hitHead = true;
			y = y + 1;
		}
		int ty = (int) Math.round(y + ya + rbounds.y + rbounds.height) / Assets.TILESIZE;
		if ((checkCollider(0f, ya) && ya >= 0) || isCollideTile((int) (x + boundsBottom.x) / Assets.TILESIZE, ty)
				&& !isCollideTile((int) (x + boundsBottom.x + boundsBottom.width) / Assets.TILESIZE, ty)) {

			y = y - 1;
			grounded = true;
		} else if (!isCollideTile((int) (x + boundsBottom.x) / Assets.TILESIZE, ty)
				&& !isCollideTile((int) (x + boundsBottom.x + boundsBottom.width) / Assets.TILESIZE, ty)) {
			grounded = false;
		}
		if (checkCollider(xa, 0f) && xa >= 0) {

			x = (float) x - 0.1f;
			xa = 0;
		}
	}

	private boolean frozen = false;
	private int freezeTime = 0;
	private int freezeChance = 0;

	private void checkAction() {
		System.out.println("freezecount: " + freezeChance);
		freezeTime++;
		if (freezeTime > 1000) {
			freezeTime = 1000;
		}
		if (frozen && freezeTime > hand.getGame().getShop().getFreezeDuration()) {
			frozen = false;
		}
		// System.out.println("grounded: " + grounded);
		if (fireDelay > 0) {
			fireDelay--;
		}
		for (int i = 0; i < hand.getMap().getEntitySys().getProjectiles().size(); i++) {
			Projectile pr = hand.getMap().getEntitySys().getProjectiles().get(i);
			if (pr.removed) {
				hand.getMap().getEntitySys().removeProjectile(pr);
			}
		}
		if (jumping) {
			gravity = 0.3f;
			ya += gravity;
			currentSprite = Assets.pJump;
		}
		if (grounded) {
			ya = 0;
			gravity = 0;
			jumping = false;
		}
		if (!grounded) {
			gravity = 0.3f;
			ya += gravity;
		}
		if (hitHead) {
			ya += gravity;
			gravity = 0.4f;
		}
		if (!hitHead) {
			gravity = 0.3f;
		}
	}

	private void checkInput() {
		/*
		 * if(ki.skill1 && !frozen && hand.getGame().getShop().isFREEZESKILL()
		 * && freezeChance < hand.getGame().getShop().getFreezeChance()){ frozen
		 * = true; freezeTime = 0; freezeChance++; }
		 */

		if (ki.shoot && fireDelay <= 0) {
			shoot(x + (s / 2), y + (s / 2) + 7);
			fireDelay = ProjectileP.FIREDELAY - hand.getGame().getShop().getFireRateUp();
		} else if (ki.duck) {
			currentSprite = Assets.pDuck;
			duck();
		} else if (!jumping && ki.jump) {
			jumping = true;
			grounded = false;
			walking = false;
			jump();
		} else {
			walking = true;
			boundsTop.y = 5;
			boundsTop.height = 12;
			rbounds.y = 4;
			rbounds.height = 56;
		}
	}

	private void jump() {

		ya = (float) v0;

	}

	private void duck() {
		walking = false;
		boundsTop.y = 18;
		boundsTop.height = 12;
		rbounds.y = 18;
		rbounds.height = 42;
	}

	@Override
	protected void checkHurt() {
		// System.out.println("PlayerLife: " + health);
		int tx = (int) Math.round(x + xa + boundsRight.x + boundsRight.width) / Assets.TILESIZE;
		if (isHarmTile(tx, (int) (y + boundsRight.y) / Assets.TILESIZE)
				&& isHarmTile(tx, (int) (y + boundsRight.y + boundsRight.height) / Assets.TILESIZE) && !hurt) {

			firstHit = true;
			hurt = true;
			hurtDelay = 0;
		}
		if ((isHarmBullet(xa, 0f) || isHarmBullet(0f, ya)) && !hurt) {

			firstHit = true;
			hurt = true;
			hurtDelay = 0;
		}

		for (int i = 0; i < hand.getMap().getEntitySys().getEntities().size(); i++) {
			Entity e = hand.getMap().getEntitySys().getEntities().get(i);
			if (e instanceof MiniUnknown) {
				MiniUnknown mu = (MiniUnknown) e;
				if (getColliderR(0f, 0f).intersects(mu.getColliderR(xa, ya))) {
					health -= 100;
				}
			}
			if (e instanceof DropGel) {
				DropGel d = (DropGel) e;
				if (getColliderR(0f, 0f).intersects(d.getColliderR(xa, ya))
						&& d.getCurrentSprite() == Assets.dropLeft[0]) {

					firstHit = true;
					hurt = true;
					hurtDelay = 0;
				}
			}
		}

		if (hurt && firstHit) {
			Sound.hurt.play();
			firstHit = false;
			health -= 1;
			Particle part = new Particle(hand, (int) x + (s / 2), (int) y + (s / 2), 100, 30, Color.red);
			hand.getMap().getEntitySys().addParticle(part);
			part.setT(0);
			part.setPartSpeed(0);
		}
		if (hurt) {
			hurtDelay++;
			if (hurtDelay % 60 == 0) {
				hurt = false;
			}
		}

		// System.out.println(health);
	}

	public boolean isHarmBullet(float xs, float ys) {
		for (Projectile pr : hand.getMap().getEntitySys().getProjectiles()) {
			if (pr instanceof ProjectileO) {
				if (pr.getColliderR(0f, 0f).intersects(this.getColliderR(xs, ys))) {
					return true;
				}
			}
		}
		return false;
	}

	private void checkLose() {

		for (int i = 0; i < hand.getMap().getEntitySys().getEntities().size(); i++) {
			Entity e = hand.getMap().getEntitySys().getEntities().get(i);

			if (e instanceof Unknown) {
				if (x <= hand.getMShift().getxShift()) {
					if (t % 30 == 0) {

					}
				}
				if (x < e.getX()) {
					Sound.shop.loop();
					Sound.lose.play();
					State.setState(hand.getGame().shopState);
					hand.getGame().getShop().setCurrentDialogue("Welcome to the Shop!");
					x = spawnX;
					hand.getMShift().setxShift(0);

					if ((ShopState) State.getState() instanceof ShopState) {
						((ShopState) State.getState()).GAMEON = false;
						((ShopState) State.getState()).SHOP = true;
					}

				}
			}

		}
		if (health <= 0) {
			Sound.shop.loop();
			State.setState(hand.getGame().shopState);
			hand.getGame().getShop().setCurrentDialogue("Welcome to the Shop!");
			if ((ShopState) State.getState() instanceof ShopState) {
				((ShopState) State.getState()).GAMEON = false;
				((ShopState) State.getState()).SHOP = true;

			}
			x = spawnX;

			hand.getMShift().setxShift(0);
		}
		// System.out.println(END);
		// System.out.println("PlayerX: " + x);
		// System.out.println("UnknownX: " +
		// hand.getMap().getEntitySys().getEntities().get(2).getX());
	}

	private void checkWin() {
		if ((x + s) >= hand.getMap().getWidth() - ((Assets.TILESIZE) * 2)) {
			Sound.clear.play();
			CLEAR = true;
			firstTime = false;
			t = 0;
			xa = 2.5f;
		}
		// System.out.println("Clear: " + CLEAR);
	}

	private void updateAnimation() {
		if (anim < 5000)
			anim++;
		else
			anim = 0;

		if (walking) {
			if (anim % 90 > 80) {
				currentSprite = Assets.pR2;
			} else if (anim % 90 > 60)
				currentSprite = Assets.pR3;
			else if (anim % 90 > 40)
				currentSprite = Assets.pR2;
			else if (anim % 90 > 20)
				currentSprite = Assets.pR1;
			else {
				currentSprite = Assets.pR2;
			}
		}

	}

	private int leafX = 0, leafY = 0;

	@Override
	public void render(Graphics g) {
		g.drawImage(currentSprite, (int) (x - hand.getMShift().getxShift()), (int) (y - hand.getMShift().getyShift()),
				s, s, null);
		if (hand.getGame().getShop().isLEAFSET()) {
			if (currentSprite == Assets.pDuck) {
				leafX = 25;
				leafY = 4;
				
			} else {
				leafX = 25;
				leafY= -6;
				
			}
			g.drawImage(Assets.leaf, (int) (x - hand.getMShift().getxShift()) + leafX,
					(int) (y - hand.getMShift().getyShift()) + leafY, 32, 32, null);
		}
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
		 * g.setColor(Color.BLACK); g.fillRect((int) (rbounds.x + x -
		 * hand.getMShift().getxShift()), (int) (rbounds.y + y -
		 * hand.getMShift().getyShift()), (int) rbounds.width, (int)
		 * rbounds.height);
		 */

	}

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public int getCcoins() {
		return ccoins;
	}

	public void subtractCcoins(int cost) {
		ccoins -= cost;
	}

	public void subtractAcoins(int cost){
		acoins -= cost;
	}
	
	public int getAcoins() {
		return acoins;
	}

	public void setAcoins(int acoins) {
		this.acoins = acoins;
	}

	public void setV0(double newV) {
		v0 = newV;
	}

	public boolean isFrozen() {
		return frozen;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	public int getFreezeCount() {
		return freezeChance;
	}

	public void setFreezeCount(int freezeCount) {
		this.freezeChance = freezeCount;
	}

}
