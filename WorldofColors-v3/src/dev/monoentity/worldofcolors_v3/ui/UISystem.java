package dev.monoentity.worldofcolors_v3.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import dev.monoentity.worldofcolors_v3.Handy;

public class UISystem {

	private Handy hand;
	private ArrayList<UIObject> uiobjects;
	
	public UISystem(Handy hand){
		this.hand = hand;
		uiobjects = new ArrayList<UIObject>();
	}
	
	public void update(){
		for(UIObject uio : uiobjects){
			uio.update();
		}
	}
	
	public void render(Graphics g){
		for(UIObject uio: uiobjects){
			uio.render(g);
		}
	}
	
	public void onMouseMove(MouseEvent evt){
		for(UIObject uio : uiobjects){
			uio.onMouseMove(evt);
		}
	}
	
	public void onMouseRelease(MouseEvent evt){
		for(UIObject uio : uiobjects){
			uio.onMouseRelease(evt);
		}
	}
	
	public void addUIObj(UIObject uio){
		uiobjects.add(uio);
	}
	
	public void removeUIObj(UIObject uio){
		uiobjects.remove(uio);
	}

	public ArrayList<UIObject> getUiobjects() {
		return uiobjects;
	}

	public void setUiobjects(ArrayList<UIObject> uiobjects) {
		this.uiobjects = uiobjects;
	}
	
}
