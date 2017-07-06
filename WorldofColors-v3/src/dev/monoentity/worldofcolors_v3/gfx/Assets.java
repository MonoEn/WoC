package dev.monoentity.worldofcolors_v3.gfx;

import java.awt.image.BufferedImage;

import dev.monoentity.worldofcolors_v3.TheGame;

public class Assets {

	public static final int TILESIZE = 32 * TheGame.SCALE, PROJECTILEOSIZE = 7 * TheGame.SCALE, PLAYERSIZE = 64 * TheGame.SCALE, BACKGROUNDWIDTH = 480 * TheGame.SCALE,
			BACKGROUNDHEIGHT = 256 * TheGame.SCALE, DISPLAYBARWIDTH = 64 * TheGame.SCALE,
			BUTTONWIDTH = 64 * TheGame.SCALE, BUTTONHEIGHT = 32 * TheGame.SCALE, DISPLAYBARHEIGHT = 16 * TheGame.SCALE, TITLEWIDTH = 160 * TheGame.SCALE, TITLEHEIGHT = 64 * TheGame.SCALE;
	public static BufferedImage title, howto_btn1, howto_btn2, bgDawn, bg, bgNoon, leaf, shop, vendor, dialogue, skill_btn1, skill_btn2, skill_btn3, aesthetic_btn1, aesthetic_btn2,
			aesthetic_btn3, displayBar, displayBar2, instructions;
	public static BufferedImage[] start_btn, howto_btn, next_btn, back_btn, empty_btn;
	public static BufferedImage[] hearts6, hearts5, hearts4, hearts3, hearts2, hearts1, cubeLeft, birdLeft, sadLeft, blackbirdLeft, angryLeft, dropLeft, bgNight, bgRain;
	public static BufferedImage aCoin, cCoin, stop, evade, bomb;
	public static BufferedImage empty, yellow, red, blue, orange, green, pink, pinkOpen, spikes, complete, pp, po;
	public static BufferedImage pJump, pDuck, pR1, pR2, pR3;
	
	public Assets() {
		getAssets();
	}

	public static void getAssets() {

		SpriteSheet main = new SpriteSheet(ImageLoader.loadImage("/tex/worldofcolors-main.png"));

		bgDawn = ImageLoader.loadImage("/tex/bg2dawn.png");
		bg = ImageLoader.loadImage("/tex/bg2.png");
		bgNoon = ImageLoader.loadImage("/tex/bg2noon.png");
		bgNight = new BufferedImage[10];
		bgNight[0] = ImageLoader.loadImage("/tex/bg2nightj.png");
		bgNight[1] = ImageLoader.loadImage("/tex/bg2nighti.png");
		bgNight[2] = ImageLoader.loadImage("/tex/bg2nighth.png");
		bgNight[3] = ImageLoader.loadImage("/tex/bg2nightg.png");
		bgNight[4] = ImageLoader.loadImage("/tex/bg2nightf.png");
		bgNight[5] = ImageLoader.loadImage("/tex/bg2nighte.png");
		bgNight[6] = ImageLoader.loadImage("/tex/bg2nightd.png");
		bgNight[7] = ImageLoader.loadImage("/tex/bg2nightc.png");
		bgNight[8] = ImageLoader.loadImage("/tex/bg2nightb.png");
		bgNight[9] = ImageLoader.loadImage("/tex/bg2night.png");
		
		bgRain = new BufferedImage[2];
		bgRain[0] = ImageLoader.loadImage("/tex/bgrain.png");
		bgRain[1] = ImageLoader.loadImage("/tex/bgrainb.png");
		
		title = main.cropOther(2, 4, 160, 64);
		start_btn = new BufferedImage[2];
		start_btn[0] = main.cropOther(3, 2, 64, 32);
		start_btn[1] = main.cropOther(3, 3, 64, 32);
		howto_btn = new BufferedImage[2];
		howto_btn[0] = main.cropOther(5, 2, 64, 32);
		howto_btn[1] = main.cropOther(5, 3, 64, 32);
		next_btn = new BufferedImage[2];
		next_btn[0] = main.cropOther(7, 2, 64, 32);
		next_btn[1] = main.cropOther(7, 3, 64, 32);
		empty_btn = new BufferedImage[2];
		empty_btn[0] = main.crop(5, 9);
		empty_btn[1] = main.crop(5, 9);
		
		displayBar = main.cropOther(0, 6, 64, 16);
		displayBar2 = main.cropOther(0,7,64,32);
		instructions = ImageLoader.loadImage("/tex/instructions.png");
		aCoin = main.crop(7,  1);
		cCoin = main.crop(8, 1);
		stop = main.crop(9, 3);
		evade = main.crop(9, 4);
		bomb = main.crop(9,5);
		shop = ImageLoader.loadImage("/tex/shopWindow.png");
		vendor = main.crop(9, 1);
		dialogue = main.cropOther(2,9,64,32);
		leaf = main.crop(4, 9);

		hearts6 = new BufferedImage[2];
		hearts6[0] = main.cropOther(6, 8, 64, 32);
		hearts6[1] = main.cropOther(8, 8, 64, 32);
		hearts5 = new BufferedImage[2];
		hearts5[0] = main.cropOther(6, 7, 64, 32);
		hearts5[1] = main.cropOther(8, 7, 64, 32);
		hearts4 = new BufferedImage[2];
		hearts4[0] = main.cropOther(6, 6, 64, 32);
		hearts4[1] = main.cropOther(8, 6, 64, 32);
		hearts3 = new BufferedImage[2];
		hearts3[0] = main.cropOther(2, 6, 64, 16);
		hearts3[1] = main.cropOther(4, 6, 64, 16);
		hearts2 = new BufferedImage[2];
		hearts2[0] = main.cropOther(2, 7, 64, 16);
		hearts2[1] = main.cropOther(4, 7, 64, 16);
		hearts1 = new BufferedImage[2];
		hearts1[0] = main.cropOther(2, 8, 64, 16);
		hearts1[1] = main.cropOther(4, 8, 64, 16);

		pJump = main.crop(0, 0);
		pDuck = main.crop(1, 0);
		pR1 = main.crop(0, 1);
		pR2 = main.crop(1, 1);
		pR3 = main.crop(2, 1);

		empty = main.crop(9, 9);
		yellow = main.crop(3, 0);
		red = main.crop(4, 0);
		blue = main.crop(5, 0);
		green = main.crop(6, 0);
		orange = main.crop(7, 0);
		pink = main.crop(8, 0);
		pinkOpen = main.crop(9, 2);
		spikes = main.crop(9, 0);
		complete = main.crop(3, 1);

		cubeLeft = new BufferedImage[2];
		cubeLeft[0] = main.crop(0, 3);
		cubeLeft[1] = main.crop(1, 3);

		birdLeft = new BufferedImage[2];
		birdLeft[0] = main.crop(0, 5);
		birdLeft[1] = main.crop(1, 5);
		
		sadLeft = new BufferedImage[1];
		sadLeft[0] = main.crop(2, 3);
		
		blackbirdLeft = new BufferedImage[2];
		blackbirdLeft[0] = main.crop(0, 4);
		blackbirdLeft[1] = main.crop(1, 4);
		
		angryLeft = new BufferedImage[2];
		angryLeft[0] = main.crop(0, 2);
		angryLeft[1] = main.crop(1, 2);
		
		dropLeft = new BufferedImage[2];
		dropLeft[0] = main.crop2(0, 8);
		dropLeft[1] = main.crop2(7, 4);
		
		pp = main.cropOther(5, 1, 7, 7);
		po = main.cropOther(6, 1, 7, 7);

	}

}
