package dev.monoentity.worldofcolors_v3.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.monoentity.worldofcolors_v3.Handy;
import dev.monoentity.worldofcolors_v3.TheGame;
import dev.monoentity.worldofcolors_v3.entities.Player;
import dev.monoentity.worldofcolors_v3.gfx.Animations;
import dev.monoentity.worldofcolors_v3.gfx.Assets;
import dev.monoentity.worldofcolors_v3.maps.Mappie;
import dev.monoentity.worldofcolors_v3.sfx.Sound;
import dev.monoentity.worldofcolors_v3.ui.UISystem;

public class GameState extends State {

	Font f = new Font("serif", Font.BOLD, 20);
	private Mappie mLayer;
	public int mWidth = 50, mHeight = 8;
	private BufferedImage timeOfDay;
	private Animations night, rain;
	private UISystem uiSys;

	

	public GameState(Handy hand) {
		super(hand);
	
		if (hand.getPlayer().firstTime) {
			setmLayer(new Mappie(hand, 50, 8));
		} else
			setmLayer(new Mappie(hand, mWidth + hand.getK(), mHeight));
		// m = new Mappie(hand, "rsc/maps/map1.lvl");
		hand.setMap(mLayer);
		uiSys = new UISystem(hand);
		hand.getMouseIn().setUISys(uiSys);
		night = new Animations(500, Assets.bgNight);
		rain = new Animations(300, Assets.bgRain);
		hand.getPlayer().setX(Player.spawnX);
		hand.getMShift().setxShift(0);

	}

	private int x = 0, y = 0, xBase = TheGame.WIDTH, yBase = 0;

	@Override
	public void update() {
		// System.out.println("MapWidth: " + hand.getMap().getWidth());
		x--;
		xBase--;
		if (x + hand.getWidth() <= 0) {
			x = 0;
		}
		if (xBase <= 0) {
			xBase = TheGame.WIDTH;
		}
		// System.out.println("K: " + hand.getK());
		hand.getMShift().shift(hand.getPlayer().getXa(), 0);
		night.update();
		rain.update();
		if (hand.getGame().isDay() && !hand.getGame().isRaining()) {
			timeOfDay = Assets.bg;
		}
		if (hand.getGame().isNoon() && !hand.getGame().isRaining()) {
			timeOfDay = Assets.bgNoon;
		}
		if (hand.getGame().isNight() && !hand.getGame().isRaining()) {
			timeOfDay = night.getCurrentFrame();
		}
		if (hand.getGame().isDawn() && !hand.getGame().isRaining()) {
			timeOfDay = Assets.bgDawn;
		}
		if (hand.getGame().isRaining()) {
			timeOfDay = rain.getCurrentFrame();
		}
		getmLayer().update();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(timeOfDay, (int) xBase, yBase, hand.getWidth(), hand.getHeight(), null);
		g.drawImage(timeOfDay, (int) x, y, hand.getWidth(), hand.getHeight(), null);
		/*
		 * if(hand.getGame().isNight()){ g.drawImage(Assets.bgNight, 0, 0,
		 * TheGame.WIDTH, TheGame.HEIGHT, null); }
		 */
		if (hand.getPlayer().firstTime) {
			g.setFont(f);
			g.setColor(Color.RED);

			g.drawString("^^^ Pay attention to your life", 50, 75);
			g.drawString("<<< And maintain a healthy distance from", 5, 225);
			g.drawString("    the left side of the screen!", 5, 250);
		}
		getmLayer().render(g);
	

	}

	public int getmWidth() {
		return mWidth;
	}

	public void setmWidth(int mWidth) {
		this.mWidth = mWidth;
	}

	public int getmHeight() {
		return mHeight;
	}

	public void setmHeight(int mHeight) {
		this.mHeight = mHeight;
	}

	public Mappie getmLayer() {
		return mLayer;
	}

	public void setmLayer(Mappie mLayer) {
		this.mLayer = mLayer;
	}

}
