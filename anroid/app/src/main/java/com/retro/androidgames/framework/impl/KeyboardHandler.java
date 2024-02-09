package com.retro.androidgames.framework.impl;

import android.view.View;
import android.view.View.OnKeyListener;
import com.retro.androidgames.framework.Input.KeyEvent;
import com.retro.androidgames.framework.impl.Pool.PoolObjectFactory;

import java.util.ArrayList;
import java.util.List;

public class KeyboardHandler implements OnKeyListener {

    boolean[] pressedKeys = new boolean[128]; //Нажата клавиша или нет, либо клавиш 128, либо это их юникод хз
    Pool<KeyEvent> keyEventPool; // Ненужные клавиши, нажатые
    List<KeyEvent> keyEventsBuffer = new ArrayList<KeyEvent>(); //Побочная. Отсюда идет в главную
    List<KeyEvent> keyEvents = new ArrayList<KeyEvent>(); //Главная

    public KeyboardHandler(View view){

        PoolObjectFactory<KeyEvent> factory = new PoolObjectFactory<KeyEvent>(){
          @Override
          public KeyEvent createObject(){ //Pool
              return new KeyEvent();
          }
        };

        keyEventPool = new Pool<KeyEvent>(factory, 100); //Pool
        view.setOnKeyListener(this);
        view.setFocusableInTouchMode(true); // Разрешения и все дела
        view.requestFocus();

    }

    @Override
    public boolean onKey(View view, int keyCode, android.view.KeyEvent event){ //Как видишь переназначение главного метода

        if(event.getAction() == android.view.KeyEvent.ACTION_MULTIPLE) {
            return false;                                                  //По сути нахуй не нужно
        }

        synchronized(this){ //Синхронизация означает, что доступ осуществляется только одним потоком. Незльзя сразу двоих
            KeyEvent keyEvent = keyEventPool.newObject(); //Либо создает, либо забирает из пула КейЕвент
            keyEvent.keyCode = keyCode;
            keyEvent.keyChar = (char) event.getUnicodeChar();

            if(event.getAction() == android.view.KeyEvent.ACTION_DOWN){
                keyEvent.type = KeyEvent.KEY_DOWN;

                if(keyCode>0 && keyCode<127) {
                    pressedKeys[keyCode] = true;
                }
            } //Да он получает значение правды, но потом мгновенно становится неправдой ->

            if(event.getAction() == android.view.KeyEvent.ACTION_UP){
                keyEvent.type = KeyEvent.KEY_UP;

                if(keyCode>0 && keyCode<127) {
                    pressedKeys[keyCode] = false;
                }
            }

            keyEventsBuffer.add(keyEvent); //Любое нажатие сразу передается в буфер
        }
        return false;
    }

    public boolean isKeyPressed(int keyCode){ //Получает булевое значение(pressedKeys)
        if(keyCode>0 && keyCode<127){
            return false;
        }
        return pressedKeys[keyCode];
    }


    public List<KeyEvent> getKeyEvents(){ // Получает нажатую клавишу(keyEvents)
        synchronized (this){
            int len = keyEvents.size();
            for(int i=0; i<len; i++)
                keyEventPool.free(keyEvents.get(i)); //Погружает ВСЕ из keyEvents в Pool
                keyEvents.clear();
                keyEvents.addAll(keyEventsBuffer); //Погружается из буфера в КейЕвентс
                keyEventsBuffer.clear();
                return keyEvents;
        }
    }
}