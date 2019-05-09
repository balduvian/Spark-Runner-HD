package game;

public class Particle extends Entity {
	
	public static final int WALLDUST = 1;
	public static final int GIB = 2;
	public static final int HEADGIB = 3;
	public static final int SPARK = 4;
	
	double hlo;
	double hhi;
	double vlo;
	double vhi;
	double sizlo;
	double sizhi;
	int timelo;
	int timehi;
	
	int parttype;
	int timer;
	int lifetime;
	
	public static Particle factory(int pt) {
		if(pt==WALLDUST) {
			return new Dust();
		}else if(pt==GIB) {
			return new Gib();
		}else if(pt==HEADGIB){
			return new HeadGib();
		}else if(pt==SPARK){
			return new Spark();
		}else {
			return null;
		}
	}
	
	public void baseops() {
		partops();
		partset();
	}
	
	public void partops() {
		
	}
	
	public void partset(){
		hvel = GameScene.rando(hlo,hhi);
		vvel = GameScene.rando(vlo, vhi);
		float s = GameScene.rando(sizlo, sizhi);
		w = s;
		h = s;
		lifetime = (int)GameScene.rando(timelo,timehi);
		timer = 0;
	}
	
	public void tick() {
		super.tick();
		timer++;
		image.frame = (int)(image.frames[image.mode]*((double)timer/lifetime));
		if(timer==lifetime) {
			destroy();
		}
	}
	
}
