package dev.monoentity.worldofcolors_v3.states;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.monoentity.worldofcolors_v3.Handy;
import dev.monoentity.worldofcolors_v3.TheGame;
import dev.monoentity.worldofcolors_v3.entities.Entity;
import dev.monoentity.worldofcolors_v3.entities.Moving;
import dev.monoentity.worldofcolors_v3.entities.Unknown;
import dev.monoentity.worldofcolors_v3.gfx.Animations;
import dev.monoentity.worldofcolors_v3.gfx.Assets;
import dev.monoentity.worldofcolors_v3.maps.Mappie;
import dev.monoentity.worldofcolors_v3.sfx.Sound;
import dev.monoentity.worldofcolors_v3.ui.ClickListener;
import dev.monoentity.worldofcolors_v3.ui.UIButton;
import dev.monoentity.worldofcolors_v3.ui.UISystem;

public class TitleState extends State {

	private boolean HOWTO = false, DRAW = false;;
	protected Mappie passingM;
	private BufferedImage timeOfDay, instruct;
	private Animations night, rain;
	private UIButton emptyButton = new UIButton(hand, 400, 384, Assets.BUTTONWIDTH, Assets.BUTTONHEIGHT,
			Assets.empty_btn, hand.getMouseIn(), new ClickListener() {

				@Override
				public void onClick() {
					HOWTO = false;

				}

			});

	public TitleState(Handy hand) {
		super(hand);
		passingM = new Mappie(hand, 300, 8, false);
		night = new Animations(500, Assets.bgNight);
		rain = new Animations(300, Assets.bgRain);
		//hand.setMap(passingM);
		uis = new UISystem(hand);

		uis.addUIObj(new UIButton(hand, 400, 320, Assets.DISPLAYBARWIDTH, Assets.DISPLAYBARHEIGHT * 2, Assets.start_btn,
				hand.getMouseIn(), new ClickListener() {

					@Override
					public void onClick() {
						hand.getMouseIn().setUISys(null);
						Sound.wind.stop();
						Sound.clear.play();
						State.setState(new GameState(hand));
						hand.getGame().setTime(0);
						hand.getPlayer().setT(0);
						hand.getGame().setRaining(false);
					}
				}));

		uis.addUIObj(new UIButton(hand, 400, 384, Assets.BUTTONWIDTH, Assets.BUTTONHEIGHT, Assets.howto_btn,
				hand.getMouseIn(), new ClickListener() {

					@Override
					public void onClick() {
						HOWTO = true;
						DRAW = true;
					}
				}));
		
			Sound.wind.loop();
		
	}

	private double x = 0, y = 0, xBase = TheGame.WIDTH, yBase = 0;

	@Override
	public void update() {
		passingM.getEntitySys().getU().setX(-300);
		x -= 0.5;
		xBase -= 0.5;
		if (x + hand.getWidth() <= 0) {
			x = 0;
		}
		if (xBase <= 0) {
			xBase = TheGame.WIDTH;
		}

		if (HOWTO && DRAW) {
			DRAW = false;
			instruct = Assets.instructions;
			uis.addUIObj(emptyButton);

		} else if (!HOWTO) {
			instruct = Assets.empty;
			uis.removeUIObj(emptyButton);
		}
		// System.out.println("MapX: " + hand.getMShift().getxShift());
		// System.out.println("MapLimit: " + (hand.getMap().getWidth() - 1536));
		if (State.getState() instanceof TitleState) {
			hand.getMouseIn().setUISys(uis);
		}
		uis.update();

		// System.out.println(hand.getMouseIn().getUISys().getUiobjects().get(0).isHoveringOver());
		hand.getMShift().shift(1f, 0);
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
		if(hand.getGame().isRaining()){
			timeOfDay = rain.getCurrentFrame();
		}
		passingM.update();
		for (int i = 0; i < hand.getMap().getEntitySys().getEntities().size(); i++) {
			Entity e = hand.getMap().getEntitySys().getEntities().get(i);
			if (e instanceof Unknown) {
				((Moving) e).setXa(2);
				if (hand.getMShift().getxShift() >= hand.getMap().getWidth() - 1536) {
					hand.getGame().titleState = new TitleState(hand);
					State.setState(hand.getGame().titleState);
					e.setX(-200);
					hand.getMShift().setxShift(200);

				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(timeOfDay, (int) xBase, (int) yBase, hand.getWidth(), hand.getHeight(), null);
		g.drawImage(timeOfDay, (int) x, (int) y, hand.getWidth(), hand.getHeight(), null);
		passingM.render(g);

		// g.drawImage(Assets.start_btn1, 400, 190, Assets.DISPLAYBARWIDTH,
		// Assets.DISPLAYBARHEIGHT*2, null);
		// g.drawImage(Assets.howto_btn1, 400, 250, Assets.DISPLAYBARWIDTH,
		// Assets.DISPLAYBARHEIGHT*2, null);
		g.drawImage(Assets.title, 300, 175, Assets.TITLEWIDTH, Assets.TITLEHEIGHT, null);
		uis.render(g);
		g.drawImage(instruct, 0, 0, hand.getWidth(), hand.getHeight(), null);
	}

}
