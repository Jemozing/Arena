/*
 * Здесь нету красивого кода, но зато все работает!
 * Copyright (c) 2019. JemozStudio
 */

package com.fire.jemozstudio.Tools;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.fire.jemozstudio.Actors.Boss;
import com.fire.jemozstudio.Actors.Enemy;
import com.fire.jemozstudio.Actors.Player;
import com.fire.jemozstudio.Main;
import com.fire.jemozstudio.View.GameScreen;

public class Wave {
    EnemysGen gen;
    private int Delay, WaveNumber, minEnemy;
    private long StartTimer;
    private String wave = "Wave - ", str, boss = "BOSS!!!";
    BitmapFont font;
    Main main;
    String skin;
    int field;
    float stX, fnX, stY, fnY;
    boolean[] fields = new boolean[4];

    public Wave(int Delay, int WaveNumber, int minEnemy, Main main){
        this.Delay = Delay;
        this.WaveNumber = WaveNumber;
        this.minEnemy = minEnemy;
        this.main = main;
        gen = new EnemysGen();
        main.param.size = (int) Main.HEIGHT / 8;
        font = main.generator.generateFont(main.param);
    }

    public int addWaveNumber(int i){
        WaveNumber += i;
        return WaveNumber;
    }
    public int getWaveNumber() {
        return WaveNumber;
    }

    public void update(Main main, Player player){
        if(GameScreen.Enemies.size == 0 && StartTimer == 0 && GameScreen.boss.size == 0){
            StartTimer = System.currentTimeMillis();
            spawnHealPack(player);
        }
        int Seconds = 0;
        if(StartTimer > 0){
            Seconds = (int) (System.currentTimeMillis() - StartTimer)/1000;
            if(!(WaveNumber % 5 == 0)){str = wave + WaveNumber;}
            else {str = boss;}
        }
        if (Seconds >= Delay && WaveNumber % 5 == 0){genBoss(main); StartTimer = 0;
            System.out.println("Boss is gen!");
        }
        if(Seconds >= Delay && !(WaveNumber % 5 == 0)){setWave(main);  StartTimer = 0;  WaveNumber++;}
    }

    public void setWave(Main main){
        int Enemies = minEnemy + WaveNumber * 2;
        //System.out.println(str + WaveNumber);
        int MaxRank = 1;
        if(WaveNumber > 3)MaxRank = 2;
        if(WaveNumber > 5)MaxRank = 3;
        if(WaveNumber > 7)MaxRank = 4;
        if(WaveNumber > 9)MaxRank = 5;
        gen.update(main, Enemies, MaxRank);
    }

    public void genBoss(Main main){
        for (int i = 0; i < fields.length; i++)fields[i] = true;
            if(GameScreen.camera.position.x + GameScreen.camera.viewportWidth / 2 >= 6400 - 1){ fields[2] = false; System.out.println("field 2 -" + fields[2]);}
            if(GameScreen.camera.position.x - GameScreen.camera.viewportWidth / 2 <= 1){ fields[0] = false; System.out.println("field 0 -" + fields[0]);}
            if(GameScreen.camera.position.y + GameScreen.camera.viewportHeight / 2 >= 3200 - 1){ fields[1] = false; System.out.println("field 1 -" + fields[1]);}
            if(GameScreen.camera.position.y - GameScreen.camera.viewportHeight / 2 <= 1){ fields[3] = false; System.out.println("field 3 -" + fields[3]);}
            int skin_num = MathUtils.random(1,2);
            //Rank = MathUtils.random(1, maxRank);
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

            GameScreen.boss.add(new Boss(main.getPlayersAt().findRegion(skin), new Vector2(MathUtils.random(stX + 100, fnX - 100),
                    MathUtils.random(stY + 100, fnY - 100)), GameScreen.player, main));
        }

        public void spawnHealPack(Player player){
            player.setHealth(-15);
        }

    public void draw(SpriteBatch batch){
        main.gl.setText(font, str);
        font.draw(batch,main.gl, GameScreen.camera.position.x - main.gl.width/2, GameScreen.camera.position.y);
    }

    public boolean isDraw(){
        return StartTimer > 0;
    }

}
