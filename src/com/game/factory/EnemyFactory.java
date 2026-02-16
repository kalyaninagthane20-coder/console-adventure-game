package com.game.factory;

import com.game.model.Enemy;
import com.game.model.Goblin;
import com.game.model.Dragon;
import java.util.Random;

public class EnemyFactory {

    public static Enemy createEnemy() {

        Random random = new Random();
        int number = random.nextInt(2);

        if (number == 0) {
            return new Goblin();
        } else {
            return new Dragon();
        }
    }
}