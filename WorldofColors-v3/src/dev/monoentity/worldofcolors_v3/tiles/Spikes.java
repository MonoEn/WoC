package dev.monoentity.worldofcolors_v3.tiles;

import dev.monoentity.worldofcolors_v3.gfx.Assets;

public class Spikes extends Tile {

	public Spikes(int ti) {
		super(Assets.spikes, ti);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isHarmful(){
		return true;
	}
	
	@Override
	public boolean isSolid(){
		return false;
	}
	
}
