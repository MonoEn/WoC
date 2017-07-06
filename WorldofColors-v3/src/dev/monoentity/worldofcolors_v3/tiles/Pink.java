package dev.monoentity.worldofcolors_v3.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.monoentity.worldofcolors_v3.gfx.Assets;

public class Pink extends Tile {
	
	public Pink(int ti) {
		super(Assets.pink, ti);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void render(Graphics g, int x, int y){
		g.drawImage(currentSprite, x, y, Assets.TILESIZE, Assets.TILESIZE, null);
	}
	
	@Override
	public boolean isBreakable(){
		return true;
	}
	
	
}
