package com.game.model;

/** Restores a fixed amount of health when used. */
public class Potion extends Item {

    public static final int HEAL_AMOUNT = 20;

    public Potion() {
        super("Healing Potion");
    }

    @Override
    public void use(Player player) {
        player.heal(HEAL_AMOUNT);
    }
}
