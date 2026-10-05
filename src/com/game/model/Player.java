package com.game.model;

/**
 * The human-controlled character. Extends {@link Character} for health rules,
 * and owns an {@link Inventory} (composition: a Player <em>has</em> items).
 */
public class Player extends Character {

    public static final int MAX_HEALTH = 100;
    public static final int BASE_DAMAGE = 20;

    private final Inventory inventory = new Inventory();

    public Player(String name) {
        super(name, MAX_HEALTH);
    }

    /** Player's attack: a fixed damage value for now (could come from a weapon). */
    @Override
    public int attack() {
        return BASE_DAMAGE;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    /**
     * Uses the next item in the inventory.
     *
     * @return {@code true} if an item was used, {@code false} if the inventory was empty
     */
    public boolean useNextItem() {
        Item item = inventory.removeFirst();
        if (item == null) {
            return false;
        }
        item.use(this);
        return true;
    }

    public int getItemCount() {
        return inventory.size();
    }
}
