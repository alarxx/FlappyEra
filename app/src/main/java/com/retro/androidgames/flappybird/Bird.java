package com.retro.androidgames.flappybird;

import com.retro.androidgames.framework.Game;
import com.retro.androidgames.framework.gamedev.DynamicGameObject;
import com.retro.androidgames.framework.impl.GLGraphics;
import com.retro.androidgames.framework.math.Rectangle;
import com.retro.androidgames.framework.math.Vector2;
import com.retro.androidgames.opengl.SpriteBatcher;

public class Bird {
    static float WORLD_WIDTH = GameScreen.WORLD_WIDTH;
    static float WORLD_HEIGHT = GameScreen.WORLD_HEIGHT;


    static float cannonAngle;

    public static final int BIRD_JUMP = 0;
    public static final int BIRD_FALL = 1;
    public static final int BIRD_HIT = 2;

    static final float BIRD_JUMP_VELOCITY = 9;
    static final float FALL_VELOCITY = -30;
    static final float BIRD_WIDTH = 2f;
    static final float BIRD_HEIGHT = 2f;

    //static Vector2 GRAVITY = World.GRAVITY;
    static int state;


    static int im;
    static Vector2 touchPos;
    static DynamicGameObject bird;

    public static Rectangle birdR;

    public static void load(GLGraphics glGraphics){


        im=0;
        touchPos = new Vector2();
        bird = new DynamicGameObject(WORLD_WIDTH/4, WORLD_HEIGHT/2, BIRD_WIDTH, BIRD_HEIGHT);

        birdR = new Rectangle(WORLD_WIDTH/4+0.1f, WORLD_HEIGHT/2, BIRD_WIDTH/2-0.2f, BIRD_HEIGHT-0.2f);
        state = BIRD_FALL;
    }

    public static void update(float deltaTime, Game game){


        //НИЗ
        if(bird.position.y<BIRD_HEIGHT/2+1.0f){
            if(state != BIRD_HIT)
                state = BIRD_HIT;
            //bird.velocity.set(0 , 0);
        }
        //ВЕРХ
        if(bird.position.y>WORLD_HEIGHT - BIRD_HEIGHT/2){
            if(state != BIRD_HIT)
                state = BIRD_HIT;
        }

        if(state == BIRD_HIT){
            bird.velocity.add(0, 0);
        }
        //ПАДЕНИЕ
        if(state == BIRD_HIT && bird.position.y > BIRD_HEIGHT/2+1){
            bird.velocity.add(0, FALL_VELOCITY*deltaTime);

           bird.position.add(-1*deltaTime, bird.velocity.y*deltaTime);
            birdR.lowerLeft.add(-1*deltaTime, bird.velocity.y * deltaTime);
        }



        //ОБНОВЛЕНИЕ, если берд хит, то позиция не изменяется
        if(state != BIRD_HIT) {
            bird.velocity.add(0, FALL_VELOCITY*deltaTime);

            bird.position.add(0, bird.velocity.y * deltaTime);
            birdR.lowerLeft.add(0, bird.velocity.y * deltaTime);
        }

        //ДЛЯ АНИМАЦИИ
        if(bird.velocity.y < -3 && state != BIRD_HIT){
            im=1;
            if(state != BIRD_FALL){
                state = BIRD_FALL;
                im=0;
            }
        }
        if(bird.velocity.y > -3 && state != BIRD_HIT){
            im=1;
            if(state != BIRD_JUMP){
                state = BIRD_JUMP;
                im=0;
            }
        }
    }

    public static void present(SpriteBatcher batcher){
        if(state == BIRD_HIT)
            cannonAngle = 0;
        if(im==0)
            cannonAngle =0;
        if(state == BIRD_FALL && im!=0){
            cannonAngle = -15;im++;
        }
        if(state == BIRD_JUMP && im!=0){
            cannonAngle = 15;im++;
        }
        batcher.drawSprite(bird.position.x, bird.position.y, Bird.BIRD_WIDTH, Bird.BIRD_HEIGHT, cannonAngle,Assets.bird);
    }

    public static void flyBird(){
        bird.velocity.set(0, BIRD_JUMP_VELOCITY);
        state = BIRD_JUMP;
    }

}
