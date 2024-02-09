package com.retro.androidgames.flappybird;

import com.retro.androidgames.framework.Game;
import com.retro.androidgames.framework.Input;

import java.util.List;

public class TouchToWorld {
    public void touch(Game game, Bird bird){
    //ДО
    List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();

    int len = touchEvents.size();
        for(int i=0; i<len; i++){
        Input.TouchEvent touchEvent = touchEvents.get(i);

        if(touchEvent.type == Input.TouchEvent.TOUCH_UP){
            Assets.playSound(Assets.touchSound);

            if(bird.state == Bird.BIRD_HIT){
                if(bird.birdR.lowerLeft.y<Bird.BIRD_HEIGHT/2+1.0f){
                game.setScreen(new MainMenuScreen(game));
                }
            }
            else{
                bird.flyBird();}
        }
    }
    }
}
