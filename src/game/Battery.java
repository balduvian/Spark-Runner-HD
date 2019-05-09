package game;
import cnge.graphics.texture.Texture;

import static game.GameAssets.*;

public class Battery extends Entity{
	
	public void baseops() {
		w =1;
		h=1;
		phys=false;
		solid = false;
		image = new Img(new Texture[][]{{battery0Texture,battery0Texture,battery0Texture,battery0Texture,battery0Texture,battery0Texture,battery0Texture,battery0Texture,battery0Texture,battery0Texture,battery0Texture,battery0Texture,battery1Texture,battery2Texture,battery3Texture,battery4Texture,battery5Texture,battery6Texture,}},8);
	}
	
	public void tick() {
		super.tick();
		if(Math.abs(GameScene.follow.x-x)<w && Math.abs(GameScene.follow.y-y)<h) {
			GameScene.energy = GameScene.MAXENERGY;
			destroy();
		}
	}
}
