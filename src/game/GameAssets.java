package game;

import cnge.core.AssetBundle;
import cnge.core.Font;
import cnge.graphics.FrameBuffer;
import cnge.graphics.shape.*;
import cnge.graphics.sound.Sound;
import cnge.graphics.texture.MultisampleTexture;
import cnge.graphics.texture.Texture;
import game.shaders.*;

import static cnge.graphics.texture.TexturePreset.TP;

public class GameAssets extends AssetBundle {

    public static final int GAME_ASSETS_LOAD_NUMBER = 49;

    /*
     *
     */
    public static RectShape rect;
    public static TextureShader textureShader;
    public static ColorShader colorShader;
    /*
     *
     */
    public static Texture block0Texture;
    public static Texture block1Texture;
    public static Texture blockStartTexture;
    public static Texture blockFinishTexture;

    public static Texture skyTexture;

    public static Texture bar0Texture;
    public static Texture bar1Texture;
    public static Texture bar2Texture;

    public static Texture count3Texture;
    public static Texture count2Texture;
    public static Texture count1Texture;
    public static Texture countGoTexture;

    public static Texture darkenTexture;

    public static Texture dust0Texture;
    public static Texture dust1Texture;
    public static Texture dust2Texture;
    public static Texture dust3Texture;

    public static Texture energyBarTexture;
    public static Texture barBackTexture;

    public static Texture gib0Texture;
    public static Texture gib1Texture;
    public static Texture gib2Texture;

    public static Texture start0Texture;
    public static Texture start1Texture;

    public static Texture playerL0Texture;
    public static Texture playerL1Texture;
    public static Texture playerL2Texture;
    public static Texture playerL3Texture;
    public static Texture playerL4Texture;
    public static Texture playerLUTexture;
    public static Texture playerLWTexture;

    public static Texture playerR0Texture;
    public static Texture playerR1Texture;
    public static Texture playerR2Texture;
    public static Texture playerR3Texture;
    public static Texture playerR4Texture;
    public static Texture playerRUTexture;
    public static Texture playerRWTexture;

    public static Texture playerVTexture;

    public static Texture titleCardTexture;

    public static Texture sparkTexture;

    public static Texture battery0Texture;
    public static Texture battery1Texture;
    public static Texture battery2Texture;
    public static Texture battery3Texture;
    public static Texture battery4Texture;
    public static Texture battery5Texture;
    public static Texture battery6Texture;
    /*
     *
     */

    public GameAssets() {
        super(GAME_ASSETS_LOAD_NUMBER);
    }

    @Override
    public void loadRoutine() {
        doLoad(rect = new RectShape());
        doLoad(textureShader = new TextureShader());
        doLoad(colorShader = new ColorShader());

        doLoad(block0Texture = new Texture("res/images/blocky0.png"), TP());
        doLoad(block1Texture = new Texture("res/images/blocky1.png"), TP());

        doLoad(blockStartTexture = new Texture("res/images/start.png"), TP());
        doLoad(blockFinishTexture = new Texture("res/images/finish.png"), TP());

        doLoad(skyTexture = new Texture("res/images/sky.png"), TP());

        doLoad(bar0Texture = new Texture("res/images/bar0.png"), TP());
        doLoad(bar1Texture = new Texture("res/images/bar1.png"), TP());
        doLoad(bar2Texture = new Texture("res/images/bar2.png"), TP());

        doLoad(gib0Texture = new Texture("res/images/gib0.png"), TP());
        doLoad(gib1Texture = new Texture("res/images/gib1.png"), TP());
        doLoad(gib2Texture = new Texture("res/images/gib2.png"), TP());

        doLoad(start0Texture = new Texture("res/images/start0.png"), TP());
        doLoad(start1Texture = new Texture("res/images/start1.png"), TP());

        doLoad(count1Texture = new Texture("res/images/countdown1.png"), TP());
        doLoad(count2Texture = new Texture("res/images/countdown2.png"), TP());
        doLoad(count3Texture = new Texture("res/images/countdown3.png"), TP());
        doLoad(countGoTexture = new Texture("res/images/countdowngo.png"), TP());

        doLoad(barBackTexture = new Texture("res/images/barback.png"), TP());
        doLoad(energyBarTexture = new Texture("res/images/energybar.png"), TP());

        doLoad(dust0Texture = new Texture("res/images/dust0.png"), TP());
        doLoad(dust1Texture = new Texture("res/images/dust1.png"), TP());
        doLoad(dust2Texture = new Texture("res/images/dust2.png"), TP());
        doLoad(dust3Texture = new Texture("res/images/dust3.png"), TP());

        doLoad(playerL0Texture = new Texture("res/images/sparkyl0.png"), TP());
        doLoad(playerL1Texture = new Texture("res/images/sparkyl1.png"), TP());
        doLoad(playerL2Texture = new Texture("res/images/sparkyl2.png"), TP());
        doLoad(playerL3Texture = new Texture("res/images/sparkyl3.png"), TP());
        doLoad(playerL4Texture = new Texture("res/images/sparkyl4.png"), TP());
        doLoad(playerLUTexture = new Texture("res/images/sparkylu.png"), TP());
        doLoad(playerLWTexture = new Texture("res/images/sparkylw.png"), TP());

        doLoad(playerR0Texture = new Texture("res/images/sparkyr0.png"), TP());
        doLoad(playerR1Texture = new Texture("res/images/sparkyr1.png"), TP());
        doLoad(playerR2Texture = new Texture("res/images/sparkyr2.png"), TP());
        doLoad(playerR3Texture = new Texture("res/images/sparkyr3.png"), TP());
        doLoad(playerR4Texture = new Texture("res/images/sparkyr4.png"), TP());
        doLoad(playerRUTexture = new Texture("res/images/sparkyru.png"), TP());
        doLoad(playerRWTexture = new Texture("res/images/sparkyrw.png"), TP());

        doLoad(playerVTexture = new Texture("res/images/sparkyv.png"), TP());

        doLoad(titleCardTexture = new Texture("res/images/titlecard.png"), TP());
        doLoad(sparkTexture = new Texture("res/images/spark.png"), TP());

        doLoad(battery0Texture = new Texture("res/images/battery0.png"), TP());
        doLoad(battery1Texture = new Texture("res/images/battery1.png"), TP());
        doLoad(battery2Texture = new Texture("res/images/battery2.png"), TP());
        doLoad(battery3Texture = new Texture("res/images/battery3.png"), TP());
        doLoad(battery4Texture = new Texture("res/images/battery4.png"), TP());
        doLoad(battery5Texture = new Texture("res/images/battery5.png"), TP());
        doLoad(battery6Texture = new Texture("res/images/battery6.png"), TP());
    }

    @Override
    public void unloadRoutine() {

    }

}
