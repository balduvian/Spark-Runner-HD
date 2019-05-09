package game;

import cnge.graphics.texture.Texture;

public class County extends GUI{
	
	public void baseops() {
		image = new Img(new Texture[][]{{GameAssets.count3Texture},{GameAssets.count2Texture},{GameAssets.count1Texture},{GameAssets.countGoTexture}},0);
		x = 96;
		y = -64;
		w = 64;
		h = 64;
		glide(96,40,16);
	}
	
	public void glidecomplete() {
		if(gs==1) {
			glide(96,40,16);
		}
		else if(gs==2) {
			glide(96,208,16);
		}
		else if(gs==3) {
			jump(96,-64);
			image.switchmode(1);
			glide(96,40,16);
		}
		else if(gs==4) {
			glide(96,40,16);
		}
		else if(gs==5) {
			glide(96,208,16);
		}
		else if(gs==6) {
			jump(96,-64);
			image.switchmode(2);
			glide(96,40,16);
		}
		else if(gs==7) {
			glide(96,40,16);
		}
		else if(gs==8) {
			glide(96,208,16);
		}
		else if(gs==9) {
			w = 96;
			jump(96,-64);
			image.switchmode(3);
			glide(96,40,16);
		}
		else if(gs==10) {
			glide(96,40,16);
		}
		else if(gs==11) {
			destroy();
		}
		
	}
}
