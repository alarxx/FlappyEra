package com.retro.androidgames.framework.impl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AndroidFastRenderView extends SurfaceView implements Runnable {
    AndroidGame game;
    Bitmap framebuffer;
    Thread renderThread = null;
    SurfaceHolder surfaceHolder;
    volatile boolean running = false;

    public AndroidFastRenderView(AndroidGame game, Bitmap framebuffer){
        super(game);
        this.game = game;
        this.framebuffer = framebuffer;
        this.surfaceHolder = getHolder();


    }

    public void resume(){
        running = true;
        renderThread = new Thread(this);
        renderThread.start();
    }

    public void run(){
        Rect dstRect = new Rect();
        long startTime = System.nanoTime();
        while(running){
            if(!surfaceHolder.getSurface().isValid()) continue;
            float deltaTime = (System.nanoTime()-startTime)/ 1000000000.0f;
            startTime = System.nanoTime();
            game.getCurrentScreen().update(deltaTime);
            game.getCurrentScreen().present(deltaTime);
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.getClipBounds(dstRect);
            canvas.drawBitmap(framebuffer, null, dstRect, null);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause(){
        running = false;
        while(true){
            try{
                renderThread.join(); //join() Уничтожает поток
            }catch (InterruptedException e){}
        }
    }

}
