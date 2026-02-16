package com.game.service;
import com.game.factory.EnemyFactory;
import com.game.model.Player;
import com.game.model.Enemy;
import com.game.model.Potion;
//import com.game.model.Goblin;
// import com.game.model.Dragon;
import java.util.Scanner;

public class Game {

    private Player player;
    private Enemy enemy;
    private Scanner sc;

    public Game() {
        player = new Player("Kalyani", 100);
        enemy = EnemyFactory.createEnemy();
        sc = new Scanner(System.in);
    }

    public void start() {

    System.out.println("Welcome to the Adventure!");

    while (player.getHealth() > 0) {

        enemy = EnemyFactory.createEnemy();
        System.out.println("\nA wild " + enemy.getType() + " appeared!");

        // battle loop
        while (player.getHealth() > 0 && enemy.getHealth() > 0) {

            System.out.println("\nYour Health: " + player.getHealth());
            System.out.println(enemy.getType() + " Health: " + enemy.getHealth());

            System.out.println("\nChoose your action:");
            System.out.println("1. Attack");
            System.out.println("2. Use Item");
            System.out.println("3. Run Away");

            int choice = sc.nextInt();

            if (choice == 1) {
                enemy.takeDamage(20);
                System.out.println("You attack!");
            }
            else if (choice == 2) {
                player.useItem();
            }
            else if (choice == 3) {
                System.out.println("You escaped!");
                break;
            }
            else {
                System.out.println("Invalid choice!");
            }

            // enemy turn
            if (enemy.getHealth() > 0) {
                player.takeDamage(enemy.attack());
                System.out.println(enemy.getType() + " attacks!");
            }
        }

        // reward if enemy defeated
        if (enemy.getHealth() == 0) {
            System.out.println("You defeated the " + enemy.getType() + "!");
            System.out.println("Enemy dropped a potion!");
            player.addItem(new Potion());
        }

        if (player.getHealth() == 0) {
            System.out.println("\nGame Over 💀");
            break;
        }

        System.out.println("\nPrepare for the next battle...");
    }
}
}