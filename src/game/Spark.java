package game;

import cnge.graphics.texture.Texture;

public class Spark extends Particle{
	public void partops() {
		phys = false;
		solid = false;
		image = new Img(new Texture[][]{{GameAssets.sparkTexture}},0);
		image.pause();
		hlo = -0.04;
		hhi = 0.04;
		vlo = -0.02;
		vhi = 0.04;
		sizlo = 0.2;
		sizhi = 0.3;
		timelo = 5;
		timehi = 15;
	}
}
