package dev.monoentity.worldofcolors_v3.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyIn implements KeyListener {

	private boolean[] keys = new boolean[256];
	public boolean jump, duck, shoot, skill1, skill2, skill3;

	public void update() {
		jump = keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP];
		duck = keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
		shoot = keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
		skill1 = keys[KeyEvent.VK_J] || keys[KeyEvent.VK_Z];
		skill2 = keys[KeyEvent.VK_K] || keys[KeyEvent.VK_X];
		skill3 = keys[KeyEvent.VK_L] || keys[KeyEvent.VK_C];
	}

	public void keyPressed(KeyEvent evt) {
		System.out.println("pressed");
		keys[evt.getKeyCode()] = true;

	}

	public void keyReleased(KeyEvent evt) {
		keys[evt.getKeyCode()] = false;

	}

	public void keyTyped(KeyEvent evt) {

	}
	
}
