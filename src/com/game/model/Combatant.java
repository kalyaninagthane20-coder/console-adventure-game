package com.game.model;

/**
 * Contract shared by everything that can take part in a fight.
 *
 * <p>An interface is used (instead of only a base class) so that the battle
 * logic depends on <em>what an object can do</em>, not on its concrete type.
 * Any future fighter (e.g. an NPC ally) only has to implement this interface.
 */
public interface Combatant {

    String getName();

    int getHealth();

    boolean isAlive();

    /** Reduces health by {@code amount}. Health never drops below zero. */
    void takeDamage(int amount);

    /**
     * Returns the damage this combatant deals on its turn.
     * Each implementation decides its own strategy (polymorphism).
     */
    int attack();
}
