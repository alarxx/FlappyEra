package com.retro.androidgames.framework.impl;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;
import com.retro.androidgames.framework.*;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public abstract class GLGame extends Activity implements Game, Renderer {

    enum GLGameState{
        Initialized, Running, Paused, Finished, Idle
    }
    GLGameState state = GLGameState.Initialized;

    GLSurfaceView glSurfaceView;
    GLGraphics glGraphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    WakeLock wakeLock;

    Object stateChanged = new Object();
    long startTime = System.nanoTime();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setRenderer(this);
        setContentView(glSurfaceView);

        PowerManager powerManager = (PowerManager)  getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");

        glGraphics = new GLGraphics(glSurfaceView);
        fileIO = new AndroidFileIO(getAssets());
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, glSurfaceView, 1, 1);
    }

    public void onResume(){
        super.onResume();
        glSurfaceView.onResume();
        wakeLock.acquire();
    }
    @Override
    public void onPause(){
        synchronized(stateChanged){
            if(isFinishing())
                state = GLGameState.Finished;
            else
                state = GLGameState.Paused;
            while(true){
                try{
                    stateChanged.wait();
                    break;
                }catch(InterruptedException e){}
            }
        }
        wakeLock.release();
        glSurfaceView.onPause();
        super.onPause();
    }

    //Реализация интерфейса Renderer
    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig config){
        glGraphics.setGL(gl10);
        synchronized(stateChanged){
            if(state== GLGameState.Initialized)
                screen = getStartScreen();
            state = GLGameState.Running;
            screen.resume();
            startTime = System.nanoTime();
        }
    }
    //Реализация интерфейса Renderer
    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height){}
    //Реализация интерфейса Renderer
    @Override
    public void onDrawFrame(GL10 gl10){
        GLGameState state = null;

        synchronized(stateChanged){
            state = this.state;
        }

        if(state == GLGameState.Running){
            float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
            startTime = System.nanoTime();

            screen.update(deltaTime);
            screen.present(deltaTime);
        }

        if(state == GLGameState.Paused){
            screen.pause();
            synchronized(stateChanged){
                this.state = GLGameState.Idle;
                stateChanged.notifyAll();
            }
        }

        if(state == GLGameState.Finished){
            screen.pause();
            screen.dispose();
            synchronized(stateChanged){
                this.state = GLGameState.Idle;
                stateChanged.notifyAll();
            }
        }
    }

    //Можно получить высоту и ширину, а также GL10(Команды)
    public GLGraphics getGLGraphics(){
        return glGraphics;
    }

    @Override
    public Input getInput() {
        return input;
    }
    @Override
    public FileIO getFileIO() {
        return fileIO;
    }
    @Override
    public Graphics getGraphics() {
        throw new IllegalStateException("We are using OpenGL!");
    }
    @Override
    public Audio getAudio() {
        return audio;
    }
    @Override
    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");
        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }
    @Override
    public Screen getCurrentScreen() {
        return screen;
    }
}
