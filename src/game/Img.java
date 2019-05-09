package game;

import cnge.graphics.texture.Texture;

public class Img {
	
	Texture[][] imgs;
	int modes;
	int[] frames;
	int mode;
	int frame;
	int frametime;
	int animtimer=0;
	boolean paused = false;
	
	public Img(Texture[][] inp, int tim) {
		frametime = tim;
		imgs = inp;
		modes = imgs.length;
		frames = new int[modes];
		for(int i=0;i<modes;i++) {
			frames[i] = imgs[i].length;
		}
	}
	
	public void tick() {
		if(!paused) {
			animtimer++;
			if(animtimer>=frametime) {
				frame = (frame+1)%frames[mode];
				animtimer = 0;
			}
		}
	}
	
	public void pause() {
		paused = true;
	}
	public void play() {
		paused = false;
	}
	
	public void switchmode(int m) {
		int oldm = mode;
		if(m!=oldm) {
			frame = 0;
			mode = m;
		}
	}
	
	public Texture getimage() {
		return imgs[mode][frame];
	}
}
