package com.game.model;

/** Weak, fast enemy: hits for its base attack power every turn. */
public class Goblin extends Enemy {

    public static final int MAX_HEALTH = 50;
    public static final int ATTACK_POWER = 15;

    public Goblin() {
        super("Goblin", MAX_HEALTH, ATTACK_POWER);
    }

    @Override
    public int attack() {
        return getAttackPower();
    }
}
