package game;

import java.util.ArrayList;

public class Entity {
	
	float x;
	float y;
	float w=1;
	float h=1;
	float lbuffer;
	float rbuffer;
	float ubuffer;
	float dbuffer;
	float groundbuffer;

	float hspeed;
	float jumpheight;
	float maxspeed = 100;
	float friction = 0.8f;

	float hvel;
	float vvel;
	boolean hdecay;
	boolean vdecay;
	
	public static final int DIRRIGHT = 0;
	public static final int DIRLEFT = 1;
	int direction = DIRRIGHT;
	
	boolean solid = true;
	boolean phys = true;
	
	Img image;
	
	int index;
	
	int etype;
	
	float[] walls = new float[4];
	boolean[] touching;
	boolean onground;
	
	ArrayList<ParticleGen> generators = new ArrayList<ParticleGen>();
	int numgenerators;
	
	public void addgenerator(ParticleGen p) {
		generators.add(p);
		++numgenerators;
	}
	
	public Entity() {
		baseops();
		if(generators != null) {
			numgenerators = generators.size();
		}
		index = GameScene.nume;
	}
	
	protected void baseops() {
		//overwrite here//
	}
	
	public void tick() {
		image.tick();
		onground = groundcheck();
		getwalls();
		
		y += vvel;
		x += hvel;
		
		if(phys) {
			vvel += GameScene.gravity;
			if(vvel > GameScene.terminal) {
				vvel = GameScene.terminal;
			}
		}else {
			if(vvel>maxspeed) {
				vvel *= friction;
			}else if(vvel < -maxspeed) {
				vvel *= friction;
			}
			if(vdecay) {
				vvel = vvel * friction;
				if(Math.abs(vvel) < 0.01) {
					vvel = 0;
				}
			}
		}
		
		if(hvel>maxspeed) {
			hvel *= friction;
		}else if(hvel<-maxspeed) {
			hvel *= friction;
		}
		if(hdecay) {
			if(onground) {
				hvel = hvel*friction;
			}else {
				hvel = (float)(hvel*Math.sqrt(friction));
			}
			if(Math.abs(hvel)<0.01) {
				hvel = 0;
			}
		}
		
		if(solid) {
			touching = new boolean[4];
			if(y>walls[2]) {
				touching[2]=true;
				vvel = 0;
				y = walls[2];
			}else if(y<walls[0]) {
				touching[0]=true;
				vvel = 0;
				y = walls[0];
			}
			
			if(x>walls[1]) {
				touching[1]=true;
				hvel = 0;
				x = walls[1];
			}else if(x<walls[3]) {
				touching[3]=true;
				hvel = 0;
				x = walls[3];
			}
		}
		
		for(int i=0;i<numgenerators;i++) {
			generators.get(i).tick();
		}
	}
	
	public int superround(double v){
		if(v%1>0.501){
			return (int)v+1;
		}else{
			return (int)v;
		}
	}
	
	public boolean groundcheck() {

		double le = x-lbuffer-groundbuffer;
		if(le<walls[3]) {
			le = walls[3];
		}
		int leftx = (int)Math.round(le);
		
		double re = x+rbuffer+groundbuffer;
		if(re>walls[1]) {
			re = walls[1];
		}
		int rightx = (int)Math.round(re);
		
		int midy = (int)Math.round(y);
		
		double flor =0;
		if(GameScene.currentlevel.mapaccess(leftx,midy+1) != Level.AIR || GameScene.currentlevel.mapaccess(rightx,midy+1) != Level.AIR ) {
			flor = midy+0.5-dbuffer;
		}else{
			flor = Integer.MAX_VALUE;
		}
		return (flor-y<0.05);
	}
	
	public void getwalls() {
		
		int leftx = (int)Math.round(x-lbuffer);
		int midx = (int)Math.round(x);
		int rightx = (int)superround(x+rbuffer);
		
		int upy = (int)Math.round(y-ubuffer);
		int midy = (int)Math.round(y);
		int downy = (int)superround(y+dbuffer);
		
			if(GameScene.currentlevel.mapaccess(leftx,midy-1) != Level.AIR || GameScene.currentlevel.mapaccess(rightx,midy-1) != Level.AIR ) {
				walls[0] = midy-0.5f+ubuffer;
			}else{
				walls[0] = Integer.MIN_VALUE;
			}
			
			if(GameScene.currentlevel.mapaccess(midx+1,upy) != Level.AIR || GameScene.currentlevel.mapaccess(midx+1,downy) != Level.AIR ) {
				walls[1] = midx+0.5f-rbuffer;
			}else {
				walls[1] = Integer.MAX_VALUE;
			}
			
			if(GameScene.currentlevel.mapaccess(leftx,midy+1) != Level.AIR || GameScene.currentlevel.mapaccess(rightx,midy+1) != Level.AIR ) {
				walls[2] = midy+0.5f-dbuffer;
			}else{
				walls[2] = Integer.MAX_VALUE;
			}
			
			if(GameScene.currentlevel.mapaccess(midx-1,upy) != Level.AIR || GameScene.currentlevel.mapaccess(midx-1,downy) != Level.AIR ) {
				walls[3] = midx-0.5f+lbuffer;
			}else {
				walls[3] = Integer.MIN_VALUE;
			}
	}
	
	public class ParticleGen {
		
		boolean active;
		double xlo;
		double xhi;
		double ylo;
		double yhi;
		int ptype;
		int spawntimelo;
		int spawntimehi;
		int burst;
		int timenow;
		int timer;
		
		public ParticleGen(double xl, double xh, double yl, double yh, int pt, int sl, int sh, int bu) {
			xlo = xl;
			xhi = xh;
			ylo = yl;
			yhi = yh;
			ptype = pt;
			spawntimelo = sl;
			spawntimehi = sh;
			timer = 0;
			active = false;
			burst = bu;
			gettime();
		}
		
		public void tick() {
			timer++;
			if(active && timer>=timenow) {
				generate();
				timer = 0;
				gettime();
			}
		}
		
		public void generate() {
			for(int i=0;i<burst;i++) {
				Particle p = Particle.factory(ptype);
				float xn = GameScene.rando(xlo,xhi);
				float yn = GameScene.rando(ylo, yhi);
				GameScene.ecreate(p, x+xn, y+yn);
			}
		}
		
		public void gettime() {
			timenow = (int)GameScene.rando(spawntimelo,spawntimehi);
		}
	}
	
	public void destroy() {
		GameScene.edelete(index);
	}

}
