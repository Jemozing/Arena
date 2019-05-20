/*
 * Здесь нету красивого кода, но зато все работает!
 * Copyright (c) 2019. JemozStudio
 */

package com.fire.jemozstudio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fire.jemozstudio.Tools.ScoresTable;
import com.fire.jemozstudio.Tools.TextInput;
import com.fire.jemozstudio.View.GameScreen;
import com.fire.jemozstudio.View.MenuScreen;

public class Main extends Game {
	private AssetsLoader loader;
	public Stage stage;
	private String playerskin = "hitman1_gun";
	private SpriteBatch batch;
	public static float WIDTH, HEIGHT;
	private TextureAtlas players, joystick, buttons, ui, blood;
	public FreeTypeFontGenerator generator;
	public FreeTypeFontGenerator.FreeTypeFontParameter param;
	private NinePatch panel;
	public TiledMap map;
	public static final String PREF = "ScoreTable";
	public static final String KEY_NAME = "Name";
	public static final String KEY_SCORE = "Score";
	public static final String KEY_WAVE = "Wave";
	private String PlayerName;
	public int nPlayers = 10;
	public ScoresTable table[] = new ScoresTable[nPlayers];
	Preferences pref;
	GameScreen gameScreen;
	public MenuScreen menuScreen;
	public GlyphLayout gl;
	public Array<Sound> sounds;
	public Sound gun;
	private int Record;
	private TextInput textInput;
	@Override
	public void create () {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		loader = new AssetsLoader();
		pref = Gdx.app.getPreferences("ScoreTable");
		map = loader.manager.get("maps/Arena.tmx");
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/default.ttf"));
		param = new FreeTypeFontGenerator.FreeTypeFontParameter();
		param.size = (int)HEIGHT / 30;
		gl = new GlyphLayout();
        sounds = new Array<Sound>();
        for (int i = 1; i < 16; i++) sounds.add(loader.manager.get("raw/man_death_"+ i +".ogg", Sound.class));
        gun = loader.manager.get("raw/gun_sound.mp3", Sound.class);
		//param.color = new Color(1,1,1,1);
		stage = new Stage(new ScreenViewport());
		players = loader.manager.get("objects/textures/actors.atlas");
		ui = loader.manager.get("ui/ui.atlas");
		joystick = loader.manager.get("objects/joystick/joystick.atlas");
		buttons = loader.manager.get("objects/buttons/buttons.atlas");
		blood = loader.manager.get("objects/textures/blood.atlas");
		panel = new NinePatch(getButtonsAt().findRegion("20"), 8,8,6,6);
		menuScreen =new MenuScreen(this);
		batch = new SpriteBatch();
		initTable();
		ReadRecords();
		sortTable();
		textInput = new TextInput(this);
		Gdx.input.getTextInput(textInput, "Welcome to Game!", "","Enter a Name");
		setScreen(menuScreen);
	}

	/*@Override
	public void render(){
		batch.setColor(0,0,0,alpha);
		batch.begin();
		batch.draw(wallpap, 0,0);
		batch.end();
		if(alpha == 0) bol = true;
		if(bol){
			if(TimeUtils.nanoTime() > 2000000000) alpha += 0.02;
			if(alpha == 1) setScreen(new MenuScreen(this));
		}
		else alpha -= 0.02;
	}*/
	//Getters in main, don't DELETE THIS!
	public TiledMap getMap() {
		return map;
	}
	public TextureAtlas getPlayersAt() { return players; }
	public TextureAtlas getJoystickAt() { return joystick; }
	public TextureAtlas getButtonsAt() { return buttons; }
	public TextureAtlas getUi() { return ui; }
	public TextureAtlas getBloodAt() { return blood; }
	public void setPlayerskin(String playerskin) { this.playerskin = playerskin; }
	public String getPlayerskin() { return playerskin; }
	public NinePatch getPanel() { return panel; }
	public SpriteBatch getBatch() { return batch; }
	public ScoresTable[] getTable() { return table; }
	public String getPlayerName() { return PlayerName; }
	public void setPlayerName(String playerName) { PlayerName = playerName; }
	public TextInput getTextInput() { return textInput; }

	public void WriteRecord(){
		for(int i=0; i<nPlayers; i++){
			pref.putString(KEY_NAME+(i+1), table[i].getName());
			pref.putLong(KEY_SCORE+(i+1), table[i].getScore());
			pref.putLong(KEY_WAVE+(i+1), table[i].getWave());
			pref.flush();
		}
		System.out.println("Records is write!");
	}
	public void ReadRecords(){
		if(pref.contains(KEY_NAME+1)){
			for(int i=1; i<nPlayers; i++){
				table[i].setName(pref.getString(KEY_NAME+i,"        "));
				table[i].setScore(pref.getLong(KEY_SCORE+i, 0));
				table[i].setWave(pref.getLong(KEY_WAVE+i,0));
			} }
		System.out.println("Records is read!");
	}
	public void initTable(){ for(int i=0; i<nPlayers; i++) table[i] = new ScoresTable("            ", 0,0); }
	public void sortTable(){
		for(int j=0; j<nPlayers; j++)
			for(int i=0; i<nPlayers; i++){
				if(table[i].getScore() < table[j].getScore()){
					ScoresTable p = table[i];
					table[i] = table[j];
					table[j] = p;
				} }
		System.out.println("Records is sort!");
	}




	@Override
	public void dispose () {
		WriteRecord();
		batch.dispose();
		stage.dispose();
		gun.dispose();
        for (int i = 0; i < sounds.size; i++) sounds.get(i).dispose();
        generator.dispose();
        map.dispose();
	}
}
