/*
 * Здесь нету красивого кода, но зато все работает!
 * Copyright (c) 2019. JemozStudio
 */

package com.fire.jemozstudio.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.fire.jemozstudio.Main;

public class CharView implements Screen {
    Main main;
    ImageButton[] buttons_pl = new ImageButton[5];
    ImageButton[] buttons_gn = new ImageButton[3];
    ImageButton.ImageButtonStyle[] buPlStyles = new ImageButton.ImageButtonStyle[5];
    ImageButton.ImageButtonStyle[] buGnStyles = new ImageButton.ImageButtonStyle[3];
    ImageButton clbut;
    ImageButton.ImageButtonStyle clstyle;
    Image fon;
    Skin skin, clskin;
    String playerskinfin = "hitman1_", gunskinfin = "gun";
    Image panel;

    public CharView(Main main){
        this.main = main;
        fon = new Image(new Texture("fon.png"));
        fon.setSize(Main.WIDTH, Main.HEIGHT);
        fon.setPosition(0,0);
    skin = new Skin(main.getPlayersAt());
    clskin = new Skin(main.getButtonsAt());
    panel = new Image(main.getPanel());
    panel.setSize(main.WIDTH - main.WIDTH / 10, main.HEIGHT - main.HEIGHT / 10 );
    panel.setPosition(main.WIDTH / 20, main.HEIGHT / 20);
    setStyles();
    createButtons();
    }

    public void setStyles(){
        for (int i = 0; i < 5; i++){
            buPlStyles[i] = new ImageButton.ImageButtonStyle();}
            for (int i = 0; i < 3; i++) buGnStyles[i] = new ImageButton.ImageButtonStyle();
        clstyle = new ImageButton.ImageButtonStyle();

        buPlStyles[0].up = skin.getDrawable("hitman1_stand");
        buPlStyles[0].down = skin.getDrawable("hitman1_stand");
        buPlStyles[1].up = skin.getDrawable("manBlue_stand");
        buPlStyles[1].down = skin.getDrawable("manBlue_stand");
        buPlStyles[2].up = skin.getDrawable("manOld_stand");
        buPlStyles[2].down = skin.getDrawable("manOld_stand");
        buPlStyles[3].up = skin.getDrawable("soldier1_stand");
        buPlStyles[3].down = skin.getDrawable("soldier1_stand");
        buPlStyles[4].up = skin.getDrawable("survivor1_stand");
        buPlStyles[4].down = skin.getDrawable("survivor1_stand");

        buGnStyles[0].up = skin.getDrawable("weapon_gun");
        buGnStyles[0].down = skin.getDrawable("weapon_gun");
        buGnStyles[1].up = skin.getDrawable("weapon_machine");
        buGnStyles[1].down = skin.getDrawable("weapon_machine");
        buGnStyles[2].up = skin.getDrawable("weapon_silencer");
        buGnStyles[2].down = skin.getDrawable("weapon_silencer");

        clstyle.up = clskin.getDrawable("2");
        clstyle.down = clskin.getDrawable("2");
    }
    public void createButtons(){
        for (int i = 0; i < 5; i++){
            buttons_pl[i] = new ImageButton(buPlStyles[i]);
            buttons_pl[i].setSize(main.WIDTH / 10, Main.HEIGHT / 10);
            buttons_pl[i].setPosition(panel.getX() + main.WIDTH / 10, panel.getY() + Main.HEIGHT / 10 * i + 100); }
        buttons_pl[0].addListener(new InputListener(){@Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){playerskinfin = "hitman1_"; return true;}});
        buttons_pl[1].addListener(new InputListener(){@Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){playerskinfin = "manBlue_"; return true;}});
        buttons_pl[2].addListener(new InputListener(){@Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){playerskinfin = "manOld_"; return true;}});
        buttons_pl[3].addListener(new InputListener(){@Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){playerskinfin = "soldier1_"; return true;}});
        buttons_pl[4].addListener(new InputListener(){@Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){playerskinfin = "survivor1_";
            System.out.println("selected survivor"); return true;}});
        for (int i = 0; i < 3; i++){
            buttons_gn[i] = new ImageButton(buGnStyles[i]);
            buttons_gn[i].setSize(main.WIDTH / 5, Main.HEIGHT / 10);
            buttons_gn[i].setPosition(panel.getX() + main.WIDTH / 10 * 5, panel.getY() + Main.HEIGHT / 5 * i + 100); }
        buttons_gn[1].addListener(new InputListener(){@Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){gunskinfin = "gun"; return true;}});
        buttons_gn[1].addListener(new InputListener(){@Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){gunskinfin = "machine"; return true;}});
        buttons_gn[1].addListener(new InputListener(){@Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){gunskinfin = "silencer"; return true;}});

        clbut = new ImageButton(clstyle);
        clbut.setSize(96,96);
        clbut.setPosition(panel.getX()+ panel.getWidth() - clbut.getWidth()/2, panel.getY() + panel.getHeight() - clbut.getHeight()/2);
        clbut.addListener(new InputListener(){@Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){ UpdAndClose(); return true;}});
    }
    public void UpdAndClose(){
        main.setPlayerskin(playerskinfin + gunskinfin);
        main.setScreen(main.menuScreen);
    }

    public String getGunskinfin() {return gunskinfin;}
    public String getPlayerskinfin() {return playerskinfin;}

    @Override
    public void show() {
        main.stage.clear();
        main.stage.addActor(fon);
        main.stage.addActor(panel);
        main.stage.addActor(clbut);
        for (int i = 0; i < 5; i++) main.stage.addActor(buttons_pl[i]);
        for (int i = 0; i < 3; i++) main.stage.addActor(buttons_gn[i]);
        Gdx.input.setInputProcessor(main.stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
        main.stage.dispose();

    }
}
