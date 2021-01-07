package com.retro.androidgames.flappybird;

import android.content.SharedPreferences;
import com.retro.androidgames.framework.Game;
import com.retro.androidgames.framework.Input.TouchEvent;
import com.retro.androidgames.framework.impl.GLScreen;
import com.retro.androidgames.framework.math.OverlapTester;
import com.retro.androidgames.framework.math.Rectangle;
import com.retro.androidgames.framework.math.Vector2;
import com.retro.androidgames.opengl.Camera2D;
import com.retro.androidgames.opengl.SpriteBatcher;

import javax.microedition.khronos.opengles.GL10;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MainMenuScreen extends GLScreen {
    static Camera2D camera;
    static float WORLD_WIDTH = World.WORLD_WIDTH;
    static float WORLD_HEIGHT = World.WORLD_HEIGHT;
    static Background background;
    static DownTown downTown;
    static SpriteBatcher batcher;
    static Bird bird;
    int len;
    TouchEvent touchEvent;
    Vector2 touchPos;

    Rectangle recty;

    int highscore1;
    int coinb;

    SharedPreferences sp;
    SharedPreferences coinE;

    SharedPreferences.Editor prefsEditor;
    SharedPreferences.Editor prefsEdito;

    public MainMenuScreen(Game game){
        super(game);
        camera = new Camera2D(glGraphics, WORLD_WIDTH, WORLD_HEIGHT);
        batcher = new SpriteBatcher(glGraphics, 100);
        background = new Background();
        bird = new Bird();
        downTown = new DownTown();
        background.load(glGraphics);
        downTown.load(glGraphics);
        bird.load(glGraphics);
        touchPos = new Vector2();
        recty = new Rectangle(0, 0, 10, 10);

        try{
            sp = glGame.getSharedPreferences("FileName", MODE_PRIVATE);
            highscore1 = sp.getInt("SAVED_TEXT",0);

        }catch(NullPointerException e){
            //prefsEditor = sp.edit();
           // prefsEditor.putInt("SAVED_TEXT", 0);
            //prefsEditor.commit();
        }

        try{
            coinE = glGame.getSharedPreferences("FileName", MODE_PRIVATE);
            coinb = coinE.getInt("SAVED_COIN",0);

        }catch(NullPointerException e){
            prefsEdito = coinE.edit();
            prefsEdito.putInt("SAVED_COIN", 0);
            prefsEdito.commit();
        }
    }

    @Override
    public void update(float deltaTime){
        if(deltaTime > 0.1f)
            deltaTime = 0.1f;
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        len = touchEvents.size();
        for(int i = 0; i < len; i++){
            touchEvent = touchEvents.get(i);
            touchPos.set(touchEvent.x, touchEvent.y);
            camera.touchToWorld(touchPos);

            //shop
            if(OverlapTester.pointInRectangle(new Rectangle(8.5f, 1.5f, 2, 2), touchPos) && touchEvent.type == TouchEvent.TOUCH_UP){
                if(coinb>0) {
                    coinb -= 1;
                    Assets.playSound(Assets.shop);
                }
                prefsEdito = coinE.edit();
                prefsEdito.putInt("SAVED_COIN", coinb);
                prefsEdito.commit();
            }
            else if(touchEvent.type == TouchEvent.TOUCH_UP){
                game.setScreen(new GameScreen(game));
            }
        }
        background.update(deltaTime);
        downTown.update(deltaTime);
    }

    @Override
    public void present(float deltaTime){

        GL10 gl = glGraphics.getGL();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        batcher.beginBatch(Assets.background);
        background.present(batcher);
        downTown.present(batcher);
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        bird.state = Bird.BIRD_HIT;
        bird.present(batcher);

        String str ="tap to start";
        Assets.font.drawText(batcher, str, 1f, 10, 0.7f, 1f);

        String nscore = "HS:"+highscore1;
        Assets.font.drawText(batcher, nscore, 1, 13.5f, 0.7f, 1);


        String coine = ""+coinb;
        Assets.font.drawText(batcher, coine, 2, 1, 0.7f, 1);
        batcher.drawSprite(1, 1, 1, 1,Assets.coin);

        //shop
        batcher.drawSprite(8.5f,1.5f,4,4,Assets.coin);
        batcher.endBatch();

        gl.glDisable(GL10.GL_BLEND);




    }
    @Override
    public void resume(){
        camera.setViewportAndMatrices();
    }
    @Override
    public void pause(){}
    @Override
    public void dispose(){}
}
