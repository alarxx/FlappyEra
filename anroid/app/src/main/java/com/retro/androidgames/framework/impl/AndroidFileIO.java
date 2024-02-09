package com.retro.androidgames.framework.impl;

import android.content.res.AssetManager;
import android.os.Environment;
import com.retro.androidgames.framework.FileIO;

import java.io.*;

public class AndroidFileIO implements FileIO {

    AssetManager assets;
    String externalStoragePath;

    //Конструктор, getAssets()
    public AndroidFileIO(AssetManager assets){
        this.assets = assets;
        this.externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator;
    }

    @Override
    public InputStream readAsset(String fileName) throws IOException{
        return assets.open(fileName);
    }
    @Override
    public InputStream readFile(String fileName) throws IOException{
        return new FileInputStream(externalStoragePath + fileName);
    }
    @Override
    public OutputStream writeFile(String fileName) throws IOException{
        return new FileOutputStream(externalStoragePath + fileName);
    }
}
