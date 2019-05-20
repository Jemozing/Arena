/*
 * Здесь нету красивого кода, но зато все работает!
 * Copyright (c) 2019. JemozStudio
 */

package com.fire.jemozstudio.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.fire.jemozstudio.Actors.Blood;
import com.fire.jemozstudio.Actors.Boss;
import com.fire.jemozstudio.Actors.Bullet;
import com.fire.jemozstudio.Actors.Enemy;
import com.fire.jemozstudio.Actors.Player;
import com.fire.jemozstudio.Main;
import com.fire.jemozstudio.Tools.BulletGenerator;
import com.fire.jemozstudio.Tools.Joystick;
import com.fire.jemozstudio.Tools.Score;
import com.fire.jemozstudio.Tools.States;
import com.fire.jemozstudio.Tools.Wave;

public class GameScreen implements Screen {

    Main main;
    Joystick joy, joy2;
    public static Player player;
    public static Array<Bullet> bullets;
    public static Array<Enemy> Enemies;
    public static Array<Blood> bloods;
    public static Array<Boss> boss;
    BulletGenerator gen;
    //EnemysGen enemysGen;
    Score score;
    OrthogonalTiledMapRenderer mapRenderer;
    public static OrthographicCamera camera;
    int[] backgroundLayers = { 0 }; // не выделяйте память при каждом кадре!
    int[] foregroundLayers = { 1 };
    States states;
    public static Wave wave;
    public GameScreen(Main main){
        this.main = main;
        camera = new OrthographicCamera(main.WIDTH, main.HEIGHT);
        camera.position.set(main.WIDTH / 2, main.HEIGHT / 2, 0);
        mapRenderer = new OrthogonalTiledMapRenderer(main.getMap(), 1);
        mapRenderer.setView(camera);
        loadActors();
        score = new Score(wave, player, main);
    }

    @Override
    public void show() {
        main.stage.clear();
        main.stage.addActor(joy);
        main.stage.addActor(joy2);
        main.stage.addActor(score);

        //main.stage.addActor(states);
       /* Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                screenY = (int) Main.HEIGHT - screenY;
                multitouch(screenX, screenY, true, pointer);
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                screenY = (int) Main.HEIGHT - screenY;
                multitouch(screenX, screenY, false, pointer);
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                screenY = (int) Main.HEIGHT - screenY;
                multitouch(screenX, screenY, true, pointer);
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        });*/
    }

    @Override
    public void render(float delta) {
        GameUpdate();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapRenderer.setView(camera);
        mapRenderer.render(backgroundLayers);
        main.getBatch().setProjectionMatrix(camera.combined);
        main.getBatch().begin();
        for(int i = 0; i < bloods.size; i++){bloods.get(i).draw(main.getBatch()); }
        GameRender(main.getBatch());
        main.getBatch().end();
        mapRenderer.render(foregroundLayers);
        main.getBatch().begin();
        if(wave.isDraw())wave.draw(main.getBatch());
        main.getBatch().end();
        main.stage.act(delta);
        main.stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        //main.table[8].setScore(player.getScore());
        //main.sortTable();
        //main.WriteRecord();
        main.stage.dispose();
        mapRenderer.dispose();
        main.gun.dispose();
        for (int i = 0; i < main.sounds.size; i++) main.sounds.get(i).dispose();
        main.map.dispose();
    }

    public void GameUpdate(){
        player.setDirection(new Vector2(joy2.getDirection().x, joy2.getDirection().y));
        player.setRotation(joy.getRotation());
        player.update();
        camera.position.set(player.position.x, player.position.y, 0);
        if(camera.position.x + camera.viewportWidth / 2 > 6400) camera.position.x = (6400-camera.viewportWidth / 2);
        if(camera.position.x - camera.viewportWidth / 2 < 0) camera.position.x = (camera.viewportWidth / 2);
        if(camera.position.y + camera.viewportHeight / 2 > 3200) camera.position.y = (3200 - camera.viewportHeight / 2);
        if(camera.position.y - camera.viewportHeight / 2 < 0)camera.position.y = (camera.viewportHeight / 2);
        camera.update();
        gen.update(joy, main);
        for(int i = 0; i < bullets.size; i++){bullets.get(i).update(); if(bullets.get(i).isOut)bullets.removeIndex(i--);}
        if(!player.isGhost()) wave.update(main, player);
        //if(Enemies.size == 0) enemysGen.waveGen(main, +1);
        for(int i = 0; i < Enemies.size; i++){  Enemies.get(i).update(); if(Enemies.get(i).getStatus() == Enemy.Status.death){
            String name = "blood1";
            int bl = MathUtils.random(1,4);
            switch (bl){ case 1: name = "blood1"; break; case 2: name = "blood2"; break;  case 3: name = "blood3"; break; case 4: name = "blood4"; break;}
            bloods.add(new Blood(main.getBloodAt().findRegion(name),Enemies.get(i).position, 128));
            int s = MathUtils.random(0,14);
            main.sounds.get(s).play(0.5f);
            Enemies.removeIndex(i--);
        }}
        for(int i = 0; i < boss.size; i++){  boss.get(i).update(); if(boss.get(i).getStatus() == Boss.Status.death){
            wave.addWaveNumber(1);
            String name = "blood1";
            int bl = MathUtils.random(1,4);
            switch (bl){ case 1: name = "blood1"; break; case 2: name = "blood2"; break;  case 3: name = "blood3"; break; case 4: name = "blood4"; break;}
            bloods.add(new Blood(main.getBloodAt().findRegion(name),boss.get(i).position, 256));
            int s = MathUtils.random(0,14);
            main.sounds.get(s).play(0.5f);
            boss.removeIndex(i--);
        }}
        if(player.isGhost()){ for(int i = 0; i < Enemies.size; i++) Enemies.removeIndex(i);  for(int i = 0; i < boss.size; i++) boss.removeIndex(i);}
        //if(player.getHealth() <= 0) System.exit(0);
        collisionPlayer();
        score.update();
    }

    public void  GameRender(SpriteBatch batch){
        player.draw(batch);
        for(int i = 0; i < bullets.size; i++)bullets.get(i).draw(batch);
        for(int i = 0; i < Enemies.size; i++){Enemies.get(i).draw(batch); }
        for(int i = 0; i < boss.size; i++){boss.get(i).draw(batch);}

    }

    public void loadActors(){
        joy = new Joystick(new Vector2( Main.WIDTH - ((Main.HEIGHT/3)/2 + (Main.HEIGHT/3)/4), (Main.HEIGHT/3)/2 + (Main.HEIGHT/3)/4), main);
        joy2 = new Joystick(new Vector2( (Main.HEIGHT/3)/2 + (Main.HEIGHT/3)/4, (Main.HEIGHT/3)/2 + (Main.HEIGHT/3)/4), main);
        bullets = new Array<Bullet>();
        Enemies = new Array<Enemy>();
        gen = new BulletGenerator();
        bloods = new Array<Blood>();
        boss = new Array<Boss>();
        //enemysGen = new EnemysGen();
        player = new Player(main.getPlayersAt().findRegion(main.getPlayerskin()), new Vector2(6400 / 2, 3200/ 2), 10, Main.HEIGHT/25, 85);
        //Enemies.add(new Enemy(main.getPlayersAt().findRegion("robot1_hold"), new Point2D(Main.WIDTH / 2, Main.HEIGHT / 4), 2, player));
        //enemysGen.waveGen(main, +1);
        states = new States(main.getUi().findRegion("hp_scale"),main.getUi().findRegion("hp_red"), player);
        wave = new Wave(7,1,3, main);
        //wave.setWave(main);

    }

    public void collisionPlayer(){
        for(int i = 0; i < Enemies.size; i++){
            if(player.bounds.overlaps(Enemies.get(i).bounds)){
                if(!Enemies.get(i).isOver()){
                Enemies.get(i).hit();
                //Enemies.get(i).direction.set(-Enemies.get(i).direction.x, -Enemies.get(i).direction.y);
                //Enemies.get(i).rotation += 180;
                player.hit(5);
                player.addScore(1);
                int s = MathUtils.random(0,14);
                main.sounds.get(s).play(0.5f);}
                Enemies.get(i).setOver(true);
            }
            else {
                Enemies.get(i).setOver(false);
            }
        }
        for (int i = 0; i < boss.size; i++){
            if(player.bounds.overlaps(boss.get(i).bounds)){
                //boss.get(i).direction.set(-boss.get(i).direction.x, -boss.get(i).direction.y);
                //boss.get(i).rotation += 180;
                if(!boss.get(i).isOver()){
                boss.get(i).hit();
                player.hit(25);
                player.addScore(1);
                int s = MathUtils.random(0,14);
                main.sounds.get(s).play(0.5f);}
                boss.get(i).setOver(true);
            }
            else {
                boss.get(i).setOver(false);
            }
        }
    }

}
