package game;

import cnge.graphics.texture.Texture;
import static game.GameAssets.*;

public class Player extends Entity{
	
	boolean victory;
	
	boolean rightlock;
	boolean leftlock;
	
	boolean jumplock;
	boolean intime;
	int jumptime = 10;
	int jumptimer;
	int jumppass;
	
	int slidir;
	
	protected void baseops() {
		h=1.25f;
		w=1;
		lbuffer = 0.1f;
		rbuffer = 0.1f;
		dbuffer = 0.625f;
		ubuffer = 0.15f;
		image = new Img(new Texture[][]{{playerR0Texture},{playerR1Texture,playerR2Texture,playerR3Texture,playerR1Texture,playerR3Texture,playerR4Texture,playerR3Texture},{playerRUTexture},{playerRWTexture},{playerL0Texture},{playerL1Texture,playerL2Texture,playerL3Texture,playerL1Texture,playerL3Texture,playerL4Texture,playerL3Texture},{playerLUTexture},{playerLWTexture},{playerVTexture}},6);
		maxspeed = 0.18f;
		hspeed = 0.01f;
		friction = 0.8f;
		jumpheight = 0.14f;
		groundbuffer = 0.3f;
		solid = true;
		
		addgenerator(new ParticleGen(rbuffer,rbuffer,-ubuffer,dbuffer,Particle.WALLDUST,5,10,1));
		addgenerator(new ParticleGen(-lbuffer,-lbuffer,-ubuffer,dbuffer,Particle.WALLDUST,5,10,1));
		addgenerator(new ParticleGen(-lbuffer,rbuffer,-ubuffer,dbuffer,Particle.GIB,5,10,10));
		addgenerator(new ParticleGen(0,0,-ubuffer,0,Particle.HEADGIB,5,10,1));
		addgenerator(new ParticleGen(-lbuffer,rbuffer,-ubuffer-0.4,-ubuffer-0.2,Particle.SPARK,10,20,1));
	}
	
	public void tick() {
		
		super.tick();
		//SPACE//
		if(GameScene.edecay) {
			generators.get(4).active = true;
		}else {
			generators.get(4).active = false;
		}
		
		if(victory) {
			image.switchmode(8);
		}else {
			if(direction==DIRRIGHT) {
				if(slidir!=0){
					image.switchmode(3);
				}else {
					if(!onground) {
						image.switchmode(2);
					}else {
						if(hvel>0) {
							image.switchmode(1);
						}else {
							image.switchmode(0);
						}
					}
				}
			}else {
				if(slidir!=0){
					image.switchmode(7);
				}else {
					if(!onground) {
						image.switchmode(6);
					}else {
						if(hvel<0) {
							image.switchmode(5);
						}else {
							image.switchmode(4);
						}
					}
				}
			}
		}
		
		groundbuffer = Math.abs(hvel*2);
		hdecay = true;
		if(intime) {
			if(GameScene.keys[GameScene.UP]){
				if(jumppass==1) {
					rightlock=true;
					hvel = -maxspeed;
				}else if(jumppass==2) {
					leftlock=true;
					hvel = maxspeed;
				}
				vvel = -jumpheight;
			}else if(GameScene.keys[GameScene.LEFT] || GameScene.keys[GameScene.RIGHT]) {
				if(jumppass==1) {
					rightlock=true;
					vvel = -jumpheight/1.5f;
				}else if(jumppass==2) {
					leftlock=true;
					vvel = -jumpheight/1.5f;
				}
			}else {
				intime = false;
			}
			jumptimer++;
			if(jumptimer==jumptime) {
				intime = false;
			}
		}else {
			if(GameScene.keys[GameScene.UP]){
				if(!jumplock) {
					if(onground || slidir !=0) {
						jumplock=true;
						jumppass = slidir;
						intime=true;
						jumptimer=0;
					}
				}
			}else {
				jumplock = false;
				leftlock = false;
				rightlock = false;
			}
		}
		
		if(onground) {
			rightlock = false;
			leftlock = false;
		}
		
		if(GameScene.keys[GameScene.RIGHT] && !rightlock){
			direction = DIRRIGHT;
			hdecay = false;
			if(onground) {
				hvel += hspeed;
			}else {
				hvel += hspeed/2;
			}
		}
		if(GameScene.keys[GameScene.LEFT] && !leftlock){
			direction = DIRLEFT;
			hdecay = false;
			if(onground) {
				hvel -= hspeed;
			}else {
				hvel -= hspeed/2;
			}
		}
		if((GameScene.keys[GameScene.RIGHT] || GameScene.keys[GameScene.LEFT]) && (leftlock || rightlock)){
			hdecay = false;
		}
		
		if(GameScene.keys[GameScene.RIGHT] && walls[2]-y>5 && touching[1] && vvel>0) {
			vvel = vvel*0.78f;
			slidir = 1;
			generators.get(0).active = true;
			generators.get(1).active = false;
			direction = DIRLEFT;
		}else if(GameScene.keys[GameScene.LEFT] && walls[2]-y>5 && touching[3] && vvel>0) {
			vvel = vvel*0.78f;
			slidir = 2;
			generators.get(0).active = false;
			generators.get(1).active = true;
			direction = DIRRIGHT;
		}else {
			slidir = 0;
			generators.get(0).active = false;
			generators.get(1).active = false;
		}
		
		if(y>GameScene.currentlevel.mapheight+GameScene.deathzone) {
			GameScene.energy = 0;
		}
		if(GameScene.currentlevel.mapaccess((int)Math.round(x),(int)Math.round(y+1))==Level.FINISH){
			if(!victory) {
				victory = true;
				GameScene.win();
			}
		}
	}

}
