package com.codecool.dartcc.model;

public enum GameType {

    GAME_301(301),
    KILLER(5);

    private int score;

    GameType(int score) {
        this.score = score;
    }

    public int getScore(){
        return score;
    }

}
