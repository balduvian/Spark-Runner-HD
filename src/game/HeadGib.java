package game;

import cnge.graphics.texture.Texture;

public class HeadGib extends Particle{
	
	public void partops() {
		phys = true;
		solid = false;
		image = new Img(new Texture[][]{{GameAssets.gib0Texture}},0);
		hlo = -0.1;
		hhi = 0.1;
		vlo = -0.25;
		vhi = -0.05;
		sizlo = 1;
		sizhi = 1;
		timelo = 1000;
		timehi = 2000;
		friction = 0.8f;
		hdecay = false;
	}
}
