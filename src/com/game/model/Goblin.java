package com.game.model;

public class Goblin extends Enemy {

    public Goblin() {
        super("Goblin", 50, 15);
    }

    @Override
    public int attack() {
        return attackPower;
    }
}