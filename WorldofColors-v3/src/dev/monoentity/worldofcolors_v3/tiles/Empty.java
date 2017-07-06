package dev.monoentity.worldofcolors_v3.tiles;

import dev.monoentity.worldofcolors_v3.gfx.Assets;

public class Empty extends Tile {

	public Empty(int ti) {
		super(Assets.empty, ti);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isSolid(){
		return false;
	}
	
}
