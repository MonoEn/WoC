package dev.monoentity.worldofcolors_v3.items;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import dev.monoentity.worldofcolors_v3.Handy;

public class ItemSystem {

	private Handy hand;
	private ArrayList<Item> items;
	
	public ItemSystem(Handy hand){
		this.hand = hand;
		items = new ArrayList<Item>();
	}
	
	public void update(){
		Iterator<Item> itemIt = items.iterator();
		while(itemIt.hasNext()){
			Item i = itemIt.next();
			if(i.isRemoved()){
				itemIt.remove();
			}
			i.update();
		}
	}
	
	public void render(Graphics g){
		for(Item i : items){
			i.render(g);
		}
	}
	
	public void addItem(Item i){
		i.setHand(hand);
		items.add(i);
	}
	
	public void removeItem(Item i){
		items.remove(i);
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	
	
	
}
