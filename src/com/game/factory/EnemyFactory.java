package com.game.factory;

import com.game.model.Dragon;
import com.game.model.Enemy;
import com.game.model.Goblin;

import java.util.Random;

/**
 * Factory Method pattern: callers ask for an enemy and never call {@code new Goblin()}
 * themselves. Adding a new enemy means adding one enum value and one case here,
 * without touching the game loop (open/closed principle).
 */
public final class EnemyFactory {

    public enum EnemyType { GOBLIN, DRAGON }

    private EnemyFactory() {
        // static utility class, not meant to be instantiated
    }

    public static Enemy create(EnemyType type) {
        switch (type) {
            case GOBLIN:
                return new Goblin();
            case DRAGON:
                return new Dragon();
            default:
                throw new IllegalArgumentException("Unknown enemy type: " + type);
        }
    }

    /** Picks an enemy type uniformly at random. The Random is injected so tests can be deterministic. */
    public static Enemy createRandom(Random random) {
        EnemyType[] types = EnemyType.values();
        return create(types[random.nextInt(types.length)]);
    }
}
