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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fire.jemozstudio.Main;

public class MenuScreen implements Screen{
    Main main;
    CharView view;
    ImageTextButton but_start, but_settings, but_player;
    ImageTextButton.ImageTextButtonStyle style_start, style_setting, style_player;
    Image fon;
    TextArea textArea;
    TextArea.TextFieldStyle txstyle;
    Skin sk_buttons;
    Image title;
    Texture img;
    NinePatch butNineUp, butNineDown;
    BitmapFont font;
    String text;
    public MenuScreen(Main main){
        this.main = main;
        img = new Texture(Gdx.files.internal("Title.png"));
        view = new CharView(main);
        title = new Image(img);
        fon = new Image(new Texture("fon.png"));
        fon.setSize(Main.WIDTH, Main.HEIGHT);
        fon.setPosition(0,0);
        font = main.generator.generateFont(main.param);
        title.setPosition(main.WIDTH / 2 - title.getWidth() / 2,  main.HEIGHT / 5 * 3 + 30);
        title.setTouchable(Touchable.disabled);
        butNineUp = new NinePatch(main.getButtonsAt().findRegion("02"),8,8,8,10);
        butNineDown = new NinePatch(main.getButtonsAt().findRegion("03"),8,8,8,7);
        //Стили для кнопок
        sk_buttons = new Skin();
        sk_buttons.add("02", butNineUp, NinePatch.class);
        sk_buttons.add("03", butNineDown, NinePatch.class);
        style_start = new ImageTextButton.ImageTextButtonStyle();
        style_setting = new ImageTextButton.ImageTextButtonStyle();
        style_player = new ImageTextButton.ImageTextButtonStyle();
        txstyle = new TextField.TextFieldStyle();
        text = "Change Name"+ "\n" + main.getPlayerName();
        setStyles();


        //Кнопки

    }

    private void  setStyles(){
        //Стиль кнопки старта
        style_start.up = sk_buttons.getDrawable("02");
        //style_start.over = sk_buttons.getDrawable("");
        style_start.down = sk_buttons.getDrawable("03");
        style_start.font = font;
        //Стиль кнопки настроек
        style_setting.up = sk_buttons.getDrawable("02");
        // style_setting.over = sk_buttons.getDrawable("");
        style_setting.down = sk_buttons.getDrawable("03");
        style_setting.font = font;
        //Стиль кнопки персонажа
        style_player.up = sk_buttons.getDrawable("02");
        //style_player.over = sk_buttons.getDrawable("");
        style_player.down = sk_buttons.getDrawable("03");
        style_player.font = font;

        main.param.size = 16;
        txstyle.font = font;
        txstyle.messageFont = font;
        txstyle.fontColor = new Color(0,0,0,1);
    }

    private void createBut(){
        //Настройки кнопки старт
        but_start = new ImageTextButton("START", style_start);
        but_start.setSize(main.WIDTH / 3, main.HEIGHT / 5);
        but_start.setPosition(main.WIDTH / 2 - but_start.getWidth() / 2,  main.HEIGHT / 5 * 2 + 30);
        //but_start.
        but_start.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) { return  true; }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) { main.setScreen(new GameScreen(main)); }
        });
        main.stage.addActor(but_start);
        //Параметры кнопки настроек
        but_settings = new ImageTextButton("Records", style_setting);
        but_settings.setSize(main.WIDTH / 3, main.HEIGHT / 5);
        but_settings.setPosition(main.WIDTH / 2 - but_settings.getWidth() / 2,  main.HEIGHT / 5 * 0 + 30);
        but_settings.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){ return true; }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) { main.setScreen(new ScoresView(main)); }
        });
        main.stage.addActor(but_settings);
        //Настройки кнопки персонаж
        but_player = new ImageTextButton("Characters", style_player);
        but_player.setSize(main.WIDTH / 3, main.HEIGHT / 5);
        but_player.setPosition(main.WIDTH / 2 - but_player.getWidth() / 2,  main.HEIGHT / 5 * 1 + 30);
        but_player.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){ return true; }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) { main.setScreen(view); }
        });
        main.stage.addActor(but_player);

        textArea = new TextArea(text, txstyle);
        textArea.setSize(250,200);
        textArea.setPosition(Main.WIDTH - textArea.getWidth()/2 * 3, Main.HEIGHT - textArea.getHeight());
        main.stage.addActor(textArea);
    }


    @Override
    public void show() {
        main.stage.clear();
        main.stage.addActor(fon);
        main.stage.addActor(title);
        createBut();
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
        main.stage.getCamera().viewportWidth = width;
        main.stage.getCamera().viewportHeight = height;
        main.stage.getCamera().update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        main.stage.clear();
    }

    @Override
    public void dispose() {
        main.stage.dispose();
        sk_buttons.dispose();
        img.dispose();

    }
}
