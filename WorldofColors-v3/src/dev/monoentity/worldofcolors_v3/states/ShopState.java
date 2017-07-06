package dev.monoentity.worldofcolors_v3.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.monoentity.worldofcolors_v3.Handy;
import dev.monoentity.worldofcolors_v3.TheGame;
import dev.monoentity.worldofcolors_v3.entities.Entity;
import dev.monoentity.worldofcolors_v3.entities.Player;
import dev.monoentity.worldofcolors_v3.entities.Unknown;
import dev.monoentity.worldofcolors_v3.gfx.Animations;
import dev.monoentity.worldofcolors_v3.gfx.Assets;
import dev.monoentity.worldofcolors_v3.maps.Mappie;
import dev.monoentity.worldofcolors_v3.sfx.Sound;
import dev.monoentity.worldofcolors_v3.ui.ClickListener;
import dev.monoentity.worldofcolors_v3.ui.UIButton;
import dev.monoentity.worldofcolors_v3.ui.UISystem;

public class ShopState extends State {

	private Font f;
	private Mappie passiveM;
	public boolean GAMEON = false, SHOP = true;
	private BufferedImage timeOfDay;
	private Animations night, rain;
	private boolean LIFELIMIT = false;
	private int lifeUp, lifeCost;
	private boolean JUMPLIMIT = false;
	private double jumpUp;
	private int jumpCost;
	private boolean COINLIMIT = false;
	private int coinUp, coinCost;
	private boolean DMGLIMIT = false;
	private int dmgUp, dmgCost;
	private boolean FIRERATELIMIT = false;
	private int fireRateUp, fireRateCost;
	private int freezeDuration, freezeCost, freezeChance;
	private boolean FREEZESKILL = false, FREEZELIMIT = false;
	private int bombRadius, bombCost, bombChance;
	private boolean BOMBSKILL = false, BOMBLIMIT = false;
	private int evadeDuration, evadeCost, evadeChance;
	private boolean EVADESKILL = false, EVADELIMIT = false;
	private String currentDialogue;
	private boolean talking = true;

	private boolean LEAFBOUGHT = false, LEAFSET = false;
	private int leafCost = 5;

	UIButton nextButton = new UIButton(hand, 725, 440, Assets.BUTTONWIDTH, Assets.BUTTONHEIGHT, Assets.next_btn,
			hand.getMouseIn(), new ClickListener() {

				@Override
				public void onClick() {
					Sound.shop.stop();
					if (hand.getGame().isDay())
						// Sound.bgm.play();
						if (hand.getGame().isNoon())
							// Sound.bgm2.play();

							GAMEON = true;
					SHOP = false;
					State.setState(new GameState(hand));
					hand.getBar().setScore(0);
					hand.getMShift().setxShift(0);
					hand.getPlayer().setHealth(hand.getPlayer().MAXHEALTH + lifeUp);
					hand.getPlayer().setX(Player.spawnX);
					hand.getPlayer().setV0(hand.getPlayer().DEFAULTJUMP - jumpUp);
					hand.getPlayer().setT(0);
					hand.getPlayer().setXa(2.5f);
					hand.getPlayer().setFreezeCount(0);
					hand.getPlayer().setFrozen(false);
				}
			});
	// Separate currency for cosmetics and upgrades-skills

	public ShopState(Handy hand) {
		super(hand);
		f = new Font("serif", Font.BOLD, 20);
		lifeUp = 0;
		lifeCost = 3;
		jumpUp = 0;
		jumpCost = 3;
		coinUp = 0;
		coinCost = 3;
		dmgUp = 0;
		dmgCost = 3;
		fireRateUp = 0;
		fireRateCost = 3;
		freezeDuration = 150;
		freezeCost = 5;
		freezeChance = 0;
		evadeDuration = 150;
		evadeCost = 5;
		evadeChance = 0;
		bombRadius = 0;
		bombCost = 5;
		bombChance = 0;

		uis = new UISystem(hand);
		uis.addUIObj(nextButton);
		uis.addUIObj(new UIButton(hand, 96, 130, Assets.BUTTONWIDTH, Assets.BUTTONHEIGHT - 20, Assets.empty_btn,
				hand.getMouseIn(), new ClickListener() {

					@Override
					public void onClick() {
						talking = true;
						if (hand.getPlayer().getCcoins() >= lifeCost && !LIFELIMIT) {
							currentDialogue = "Nice doing business with ya!";
							Sound.purchase.play();
							hand.getBar().setLifeUpgraded(true);
							lifeUp++;
							hand.getPlayer().subtractCcoins(lifeCost);
							lifeCost *= 3;
						} else if (hand.getPlayer().getCcoins() < lifeCost) {
							currentDialogue = "You don't have enough coins...";
							Sound.notEnough.play();
						} else if (LIFELIMIT) {
							currentDialogue = "All sold out.";
							Sound.notEnough.play();
						}
					}
				}));

		uis.addUIObj(new UIButton(hand, 96, 178, Assets.BUTTONWIDTH, Assets.BUTTONHEIGHT - 20, Assets.empty_btn,
				hand.getMouseIn(), new ClickListener() {

					@Override
					public void onClick() {
						talking = true;
						if (hand.getPlayer().getCcoins() >= jumpCost && !JUMPLIMIT) {
							currentDialogue = "Nice doing business with ya!";
							Sound.purchase.play();
							jumpUp += 0.5;
							hand.getPlayer().subtractCcoins(jumpCost);
							jumpCost *= 3;
						} else if (hand.getPlayer().getCcoins() < jumpCost) {
							currentDialogue = "You don't have enough coins...";
							Sound.notEnough.play();
						} else if (JUMPLIMIT) {
							currentDialogue = "All sold out.";
							Sound.notEnough.play();
						}
					}

				}));

		uis.addUIObj(new UIButton(hand, 96, 226, Assets.BUTTONWIDTH, Assets.BUTTONHEIGHT - 20, Assets.empty_btn,
				hand.getMouseIn(), new ClickListener() {

					@Override
					public void onClick() {
						talking = true;
						if (hand.getPlayer().getCcoins() >= coinCost && !COINLIMIT) {
							currentDialogue = "Nice doing business with ya!";
							Sound.purchase.play();
							coinUp += 60;
							hand.getPlayer().subtractCcoins(coinCost);
							coinCost *= 3;
						} else if (hand.getPlayer().getCcoins() < coinCost) {
							currentDialogue = "You don't have enough coins...";
							Sound.notEnough.play();
						} else if (COINLIMIT) {
							currentDialogue = "All sold out.";
							Sound.notEnough.play();
						}
					}
				}));
		uis.addUIObj(new UIButton(hand, 96, 274, Assets.BUTTONWIDTH + 10, Assets.BUTTONHEIGHT - 20, Assets.empty_btn,
				hand.getMouseIn(), new ClickListener() {

					@Override
					public void onClick() {
						talking = true;
						if (hand.getPlayer().getCcoins() >= dmgCost && !DMGLIMIT) {
							currentDialogue = "Nice doing business with ya!";
							Sound.purchase.play();
							dmgUp++;
							hand.getPlayer().subtractCcoins(dmgCost);
							dmgCost *= 3;
						} else if (hand.getPlayer().getCcoins() < dmgCost) {
							currentDialogue = "You don't have enough coins...";
							Sound.notEnough.play();
						} else if (DMGLIMIT) {
							currentDialogue = "All sold out.";
							Sound.notEnough.play();
						}

					}
				}));
		uis.addUIObj(new UIButton(hand, 96, 322, Assets.BUTTONWIDTH + 10, Assets.BUTTONHEIGHT - 20, Assets.empty_btn,
				hand.getMouseIn(), new ClickListener() {

					@Override
					public void onClick() {
						talking = true;
						if (hand.getPlayer().getCcoins() >= fireRateCost && !FIRERATELIMIT) {
							currentDialogue = "Nice doing business with ya!";
							Sound.purchase.play();
							fireRateUp += 5;
							hand.getPlayer().subtractCcoins(fireRateCost);
							fireRateCost *= 3;
						} else if (hand.getPlayer().getCcoins() < fireRateCost) {
							currentDialogue = "You don't have enough coins...";
							Sound.notEnough.play();
						} else if (FIRERATELIMIT) {
							currentDialogue = "All sold out.";
							Sound.notEnough.play();
						}

					}
				}));

		uis.addUIObj(new UIButton(hand, 448, 152, Assets.TILESIZE, Assets.TILESIZE + 16, Assets.empty_btn,
				hand.getMouseIn(), new ClickListener() {

					@Override
					public void onClick() {
						talking = true;
						if (hand.getPlayer().getCcoins() >= freezeCost && !FREEZELIMIT) {
							currentDialogue = "Nice doing business with ya!";
							Sound.purchase.play();
							FREEZESKILL = true;
							freezeDuration += 30;
							freezeChance++;
							hand.getPlayer().subtractCcoins(freezeCost);
							freezeCost *= 2;
						} else if (hand.getPlayer().getCcoins() < freezeCost) {
							currentDialogue = "You don't have enough coins...";
							Sound.notEnough.play();
						} else if (FREEZELIMIT) {
							currentDialogue = "All sold out.";
							Sound.notEnough.play();
						}

					}
				}));

		uis.addUIObj(new UIButton(hand, 448, 248, Assets.TILESIZE, Assets.TILESIZE + 16, Assets.empty_btn,
				hand.getMouseIn(), new ClickListener() {

					@Override
					public void onClick() {
						talking = true;
						Sound.notEnough.play();
						currentDialogue = "That item is locked.";
					}
				}));

		uis.addUIObj(new UIButton(hand, 448, 344, Assets.TILESIZE, Assets.TILESIZE + 16, Assets.empty_btn,
				hand.getMouseIn(), new ClickListener() {

					@Override
					public void onClick() {
						talking = true;
						Sound.notEnough.play();
						currentDialogue = "That item is locked.";
					}
				}));

		uis.addUIObj(new UIButton(hand, 768, 112, Assets.TILESIZE - 16, Assets.TILESIZE, Assets.empty_btn,
				hand.getMouseIn(), new ClickListener() {
					@Override
					public void onClick() {
						talking = true;
						if (hand.getPlayer().getAcoins() >= leafCost && !LEAFBOUGHT) {
							currentDialogue = "Nice doing business with ya!";
							Sound.purchase.play();
							LEAFBOUGHT = true;
							LEAFSET = true;
							hand.getPlayer().subtractAcoins(leafCost);
						} else if (hand.getPlayer().getAcoins() < leafCost && !LEAFBOUGHT) {
							currentDialogue = "You don't have enough coins...";
							Sound.notEnough.play();
						} else if (LEAFBOUGHT) {
							LEAFSET = true;
						}

					}

				}));

		uis.addUIObj(new UIButton(hand, 854, 135, 16, 16, Assets.empty_btn, hand.getMouseIn(), new ClickListener() {
			@Override
			public void onClick() {
				if (LEAFBOUGHT) {
					LEAFSET = false;
				}

			}

		}));

		uis.addUIObj(new UIButton(hand, 768, 178, Assets.TILESIZE - 16, Assets.TILESIZE, Assets.empty_btn,
				hand.getMouseIn(), new ClickListener() {
					@Override
					public void onClick() {
						talking = true;
						Sound.notEnough.play();
						currentDialogue = "That item is locked.";
					}

				}));
		
		uis.addUIObj(new UIButton(hand, 768, 242, Assets.TILESIZE - 16, Assets.TILESIZE, Assets.empty_btn,
				hand.getMouseIn(), new ClickListener() {
					@Override
					public void onClick() {
						talking = true;
						Sound.notEnough.play();
						currentDialogue = "That item is locked.";
					}

				}));
		
		uis.addUIObj(new UIButton(hand, 768, 306, Assets.TILESIZE - 16, Assets.TILESIZE, Assets.empty_btn,
				hand.getMouseIn(), new ClickListener() {
					@Override
					public void onClick() {
						talking = true;
						Sound.notEnough.play();
						currentDialogue = "That item is locked.";
					}

				}));
		
		uis.addUIObj(new UIButton(hand, 768, 370, Assets.TILESIZE - 16, Assets.TILESIZE, Assets.empty_btn,
				hand.getMouseIn(), new ClickListener() {
					@Override
					public void onClick() {
						talking = true;
						Sound.notEnough.play();
						currentDialogue = "That item is locked.";
					}

				}));
		
		passiveM = new Mappie(hand, 300, 8, true);
		night = new Animations(500, Assets.bgNight);
		rain = new Animations(300, Assets.bgRain);
		hand.setMap(passiveM);
		currentDialogue = "Welcome to the Shop!";
	}

	private int x = 0, y = 0, xBase = TheGame.WIDTH, yBase = 0;

	@Override
	public void update() {
		checkLifeUpgrades();
		checkJumpUpgrades();
		checkCoinUpgrades();
		checkDamageUpgrades();
		checkFireRateUgrades();
		checkStopUpgrades();
		checkEvadeUpgrades();
		checkBombUpgrades();

		for (int i = 0; i < passiveM.getEntitySys().getEntities().size(); i++) {
			Entity e = passiveM.getEntitySys().getEntities().get(i);
			if (e instanceof Unknown) {
				if (!GAMEON && SHOP) {
					SHOP = false;
					e.setX(-300);
				}
				if (e.getX() + e.getSize() >= passiveM.getWidth() - 1) {
					e.setX(-300);
				}
			}
		}

		for (int i = 0; i < passiveM.getEntitySys().getEntities().size(); i++) {
			Entity e = passiveM.getEntitySys().getEntities().get(i);
			if (e instanceof Unknown) {

			}
		}
		x--;
		xBase--;
		if (x + hand.getWidth() <= 0) {
			x = 0;
		}
		if (xBase <= 0) {
			xBase = TheGame.WIDTH;
		}
		if ((State.getState() instanceof ShopState)) {
			hand.getMouseIn().setUISys(uis);
		}
		uis.update();
		hand.getMShift().setxShift(500);
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
		passiveM.update();

	}

	private void checkEvadeUpgrades() {
		if (evadeChance >= 3) {
			EVADELIMIT = true;
		}
	}

	private void checkBombUpgrades() {
		if (bombChance >= 3) {
			BOMBLIMIT = true;
		}
	}

	private void checkStopUpgrades() {
		if (freezeChance >= 3) {
			FREEZELIMIT = true;
		}
	}

	private void checkFireRateUgrades() {
		if (fireRateUp >= 15) {
			FIRERATELIMIT = true;
		}
	}

	private void checkDamageUpgrades() {
		if (dmgUp >= 3) {
			DMGLIMIT = true;
		}
	}

	private void checkCoinUpgrades() {
		if (coinUp >= 180) {
			COINLIMIT = true;
		}
	}

	private void checkJumpUpgrades() {
		if (jumpUp >= 1.5)
			JUMPLIMIT = true;
	}

	private void checkLifeUpgrades() {

		if (lifeUp >= 3) {
			LIFELIMIT = true;
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(timeOfDay, (int) xBase, yBase, hand.getWidth(), hand.getHeight(), null);
		g.drawImage(timeOfDay, (int) x, y, hand.getWidth(), hand.getHeight(), null);
		passiveM.render(g);
		g.drawImage(Assets.shop, 0, 0, hand.getWidth(), hand.getHeight(), null);
		if (lifeUp >= 1) {
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
			if (hand.getGame().isNight() || hand.getGame().isRaining()) {
				g.setColor(Color.WHITE);
			}
			g.fillOval(236, 142, 15, 15);
		}
		if (lifeUp >= 2) {
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
			if (hand.getGame().isNight() || hand.getGame().isRaining()) {
				g.setColor(Color.WHITE);
			}
			g.fillOval(253, 142, 15, 15);
		}
		if (lifeUp >= 3) {
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
			if (hand.getGame().isNight() || hand.getGame().isRaining()) {
				g.setColor(Color.WHITE);
			}
			g.fillOval(270, 142, 15, 15);
		}
		g.setFont(f);
		if (hand.getGame().isNight() || hand.getGame().isRaining()) {
			g.setColor(Color.WHITE);
		} else
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
		if (lifeCost <= 27) {
			g.drawString("" + lifeCost, 326, 157);
		} else {
			g.drawString("MAX", 326, 157);
		}

		if (jumpUp >= 0.5) {
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
			if (hand.getGame().isNight() || hand.getGame().isRaining()) {
				g.setColor(Color.WHITE);
			}
			g.fillOval(236, 190, 15, 15);
		}
		if (jumpUp >= 1) {
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
			if (hand.getGame().isNight() || hand.getGame().isRaining()) {
				g.setColor(Color.WHITE);
			}
			g.fillOval(253, 190, 15, 15);
		}
		if (jumpUp >= 1.5) {
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
			if (hand.getGame().isNight() || hand.getGame().isRaining()) {
				g.setColor(Color.WHITE);
			}
			g.fillOval(270, 190, 15, 15);
		}
		g.setFont(f);
		if (hand.getGame().isNight() || hand.getGame().isRaining()) {
			g.setColor(Color.WHITE);
		} else
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
		if (jumpCost <= 27) {
			g.drawString("" + jumpCost, 326, 205);
		} else {
			g.drawString("MAX", 326, 205);
		}

		if (coinUp >= 60) {
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
			if (hand.getGame().isNight() || hand.getGame().isRaining()) {
				g.setColor(Color.WHITE);
			}
			g.fillOval(236, 238, 15, 15);
		}
		if (coinUp >= 120) {
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
			if (hand.getGame().isNight() || hand.getGame().isRaining()) {
				g.setColor(Color.WHITE);
			}
			g.fillOval(253, 238, 15, 15);
		}
		if (coinUp >= 180) {
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
			if (hand.getGame().isNight() || hand.getGame().isRaining()) {
				g.setColor(Color.WHITE);
			}
			g.fillOval(270, 238, 15, 15);
		}
		g.setFont(f);
		if (hand.getGame().isNight() || hand.getGame().isRaining()) {
			g.setColor(Color.WHITE);
		} else
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
		if (coinCost <= 27) {
			g.drawString("" + coinCost, 326, 253);
		} else {
			g.drawString("MAX", 326, 253);
		}

		if (dmgUp >= 1) {
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
			if (hand.getGame().isNight() || hand.getGame().isRaining()) {
				g.setColor(Color.WHITE);
			}
			g.fillOval(236, 286, 15, 15);
		}
		if (dmgUp >= 2) {
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
			if (hand.getGame().isNight() || hand.getGame().isRaining()) {
				g.setColor(Color.WHITE);
			}
			g.fillOval(253, 286, 15, 15);
		}
		if (dmgUp >= 3) {
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
			if (hand.getGame().isNight() || hand.getGame().isRaining()) {
				g.setColor(Color.WHITE);
			}
			g.fillOval(270, 286, 15, 15);
		}
		g.setFont(f);
		if (hand.getGame().isNight() || hand.getGame().isRaining()) {
			g.setColor(Color.WHITE);
		} else
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
		if (dmgCost <= 27) {
			g.drawString("" + dmgCost, 326, 301);
		} else {
			g.drawString("MAX", 326, 301);
		}

		if (fireRateUp >= 5) {
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
			if (hand.getGame().isNight() || hand.getGame().isRaining()) {
				g.setColor(Color.WHITE);
			}
			g.fillOval(236, 334, 15, 15);
		}
		if (fireRateUp >= 10) {
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
			if (hand.getGame().isNight() || hand.getGame().isRaining()) {
				g.setColor(Color.WHITE);
			}
			g.fillOval(253, 334, 15, 15);
		}
		if (fireRateUp >= 15) {
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
			if (hand.getGame().isNight() || hand.getGame().isRaining()) {
				g.setColor(Color.WHITE);
			}
			g.fillOval(270, 334, 15, 15);
		}
		g.setFont(f);
		if (hand.getGame().isNight() || hand.getGame().isRaining()) {
			g.setColor(Color.WHITE);
		} else
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
		if (fireRateCost <= 27) {
			g.drawString("" + fireRateCost, 326, 349);
		} else {
			g.drawString("MAX", 326, 349);
		}

		/*if (evadeDuration >= 180) {
			g.drawString("Duration: " + evadeDuration / 60 + "s", 610, 378);
			g.drawString("Level: " + evadeChance + "/3", 610, 400);
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
			if (hand.getGame().isNight() || hand.getGame().isRaining()) {
				g.setColor(Color.WHITE);
			}
			g.fillOval(546, 334, 15, 15);
		}
		if (evadeDuration >= 210) {
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
			if (hand.getGame().isNight() || hand.getGame().isRaining()) {
				g.setColor(Color.WHITE);
			}
			g.fillOval(562, 334, 15, 15);
		}
		if (evadeDuration >= 240) {
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
			if (hand.getGame().isNight() || hand.getGame().isRaining()) {
				g.setColor(Color.WHITE);
			}
			g.fillOval(578, 334, 15, 15);
		}
		g.setFont(f);
		if (hand.getGame().isNight() || hand.getGame().isRaining()) {
			g.setColor(Color.WHITE);
		} else
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
		if (evadeCost <= 20) {
			g.drawString("" + evadeCost, 552, 390);
		} else {
			g.drawString("MAX", 552, 390);
		}

		if (bombRadius >= 5) {
			g.drawString("Radius: " + bombRadius / 60 + "m", 610, 282);
			g.drawString("Level: " + bombChance + "/3", 610, 304);
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
			if (hand.getGame().isNight() || hand.getGame().isRaining()) {
				g.setColor(Color.WHITE);
			}
			g.fillOval(546, 238, 15, 15);
		}
		if (bombRadius >= 10) {
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
			if (hand.getGame().isNight() || hand.getGame().isRaining()) {
				g.setColor(Color.WHITE);
			}
			g.fillOval(562, 238, 15, 15);
		}
		if (bombRadius >= 15) {
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
			if (hand.getGame().isNight() || hand.getGame().isRaining()) {
				g.setColor(Color.WHITE);
			}
			g.fillOval(578, 238, 15, 15);
		}
		g.setFont(f);
		if (hand.getGame().isNight() || hand.getGame().isRaining()) {
			g.setColor(Color.WHITE);
		} else
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
		if (bombCost <= 20) {
			g.drawString("" + bombCost, 552, 294);
		} else {
			g.drawString("MAX", 552, 294);
		}*/

		if (freezeDuration >= 180) {
			g.drawString("Duration: " + freezeDuration / 60 + "s", 610, 186);
			g.drawString("Level: " + freezeChance + "/3", 610, 208);
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
			if (hand.getGame().isNight() || hand.getGame().isRaining()) {
				g.setColor(Color.WHITE);
			}
			g.fillOval(526, 142, 15, 15);
		}
		if (freezeDuration >= 210) {
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
			if (hand.getGame().isNight() || hand.getGame().isRaining()) {
				g.setColor(Color.WHITE);
			}
			g.fillOval(542, 142, 15, 15);
		}
		if (freezeDuration >= 240) {
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
			if (hand.getGame().isNight() || hand.getGame().isRaining()) {
				g.setColor(Color.WHITE);
			}
			g.fillOval(558, 142, 15, 15);
		}
		g.setFont(f);
		if (hand.getGame().isNight() || hand.getGame().isRaining()) {
			g.setColor(Color.WHITE);
		} else
			g.setColor(Color.getHSBColor(0.1f, 0.9f, 0.5f));
		if (freezeCost <= 20) {
			g.drawString("" + freezeCost, 552, 198);
		} else {
			g.drawString("MAX", 552, 198);
		}

		if (!LEAFBOUGHT) {
			g.drawString("" + leafCost, 854, 150);
		} else if (LEAFSET) {
			g.drawRect(760, 112, Assets.TILESIZE, Assets.TILESIZE);
			g.drawString("X", 854, 150);
		} else if (!LEAFSET) {
			g.drawString("X", 854, 150);
		}

		g.drawImage(Assets.vendor, 100, 400, Assets.TILESIZE, Assets.TILESIZE, null);
		if (talking) {
			// g.drawImage(Assets.dialogue, 200, 400, Assets.BUTTONWIDTH,
			// Assets.BUTTONHEIGHT, null);
			g.drawString(currentDialogue, 179, 400);
		}
		uis.render(g);
	}

	public double getJumpUp() {
		return jumpUp;
	}

	public int getCoinUp() {
		return coinUp;
	}

	public void setCoinUp(int coinUp) {
		this.coinUp = coinUp;
	}

	public int getDmgUp() {
		return dmgUp;
	}

	public int getFireRateUp() {
		return fireRateUp;
	}

	public String getCurrentDialogue() {
		return currentDialogue;
	}

	public void setCurrentDialogue(String currentDialogue) {
		this.currentDialogue = currentDialogue;
	}

	public int getFreezeDuration() {
		return freezeDuration;
	}

	public void setFreezeDuration(int freezeDuration) {
		this.freezeDuration = freezeDuration;
	}

	public boolean isFREEZESKILL() {
		return FREEZESKILL;
	}

	public void setFREEZESKILL(boolean fREEZESKILL) {
		FREEZESKILL = fREEZESKILL;
	}

	public int getFreezeChance() {
		return freezeChance * 30;
	}

	public void setFreezeChance(int freezeChance) {
		this.freezeChance = freezeChance;
	}

	public int getEvadeChance() {
		return evadeChance * 30;
	}

	public void setEvadeChance(int evadeChance) {
		this.evadeChance = evadeChance;
	}

	public int getEvadeDuration() {
		return evadeDuration;
	}

	public void setEvadeDuration(int evadeDuration) {
		this.evadeDuration = evadeDuration;
	}

	public boolean isEVADESKILL() {
		return EVADESKILL;
	}

	public void setEVADESKILL(boolean eVADESKILL) {
		EVADESKILL = eVADESKILL;
	}

	public int getBombRadius() {
		return bombRadius;
	}

	public void setBombRadius(int bombRadius) {
		this.bombRadius = bombRadius;
	}

	public int getBombChance() {
		return bombChance * 30;
	}

	public void setBombChance(int bombChance) {
		this.bombChance = bombChance;
	}

	public boolean isBOMBSKILL() {
		return BOMBSKILL;
	}

	public void setBOMBSKILL(boolean bOMBSKILL) {
		BOMBSKILL = bOMBSKILL;
	}

	public boolean isLEAFBOUGHT() {
		return LEAFBOUGHT;
	}

	public void setLEAFBOUGHT(boolean lEAFBOUGHT) {
		LEAFBOUGHT = lEAFBOUGHT;
	}

	public boolean isLEAFSET() {
		return LEAFSET;
	}

	public void setLEAFSET(boolean lEAFSET) {
		LEAFSET = lEAFSET;
	}

}
