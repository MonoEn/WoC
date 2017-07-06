package dev.monoentity.worldofcolors_v3;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
//import java.awt.image.DataBufferInt;
import java.util.Random;

import javax.swing.JFrame;

import dev.monoentity.worldofcolors_v3.entities.Player;
import dev.monoentity.worldofcolors_v3.entities.Unknown;
import dev.monoentity.worldofcolors_v3.gfx.Assets;
import dev.monoentity.worldofcolors_v3.gfx.MapShift;
//import dev.monoentity.worldofcolors_v3.gfx.Screen;
import dev.monoentity.worldofcolors_v3.input.KeyIn;
import dev.monoentity.worldofcolors_v3.input.MouseIn;
import dev.monoentity.worldofcolors_v3.sfx.Sound;
import dev.monoentity.worldofcolors_v3.states.GameState;
import dev.monoentity.worldofcolors_v3.states.ShopState;
import dev.monoentity.worldofcolors_v3.states.State;
import dev.monoentity.worldofcolors_v3.states.TitleState;

public class TheGame extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int SCALE = 2, WIDTH = 480 * SCALE, HEIGHT = 256 * SCALE;
	private Thread gThread;
	private JFrame window;
	private boolean running = false;
	private BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	//private int[] px = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

	// SCREEN
	//private Screen screen;

	// INPUT
	private KeyIn keyIn;
	private MouseIn mouseIn;

	// STATES
	public GameState gameState;
	public State altGameState;
	public State titleState;
	public ShopState shopState;

	// MAPSHIFT
	private MapShift mShift;
	private int k;

	// HANDY
	private Handy hand;

	// SCORE
	private DisplayBar bar;

	// PLAYER
	private Player p1;

	// TIME OF DAY
	private int time;
	private boolean dawn = false, day = true, noon = false, night = false;
	private boolean raining = false;

	private Random rnd = new Random();
	private int chanceOfRain;
	private int lvl = 1;
	private boolean LVL3 = false, LVL4= false, LVL5 = false;

	public TheGame() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		window = new JFrame("World of Colors");

		Assets.getAssets();
		Sound.getSounds();

		// SCREEN
		//screen = new Screen(WIDTH, HEIGHT);

		// INPUT
		keyIn = new KeyIn();
		addKeyListener(keyIn);
		mouseIn = new MouseIn();
		addMouseListener(mouseIn);
		addMouseMotionListener(mouseIn);

		// HANDY DANDY
		hand = new Handy(this);

		// MAPSHIFT
		mShift = new MapShift(hand, 0, 0);

		titleState = new TitleState(hand);
		shopState = new ShopState(hand);

		// SCORE
		bar = new DisplayBar(hand);

		// PLAYER
		p1 = new Player(hand, Player.spawnX, Player.spawnY, hand.getKeyIn());
		hand.getPlayer().setX(Player.spawnX);
		hand.getPlayer().setY(Player.spawnY);
		
		k = 1;
		time = 0;

		State.setState(titleState);
	}

	public static void main(String[] args) {

		TheGame game = new TheGame();

		game.window.setResizable(false);
		game.window.add(game);
		game.window.pack();
		game.window.setLocationRelativeTo(null);
		game.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.window.setVisible(true);

		game.start();

	}

	public void time() {
		// System.out.println(hand.getPlayer().getHealth());

		// System.out.println("raining: " + raining);
		time++;
		if (time > 10000) {
			Sound.bells.play();
			time = 0;
		}
		if (time == 1 && State.getState() instanceof GameState) {
			//Sound.bgm.loop();

		}

		if (time == 2000 && State.getState() instanceof GameState) {
			//Sound.bgm2.loop();
			//Sound.bgm.stop();
		}
		if (!raining && time >= 2000) {

			noon = true;
			day = false;
			night = false;
			dawn = false;
		}
		if (time == 4000) {
			Sound.night.play();
			//Sound.bgm2.stop();
		}
		if (!raining && time >= 4000) {
			night = true;
			day = false;
			noon = false;
			dawn = false;
		}
		if (time == 8000) {
			Sound.dawn.play();
		}
		if (!raining && time >= 8000) {

			dawn = true;
			night = false;
			day = false;
			noon = false;
		}
		if (!raining && time < 2000) {
			day = true;
			noon = false;
			night = false;
			dawn = false;
		}
		chanceOfRain = rnd.nextInt(10000);
		if (chanceOfRain % 7679 == 0) {
			raining = true;
			day = false;
			noon = false;
			night = false;
			dawn = false;
		}
		if (raining && chanceOfRain % 5000 == 0) {
			raining = false;
		}
	}

	public void update() {
		System.out.println("LVL: " + lvl);
		if(lvl == 3){
			//BLACKBIRDS
			//REMOVE BOXBIRDS
			setLVL3(true);
		}
		
		if(lvl == 4){
			//ANGRYGEL AND DROPGEL
			//REMOVE CUBEGEL AND SADGEL
			setLVL4(true);
		}
		
		if(lvl == 5){
			//MINI UNKNOWNS (1-HIT KILL)
			setLVL5(true);
		}
		System.out.println("Time: " + time);
		keyIn.update();
		mouseIn.update();

		time();

		if (!hand.getPlayer().END && hand.getPlayer().CLEAR) {
			hand.getPlayer().CLEAR = false;
			lvl++;
			k += 25;
			gameState = new GameState(hand);
			State.setState(gameState);
			hand.getPlayer().setX(Player.spawnX);
			hand.getMShift().setxShift(0);
			for (int i = 0; i < hand.getMap().getEntitySys().getEntities().size(); i++) {
				if (hand.getMap().getEntitySys().getEntities().get(i) instanceof Unknown) {
					hand.getMap().getEntitySys().getEntities().get(i).setX(Player.spawnX - 285);
				}
			}
		}

		/*
		 * if (TitleState.LIMIT) { ((TitleState) State.getState()).LIMIT =
		 * false; titleState = new TitleState(hand); State.setState(titleState);
		 * }
		 */

		if (State.getState() != null)
			State.getState().update();

		bar.update();
		if (!(State.getState() instanceof TitleState) && !(State.getState() instanceof ShopState)) {
			p1.update();
		}

	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		// screen.clear();
		// screen.render();

		// for(int i = 0; i < px.length; i++){
		// px[i] = screen.px[i];
		// }

		Graphics g = bs.getDrawGraphics();
		g.clearRect(0, 0, getWidth(), getHeight());
		// Draw here
		g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
		if (State.getState() != null)
			State.getState().render(g);

		// g.drawImage(Assets.pR1, 100, 100, null);
		// g.drawImage(Assets.pR2, 200, 100, null);
		// g.drawImage(Assets.cCoin, 100, 100, null);

		bar.render(g);

		if (!hand.getPlayer().END || !(State.getState() instanceof TitleState))
			p1.render(g);
		
		
		// End here
		g.dispose();
		bs.show();
	}

	public void init() {
		this.requestFocus();
	}

	public synchronized void start() {
		if (running)
			return;
		running = true;
		gThread = new Thread(this);
		gThread.start();
	}

	@Override
	public void run() {
		init();
		long lastTime = System.nanoTime();
		final double numTicks = 60;
		double ns = 1000000000 / numTicks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " ticks, fps: " + frames);
				window.setTitle("World of Colors" + "  <" + updates + " ticks, fps: " + frames + ">  ");
				updates = 0;
				frames = 0;
			}
			// System.out.println("RUNNING");
		}
		stop();
	}

	public synchronized void stop() {
		if (!running)
			return;
		running = false;
		try {
			gThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// GETTERS
	public KeyIn getKeyIn() {
		return keyIn;
	}

	public MouseIn getMouseIn() {
		return mouseIn;
	}

	public MapShift getMShift() {
		return mShift;
	}

	public State getState() {
		return State.getState();
	}

	public GameState getGameState() {
		return gameState;
	}

	public Player getPlayer() {
		return p1;
	}

	public void setPlayer(Player p) {
		p = p1;
	}

	public int getK() {
		return k;
	}

	public boolean isDay() {
		return day;
	}

	public boolean isNoon() {
		return noon;
	}

	public boolean isNight() {
		return night;
	}

	public boolean isDawn() {
		return dawn;
	}

	public void setDawn(boolean dawn) {
		this.dawn = dawn;
	}

	public DisplayBar getBar() {
		return bar;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public boolean isRaining() {
		return raining;
	}

	public void setRaining(boolean raining) {
		this.raining = raining;
	}

	public int getChanceOfRain() {
		return chanceOfRain;
	}

	public void setChanceOfRain(int chanceOfRain) {
		this.chanceOfRain = chanceOfRain;
	}
	
	public ShopState getShop(){
		return shopState;
	}

	public boolean isLVL3() {
		return LVL3;
	}

	public void setLVL3(boolean lVL3) {
		LVL3 = lVL3;
	}

	public boolean isLVL4() {
		return LVL4;
	}

	public void setLVL4(boolean lVL4) {
		LVL4 = lVL4;
	}

	public boolean isLVL5() {
		return LVL5;
	}

	public void setLVL5(boolean lVL5) {
		LVL5 = lVL5;
	}

}
