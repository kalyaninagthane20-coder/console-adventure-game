package com.game;

import com.game.factory.EnemyFactory;
import com.game.model.Dragon;
import com.game.model.Enemy;
import com.game.model.Goblin;
import com.game.model.Inventory;
import com.game.model.Item;
import com.game.model.Player;
import com.game.model.Potion;
import com.game.service.Battle;
import com.game.service.BattleAction;
import com.game.service.Game;
import com.game.ui.ConsoleView;
import com.game.ui.InputReader;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

/**
 * Dependency-free test runner (no JUnit needed, so it runs with just the JDK).
 *
 * <pre>
 *   javac -d out $(find src test -name "*.java")
 *   java -cp out com.game.GameTests
 * </pre>
 */
public class GameTests {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        testCharacterHealthIsClamped();
        testHealIsCappedAtMaxHealth();
        testInventoryIsFifo();
        testPotionRestoresHealth();
        testPolymorphicAttack();
        testFactoryCreatesRequestedType();
        testRandomFactoryIsDeterministicWithSeed();
        testBattleAttackDefeatsGoblin();
        testEnemyCounterAttacksWhenAlive();
        testRunAwayEndsBattle();
        testUseItemWithoutItemsDoesNotLoseTurn();
        testPlayerDiesWhenHealthReachesZero();
        testMenuChoiceMapping();
        testGameQuitsFromMenu();
        testGameIgnoresInvalidInputAndRecovers();

        System.out.println();
        System.out.println("Passed: " + passed + ", Failed: " + failed);
        if (failed > 0) {
            System.exit(1);
        }
    }

    // ---------- model: encapsulation & inheritance ----------

    static void testCharacterHealthIsClamped() {
        Player p = new Player("Test");
        p.takeDamage(500);
        check("health never goes below zero", p.getHealth() == 0);
        check("dead player is not alive", !p.isAlive());
    }

    static void testHealIsCappedAtMaxHealth() {
        Player p = new Player("Test");
        p.takeDamage(10);
        p.heal(1000);
        check("heal is capped at max health", p.getHealth() == Player.MAX_HEALTH);
    }

    static void testInventoryIsFifo() {
        Inventory inv = new Inventory();
        Item first = new Potion();
        Item second = new Potion();
        inv.add(first);
        inv.add(second);
        check("oldest item comes out first", inv.removeFirst() == first);
        check("size drops after removal", inv.size() == 1);
    }

    static void testPotionRestoresHealth() {
        Player p = new Player("Test");
        p.takeDamage(30);
        p.addItem(new Potion());
        check("player uses an item", p.useNextItem());
        check("potion heals by HEAL_AMOUNT", p.getHealth() == 70 + Potion.HEAL_AMOUNT);
        check("no item left", !p.useNextItem());
    }

    // ---------- polymorphism ----------

    static void testPolymorphicAttack() {
        Enemy goblin = new Goblin();
        Enemy dragon = new Dragon();
        check("goblin attacks with base power", goblin.attack() == Goblin.ATTACK_POWER);
        check("dragon breath adds its bonus", dragon.attack() == Dragon.ATTACK_POWER + Dragon.BREATH_BONUS);
        check("dragon out-damages goblin", dragon.attack() > goblin.attack());
    }

    // ---------- factory ----------

    static void testFactoryCreatesRequestedType() {
        check("factory makes a Goblin", EnemyFactory.create(EnemyFactory.EnemyType.GOBLIN) instanceof Goblin);
        check("factory makes a Dragon", EnemyFactory.create(EnemyFactory.EnemyType.DRAGON) instanceof Dragon);
    }

    static void testRandomFactoryIsDeterministicWithSeed() {
        Enemy a = EnemyFactory.createRandom(new Random(42));
        Enemy b = EnemyFactory.createRandom(new Random(42));
        check("same seed gives same enemy type", a.getType().equals(b.getType()));
    }

    // ---------- battle rules ----------

    static void testBattleAttackDefeatsGoblin() {
        Player p = new Player("Test");
        Goblin goblin = new Goblin();
        Battle battle = new Battle(p, goblin);

        Battle.RoundResult r1 = battle.playRound(BattleAction.ATTACK);
        check("goblin survives first hit", r1 == Battle.RoundResult.ONGOING);
        check("goblin took the player's base damage", goblin.getHealth() == Goblin.MAX_HEALTH - Player.BASE_DAMAGE);

        battle.playRound(BattleAction.ATTACK);
        Battle.RoundResult r3 = battle.playRound(BattleAction.ATTACK);
        check("third hit defeats the goblin (50 hp / 20 dmg)", r3 == Battle.RoundResult.ENEMY_DEFEATED);
    }

    static void testEnemyCounterAttacksWhenAlive() {
        Player p = new Player("Test");
        Dragon dragon = new Dragon();
        new Battle(p, dragon).playRound(BattleAction.ATTACK);
        check("dragon counter-attacks the player",
              p.getHealth() == Player.MAX_HEALTH - dragon.attack());
    }

    static void testRunAwayEndsBattle() {
        Battle battle = new Battle(new Player("Test"), new Goblin());
        check("running away returns PLAYER_FLED", battle.playRound(BattleAction.RUN) == Battle.RoundResult.PLAYER_FLED);
    }

    static void testUseItemWithoutItemsDoesNotLoseTurn() {
        Player p = new Player("Test");
        Goblin goblin = new Goblin();
        Battle battle = new Battle(p, goblin);
        Battle.RoundResult r = battle.playRound(BattleAction.USE_ITEM);
        check("using item with empty inventory is ONGOING", r == Battle.RoundResult.ONGOING);
        check("enemy did not attack when no item was used", p.getHealth() == Player.MAX_HEALTH);
    }

    static void testPlayerDiesWhenHealthReachesZero() {
        // Dragon hits for 35 per turn, player has 100 HP: dead after round 3 (100 -> 65 -> 30 -> -5).
        Player p = new Player("Test");
        Battle battle = new Battle(p, new Dragon());
        Battle.RoundResult last = Battle.RoundResult.ONGOING;
        for (int i = 0; i < 10 && last == Battle.RoundResult.ONGOING; i++) {
            last = battle.playRound(BattleAction.ATTACK);
        }
        check("dragon kills the player in exactly 3 rounds", last == Battle.RoundResult.PLAYER_DEFEATED);
        check("defeated player has 0 health", p.getHealth() == 0);
    }

    static void testMenuChoiceMapping() {
        check("menu 1 is ATTACK", BattleAction.fromMenuChoice(1).orElse(null) == BattleAction.ATTACK);
        check("menu 2 is USE_ITEM", BattleAction.fromMenuChoice(2).orElse(null) == BattleAction.USE_ITEM);
        check("menu 3 is RUN", BattleAction.fromMenuChoice(3).orElse(null) == BattleAction.RUN);
        check("menu 9 is unknown", !BattleAction.fromMenuChoice(9).isPresent());
    }

    // ---------- game loop driven by scripted input ----------

    static void testGameQuitsFromMenu() {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        ConsoleView view = new ConsoleView(new PrintStream(buf));
        InputReader input = new InputReader(new Scanner("4\n"), view);
        Game.Outcome outcome = new Game(new Player("Test"), input, view, new Random(1)).run();
        check("choosing 4 quits the game", outcome == Game.Outcome.QUIT);
        check("quit message is printed", buf.toString().contains("Thanks for playing"));
    }

    static void testGameIgnoresInvalidInputAndRecovers() {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        ConsoleView view = new ConsoleView(new PrintStream(buf));
        // letters, out-of-range number, then a valid "quit"
        InputReader input = new InputReader(new Scanner("abc\n99\n4\n"), view);
        Game.Outcome outcome = new Game(new Player("Test"), input, view, new Random(1)).run();
        check("game survives bad input and still quits", outcome == Game.Outcome.QUIT);
        check("invalid-choice message shown", buf.toString().contains("Invalid choice"));
    }

    // ---------- tiny assertion helper ----------

    private static void check(String description, boolean condition) {
        if (condition) {
            passed++;
            System.out.println("  PASS  " + description);
        } else {
            failed++;
            System.out.println("  FAIL  " + description);
        }
    }
}
