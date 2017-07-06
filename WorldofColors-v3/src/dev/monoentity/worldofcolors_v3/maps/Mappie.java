package dev.monoentity.worldofcolors_v3.maps;

import java.awt.Graphics;
import java.util.Random;

import dev.monoentity.worldofcolors_v3.Handy;
import dev.monoentity.worldofcolors_v3.entities.AngryGel;
import dev.monoentity.worldofcolors_v3.entities.BlackBird;
import dev.monoentity.worldofcolors_v3.entities.BoxBird;
import dev.monoentity.worldofcolors_v3.entities.CubeGel;
import dev.monoentity.worldofcolors_v3.entities.DropGel;
import dev.monoentity.worldofcolors_v3.entities.Entity;
import dev.monoentity.worldofcolors_v3.entities.EntitySystem;
import dev.monoentity.worldofcolors_v3.entities.MiniUnknown;
import dev.monoentity.worldofcolors_v3.entities.Player;
import dev.monoentity.worldofcolors_v3.entities.ProjectileO;
import dev.monoentity.worldofcolors_v3.entities.SadGel;
import dev.monoentity.worldofcolors_v3.entities.Unknown;
import dev.monoentity.worldofcolors_v3.gfx.Assets;
import dev.monoentity.worldofcolors_v3.input.KeyIn;
import dev.monoentity.worldofcolors_v3.items.Item;
import dev.monoentity.worldofcolors_v3.items.ItemSystem;
import dev.monoentity.worldofcolors_v3.maps.utils.Utility;
import dev.monoentity.worldofcolors_v3.states.GameState;
import dev.monoentity.worldofcolors_v3.states.State;
import dev.monoentity.worldofcolors_v3.tiles.Tile;

public class Mappie {

	protected Handy hand;
	protected KeyIn keyIn;
	protected int width, height;
	protected int spawnX, spawnY;
	protected int[] tiles;
	protected int[][] tiler;
	private Random rand = new Random();
	private int spawnTimer = 0;

	private int time = 0;
	private int change = 0;

	private Random r = new Random();

	private EntitySystem es;
	private ItemSystem is;

	public boolean isCollideTile(int x, int y) {
		if (x < 0 || x >= 1920 || y < 0 || y >= 1920)
			return false;
		return hand.getMap().getTile(x, y).isSolid();

	}

	private void loadMap(String path) {
		String file = Utility.loadFileAsString(path);

		String[] data = file.split("\\s+");

		width = Utility.parseInt(data[0]);
		height = Utility.parseInt(data[1]);

		tiler = new int[width][height];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiler[x][y] = Utility.parseInt(data[(x + y * width) + 2]);
			}
		}
	}

	public Mappie(Handy hand, int w, int h) {
		this.hand = hand;
		es = new EntitySystem(hand, new Unknown(hand, Player.spawnX - 285, Player.spawnY));
		is = new ItemSystem(hand);
		width = w;
		height = h;
		tiler = new int[w][h];
		// es.add(new CubeGel(hand, 1000, 240));
		// es.add(new CubeGel(hand, 1100, 240));
		// es.add(new CubeGel(hand, 1200, 240));
		int maxX = getWidth() - Assets.TILESIZE - 600;
		int maxY = getHeight() - (Assets.TILESIZE * 6);
		if (!hand.getGame().isLVL4()) {
			spawnX = rand.nextInt(maxX) + 500;
			spawnY = rand.nextInt(maxY) + Assets.TILESIZE * 2;
			es.add(new CubeGel(hand, spawnX, spawnY));
			spawnX = rand.nextInt(maxX) + 500;
			spawnY = rand.nextInt(maxY) + Assets.TILESIZE * 2;
			es.add(new SadGel(hand, spawnX, spawnY));
		}
		if (!hand.getGame().isLVL3()) {
			spawnX = rand.nextInt(maxX) + 500;
			spawnY = rand.nextInt(maxY) + Assets.TILESIZE * 2;
			es.add(new BoxBird(hand, spawnX, spawnY));
		}
		spawnX = rand.nextInt(maxX) + 500;
		spawnY = rand.nextInt(maxY) + Assets.TILESIZE * 3;
		es.addProjectile(new ProjectileO(hand, spawnX, spawnY));
		// es.add(new BlackBird(hand, 1200, 230));
		// es.add(new AngryGel(hand, 1400, 230));
		// es.add(new DropGel(hand, 700, 190));
		/*
		 * if (hand.getPlayer().firstTime && !hand.getPlayer().aCoinPickup) {
		 * spawnX = rand.nextInt(maxX) + 500; spawnY = rand.nextInt(maxY) +
		 * Assets.TILESIZE * 2; is.addItem(Item.aCoin.createItem(spawnX,
		 * spawnY)); }
		 */

		if (hand.getPlayer().firstTime && !hand.getPlayer().cCoinPickup) {
			spawnX = rand.nextInt(maxX) + 500;
			spawnY = rand.nextInt(maxY) + Assets.TILESIZE * 2;
			is.addItem(Item.cCoin.createItem(spawnX, spawnY));
		}
		genMap();

	}

	public Mappie(Handy hand, int w, int h, boolean shop) {
		this.hand = hand;
		es = new EntitySystem(hand, new Unknown(hand, -300, Player.spawnY));
		is = new ItemSystem(hand);
		width = w;
		height = h;
		tiler = new int[w][h];
		genMap();

	}

	private void time() {

	}

	public void update() {
		// System.out.println("ProjectileSpawn: " + spawnX + ", " + spawnY);
		time++;
		if (time > 10000) {
			time = 0;
		}

		int x0 = (int) Math.max(0, (hand.getMShift().getxShift()) / Assets.TILESIZE);
		int xf = (int) Math.min(width, (hand.getMShift().getxShift() + hand.getWidth()) / Assets.TILESIZE + 1);
		int y0 = (int) Math.max(0, (hand.getMShift().getyShift()) / Assets.TILESIZE);
		int yf = (int) Math.min(height, (hand.getMShift().getyShift() + hand.getHeight()) / Assets.TILESIZE + 1);

		if (State.getState() instanceof GameState && !hand.getPlayer().firstTime) {
			/*
			 * if(time % 600 == 0){ spawnX = rand.nextInt(getWidth() -
			 * Assets.TILESIZE*2) + (int) (hand.getPlayer().getX() +
			 * hand.getWidth()); spawnY = rand.nextInt(getHeight() -
			 * (Assets.TILESIZE * 6)) + Assets.TILESIZE * 2;
			 * //is.addItem(Item.aCoin.createItem(spawnX, spawnY)); }
			 */
			int maxX = hand.getMap().getWidth() - (Assets.TILESIZE * 3);
			int maxY = hand.getMap().getHeight() - (Assets.TILESIZE * 6);
			if (time % (420 - hand.getGame().getShop().getCoinUp()) == 0) {
				spawnX = rand.nextInt(hand.getWidth()) + (int) (hand.getPlayer().getX() + hand.getWidth());
				spawnY = rand.nextInt(maxY) + Assets.TILESIZE * 2;
				if (rand.nextInt(hand.getWidth())
						+ (int) (hand.getPlayer().getX() + hand.getWidth()) <= (hand.getMap().getWidth()
								- (Assets.TILESIZE * 4)))
					is.addItem(Item.cCoin.createItem(spawnX, spawnY));
			}

			if (hand.getGame().getShop().isFREEZESKILL()) {
				if (time % (420 - hand.getGame().getShop().getFreezeChance()) == 0) {
					spawnX = rand.nextInt(hand.getWidth()) + (int) (hand.getPlayer().getX() + hand.getWidth());
					spawnY = rand.nextInt(maxY) + Assets.TILESIZE * 2;
					if (rand.nextInt(hand.getWidth())
							+ (int) (hand.getPlayer().getX() + hand.getWidth()) <= (hand.getMap().getWidth()
									- (Assets.TILESIZE * 4)))
						is.addItem(Item.powerUp1.createItem(spawnX, spawnY));
				}
			}

			if (time % 300 == 0) {
				spawnX = rand.nextInt(getWidth() - Assets.TILESIZE * 2)
						+ (int) (hand.getPlayer().getX() + hand.getWidth());
				spawnY = rand.nextInt(maxY) + Assets.TILESIZE * 3;
				es.addProjectile(new ProjectileO(hand, spawnX, spawnY));
			}

			if (!hand.getGame().isLVL3()) {
				if (time % 320 == 0) {
					spawnX = rand.nextInt(maxX) + (int) (hand.getPlayer().getX() + hand.getWidth());
					spawnY = rand.nextInt(maxY) + Assets.TILESIZE * 2;
					es.add(new BoxBird(hand, spawnX, spawnY));
				}
			} else {
				if (time % 320 == 0) {
					spawnX = rand.nextInt(maxX) + (int) (hand.getPlayer().getX() + hand.getWidth());
					spawnY = rand.nextInt(maxY) + Assets.TILESIZE * 2;
					es.add(new BlackBird(hand, spawnX, spawnY));
				}
			}

			if (!hand.getGame().isLVL4()) {
				if (time % 300 == 0) {
					spawnX = rand.nextInt(maxX) + (int) (hand.getPlayer().getX() + hand.getWidth());
					spawnY = rand.nextInt(maxY) + Assets.TILESIZE * 2;
					es.add(new CubeGel(hand, spawnX, spawnY));
				}

				if (time % 360 == 0) {
					spawnX = rand.nextInt(maxX) + (int) (hand.getPlayer().getX() + hand.getWidth());
					spawnY = rand.nextInt(maxY) + Assets.TILESIZE * 2;
					es.add(new SadGel(hand, spawnX, spawnY));
				}
			} else {
				if (time % 300 == 0) {
					spawnX = rand.nextInt(maxX) + (int) (hand.getPlayer().getX() + hand.getWidth());
					spawnY = rand.nextInt(maxY) + Assets.TILESIZE * 2;
					es.add(new AngryGel(hand, spawnX, spawnY));
				}

				if (time % 360 == 0) {
					spawnX = rand.nextInt(maxX) + (int) (hand.getPlayer().getX() + hand.getWidth());
					spawnY = rand.nextInt(maxY) + Assets.TILESIZE * 2;
					es.add(new DropGel(hand, spawnX, spawnY));
				}
			}

			if (hand.getGame().isLVL5()) {
				if (time % 360 == 0) {
					spawnX = rand.nextInt(maxX) + (int) (hand.getPlayer().getX() + hand.getWidth());
					spawnY = rand.nextInt(maxY) + Assets.TILESIZE * 2;
					es.add(new MiniUnknown(hand, spawnX, spawnY, 32));
				}
			}
		}
		for (int i = 0; i < es.getEntities().size(); i++) {
			Entity e = es.getEntities().get(i);
			if (e instanceof Unknown) {
				((Unknown) e).setXa(hand.getPlayer().getXa());
			}
			// System.out.println("Unknown: " + es.getEntities().get(i).getX());

		}

		// System.out.println(change);
		es.update();
		is.update();

		// System.out.println(((Moving) es.getEntities().get(i)).getX());
	}

	public void render(Graphics g) {

		int x0 = (int) Math.max(0, (hand.getMShift().getxShift()) / Assets.TILESIZE);
		int xf = (int) Math.min(width, (hand.getMShift().getxShift() + hand.getWidth()) / Assets.TILESIZE + 1);
		int y0 = (int) Math.max(0, (hand.getMShift().getyShift()) / Assets.TILESIZE);
		int yf = (int) Math.min(height, (hand.getMShift().getyShift() + hand.getHeight()) / Assets.TILESIZE + 1);
		for (int y = y0; y < yf; y++) {
			for (int x = x0; x < xf; x++) {
				getTile(x, y).render(g, (int) (x * Assets.TILESIZE - hand.getMShift().getxShift()),
						(int) (y * Assets.TILESIZE - hand.getMShift().getyShift()));

			}
		}
		is.render(g);
		es.render(g);
	}

	protected void genMap() {
		for (int y = 0; y < height - 2; y++) {
			for (int x = 0; x < 9; x++) {
				tiler[x][y] = 0;
			}
		}
		for (int y = 5; y < height - 2; y++) {
			for (int x = 9; x < width - 3; x++) {
				tiler[x][y] = rand.nextInt(10);
			}
		}

		for (int y = 0; y < height - 2; y++) {
			for (int x = width - 3; x < width - 2; x++) {
				tiler[x][y] = 10;
			}
		}

		for (int y = 0; y < height - 2; y++) {
			for (int x = width - 2; x < width; x++) {
				tiler[x][y] = 0;
			}
		}

		for (int y = 6; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiler[x][y] = 3;
			}
		}
	}

	public Tile getTile(int x, int y) {
		Tile t = Tile.tiles[tiler[x][y]];
		if (x < 0 || y < 0 || x >= width || y >= height)
			t = null;

		if (t == null)
			return Tile.empty;
		return t;
	}

	public EntitySystem getEntitySys() {
		return es;
	}

	public int getWidth() {
		return width * Assets.TILESIZE;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height * Assets.TILESIZE;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public ItemSystem getIs() {
		return is;
	}

	public void setIs(ItemSystem is) {
		this.is = is;
	}

	public Handy getHand() {
		return hand;
	}

	public void setHand(Handy hand) {
		this.hand = hand;
	}

}
