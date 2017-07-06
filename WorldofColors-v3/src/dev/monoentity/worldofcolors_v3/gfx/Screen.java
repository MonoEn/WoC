package dev.monoentity.worldofcolors_v3.gfx;

import java.util.Random;

import dev.monoentity.worldofcolors_v3.TheGame;

public class Screen {

	private static int MAPSIZE = 480 * TheGame.SCALE, MAPWIDTH = 480 * TheGame.SCALE, MAPHEIGHT = 320 * TheGame.SCALE;
	private int width, height;
	public int[] px;
	public int[] tiles = new int[MAPSIZE * MAPSIZE];
	private Random rnd = new Random();

	public Screen(int w, int h) {
		width = w;
		height = h;
		px = new int[w * h];// 1228800

		for (int i = 0; i < MAPSIZE * MAPSIZE; i++) {
				tiles[i] = rnd.nextInt(0xffffff);
		}
	}

	public void clear() {
		for (int i = 0; i < px.length; i++) {
			px[i] = 0;
		}
	}

	public void render() {
		for (int y = 0; y < height; y++) {
			if (y < 0 || y >= height)
				break;
			for (int x = 0; x < width; x++) {
				if (x< 0 || x >= width)
					break;
				int ti = (x >> 5) + (y >> 5) * MAPSIZE;
				px[x+y*width] = tiles[ti];
			}
		}
	}

}
