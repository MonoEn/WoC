package dev.monoentity.worldofcolors_v3.entities;

import dev.monoentity.worldofcolors_v3.TheGame;
import dev.monoentity.worldofcolors_v3.gfx.Screen;

public interface GameObject {

	public void render(Screen scrn, int scale);
	
	public void update(TheGame game);
	
}
