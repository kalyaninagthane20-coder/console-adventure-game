package com.game.model;

public abstract class Item {

    protected String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // what item does
    public abstract void use(Player player);
}