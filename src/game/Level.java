package game;

import java.util.ArrayList;

public class Level {
	
	public static final int AIR = 0;
	public static final int WALL = 1;
	public static final int START = 2;
	public static final int FINISH = 3;
	
	int[][] map;
	int mapwidth;
	int mapheight;
	
	int spawny;
	int spawnx;
	
	int finy;
	int finx;
	
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	int nume;
	
	public int mapaccess(int x, int y) {
		if(x>-1 && x< mapwidth) {
			if(y<0) {
				if(map[0][x] != Level.AIR) {
					return Level.WALL;//above bounds
				}else {
					return Level.AIR;
				}
			}else if(y>=GameScene.currentlevel.mapheight){
				if(map[GameScene.currentlevel.mapheight-1][x] != Level.AIR) {
					return Level.WALL;//above bounds
				}else {
					return Level.AIR;
				}
			}else {
				return map[y][x];//normal, in bounds
			}
		}else {
			return Level.WALL;//outside of bounds on sides
		}
	}
	
	public Level(int width, int height){
		mapheight = height;
		mapwidth = width;
		map = new int[mapheight][mapwidth];
	}
	
	public Level() {
		
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
		nume++;
	}
	
	public void finalize(int[][] m) {
		map = m;
		mapheight = m.length;
		mapwidth = m[0].length;
		
		for(int y=0;y<mapheight;y++) {
			for(int x=0;x<mapwidth;x++) {
				if(map[y][x]==START) {
					spawny = y-1;
					spawnx = x;
				}else if(map[y][x]==FINISH) {
					finy = y-1;
					finx = x;
				}
			}
		}
	}
}
