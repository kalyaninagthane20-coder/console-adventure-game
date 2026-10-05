package com.game.model;

/**
 * Abstract enemy. Inherits health handling from {@link Character} and adds an
 * attack strength. Concrete enemies (Goblin, Dragon) override {@link #attack()}
 * to change <em>how</em> they hurt the player: this is runtime polymorphism.
 */
public abstract class Enemy extends Character {

    private final int attackPower;

    protected Enemy(String name, int maxHealth, int attackPower) {
        super(name, maxHealth);
        if (attackPower < 0) {
            throw new IllegalArgumentException("attackPower cannot be negative");
        }
        this.attackPower = attackPower;
    }

    protected int getAttackPower() {
        return attackPower;
    }

    /** Short description shown in the console, e.g. "Goblin". */
    public String getType() {
        return getName();
    }
}
