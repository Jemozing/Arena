/*
 * Здесь нету красивого кода, но зато все работает!
 * Copyright (c) 2019. JemozStudio
 */

package com.fire.jemozstudio.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.fire.jemozstudio.Main;

public class ScoresView implements Screen {
    Main main;
    BitmapFont  font;
    Label label;
    Label.LabelStyle lbstyle;
    ImageButton clbut;
    ImageButton.ImageButtonStyle clstyle;
    ImageTextButton Chn_Name;
    ImageTextButton.ImageTextButtonStyle st_chname;
    NinePatch butNineUp, butNineDown;
    Image fon;
    Skin skin;
    String strTable = "Records:\n";
    public ScoresView(Main main){
        this.main = main;
        main.param.size = (int) Main.HEIGHT / 18;
        font = main.generator.generateFont(main.param);
        fon = new Image(new Texture("fon.png"));
        fon.setSize(Main.WIDTH, Main.HEIGHT);
        fon.setPosition(0,0);
        butNineUp = new NinePatch(main.getButtonsAt().findRegion("02"),8,8,8,10);
        butNineDown = new NinePatch(main.getButtonsAt().findRegion("03"),8,8,8,7);
        setStyle();
        createLabel();
       // panel = new Image(main.getPanel());

    }

    private void setStyle(){
        skin = new Skin();
        st_chname = new ImageTextButton.ImageTextButtonStyle();
        skin.add("panel", main.getPanel(), NinePatch.class);
        skin.add("cl", main.getButtonsAt().findRegion("2"), TextureRegion.class);
        skin.add("02", butNineUp, NinePatch.class);
        skin.add("03", butNineDown, NinePatch.class);
        lbstyle = new Label.LabelStyle();
        lbstyle.background = skin.getDrawable("panel");
        lbstyle.font = font;
        lbstyle.fontColor = new Color(1,1,1,1);
        for(int i=0; i<main.nPlayers; i++)
            strTable+=(i+1)+". "+main.getTable()[i].getName()+"  "+main.getTable()[i].getScore()+"\n";
        clstyle = new ImageButton.ImageButtonStyle();
        clstyle.up = skin.getDrawable("cl");
        clstyle.down = skin.getDrawable("cl");
        st_chname.font = font;
        st_chname.up = skin.getDrawable("02");
        st_chname.down = skin.getDrawable("03");
    }
    private void createLabel(){
        label = new Label(strTable, lbstyle);
        label.setSize(Main.WIDTH / 3, Main.HEIGHT / 10 * 8);
        label.setPosition(Main.WIDTH/ 2 - label.getWidth()/ 2, Main.HEIGHT / 2 - label.getHeight() / 2 + 80);

        clbut = new ImageButton(clstyle);
        clbut.setSize(96,96);
        clbut.setPosition(label.getX()+ label.getWidth() - clbut.getWidth()/2, label.getY() + label.getHeight() - clbut.getHeight()/2);
        clbut.addListener(new InputListener(){@Override public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){ main.setScreen(main.menuScreen); return true;}});

        Chn_Name = new ImageTextButton("Change Name", st_chname);
        Chn_Name.setSize(Main.WIDTH / 3, Main.HEIGHT / 6);
        Chn_Name.setPosition(Main.WIDTH / 2 - Chn_Name.getWidth() / 2,  10);
        //but_start.
        Chn_Name.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { return  true; }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) { Gdx.input.getTextInput(main.getTextInput(),"Please enter your name","","Name"); }
        });
    }

    @Override
    public void show() {
        main.stage.clear();
        main.stage.addActor(fon);
        main.stage.addActor(label);
        main.stage.addActor(clbut);
        main.stage.addActor(Chn_Name);
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
        main.dispose();
    }
}
