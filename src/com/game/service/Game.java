package com.game.service;

import com.game.factory.EnemyFactory;
import com.game.model.Enemy;
import com.game.model.Player;
import com.game.model.Potion;
import com.game.ui.ConsoleView;
import com.game.ui.InputReader;

import java.util.Random;

/**
 * Game loop: keeps creating encounters until the player dies or quits.
 * Depends only on abstractions it is given (input, view, random source), so the
 * whole game can be driven from a test without a real keyboard.
 */
public class Game {

    public enum Outcome { PLAYER_DIED, QUIT }

    private final Player player;
    private final InputReader input;
    private final ConsoleView view;
    private final Random random;

    public Game(Player player, InputReader input, ConsoleView view, Random random) {
        this.player = player;
        this.input = input;
        this.view = view;
        this.random = random;
    }

    public Outcome run() {
        view.showWelcome(player.getName());

        while (player.isAlive()) {
            Enemy enemy = EnemyFactory.createRandom(random);
            view.showEncounter(enemy.getType());

            Battle battle = new Battle(player, enemy);
            Battle.RoundResult result = Battle.RoundResult.ONGOING;

            while (result == Battle.RoundResult.ONGOING) {
                view.showStatus(player, enemy);
                view.showActions(player.getItemCount());

                int choice = input.readChoice(1, 4);
                if (choice == InputReader.QUIT || choice == 4) {
                    view.showQuit();
                    return Outcome.QUIT;
                }

                BattleAction action = BattleAction.fromMenuChoice(choice).orElse(null);
                if (action == null) {
                    view.showInvalidChoice();
                    continue;
                }

                result = battle.playRound(action);
                view.showEvents(battle.getLastEvents());
            }

            switch (result) {
                case ENEMY_DEFEATED:
                    Potion loot = new Potion();
                    player.addItem(loot);
                    view.showLoot(loot.getName());
                    break;
                case PLAYER_FLED:
                    view.showFled();
                    break;
                case PLAYER_DEFEATED:
                    view.showGameOver();
                    return Outcome.PLAYER_DIED;
                default:
                    break;
            }
        }
        return Outcome.PLAYER_DIED;
    }
}
