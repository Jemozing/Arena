/*
 * Здесь нету красивого кода, но зато все работает!
 * Copyright (c) 2019. JemozStudio
 */

package com.fire.jemozstudio.Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.fire.jemozstudio.Actors.Player;
import com.fire.jemozstudio.Main;

public class Score extends Actor {
    Main main;
    Player player;
    Wave wave;
    BitmapFont font;
    int Waves, Score, Health;
    String wav = "Waves complete - ";
    String score = "Score - ";
    String health = "HP - ";
    boolean menu = false;
    NinePatch region;
    float w = 1000, h = 750;
    public Score(Wave wave, final Player player, final Main main){
        this.main = main;
        this.player = player;
        this.wave = wave;
        main.param.size = (int) Main.HEIGHT / 14;
        font = main.generator.generateFont(main.param);
        Waves = wave.getWaveNumber();
        Score = player.getScore();
        Health = player.getHealth();
        region = main.getPanel();
        setTouchable(Touchable.enabled);
        //setBounds(0,0, 6400,3200);
        /*setBounds(main.stage.getCamera().position.x - main.stage.getCamera().viewportWidth / 2,
                main.stage.getCamera().position.y - main.stage.getCamera().viewportHeight / 2,
                main.stage.getCamera().position.x + main.stage.getCamera().viewportWidth / 2,
                main.stage.getCamera().position.y + main.stage.getCamera().viewportHeight / 2
                );*/
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(player.isGhost()){
                    main.stage.clear();
                    main.table[9].setName(main.getPlayerName());
                    main.table[9].setScore(Score);
                    main.table[9].setWave(Waves);
                    main.sortTable();
                    main.WriteRecord();
                    main.setScreen(main.menuScreen); }
                System.out.println("touch me)");
                return true;
            }
        });
    }

    public void update(){
        Waves = wave.getWaveNumber() - 1;
        Score = player.getScore();
        Health = player.getHealth();
        if(player.isGhost()){ setBounds(main.stage.getCamera().position.x - w/2, main.stage.getCamera().position.y - h/2, w, h);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        if(player.isGhost()){
        region.draw(batch, main.stage.getCamera().position.x - w/2, main.stage.getCamera().position.y - h/2, w, h);
        main.gl.setText(font, wav + Waves);
        font.draw(batch,main.gl, main.stage.getCamera().position.x - main.gl.width/2, main.stage.getCamera().position.y + main.gl.height * 2);
        main.gl.setText(font, score + Score);
        font.draw(batch,main.gl, main.stage.getCamera().position.x - main.gl.width/2, main.stage.getCamera().position.y);
        }
        else {
            main.gl.setText(font, score + Score);
            font.draw(batch,main.gl, main.stage.getCamera().position.x + main.stage.getCamera().viewportWidth / 2 - main.gl.width * 1.2f,
                    main.stage.getCamera().position.y + main.stage.getCamera().viewportHeight / 2 - main.gl.height * 1.2f);
            main.gl.setText(font, health + Health);
            font.draw(batch,main.gl, main.stage.getCamera().position.x - main.stage.getCamera().viewportWidth / 2 + main.gl.width * 0.5f,
                    main.stage.getCamera().position.y + main.stage.getCamera().viewportHeight / 2 - main.gl.height * 1.2f);
        }
    }
}
