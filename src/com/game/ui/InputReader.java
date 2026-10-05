package com.game.ui;

import java.util.Scanner;

/**
 * Reads menu choices from the user and validates them.
 *
 * <p>The old version called {@code Scanner.nextInt()} directly, which crashes
 * with an exception when the user types letters. This class re-prompts instead.
 */
public class InputReader {

    /** Returned when the input stream ends (e.g. Ctrl+Z / piped input runs out). */
    public static final int QUIT = -1;

    private final Scanner scanner;
    private final ConsoleView view;

    public InputReader(Scanner scanner, ConsoleView view) {
        this.scanner = scanner;
        this.view = view;
    }

    /**
     * Keeps asking until the user enters a whole number in [min, max].
     *
     * @return the chosen number, or {@link #QUIT} if input has ended
     */
    public int readChoice(int min, int max) {
        while (true) {
            if (!scanner.hasNextLine()) {
                return QUIT;
            }
            String line = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // fall through and ask again
            }
            view.showInvalidChoice();
        }
    }
}
