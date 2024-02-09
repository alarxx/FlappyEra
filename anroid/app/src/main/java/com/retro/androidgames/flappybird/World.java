package com.retro.androidgames.flappybird;

import android.content.SharedPreferences;
import com.retro.androidgames.framework.Game;
import com.retro.androidgames.framework.impl.GLGraphics;
import com.retro.androidgames.framework.math.OverlapTester;
import com.retro.androidgames.framework.math.Vector2;
import com.retro.androidgames.opengl.SpriteBatcher;

import javax.microedition.khronos.opengles.GL10;

public class World {
    public static float WORLD_WIDTH = 10.0f;
    public static float WORLD_HEIGHT = 15.0f;
    public static int score = 0;
    public static int coin = 0;
    static TouchToWorld touch;



    //public static Vector2 GRAVITY = new Vector2(0, -16);

    public static Background background;
    public static DownTown downTown;
    public static SpriteBatcher batcher;
    public static Bird bird;
    public static Column column = new Column();
    static BirdOverlapTester BOT_OVERLAP;

    static int gameOverLen;
    static Vector2 columnVector;
    static int higha;

static int count_coin1;
    static int highscore1;

    public static void load(GLGraphics glGraphics, int highscore, int count_coin){

        highscore1 = highscore;
        higha = highscore1;
        count_coin1 = count_coin;
        batcher = new SpriteBatcher(glGraphics, 100);

        background = new Background();
        downTown = new DownTown();

        bird = new Bird();
        touch = new TouchToWorld();
        score = 0;
        gameOverLen=0;
        background.load(glGraphics);

        column.load(WORLD_WIDTH + Column.COLUMN_WIDTH/2);

        bird.load(glGraphics);
        column.scoreColumnBoolean = true;
        downTown.load(glGraphics);
        BOT_OVERLAP = new BirdOverlapTester();
        //BOT_OVERLAP.scoreBoolean = true;
        coin =0;
    }

    public static void update(float deltaTime, Game game){
        if(bird.state == Bird.BIRD_HIT && gameOverLen==0){
            Assets.playSound(Assets.sasi);
            gameOverLen++;
        }


        bird.update(deltaTime, game);
        touch.touch(game, bird);


        columnVector = new Vector2(column.columnT1.position.x, column.columnT1.position.y - Column.COLUMN_HEIGHT/2 - Column.COLUMN_DELTA/2);
        if(OverlapTester.pointInRectangle(bird.birdR, columnVector) && column.scoreColumnBoolean) {
            column.coin1b = false;
            score+=1;
            coin+=1;
            Assets.playSound(Assets.coinS);
            column.scoreColumnBoolean = false;
        }


        columnVector = new Vector2(column.columnT2.position.x, column.columnT2.position.y - Column.COLUMN_HEIGHT/2 - Column.COLUMN_DELTA/2);
        if(OverlapTester.pointInRectangle(bird.birdR, columnVector) && column.scoreColumnBoolean) {
            column.coin2b = false;
            score+=1;
            coin+=1;
            Assets.playSound(Assets.coinS);
            column.scoreColumnBoolean = false;
        }




        //ПРОВЕРКА НА СТОЛКНОВЕНИЕ ПТИЦЫ И КОЛОННЫ
        if(bird.state != Bird.BIRD_HIT) {

            background.update(deltaTime);
            downTown.update(deltaTime);
            column.update(deltaTime, WORLD_WIDTH + Column.COLUMN_WIDTH);

            if (BOT_OVERLAP.isOverlap(bird.birdR, column.columnRT1, column.columnBR1)) {
                bird.flyBird();
                bird.state = Bird.BIRD_HIT;
            }
            if (BOT_OVERLAP.isOverlap(bird.birdR, column.columnRT2, column.columnBR2)) {
                bird.flyBird();
                bird.state = Bird.BIRD_HIT;
            }
        }

    }
    public static void present(GLGraphics glGraphics, SharedPreferences sp0){
        GL10 gl = glGraphics.getGL();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);


        batcher.beginBatch(Assets.background);
        background.present(batcher);

        column.present(batcher);
        downTown.present(batcher);

        int hd = coin+count_coin1;
        if(bird.state == Bird.BIRD_HIT ){
            //Highscore
            SharedPreferences.Editor prefsEditor;

            if(score > highscore1){
                prefsEditor = sp0.edit();
                prefsEditor.putInt("SAVED_TEXT", score);
                prefsEditor.commit();

            }

            if(score > higha) {
                highscore1 = sp0.getInt("SAVED_TEXT", 0);
                //TEXT
                String nim = "HS:" + highscore1;
                Assets.font.drawText(batcher, nim, 3.3f, 10.5f, 1, 1);
            }


            //COIN
            prefsEditor = sp0.edit();
            prefsEditor.putInt("SAVED_COIN", hd);
            prefsEditor.commit();

            String str = "GAME OVER";
            Assets.font.drawText(batcher, str, 1f, 9, 1, 1);
        }
            //TEXT
            String nim = "" + score;
            Assets.font.drawText(batcher, nim, 8, 13.5f, 0.8f, 0.8f);


        String coinA = "" + hd;
        Assets.font.drawText(batcher, coinA, 2, 13.5f, 0.8f, 0.8f);
        batcher.drawSprite(1, 13.5f, 1.5f, 1.5f,Assets.coin);

        bird.present(batcher);
        batcher.endBatch();
        gl.glDisable(GL10.GL_BLEND);

    }
}
