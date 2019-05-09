package game;

import cnge.graphics.texture.Texture;

public class Dust extends Particle{
	
	public void partops() {
		phys = false;
		solid = false;
		image = new Img(new Texture[][]{{GameAssets.dust0Texture,GameAssets.dust1Texture,GameAssets.dust2Texture,GameAssets.dust3Texture}},0);
		image.pause();
		hlo = -0.02;
		hhi = 0.02;
		vlo = -0.04;
		vhi = 0.04;
		sizlo = 0.2;
		sizhi = 0.3;
		timelo = 15;
		timehi = 40;
	}
}
