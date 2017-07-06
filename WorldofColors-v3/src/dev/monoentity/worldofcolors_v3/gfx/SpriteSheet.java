package dev.monoentity.worldofcolors_v3.gfx;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private BufferedImage ss;
	private final static int TILESIZE = 32;
	
	public SpriteSheet(BufferedImage ss){
		this.ss = ss;
	}
	
	public BufferedImage crop(int row, int column){
		return ss.getSubimage(row*TILESIZE, column*TILESIZE, TILESIZE, TILESIZE);
	}

	public BufferedImage crop2(int row, int column){
		return ss.getSubimage(row*TILESIZE, column*TILESIZE, 2*TILESIZE, 2*TILESIZE);
	}
	
	public BufferedImage cropOther(int row, int column, int width, int height){
		return ss.getSubimage(row*TILESIZE, column*TILESIZE, width, height);
	}
	
}
