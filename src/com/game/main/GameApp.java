package com.game.main;

import com.game.model.Player;
import com.game.service.Game;
import com.game.ui.ConsoleView;
import com.game.ui.InputReader;

import java.util.Random;
import java.util.Scanner;

/**
 * Entry point. This is the only place where the object graph is wired together
 * (manual dependency injection): player, input, view and random source are created
 * here and handed to the Game.
 */
public class GameApp {

    public static void main(String[] args) {
        ConsoleView view = new ConsoleView(System.out);
        InputReader input = new InputReader(new Scanner(System.in), view);
        Player player = new Player("Kalyani");

        new Game(player, input, view, new Random()).run();
    }
}
