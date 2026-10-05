package com.game.service;

import java.util.Optional;

/** The actions a player can choose during a battle round. */
public enum BattleAction {
    ATTACK,
    USE_ITEM,
    RUN;

    /** Maps the menu number shown to the player (1, 2, 3) to an action. */
    public static Optional<BattleAction> fromMenuChoice(int choice) {
        switch (choice) {
            case 1:
                return Optional.of(ATTACK);
            case 2:
                return Optional.of(USE_ITEM);
            case 3:
                return Optional.of(RUN);
            default:
                return Optional.empty();
        }
    }
}
