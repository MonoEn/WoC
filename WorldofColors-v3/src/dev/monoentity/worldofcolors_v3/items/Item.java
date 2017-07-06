package dev.monoentity.worldofcolors_v3.items;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import dev.monoentity.worldofcolors_v3.Handy;
import dev.monoentity.worldofcolors_v3.gfx.Assets;

public class Item {

	public static final int PICKED_UP = -1;
	protected Handy hand;
	protected BufferedImage tex;
	protected String name;
	protected final int id;
	protected int s = Assets.TILESIZE;
	protected boolean removed = false;
	public static Item[] items = new Item[100];
	public static Item aCoin = new Item(Assets.aCoin, "ACoin", 0);
	public static Item cCoin = new Item(Assets.cCoin, "CCoin", 1);
	public static Item powerUp1 = new Item(Assets.stop, "Cold Stop", 2);
	public static Item powerUp3 = new Item(Assets.bomb, "Burst Bomb", 3);
	public static Item powerUp2 = new Item(Assets.evade, "Evasive Step", 4);
	protected Ellipse2D.Double bounds;
	
	protected int x, y, count;
	
	public Item(BufferedImage itemSprite, String itemName, int itemId){
		tex = itemSprite;
		name = itemName;
		id = itemId;
		count = 1;
		
		bounds = new Ellipse2D.Double(0, 0, Assets.TILESIZE, Assets.TILESIZE);
		
		bounds.x = 16;
		bounds.y = 16;
		bounds.width = 32;
		bounds.height = 32;
		
		items[itemId] = this;
	}
	
	public void update(){
		
	}
	
	public Ellipse2D.Double getColliderE() {
		return new Ellipse2D.Double((int) (x + bounds.x), (int) (y + bounds.y), (int) bounds.width,
				(int) bounds.height);
	}
	
	public void render(Graphics g){
		if(hand != null){
			render(g, (int)(x-hand.getMShift().getxShift()), (int)(y-hand.getMShift().getyShift()));
			//Graphics2D g2d = (Graphics2D) g;
			//g2d.draw(new Ellipse2D.Double((double)(x+bounds.x-hand.getMShift().getxShift()), (double)(y+bounds.y-hand.getMShift().getyShift()), (double)bounds.width, (double)bounds.height));
		}else
			return;
	}
	
	public void render(Graphics g, int x, int y){
		g.drawImage(tex, x, y, Assets.TILESIZE, Assets.TILESIZE, null);
	}
	
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Item createItem(int x, int y){
		Item i = new Item(tex, name, id);
		i.setLocation(x, y);
		return i;
	}
	
	public void remove() {
		removed = true;
	}

	public BufferedImage getTex() {
		return tex;
	}

	public void setTex(BufferedImage tex) {
		this.tex = tex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	public Handy getHand() {
		return hand;
	}

	public void setHand(Handy hand) {
		this.hand = hand;
	}

	public int getS() {
		return s;
	}

	public void setS(int s) {
		this.s = s;
	}

	public Ellipse2D.Double getBounds() {
		return bounds;
	}

	public void setBounds(Ellipse2D.Double bounds) {
		this.bounds = bounds;
	}
	
	
	
}
