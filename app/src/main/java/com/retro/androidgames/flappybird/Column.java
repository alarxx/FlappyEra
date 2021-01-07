package com.retro.androidgames.flappybird;

import com.retro.androidgames.framework.gamedev.DynamicGameObject;
import com.retro.androidgames.framework.math.Rectangle;
import com.retro.androidgames.opengl.SpriteBatcher;

import java.util.Random;

public class Column {
    private static float WORLD_WIDTH = GameScreen.WORLD_WIDTH;
    private static float WORLD_HEIGHT = GameScreen.WORLD_HEIGHT;

    public static boolean coin1b = true;
    public static boolean coin2b = true;

    static final float COLUMN_WIDTH = 2.5f;
    static final float COIN = 2f;
    static final float COLUMN_HEIGHT = WORLD_HEIGHT;
    public static final float COLUMN_DELTA = 3.75f;

    private static float COLUMN_VELOCITY;

    public static DynamicGameObject columnT1;
    public static DynamicGameObject columnB1;
    public static Rectangle columnRT1 ;
    public static Rectangle columnBR1 ;

    public static DynamicGameObject columnT2;
    public static DynamicGameObject columnB2;
    public static Rectangle columnRT2 ;
    public static Rectangle columnBR2 ;

    public static float y1;
    public static float y2;

    public static boolean scoreColumnBoolean;

    private static Random r = new Random();

    public static DynamicGameObject coin1;
    public static DynamicGameObject coin2;

    public static void load(float x){
        COLUMN_VELOCITY = -4.5f;
        scoreColumnBoolean = true;
        coin1b = true;
        coin2b = true;

        //ПЕРВАЯ
        y1 = r.nextFloat()*(WORLD_HEIGHT - COLUMN_DELTA)+COLUMN_WIDTH/2;
        //Проверка на края мира
        if(y1<4.5f) //COLUMN_DELTA/2+1.0f=3
            y1 = r.nextFloat()*(WORLD_HEIGHT - COLUMN_DELTA)+COLUMN_WIDTH/2;
        if(y1<4.5f)
            y1 = 4.5f;
        if(y1 > WORLD_HEIGHT - COLUMN_DELTA)
            y1= WORLD_HEIGHT - COLUMN_DELTA-1.0f;

        if(Math.abs(y1-y2)>5){
            while(Math.abs(y2-y1)>5){
                if(y1-y2 > 0)
                    y1-=1.0f;
                if(y1-y2 < 0)
                    y1+=1.0f;
            }
        }

        coin1 = new DynamicGameObject(x, y1, COIN, COIN);
        coin1b = true;
        columnT1 = new DynamicGameObject(x, y1 + COLUMN_HEIGHT/2 + COLUMN_DELTA/2, COLUMN_WIDTH, COLUMN_HEIGHT);
        columnB1 = new DynamicGameObject(x, y1 - COLUMN_HEIGHT/2 - COLUMN_DELTA/2, COLUMN_WIDTH, COLUMN_HEIGHT);
        columnRT1 = new Rectangle(x, y1 + COLUMN_HEIGHT/2 + COLUMN_DELTA/2, COLUMN_WIDTH, COLUMN_HEIGHT);
        columnBR1 = new Rectangle(x, y1 - COLUMN_HEIGHT/2 - COLUMN_DELTA/2, COLUMN_WIDTH, COLUMN_HEIGHT);


        //ВТОРАЯ
        y2 = r.nextFloat()*(WORLD_HEIGHT - COLUMN_DELTA)+COLUMN_WIDTH/2;
        if(y1<4.5f) //COLUMN_DELTA/2+1.0f=3
            y1 = r.nextFloat()*(WORLD_HEIGHT - COLUMN_DELTA)+COLUMN_WIDTH/2;
        //проверка на края мира
        if(y2<4.5f)//COLUMN_DELTA/2+1.0f
            y2 = 4.5f;
        if(y2 > 12)//WORLD_HEIGHT - COLUMN_DELTA/2-1.0f=12
            y2 = 12;

        if(Math.abs(y2-y1)>5){
            while(Math.abs(y2-y1)>5){
                if(y2-y1 > 0)
                    y2-=1.0f;
                if(y2-y1 < 0)
                    y2+=1.0f;
            }
        }

        coin2 = new DynamicGameObject(x + WORLD_WIDTH/2+ COLUMN_WIDTH, y2,COIN,COIN);
        coin2b = true;
        columnT2 = new DynamicGameObject(x + WORLD_WIDTH/2+ COLUMN_WIDTH, y2 + COLUMN_HEIGHT/2 + COLUMN_DELTA/2, COLUMN_WIDTH, COLUMN_HEIGHT);
        columnB2 = new DynamicGameObject(x + WORLD_WIDTH/2+ COLUMN_WIDTH, y2 - COLUMN_HEIGHT/2 - COLUMN_DELTA/2, COLUMN_WIDTH, COLUMN_HEIGHT);
        columnRT2 = new Rectangle(x + WORLD_WIDTH/2+ COLUMN_WIDTH, y2 + COLUMN_HEIGHT/2 + COLUMN_DELTA/2, COLUMN_WIDTH, COLUMN_HEIGHT);
        columnBR2 = new Rectangle(x + WORLD_WIDTH/2+ COLUMN_WIDTH, y2 - COLUMN_HEIGHT/2 - COLUMN_DELTA/2, COLUMN_WIDTH, COLUMN_HEIGHT);

    }
    public static void update(float deltaTime, float x){

        //ПЕРВАЯ
        if(columnT1.position.x>-COLUMN_WIDTH/2) {
            columnT1.velocity.set(COLUMN_VELOCITY * deltaTime, 0);

            coin1.position.add(columnT2.velocity.x, columnT1.velocity.y);

            columnT1.position.add(columnT1.velocity.x, columnT1.velocity.y);
            columnT1.bounds.lowerLeft.add(columnT1.velocity.x, columnT1.velocity.y);
            columnRT1.lowerLeft.add(columnT1.velocity.x, columnT1.velocity.y);
            columnB1.velocity.set(COLUMN_VELOCITY * deltaTime, 0);
            columnB1.position.add(columnB1.velocity.x, columnB1.velocity.y);
            columnB1.bounds.lowerLeft.add(columnB1.velocity.x, columnB1.velocity.y);
            columnBR1.lowerLeft.add(columnT1.velocity.x, columnT1.velocity.y);

        }else{

            if(COLUMN_VELOCITY != -7)
                COLUMN_VELOCITY -= 0.005f;

            scoreColumnBoolean = true;
            y1 = r.nextFloat()*(WORLD_HEIGHT - COLUMN_DELTA)+COLUMN_WIDTH/2;
                  //Проверка на края мира
            if(y1<4.5f) //COLUMN_DELTA/2+1.0f=3
                y1 = r.nextFloat()*(WORLD_HEIGHT - COLUMN_DELTA)+COLUMN_WIDTH/2;
            if(y1<4.5f)
                y1 = 4.5f;
            if(y1 > WORLD_HEIGHT - COLUMN_DELTA/2-1.0f)
                y1= WORLD_HEIGHT - COLUMN_DELTA/2-1.0f;

            if(Math.abs(y1-y2)>5){
                while(Math.abs(y2-y1)>5){
                    if(y1-y2 > 0)
                        y1-=1.0f;
                    if(y1-y2 < 0)
                        y1+=1.0f;
                }
            }
            coin1 = new DynamicGameObject(x +COLUMN_WIDTH/2 , y1,COIN,COIN);
            coin1b = true;
            columnT1 = new DynamicGameObject(x+COLUMN_WIDTH/2, y1 + COLUMN_HEIGHT/2 + COLUMN_DELTA/2, COLUMN_WIDTH, COLUMN_HEIGHT);
            columnB1 = new DynamicGameObject(x+COLUMN_WIDTH/2, y1 - COLUMN_HEIGHT/2 - COLUMN_DELTA/2, COLUMN_WIDTH, COLUMN_HEIGHT);
            columnRT1 = new Rectangle(x+COLUMN_WIDTH/2, y1 + COLUMN_HEIGHT/2 + COLUMN_DELTA/2, COLUMN_WIDTH, COLUMN_HEIGHT);
            columnBR1 = new Rectangle(x+COLUMN_WIDTH/2, y1 - COLUMN_HEIGHT/2 - COLUMN_DELTA/2, COLUMN_WIDTH, COLUMN_HEIGHT);

        }




        //ВТОРАЯ
        if(columnT2.position.x>-COLUMN_WIDTH/2){
            columnT2.velocity.set(COLUMN_VELOCITY*deltaTime, 0);
            columnT2.position.add(columnT2.velocity.x, columnT1.velocity.y);

            coin2.position.add(columnT2.velocity.x, 0);

            columnT2.bounds.lowerLeft.add(columnT2.velocity.x, columnT2.velocity.y);
            columnRT2.lowerLeft.add(columnT2.velocity.x, columnT2.velocity.y);
            columnB2.velocity.set(COLUMN_VELOCITY*deltaTime, 0);
            columnB2.position.add(columnB2.velocity.x, columnB2.velocity.y);
            columnB2.bounds.lowerLeft.add(columnB1.velocity.x, columnB2.velocity.y);
            columnBR2.lowerLeft.add(columnT2.velocity.x, columnT2.velocity.y);

        }else{

            if(COLUMN_VELOCITY != -7)
                COLUMN_VELOCITY -= 0.005f;

            scoreColumnBoolean=true;
            y2 =r.nextFloat()*(WORLD_HEIGHT - COLUMN_DELTA)+COLUMN_WIDTH/2;


            if(y1<4.5f) //COLUMN_DELTA/2+1.0f=3
                y1 = r.nextFloat()*(WORLD_HEIGHT - COLUMN_DELTA)+COLUMN_WIDTH/2;
            // Проверка на края мира
            if(y2<4.5f)
                y2 = 4.5f;
            if(y2 > WORLD_HEIGHT - COLUMN_DELTA/2-1.0f)
                y2= WORLD_HEIGHT - COLUMN_DELTA/2-1.0f;


            if(Math.abs(y2-y1)>5){
                while(Math.abs(y2-y1)>5){
                    if(y2-y1 > 0)
                        y2-=1.0f;
                    if(y2-y1 < 0)
                        y2+=1.0f;
                }
            }
            coin2 = new DynamicGameObject(x +COLUMN_WIDTH/2 , y2,COIN,COIN);
            coin2b = true;
            columnT2 = new DynamicGameObject(x +COLUMN_WIDTH/2, y2 + COLUMN_HEIGHT/2 + COLUMN_DELTA/2, COLUMN_WIDTH, COLUMN_HEIGHT);
            columnB2 = new DynamicGameObject(x +COLUMN_WIDTH/2, y2 - COLUMN_HEIGHT/2 - COLUMN_DELTA/2, COLUMN_WIDTH, COLUMN_HEIGHT);
            columnRT2 = new Rectangle(x +COLUMN_WIDTH/2, y2 + COLUMN_HEIGHT/2 + COLUMN_DELTA/2, COLUMN_WIDTH, COLUMN_HEIGHT);
            columnBR2 = new Rectangle(x+COLUMN_WIDTH/2, y2 - COLUMN_HEIGHT/2 - COLUMN_DELTA/2, COLUMN_WIDTH, COLUMN_HEIGHT);

        }


    }
    public static void present(SpriteBatcher batcher){
        batcher.drawSprite(columnT1.position.x, columnT1.position.y, COLUMN_WIDTH, COLUMN_HEIGHT, Assets.columnT);
        batcher.drawSprite(columnB1.position.x, columnB1.position.y, COLUMN_WIDTH, COLUMN_HEIGHT,  Assets.columnB);

        if(coin1b == true)
            batcher.drawSprite(coin1.position.x, coin1.position.y, COIN, COIN,  Assets.coin);
        if(coin2b == true)
            batcher.drawSprite(coin2.position.x, coin2.position.y, COIN, COIN,  Assets.coin);

        batcher.drawSprite(columnT2.position.x, columnT2.position.y, COLUMN_WIDTH, COLUMN_HEIGHT,  Assets.columnT);
        batcher.drawSprite(columnB2.position.x, columnB2.position.y, COLUMN_WIDTH, COLUMN_HEIGHT,Assets.columnB);
    }
}
