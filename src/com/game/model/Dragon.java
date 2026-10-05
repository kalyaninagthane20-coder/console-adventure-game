package com.game.model;

/**
 * Boss-style enemy. Tougher than a Goblin, and its breath attack adds a
 * bonus on top of the base power (overridden behaviour of {@link #attack()}).
 */
public class Dragon extends Enemy {

    public static final int MAX_HEALTH = 120;
    public static final int ATTACK_POWER = 25;
    public static final int BREATH_BONUS = 10;

    public Dragon() {
        super("Dragon", MAX_HEALTH, ATTACK_POWER);
    }

    @Override
    public int attack() {
        return getAttackPower() + BREATH_BONUS;
    }
}
