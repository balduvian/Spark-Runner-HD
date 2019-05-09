package game;

import cnge.graphics.texture.Texture;

public class Bar extends GUI {
	
	public void baseops(){
		image = new Img(new Texture[][]{{GameAssets.bar2Texture},{GameAssets.bar1Texture},{GameAssets.bar0Texture}},0);
		w = 160;
		h = 16;
		x = 44;
 		y = 6;
	}
	
	public void tick() {
		w = (int)(((double)GameScene.energy/GameScene.MAXENERGY)*160);
		x = 48;
		image.switchmode((int)Math.ceil(((double)GameScene.energy/GameScene.MAXENERGY)*2));
	}

}
