package game;

import cnge.graphics.texture.Texture;

public class EnergyBar extends GUI{
	
	public void baseops(){
		image = new Img(new Texture[][]{{GameAssets.barBackTexture},{GameAssets.energyBarTexture}},0);
		w = 168;
		h = 36;
		x = 44;
 		y = 2;
	}
}
