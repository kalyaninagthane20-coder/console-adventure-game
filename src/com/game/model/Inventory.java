package com.game.model;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Encapsulated item storage. Callers cannot touch the underlying collection,
 * so the rules (FIFO use order, no nulls) live in one place.
 */
public class Inventory {

    private final Deque<Item> items = new ArrayDeque<>();

    public void add(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        items.addLast(item);
    }

    /** Removes and returns the oldest item, or {@code null} when empty. */
    public Item removeFirst() {
        return items.pollFirst();
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
