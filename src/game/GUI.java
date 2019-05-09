package game;

public class GUI {
	
	int index;
	
	int gs = 0;
	
	float x;
	float y;
	float w;
	float h;
	
	int timer;
	int timergoal;
	double xper;
	double yper;
	float targetx;
	float targety;
	boolean moving;
	
	Img image;
	
	public GUI() {
		baseops();
		index = GameScene.numg;
	}
	
	public void baseops() {
		//override this//
	}
	
	public void destroy() {
		GameScene.gdelete(index);
	}
	
	public void tick() {
		timer++;
		if(moving) {
			if(Math.abs(x-targetx)>xper) {
				x += xper;
			}else {
				x = targetx;
			}
			if(Math.abs(y-targety)>yper) {
				y += yper;
			}else {
				y = targety;
			}
			if(timer >= timergoal) {
				y = targety;
				x = targetx;
				moving = false;
				gs++;
				glidecomplete();
			}
		}
		image.tick();
	}
	
	public void glide(float tox, float toy, int time) {
		moving = true;
		targetx = tox;
		targety = toy;
		timergoal = time;
		xper = (targetx-x)/timergoal;
		yper = (targety-y)/timergoal;
		timer = 0;
	}
	
	public void jump(float tox, float toy) {
		x = tox;
		y = toy;
	}
	
	public void glidecomplete() {
		//override here//
	}

}
