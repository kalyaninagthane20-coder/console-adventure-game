package com.game.model;

/**
 * Abstract base class for all living things in the game.
 *
 * <p>Holds the state that Player and Enemy have in common (name and health) and
 * the rules for changing it. Subclasses inherit this behaviour and cannot
 * corrupt the health value directly because the field is {@code private}
 * (encapsulation).
 */
public abstract class Character implements Combatant {

    private final String name;
    private final int maxHealth;
    private int health;

    protected Character(String name, int maxHealth) {
        if (maxHealth <= 0) {
            throw new IllegalArgumentException("maxHealth must be positive");
        }
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public void takeDamage(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("damage cannot be negative");
        }
        health = Math.max(0, health - amount);
    }

    /** Restores health, capped at {@link #getMaxHealth()}. */
    public void heal(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("heal amount cannot be negative");
        }
        health = Math.min(maxHealth, health + amount);
    }
}
