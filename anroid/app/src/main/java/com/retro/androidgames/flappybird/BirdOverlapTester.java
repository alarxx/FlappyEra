package com.retro.androidgames.flappybird;

import com.retro.androidgames.framework.math.OverlapTester;
import com.retro.androidgames.framework.math.Rectangle;
import com.retro.androidgames.framework.math.Vector2;

public class BirdOverlapTester {
    boolean r = false;
    public static int score = 0;
    public static boolean scoreBoolean;

    public int scoreCounter(Rectangle birdR, Vector2 point, boolean scoreColumnBoolean){
        while(OverlapTester.pointInRectangle(birdR, point) && scoreColumnBoolean) {
            score+=1;

            scoreColumnBoolean=false;
        }
        return score;
    }

    public boolean isOverlap(Rectangle birdR, Rectangle columnTR, Rectangle columnBR) {
        if(OverlapTester.overlapRectangles(birdR, columnBR) || OverlapTester.overlapRectangles(birdR, columnTR)){

            r=true;
        }return r?true:false;
    }

}
