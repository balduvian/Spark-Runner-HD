package game;

import cnge.graphics.texture.Texture;

public class TitleCard extends GUI {
	
	public void baseops() {
		x=0;
		y=0;
		w = 256;
		h = 192;
		image = new Img(new Texture[][]{{GameAssets.titleCardTexture}},0);
	}
	
}
