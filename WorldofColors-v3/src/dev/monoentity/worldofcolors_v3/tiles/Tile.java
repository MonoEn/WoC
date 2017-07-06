package dev.monoentity.worldofcolors_v3.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.monoentity.worldofcolors_v3.TheGame;
import dev.monoentity.worldofcolors_v3.gfx.Assets;

public class Tile {

	protected BufferedImage tex;
	protected final int i;
	public BufferedImage currentSprite = Assets.pink;

	public static Tile[] tiles = new Tile[5000];
	public static Tile empty = new Empty(0);
	public static Tile empty2 = new Empty(1);
	public static Tile empty3 = new Empty(2);
	public static Tile yellow = new Yellow(3);
	public static Tile blue = new Blue(4);
	public static Tile red = new Red(5);
	public static Tile orange = new Orange(6);
	public static Tile green = new Green(7);
	public static Tile pink = new Pink(8); // breakable
	public static Tile spikes = new Spikes(9);
	public static Tile clear = new Complete(10);
	
	protected boolean opened = false;

	public Tile(BufferedImage tileSprite, int ti) {
		tex = tileSprite;
		i = ti;
		tiles[ti] = this;
	}

	public void update() {

	}

	public void render(Graphics g, int x, int y) {
		g.drawImage(tex, x, y, Assets.TILESIZE, Assets.TILESIZE, null);
	}

	public boolean isSolid() {
		return true;
	}

	public boolean isBreakable() {
		return false;
	}

	public boolean isHarmful() {
		return false;
	}

	public int getI() {
		return i;
	}

	public boolean isOpened() {
		return opened;

	}
	
	public void open(){
		opened = true;
	}

}
