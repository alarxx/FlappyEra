package com.retro.androidgames.flappybird;

import com.retro.androidgames.framework.Screen;
import com.retro.androidgames.framework.impl.GLGame;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainMenu extends GLGame {
    boolean firstTimeCreated = true;
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config){
        super.onSurfaceCreated(gl, config);
        if(firstTimeCreated){
            Assets.load(this);
            firstTimeCreated = false;
        }else
            Assets.reload();
    }

    @Override
    public void onPause(){
        super.onPause();
        Assets.music.pause();
    }

    @Override
    public Screen getStartScreen(){
        return new MainMenuScreen(this);
    }
}
