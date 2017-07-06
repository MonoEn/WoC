package dev.monoentity.worldofcolors_v3;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.monoentity.worldofcolors_v3.gfx.Animations;
import dev.monoentity.worldofcolors_v3.gfx.Assets;
import dev.monoentity.worldofcolors_v3.states.ShopState;
import dev.monoentity.worldofcolors_v3.states.State;
import dev.monoentity.worldofcolors_v3.states.TitleState;

public class DisplayBar {

	private Font f; 
	private int score, ccoins, acoins;
	private String scoreCount, cCoinCount, aCoinCount;
	private int time = 0;
	private BufferedImage heartsSprite;
	private BufferedImage lifeDisplay;
	private int scale;
	private int scale2;
	private boolean lifeUpgraded;

	public boolean isLifeUpgraded() {
		return lifeUpgraded;
	}

	public void setLifeUpgraded(boolean lifeUpgraded) {
		this.lifeUpgraded = lifeUpgraded;
	}

	private Handy hand;

	private Animations anim6, anim5, anim4, anim3, anim2, anim1;

	public DisplayBar(Handy hand) {
		this.hand = hand;
		f = new Font("serif", Font.BOLD, 20);
		score = 0;
		ccoins = 0;
		acoins = 0;
		scale = 1;
		scale2 = 1;
		anim6 = new Animations(500, Assets.hearts6);
		anim5 = new Animations(500, Assets.hearts5);
		anim4 = new Animations(500, Assets.hearts4);
		anim3 = new Animations(400, Assets.hearts3);
		anim2 = new Animations(300, Assets.hearts2);
		anim1 = new Animations(100, Assets.hearts1);
		lifeDisplay = Assets.displayBar;
		heartsSprite = anim3.getCurrentFrame();
	}

	public void update() {
		if(lifeUpgraded){
			lifeDisplay = Assets.displayBar2;
			scale = 2;
		}
		anim6.update();
		anim5.update();
		anim4.update();
		anim3.update();
		anim2.update();
		anim1.update();
		// System.out.println(time);
		time++;
		if (time > 1000) {
			time = 0;
		}
		if (time % 60 == 0 && !(State.getState() instanceof ShopState) && !(State.getState() instanceof TitleState)) {
			score++;
		}
		this.ccoins = hand.getPlayer().ccoins;
		this.acoins = hand.getPlayer().acoins;
		/*
		 * if (Player.CLEAR) { clear = true; } if(clear && !unclear){ scoreCount
		 * = "Score: " + score + " x 2"; //score *= 2; if(time % 180 == 0){
		 * score *= 2; unclear = true; } } else
		 */ scoreCount = "Score: " + score;
		 cCoinCount = " "+ ccoins;
		 aCoinCount = " " + acoins; 
	}

	public void render(Graphics g) {
		if (!(hand.getGame().getState() instanceof TitleState)) {
			g.drawImage(lifeDisplay, 30, 4, Assets.DISPLAYBARWIDTH, Assets.DISPLAYBARHEIGHT*scale, null);
			g.drawImage(Assets.displayBar, 800, 4, Assets.DISPLAYBARWIDTH, Assets.DISPLAYBARHEIGHT, null);
			g.setFont(f);
			
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
			if(hand.getGame().isNight() || hand.getGame().isRaining()){
				g.setColor(Color.WHITE);
			}
			g.drawString(scoreCount, 815, 27);
			g.drawImage(heartsSprite, 30, 5, Assets.DISPLAYBARWIDTH, Assets.DISPLAYBARHEIGHT*scale2, null);
			g.drawString(cCoinCount, 205, 27);
			g.drawImage(Assets.cCoin, TheGame.WIDTH/2 - 300, 5, Assets.TILESIZE/2, Assets.TILESIZE/2, null);
			g.drawImage(Assets.aCoin, TheGame.WIDTH/2 + -240, 5, Assets.TILESIZE/2, Assets.TILESIZE/2,null);
			g.drawString(aCoinCount, 265, 27);
			if(hand.getPlayer().health == 6){
				scale2 = 2;
				heartsSprite = anim6.getCurrentFrame();
			}
			if(hand.getPlayer().health == 5){
				scale2 = 2;
				heartsSprite = anim5.getCurrentFrame();
			}
			if(hand.getPlayer().health == 4){
				scale2 = 2;
				heartsSprite = anim4.getCurrentFrame();
			}
			if (hand.getPlayer().health == 3) {
				scale2 = 1;
				heartsSprite = anim3.getCurrentFrame();
			}
			if (hand.getPlayer().health == 2) {
				scale2 = 1;
				heartsSprite = anim2.getCurrentFrame();
			}
			if (hand.getPlayer().health == 1) {
				scale2 = 1;
				heartsSprite = anim1.getCurrentFrame();
			}
			if (hand.getPlayer().health == 0) {
				scale2 = 1;
				heartsSprite = Assets.empty;
			}
		}
		// g.drawString("Health: " + hand.getPlayer().health, 45, 27);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	

}
