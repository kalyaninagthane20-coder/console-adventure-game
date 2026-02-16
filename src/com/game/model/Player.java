package com.game.model;
import java.util.ArrayList;

public class Player {
    private ArrayList<Item> inventory = new ArrayList<>();

    private String name;
    private int health;

    public Player(String name, int health) {
        this.name = name;
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }


public void takeDamage(int damage) {
    health = health - damage;

    if (health < 0) {
        health = 0;
    }
}

public void heal(int amount) {
    health = health + amount;

    if (health > 100) {
        health = 100;
    }
}
public void addItem(Item item) {
    inventory.add(item);
    System.out.println(item.getName() + " added to inventory!");
}

public void useItem() {
    if (inventory.isEmpty()) {
        System.out.println("No items in inventory!");
        return;
    }

    Item item = inventory.remove(0);
    item.use(this);
}
}