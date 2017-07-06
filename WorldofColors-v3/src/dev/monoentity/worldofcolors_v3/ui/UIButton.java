package dev.monoentity.worldofcolors_v3.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.monoentity.worldofcolors_v3.Handy;
import dev.monoentity.worldofcolors_v3.input.MouseIn;

public class UIButton extends UIObject {

	private BufferedImage[] imgSprites;
	private BufferedImage buttonSprite;
	private ClickListener click;
	
	public UIButton(Handy hand, float ux, float uy, int uWidth, int uHeight, BufferedImage[] buttonSprites, MouseIn mouseIn, ClickListener buttonClick) {
		super(hand, ux, uy, uWidth, uHeight, mouseIn);
		imgSprites = buttonSprites;
		click = buttonClick;
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics g) {
		if(isHoveringOver())
			buttonSprite = imgSprites[1];
		else
			buttonSprite = imgSprites[0];
		g.drawImage(buttonSprite, (int)(x), (int)(y), width, height, null);
	}

	@Override
	public void onClick() {
		click.onClick();
	}

}
