/*
 * Здесь нету красивого кода, но зато все работает!
 * Copyright (c) 2019. JemozStudio
 */

package com.fire.jemozstudio.Tools;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.fire.jemozstudio.Actors.Enemy;
import com.fire.jemozstudio.Main;
import com.fire.jemozstudio.View.GameScreen;

public class EnemysGen {
   private String skin;
   private int Rank = 1;
   private int field;
   private float stX, fnX, stY, fnY;
  private   boolean[] fields = new boolean[4];

   // public void waveGen(Main main, int wave, int maxRank){ update(main, wave * 5, maxRank); }

    public void update(Main main, int sum, int maxRank){
        for (int i = 0; i < fields.length; i++)fields[i] = true;
        for (int i = 0; i < sum; i++){
            if(GameScreen.camera.position.x + GameScreen.camera.viewportWidth / 2 >= 6400 - 1){ fields[2] = false; System.out.println("field 2 -" + fields[2]);}
            if(GameScreen.camera.position.x - GameScreen.camera.viewportWidth / 2 <= 1){ fields[0] = false; System.out.println("field 0 -" + fields[0]);}
            if(GameScreen.camera.position.y + GameScreen.camera.viewportHeight / 2 >= 3200 - 1){ fields[1] = false; System.out.println("field 1 -" + fields[1]);}
            if(GameScreen.camera.position.y - GameScreen.camera.viewportHeight / 2 <= 1){ fields[3] = false; System.out.println("field 3 -" + fields[3]);}
            int skin_num = MathUtils.random(1,2);
            Rank = MathUtils.random(1, maxRank);
            switch (skin_num){
                case 1: skin = "robot1_hold"; break;
                case 2: skin = "zoimbie1_hold"; break;
            }
            while (true){
                field = MathUtils.random(0,3);
                if(fields[field]) break;
                else field = MathUtils.random(0,3);
            }
            switch (field){
                case 0:{
                    stX= GameScreen.camera.position.x - GameScreen.camera.viewportWidth;
                    stY = GameScreen.camera.position.y - GameScreen.camera.viewportHeight /2;
                    fnX = GameScreen.camera.position.x - GameScreen.camera.viewportWidth/2;
                    fnY = GameScreen.camera.position.y + GameScreen.camera.viewportHeight /2;
                }
                case 1:{
                    stX= GameScreen.camera.position.x - GameScreen.camera.viewportWidth;
                    stY = GameScreen.camera.position.y + GameScreen.camera.viewportHeight /2;
                    fnX = GameScreen.camera.position.x + GameScreen.camera.viewportWidth;
                    fnY = GameScreen.camera.position.y + GameScreen.camera.viewportHeight;
                }
                case 2:{
                    stX= GameScreen.camera.position.x + GameScreen.camera.viewportWidth / 2;
                    stY = GameScreen.camera.position.y - GameScreen.camera.viewportHeight /2;
                    fnX = GameScreen.camera.position.x + GameScreen.camera.viewportWidth;
                    fnY = GameScreen.camera.position.y + GameScreen.camera.viewportHeight /2;
                }
                case 3:{
                    stX= GameScreen.camera.position.x - GameScreen.camera.viewportWidth;
                    stY = GameScreen.camera.position.y - GameScreen.camera.viewportHeight;
                    fnX = GameScreen.camera.position.x + GameScreen.camera.viewportWidth;
                    fnY = GameScreen.camera.position.y - GameScreen.camera.viewportHeight /2;
                }
            }

            GameScreen.Enemies.add(new Enemy(main.getPlayersAt().findRegion(skin), new Vector2(MathUtils.random(stX + 100, fnX - 100),
                    MathUtils.random(stY + 100, fnY - 100)), Rank, GameScreen.player, main));
        }
    }
}
