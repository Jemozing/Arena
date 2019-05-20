/*
 * Здесь нету красивого кода, но зато все работает!
 * Copyright (c) 2019. JemozStudio
 */

package com.fire.jemozstudio.Tools;

public class ScoresTable {
    private long score, wave;
    private String name;
   public ScoresTable(String name, long score, long wave){
        this.name = name;
        this.score = score;
        this.wave = wave;
    }

    public long getScore() { return score;}
    public String getName() { return name;}
    public void setScore(long score) { this.score = score;}
    public void setName(String name) { this.name = name; }
    public long getWave() { return wave; }
    public void setWave(long wave) { this.wave = wave; }
}
