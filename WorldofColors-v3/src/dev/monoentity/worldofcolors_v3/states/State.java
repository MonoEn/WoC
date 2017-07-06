package dev.monoentity.worldofcolors_v3.states;

import java.awt.Graphics;

import dev.monoentity.worldofcolors_v3.Handy;
import dev.monoentity.worldofcolors_v3.maps.Mappie;
import dev.monoentity.worldofcolors_v3.ui.UISystem;

public abstract class State {

	private static State currentState = null;
	protected Handy hand;
	protected UISystem uis;
	protected Mappie m;
	
	public State(Handy hand){
		this.hand = hand;
		
	}
	
	public abstract void update();
	
	public abstract void render(Graphics g);
	
	public static State getState(){
		return currentState;
	}
	
	public static void setState(State s){
		currentState = s;
	}
	
}
