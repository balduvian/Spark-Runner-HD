package game;

import cnge.graphics.texture.Texture;

public class Gib extends Particle{
	
	public void partops() {
		phys = true;
		solid = false;
		image = new Img(new Texture[][]{{GameAssets.gib1Texture},{GameAssets.gib2Texture}},0);
		image.switchmode((int)(Math.random()*2));
		hlo = -0.09;
		hhi = 0.09;
		vlo = -0.25;
		vhi = -0.05;
		sizlo = 0.45;
		sizhi = 0.55;
		timelo = 1000;
		timehi = 2000;
		friction = 0.8f;
		hdecay = false;
	}
}
