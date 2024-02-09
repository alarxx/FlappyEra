package com.retro.androidgames.framework.impl;

import java.util.ArrayList;
import java.util.List;

public class Pool<T> {

    public interface PoolObjectFactory<T> {
        public T createObject();
    }

    private final List<T> freeObjects;
    private final PoolObjectFactory<T> factory;
    private final int maxSize;

    public Pool(PoolObjectFactory<T> factory, int maxSize) {  //1
        this.factory = factory;
        this.maxSize = maxSize;
        this.freeObjects = new ArrayList<T>(maxSize);//назначается
    }

    public T newObject() {  //2
        T object = null;
        if (freeObjects.size() == 0) // В первый раз
            object = factory.createObject();
        else
            object = freeObjects.remove(freeObjects.size()-1); // Забирает из 0-го значения
        return object;
    }

    public void free(T object) {
        if (freeObjects.size() < maxSize)
            freeObjects.add(object);
    }
}


