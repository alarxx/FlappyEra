package com.retro.androidgames.framework;

import com.retro.androidgames.framework.Graphics.PixmapFormat;

public interface Pixmap {
    public int getWidth();
    public int getHeight();
    public PixmapFormat getFormat();
    public void dispose();
}

