package com.retro.androidgames.framework.impl;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.opengles.GL10;

public class GLGraphics {
    GLSurfaceView glSurfaceView;
    GL10 gl10;

    GLGraphics(GLSurfaceView glSurfaceView){ //То есть поток
        this.glSurfaceView = glSurfaceView;
    }

    public GL10 getGL(){
        return gl10;
    }

    void setGL(GL10 gl10){
        this.gl10 = gl10;
    }

    public int getWidth(){
        return glSurfaceView.getWidth();
    }

    public int getHeight(){
        return glSurfaceView.getHeight();
    }
}
