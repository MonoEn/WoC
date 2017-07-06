package dev.monoentity.worldofcolors_v3.tiles;

import dev.monoentity.worldofcolors_v3.gfx.Assets;

public class Complete extends Tile {

	public Complete(int ti) {
		super(Assets.complete, ti);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isSolid(){
		return false;
	}

}
