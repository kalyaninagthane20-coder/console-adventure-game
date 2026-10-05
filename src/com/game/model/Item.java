package com.game.model;

/**
 * Abstract item. Every item has a name, and each concrete item defines its own
 * effect in {@link #use(Player)}. Subclasses are interchangeable wherever an
 * {@code Item} is expected (polymorphism).
 */
public abstract class Item {

    private final String name;

    protected Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /** Applies this item's effect to the player. */
    public abstract void use(Player player);
}
