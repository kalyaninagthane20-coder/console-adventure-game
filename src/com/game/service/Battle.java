package com.game.service;

import com.game.model.Combatant;
import com.game.model.Player;
import com.game.model.Enemy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Runs one fight between the player and one enemy.
 *
 * <p>The battle does not print anything. It records what happened as text events
 * that the caller (the UI) can show. This keeps game rules separate from I/O and
 * makes the rules easy to unit-test.
 */
public class Battle {

    public enum RoundResult { ONGOING, ENEMY_DEFEATED, PLAYER_DEFEATED, PLAYER_FLED }

    private final Player player;
    private final Enemy enemy;
    private final List<String> events = new ArrayList<>();

    public Battle(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    /**
     * Plays one round: the player's action, then the enemy's counter-attack
     * if it survived and the player did not flee.
     */
    public RoundResult playRound(BattleAction action) {
        events.clear();

        switch (action) {
            case ATTACK:
                hit(player, enemy);
                break;
            case USE_ITEM:
                if (!player.useNextItem()) {
                    events.add("No items in inventory! You lose no turn.");
                    return RoundResult.ONGOING;
                }
                events.add(player.getName() + " used an item.");
                break;
            case RUN:
                events.add(player.getName() + " escaped!");
                return RoundResult.PLAYER_FLED;
            default:
                throw new IllegalStateException("Unhandled action: " + action);
        }

        if (!enemy.isAlive()) {
            events.add("You defeated the " + enemy.getType() + "!");
            return RoundResult.ENEMY_DEFEATED;
        }

        hit(enemy, player);
        if (!player.isAlive()) {
            events.add("You were defeated by the " + enemy.getType() + ".");
            return RoundResult.PLAYER_DEFEATED;
        }
        return RoundResult.ONGOING;
    }

    /** Applies {@code attacker.attack()} damage to {@code target} and records the event. */
    private void hit(Combatant attacker, Combatant target) {
        int damage = attacker.attack();
        target.takeDamage(damage);
        events.add(attacker.getName() + " hits " + target.getName() + " for " + damage + " damage.");
    }

    /** Events from the most recent round, in order. */
    public List<String> getLastEvents() {
        return Collections.unmodifiableList(events);
    }

    public Player getPlayer() {
        return player;
    }

    public Enemy getEnemy() {
        return enemy;
    }
}
