package com.retro.androidgames.flappybird;



import com.retro.androidgames.framework.Music;
import com.retro.androidgames.framework.Sound;
import com.retro.androidgames.framework.impl.GLGame;
import com.retro.androidgames.opengl.Animation;
import com.retro.androidgames.opengl.Font;
import com.retro.androidgames.opengl.Texture;
import com.retro.androidgames.opengl.TextureRegion;

public class Assets {

    public static Texture background;
    public static TextureRegion columnT;
    public static TextureRegion columnB;
    public static TextureRegion bird;
    public static TextureRegion birdT;
    public static TextureRegion birdB;
    public static TextureRegion coin;

    public static TextureRegion backgroundRegion;
    public static TextureRegion down;

    public static Font font;
    public static Music music;
    public static Sound touchSound;
    public static Sound sasi;
    public static Sound coinS;
    public static Sound shop;


    public static Animation birdAnimation;



    public static void load(GLGame glGame){
        background = new Texture(glGame, "background.png");

        backgroundRegion = new TextureRegion(background, 0, 0, 320, 480);
        down = new TextureRegion(background, 320, 480, 320, 64);

        columnT = new TextureRegion(background, 384, 0, 64, 176);
        columnB = new TextureRegion(background, 448, 0, 64, 176);
        bird = new TextureRegion(background, 576, 0, 64, 64);
        coin = new TextureRegion(background, 576, 64, 64, 64);


        birdAnimation = new Animation(0.2f, bird, birdT, birdB);

        font = new Font(background, 0, 480, 16, 16, 20);

        touchSound = glGame.getAudio().newSound("jump.ogg");

        sasi = glGame.getAudio().newSound("sasi.ogg");
        coinS = glGame.getAudio().newSound("coin.ogg");
        shop = glGame.getAudio().newSound("shop.ogg");


        music = glGame.getAudio().newMusic("kalinka.mp3");
        music.setLooping(true);
        music.setVolume(0.5f);
        music.play();
    }
    public static void reload(){
        background.reload();
        music.play();
    }


    public static void playSound(Sound sound){
        if(true)
            sound.play(1.0f);
    }

}
