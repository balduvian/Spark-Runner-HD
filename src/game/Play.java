package game;

import cnge.core.CNGE;
import cnge.graphics.texture.Texture;

public class Play extends GUI {

	public void baseops() {
		x=0;
		y=112;
		w = 256;
		h = 24;
		image = new Img(new Texture[][]{{GameAssets.start0Texture,GameAssets.start1Texture}},48);
	}
	
	public void tick() {
		super.tick();
		if(GameScene.keys[GameScene.UP] || GameScene.keys[GameScene.RIGHT] || GameScene.keys[GameScene.DOWN] || GameScene.keys[GameScene.LEFT]) {
			GameScene.startlevel(GameScene.level);
		}
	}
}
