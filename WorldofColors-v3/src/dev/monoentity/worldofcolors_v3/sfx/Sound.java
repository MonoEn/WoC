package dev.monoentity.worldofcolors_v3.sfx;

import java.applet.Applet;
import java.applet.AudioClip;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import dev.monoentity.worldofcolors_v3.Handy;

public class Sound {

	public static AudioClip hurt;
	public static AudioClip bird;
	public static AudioClip lose;
	public static AudioClip clear;
	public static AudioClip night;
	public static AudioClip dawn;
	public static AudioClip rain;
	public static AudioClip bells;
	public static AudioClip brick;
	public static AudioClip wind;
	public static AudioClip cubegel;
	public static AudioClip sadgel;
	public static AudioClip purchase = Applet.newAudioClip(Sound.class.getResource("/sfx/purchase.wav"));
	public static AudioClip notEnough = Applet.newAudioClip(Sound.class.getResource("/sfx/error.wav"));
	public static AudioClip shop = Applet.newAudioClip(Sound.class.getResource("/sfx/shop.wav"));
	public static AudioClip coin = Applet.newAudioClip(Sound.class.getResource("/sfx/coin.wav"));
	public static AudioClip jump;

	// public FloatControl bgmVolume = (FloatControl)
	// ((Clip)bgm).getControl(FloatControl.Type.MASTER_GAIN);

	private Handy hand;

	public Sound(Handy hand, String path) {
		this.hand = hand;
		getSounds();
	}

	public static void getSounds() {
		hurt = Applet.newAudioClip(Sound.class.getResource("/sfx/hurt.wav"));
		bird = Applet.newAudioClip(Sound.class.getResource("/sfx/bird.wav"));
		lose = Applet.newAudioClip(Sound.class.getResource("/sfx/lose.wav"));
		clear = Applet.newAudioClip(Sound.class.getResource("/sfx/clear.wav"));
		night = Applet.newAudioClip(Sound.class.getResource("/sfx/night.wav"));
		dawn = Applet.newAudioClip(Sound.class.getResource("/sfx/dawn.wav")); 
		bells = Applet.newAudioClip(Sound.class.getResource("/sfx/bells.wav"));
		brick = Applet.newAudioClip(Sound.class.getResource("/sfx/brick.wav"));
		wind  = Applet.newAudioClip(Sound.class.getResource("/sfx/wind.wav"));
		cubegel = Applet.newAudioClip(Sound.class.getResource("/sfx/cubegel.wav"));
		sadgel = Applet.newAudioClip(Sound.class.getResource("/sfx/sadgel.wav"));
		jump = Applet.newAudioClip(Sound.class.getResource("/sfx/jump.wav"));
	}

	public Handy getHand() {
		return hand;
	}

	public void setHand(Handy hand) {
		this.hand = hand;
	}

}
