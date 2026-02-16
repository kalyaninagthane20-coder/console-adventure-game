package com.game.model;

public class Dragon extends Enemy {

    public Dragon() {
        super("Dragon", 120, 25);
    }

    @Override
    public int attack() {
        return attackPower + 10;
    }
}