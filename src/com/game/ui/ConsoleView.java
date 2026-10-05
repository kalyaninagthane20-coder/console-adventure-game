package com.game.ui;

import com.game.model.Enemy;
import com.game.model.Player;

import java.io.PrintStream;
import java.util.List;

/**
 * Everything the player sees. All printing is here, so the game rules never
 * call {@code System.out} directly. Tests can pass any {@link PrintStream}.
 */
public class ConsoleView {

    private final PrintStream out;

    public ConsoleView(PrintStream out) {
        this.out = out;
    }

    public void showWelcome(String playerName) {
        out.println("Welcome to the Adventure, " + playerName + "!");
    }

    public void showEncounter(String enemyType) {
        out.println("\nA wild " + enemyType + " appeared!");
    }

    public void showStatus(Player player, Enemy enemy) {
        out.println();
        out.println("Your health: " + player.getHealth() + "/" + player.getMaxHealth());
        out.println(enemy.getType() + " health: " + enemy.getHealth());
    }

    public void showActions(int itemCount) {
        out.println("\nChoose your action:");
        out.println("1. Attack");
        out.println("2. Use item (" + itemCount + " available)");
        out.println("3. Run away");
        out.println("4. Quit game");
        out.print("> ");
    }

    public void showEvents(List<String> events) {
        for (String event : events) {
            out.println(event);
        }
    }

    public void showInvalidChoice() {
        out.println("Invalid choice, please enter a number from the menu.");
    }

    public void showLoot(String itemName) {
        out.println("The enemy dropped a " + itemName + "!");
    }

    public void showFled() {
        out.println("Prepare for the next encounter...");
    }

    public void showGameOver() {
        out.println("\nGame Over. Better luck next time!");
    }

    public void showQuit() {
        out.println("Thanks for playing!");
    }
}
