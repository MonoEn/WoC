package dev.monoentity.worldofcolors_v3;

import dev.monoentity.worldofcolors_v3.entities.Player;
import dev.monoentity.worldofcolors_v3.gfx.MapShift;
import dev.monoentity.worldofcolors_v3.input.KeyIn;
import dev.monoentity.worldofcolors_v3.input.MouseIn;
import dev.monoentity.worldofcolors_v3.maps.Mappie;
import dev.monoentity.worldofcolors_v3.states.GameState;

public class Handy {

	private TheGame game;
	private Mappie map;
	
	public Handy(TheGame game){
		this.game = game;
	}
	
	public GameState getGameState(){
		return game.getGameState();
	}
	
	public MapShift getMShift(){
		return game.getMShift();
	}
	
	public KeyIn getKeyIn(){
		return game.getKeyIn();
	}
	
	public MouseIn getMouseIn(){
		return game.getMouseIn();
	}
	
	public int getWidth(){
		return game.getWidth();
	}
	
	public int getHeight(){
		return game.getHeight();
	}

	public TheGame getGame() {
		return game;
	}

	public void setGame(TheGame g) {
		game = g;
	}

	public Mappie getMap() {
		return map;
	}

	public void setMap(Mappie m) {
		map = m;
	}
	
	public Player getPlayer(){
		return game.getPlayer();
	}
	
	public int getK(){
		return game.getK();
	}
	
	public DisplayBar getBar(){
		return game.getBar();
	}
	
}
