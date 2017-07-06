package dev.monoentity.worldofcolors_v3.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import dev.monoentity.worldofcolors_v3.ui.UISystem;

public class MouseIn implements MouseListener, MouseMotionListener {
	
	public boolean leftPressed, rightPressed;
	private static int mouseX, mouseY;
	private UISystem uiSys;
	
	public MouseIn(){
		
	}
	
	public void setUISys(UISystem u){
		uiSys = u;
	}
	
	public UISystem getUISys(){
		return uiSys;
	}
	
	public void update(){
		
	}
	
	@Override
	public void mouseDragged(MouseEvent evt) {
		
		mouseX = evt.getX();
		mouseY = evt.getY();
		
	}

	@Override
	public void mouseMoved(MouseEvent evt) {
		mouseX = evt.getX();
		mouseY = evt.getY();
		
		if(uiSys != null)
			uiSys.onMouseMove(evt);
	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent evt) {
		
		
	}

	@Override
	public void mouseExited(MouseEvent evt) {
		
		
	}

	@Override
	public void mousePressed(MouseEvent evt) {
		
		if(evt.getButton() == MouseEvent.BUTTON1){
			leftPressed = true;
		}else if(evt.getButton() == MouseEvent.BUTTON3){
			rightPressed = true;
		} 
		
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		
		if(evt.getButton() == MouseEvent.BUTTON1){
			leftPressed = false;
		}else if(evt.getButton() == MouseEvent.BUTTON3){
			rightPressed = false;
		}
		
		if(uiSys != null)
			uiSys.onMouseRelease(evt);
	}
	
	
	//GETTERS
		public boolean isLeftPressed(){
			return leftPressed;
		}
		
		public boolean isRightPressed(){
			return rightPressed;
		}
		
		public static int getMouseX(){
			return mouseX;
		}
		
		public static int getMouseY(){
			return mouseY;
		}
		
	
	
}
