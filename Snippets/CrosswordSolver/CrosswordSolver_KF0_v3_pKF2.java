package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Backtracking-based crossword puzzle solver.
 *
 * <p>Example:
 * <pre>
 *  puzzle = {
 *      {' ', ' ', ' '},
 *      {' ', ' ', ' '},
 *      {' ', ' ', ' '}
 *  };
 *  words = List.of("cat", "dog");
 *
 *  Result:
 *  {
 *      {'c', 'a', 't'},
 *      {' ', ' ', ' '},
 *      {'d', 'o', 'g'}
 *  }
 * </pre>
 */
public final class CrosswordSolver {

    private static final char EMPTY_CELL = ' ';

    private CrosswordSolver() {
        // Utility class; prevent instantiation.
    }

    /**
     * Checks whether {@code word} can be placed starting at ({@code row}, {@code col})
     * in the given orientation.
     *
     * <p>A placement is valid if:
     * <ul>
     *   <li>the word fits within the puzzle bounds, and</li>
     *   <li>all cells it would occupy are currently empty.</li>
     * </ul>
     */
    public static boolean isValid(char[][] puzzle, String word, int row, int col, boolean vertical) {
        int wordLength = word.length();

        if (vertical) {
            if (row + wordLength > puzzle.length) {
                return false;
            }
            for (int i = 0; i < wordLength; i++) {
                if (puzzle[row + i][col] != EMPTY_CELL) {
                    return false;
                }
            }
        } else {
            if (col + wordLength > puzzle[0].length) {
                return false;
            }
            for (int i = 0; i < wordLength; i++) {
                if (puzzle[row][col + i] != EMPTY_CELL) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Places {@code word} into the puzzle starting at ({@code row}, {@code col})
     * in the given orientation.
     */
    public static void placeWord(char[][] puzzle, String word, int row, int col, boolean vertical) {
        int wordLength = word.length();

        if (vertical) {
            for (int i = 0; i < wordLength; i++) {
                puzzle[row + i][col] = word.charAt(i);
            }
        } else {
            for (int i = 0; i < wordLength; i++) {
                puzzle[row][col + i] = word.charAt(i);
            }
        }
    }

    /**
     * Removes {@code word} from the puzzle starting at ({@code row}, {@code col})
     * in the given orientation, restoring cells to {@link #EMPTY_CELL}.
     */
    public static void removeWord(char[][] puzzle, String word, int row, int col, boolean vertical) {
        int wordLength = word.length();

        if (vertical) {
            for (int i = 0; i < wordLength; i++) {
                puzzle[row + i][col] = EMPTY_CELL;
            }
        } else {
            for (int i = 0; i < wordLength; i++) {
                puzzle[row][col + i] = EMPTY_CELL;
            }
        }
    }

    /**
     * Attempts to place all {@code words} into {@code puzzle} using backtracking.
     *
     * @return {@code true} if all words can be placed; {@code false} otherwise
     */
    public static boolean solveCrossword(char[][] puzzle, Collection<String> words) {
        List<String> remainingWords = new ArrayList<>(words);

        for (int row = 0; row < puzzle.length; row++) {
            for (int col = 0; col < puzzle[0].length; col++) {
                if (puzzle[row][col] != EMPTY_CELL) {
                    continue;
                }

                // Try placing each remaining word at this empty cell.
                List<String> snapshot = new ArrayList<>(remainingWords);
                for (String word : snapshot) {
                    // Try both orientations at this position.
                    for (boolean vertical : new boolean[] {true, false}) {
                        if (!isValid(puzzle, word, row, col, vertical)) {
                            continue;
                        }

                        placeWord(puzzle, word, row, col, vertical);
                        remainingWords.remove(word);

                        if (solveCrossword(puzzle, remainingWords)) {
                            return true;
                        }

                        // Backtrack: undo placement and restore word to the pool.
                        remainingWords.add(word);
                        removeWord(puzzle, word, row, col, vertical);
                    }
                }

                // No word fits at this cell in any orientation.
                return false;
            }
        }

        // No empty cells left; puzzle is solved.
        return true;
    }
}