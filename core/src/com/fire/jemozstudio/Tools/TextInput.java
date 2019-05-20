/*
 * Здесь нету красивого кода, но зато все работает!
 * Copyright (c) 2019. JemozStudio
 */

package com.fire.jemozstudio.Tools;

import com.badlogic.gdx.Input;
import com.fire.jemozstudio.Main;

public class TextInput implements Input.TextInputListener {
    Main main;
    public TextInput(Main main){
        this.main = main;
    }

    @Override
    public void input(String text) {
        if(text == "")main.setPlayerName("Noname");
        else main.setPlayerName(text);
        System.out.println("Name is set");
    }

    @Override
    public void canceled() {
        main.setPlayerName("Noname");
        System.out.println("set default name");
    }
}