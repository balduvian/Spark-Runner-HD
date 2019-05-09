package game;

import cnge.core.*;
import cnge.graphics.Transform;
import org.joml.Matrix4f;
import org.joml.Vector4f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class GameScene extends Scene {

    private static long goal = 0;

    public static final float OSWIDTH = 16;
    public static final float OSHEIGHT = 9;
    public static final double HALFH = OSHEIGHT/2;
    public static final double HALFW = OSWIDTH/2;

    public static final int GUIWIDTH = 256;
    public static final int GUIHEIGHT = 144;
    public static final int GUIHALFW = GUIWIDTH/2;
    public static final int GUIHALFH = GUIHEIGHT/2;

    static int level;

    static boolean cutscene;////

    static float globalx;
    static float globaly;
    static float gravity = 0.01f;
    static float terminal = 2;
    static float deathzone = 5;

    static Level[] levels;
    public static int numlevels;
    static Level currentlevel;

    public static int energy;
    public static final int MAXENERGY = 60*7;
    public static boolean edecay;

    public static final int LOADING = 0;
    public static final int MENU = 1;
    public static final int GAME = 2;
    static int gamemode = LOADING;

    public static Entity follow;

    static public ArrayList<Entity> entities = new ArrayList<Entity>();
    static int nume =0;

    static public ArrayList<GUI> guis = new ArrayList<GUI>();
    static int numg =0;

    public static int viewup;
    public static int viewright;
    public static int viewdown;
    public static int viewleft;
    public static int viewvert;
    public static int viewhor;
    public static int viewhbuffer;
    public static int viewvbuffer;
    public static int[][] templevel;

    public static boolean timeron = false;
    public static int timer;
    public static int timergoal;
    public static int timerflag;

    public static final int GOFLAG = 0;
    public static final int RESPAWNFLAG = 1;

    public static boolean[] keys = new boolean[4];

    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;

    public GameScene(Class<? extends AssetBundle>... unloads) {
        super(unloads, GameLoadScreen.class, GameAssets.class);
    }

    @Override
    public void sceneStart() {
        loadLevels();
        gotomenu();
    }

    @Override
    public void update() {
        //poll//
        keys[UP] = window.keyPressed(GLFW_KEY_W) || window.keyPressed(GLFW_KEY_SPACE);
        keys[RIGHT] = window.keyPressed(GLFW_KEY_D);
        keys[DOWN] = window.keyPressed(GLFW_KEY_S);
        keys[LEFT] = window.keyPressed(GLFW_KEY_A);

        for(int i=0;i<numg;i++){
            guis.get(i).tick();
        }
        if(timeron) {
            timer++;
            //System.out.println(timer);
            if(timer>=timergoal) {
                timeron=false;
                timercomplete();
            }
        }

        if(cutscene) {
            keys[UP] = false;
            keys[RIGHT] = false;
            keys[DOWN] = false;
            keys[LEFT] = false;
        }

        if(gamemode == GAME) {

            for(int i=0;i<nume;i++){
                entities.get(i).tick();
            }

            if(follow !=null) {
                float fy = follow.y;
                if(fy > currentlevel.mapheight) {
                    fy = currentlevel.mapheight;
                }/*else if(fy < viewvbuffer-1) {
					fy = viewvbuffer-1;
				}*/
                float fx = follow.x;
                //if(fx > currentlevel.mapwidth) {
                //    fx = currentlevel.mapwidth;
               // }else if(fx < viewhbuffer) {
               //     fx = viewhbuffer;
               // }
                globaly = fy;
                globalx = fx;
                camera.getTransform().setSize(16, 9);
                camera.getTransform().setCenter(fx, fy);
            }

            if(edecay) {
                energy--;
                if(energy<0) {
                    die();
                    edecay=false;
                }
            }

            viewup = (int)(globaly - 0.5 - OSHEIGHT / 2) - 1;
            viewright = (int)(globalx + 0.5 + OSWIDTH / 2) + 1;
            viewdown = (int)(globaly + 0.5 + OSHEIGHT / 2) + 1;
            viewleft = (int)(globalx - 0.5 - OSWIDTH / 2) - 1;
            viewvert = viewdown - viewup;
            viewhor = viewright - viewleft;
            viewvbuffer = viewvert / 2;
            viewhbuffer = viewhor / 2 - 1;

            int[][] temp = new int[viewvert][viewhor];
            for(int y=viewup;y<viewdown;++y){
                for(int x=viewleft;x<viewright;++x){
                    temp[y-viewup][x-viewleft] = currentlevel.mapaccess(x,y);
                }
            }
            templevel = temp;

        }
    }

    public float psuedo(float a, float b) {
        double ap = a * 12.9898;
        double bp = b * 78.233;
        double m = Math.cos(bp - ap);
        double app = Math.sin(ap * m) * 43758.5453;
        double bpp = Math.sin(bp * m) * 43758.5453;
        double f = Math.abs(app - bpp);
        return (float)(f - Math.floor(f));
    }

    @Override
    public void render() {
        camera.setDims(OSWIDTH, OSHEIGHT);

        float backWid = 5;

        int hh = (int)Math.ceil(OSHEIGHT / backWid) + 1;
        int ww = (int)Math.ceil(OSWIDTH / backWid) + 1;

        for(int y = -1; y < hh; ++y) {
            for(int x = -1; x < ww; ++x) {
                GameAssets.skyTexture.bind();
                GameAssets.textureShader.enable();
                GameAssets.textureShader.setMvp(camera.getMP(camera.getMDims(x * backWid - ((globalx * 0.25f) % backWid), y * backWid - ((globaly * 0.25f) % backWid), backWid, backWid)));
                GameAssets.rect.render();
            }
        }

        if(gamemode == GAME) {

            for(int y = 0; y < viewvert; ++y){
                for(int x = 0; x < viewhor; ++x){
                    float acy = y + viewup - 0.5f;
                    float acx = x + viewleft - 0.5f;

                    //draw blocks
                    //GameAssets.textureShader.enable();
                    GameAssets.textureShader.setMvp(camera.getMVP(camera.getMDims(acx, acy, 1, 1)));

                    if(templevel[y][x] == Level.WALL){
                        if(psuedo(acx + level, acy - level) > 0.5) {
                            GameAssets.block0Texture.bind();
                        }else {
                            GameAssets.block1Texture.bind();
                        }
                        GameAssets.rect.render();
                    }else if(templevel[y][x] == Level.START){
                        GameAssets.blockStartTexture.bind();
                        GameAssets.rect.render();
                    }else if(templevel[y][x] == Level.FINISH){
                        GameAssets.blockFinishTexture.bind();
                        GameAssets.rect.render();
                    }
                }
            }

            for(int i = 0; i < nume; ++i) {
                Entity e = entities.get(i);

                e.image.getimage().bind();
                //GameAssets.textureShader.enable();
                GameAssets.textureShader.setMvp(camera.getMVP(camera.getMDims(e.x - e.w / 2, e.y - e.h / 2, e.w, e.h)));
                GameAssets.rect.render();
            }
        }

        camera.setDims(256, 144);

        for(int i = 0; i < numg; ++i) {
            GUI e = guis.get(i);

            if(e.image.getimage() != null) {
                e.image.getimage().bind();
            } else {
                System.out.println(e);
            }
            GameAssets.textureShader.enable();
            GameAssets.textureShader.setMvp(camera.getMP(camera.getMDims(e.x, e.y, e.w, e.h)));
            GameAssets.rect.render();
        }

    }

    public static void gotomenu() {
        gcreate(new TitleCard());
        gcreate(new Play());
        level = 0;
    }

    public static float rando(double lo, double hi) {
        return (float)(Math.random()*(hi-lo)+lo);
    }

    public static void ecreate(Entity e, float px, float py) {
        e.x = px;
        e.y = py;
        entities.add(e);
        nume++;
    }

    public static void edelete(int indx) {
        entities.remove(indx);
        for(int i=indx;i<nume-1;i++) {
            entities.get(i).index--;
        }
        nume--;
    }

    public static void eclear() {
        entities.clear();
        nume=0;
    }

    public static void gcreate(GUI g) {
        guis.add(g);
        numg++;
    }

    public static void gdelete(int indx) {
        guis.remove(indx);
        for(int i=indx;i<numg-1;i++) {
            guis.get(i).index--;
        }
        numg--;
    }

    public static void gclear() {
        guis.clear();
        numg=0;
    }

    public static void startlevel(int l) {
        gclear();
        eclear();
        if(l<numlevels) {
            gamemode = LOADING;
            cutscene = true;
            level = l;
            energy = MAXENERGY;
            edecay = false;
            currentlevel = levels[level];
            Player p = new Player();
            follow = p;
            ecreate(p,currentlevel.spawnx,currentlevel.spawny);
            EnergyBar e = new EnergyBar();
            e.image.switchmode(0);
            gcreate(e);
            gcreate(new Bar());
            e = new EnergyBar();
            e.image.switchmode(1);
            gcreate(e);
            for(int i=0;i<currentlevel.nume;i++) {
                ecreate(new Battery(),currentlevel.entities.get(i).x,currentlevel.entities.get(i).y);
            }
            gamemode = GAME;
            gcreate(new County());
            starttimer(GOFLAG,144);
        }else {
            gotomenu();
        }
    }

    public void die() {
        follow.generators.get(2).generate();
        follow.generators.get(3).generate();
        edelete(follow.index);
        starttimer(RESPAWNFLAG,120);
    }

    public static void win() {
        edecay = false;
        cutscene = true;
        level++;
        starttimer(RESPAWNFLAG,80);
    }

    public static void starttimer(int flag, int goal) {
        timeron= true;
        timerflag = flag;
        timergoal = goal;
        timer = 0;
    }

    public static void timercomplete() {
        if(timerflag==GOFLAG) {
            cutscene = false;
            edecay = true;
        }else if(timerflag==RESPAWNFLAG) {
            startlevel(level);
        }
    }

    public void loadLevels(){
        String[] paths = {
                "res/levels/level1.pbm",
                "res/levels/level2.pbm",
                "res/levels/level3.pbm",
                "res/levels/level4.pbm",
                "res/levels/level5.pbm",
        };
        numlevels = paths.length;
        levels = new Level[numlevels];
        for(int f=0;f<numlevels;f++) {
            levels[f] = new Level();
            try {
                int[] wall = {0,0,0}; int[] start = {0,0,255}; int[] finish = {0,255,0}; int[] battery = {128,128,128};

                int[][] temp = null;
                FileInputStream s = new FileInputStream(paths[f]);
                int max = s.available();
                int line = 0;
                String tempwid = "";
                String temphih = "";
                int wid = 0;
                int hih = 0;
                int[] bitread = new int[3];
                int bindex = 0;
                int ys = 0;
                int xs = 0;
                for(int i=0;i<max;i++) {
                    int now = s.read();
                    if(now==10) {
                        if(line==1){
                            wid = Integer.parseInt(tempwid);
                        }else if(line==2) {
                            hih = Integer.parseInt(temphih);
                            temp = new int[hih][wid];
                        }
                        line++;
                    }else {
                        if(line==1) {
                            tempwid += (char)(now);
                        }else if(line==2) {
                            temphih += (char)(now);
                        }else if(line==4) {
                            bitread[bindex] = now;
                            bindex++;
                            if(bindex==3) {
                                bindex = 0;
                                if(bitread[0] == wall[0] && bitread[1] == wall[1]  && bitread[2] == wall[2]) {
                                    temp[ys][xs] = Level.WALL;
                                }else if(bitread[0] == start[0] && bitread[1] == start[1]  && bitread[2] == start[2]) {
                                    temp[ys][xs] = Level.START;
                                }else if(bitread[0] == finish[0] && bitread[1] == finish[1]  && bitread[2] == finish[2]) {
                                    temp[ys][xs] = Level.FINISH;
                                }else if(bitread[0] == battery[0] && bitread[1] == battery[1]  && bitread[2] == battery[2]) {
                                    Battery b = new Battery();
                                    b.x = xs;
                                    b.y = ys;
                                    levels[f].addEntity(b);
                                }
                                xs++;
                                if(xs==wid) {
                                    xs=0;
                                    ys++;
                                }
                            }
                        }
                    }
                }
                s.close();
                levels[f].finalize(temp);
            }catch(Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
