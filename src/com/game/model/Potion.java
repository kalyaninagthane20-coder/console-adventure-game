package com.game.model;

public class Potion extends Item {

    private int healAmount;

    public Potion() {
        super("Healing Potion");
        this.healAmount = 20;
    }

    @Override
    public void use(Player player) {
        player.heal(healAmount);
        System.out.println("You used a potion! Restored " + healAmount + " HP.");
    }
}