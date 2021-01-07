package com.retro.androidgames.flappybird;

import android.content.SharedPreferences;
import com.retro.androidgames.framework.Game;
import com.retro.androidgames.framework.impl.GLScreen;
import com.retro.androidgames.opengl.Camera2D;

import static android.content.Context.MODE_PRIVATE;


public class GameScreen extends GLScreen {

    public static Camera2D camera;
    static float WORLD_WIDTH = World.WORLD_WIDTH;
    static float WORLD_HEIGHT = World.WORLD_HEIGHT;
    World world = new World();
    public static SharedPreferences sp;
    public static SharedPreferences s;

    public GameScreen(Game game){

        super(game);
        camera = new Camera2D(glGraphics, WORLD_WIDTH, WORLD_HEIGHT);

        sp = glGame.getSharedPreferences("FileName", MODE_PRIVATE);
        s = glGame.getSharedPreferences("FileName", MODE_PRIVATE);

        int highscore = sp.getInt("SAVED_TEXT",0);
        int coin = s.getInt("SAVED_COIN",0);

        world.load(glGraphics, highscore, coin);
    }

    @Override
    public void update(float deltaTime){
        if(deltaTime > 0.05f)
            deltaTime = 0.05f;
        world.update(deltaTime, game);

        }

    @Override
    public void present(float deltaTime){

        world.present(glGraphics, sp);
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
