package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility class for filling a character grid with words using backtracking.
 *
 * <p>The grid is assumed to be pre-filled with either spaces (' ') for empty
 * cells or other characters for blocked/occupied cells. Words are placed
 * either vertically (top to bottom) or horizontally (left to right).</p>
 */
public final class WordGridBacktracking {

    private static final char EMPTY_CELL = ' ';

    private WordGridBacktracking() {
        // Utility class; prevent instantiation.
    }

    /**
     * Returns {@code true} if {@code word} can be placed starting at
     * ({@code row}, {@code col}) in the given orientation without going out of
     * bounds or overlapping non-empty cells.
     */
    public static boolean canPlaceWord(char[][] grid, String word, int row, int col, boolean vertical) {
        int wordLength = word.length();

        if (vertical) {
            if (row + wordLength > grid.length) {
                return false;
            }
            for (int offset = 0; offset < wordLength; offset++) {
                if (grid[row + offset][col] != EMPTY_CELL) {
                    return false;
                }
            }
        } else {
            if (col + wordLength > grid[0].length) {
                return false;
            }
            for (int offset = 0; offset < wordLength; offset++) {
                if (grid[row][col + offset] != EMPTY_CELL) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Places {@code word} into {@code grid} starting at ({@code row},
     * {@code col}) in the given orientation. Assumes the placement is valid.
     */
    public static void placeWord(char[][] grid, String word, int row, int col, boolean vertical) {
        int wordLength = word.length();

        for (int offset = 0; offset < wordLength; offset++) {
            char ch = word.charAt(offset);
            if (vertical) {
                grid[row + offset][col] = ch;
            } else {
                grid[row][col + offset] = ch;
            }
        }
    }

    /**
     * Removes {@code word} from {@code grid} by resetting its cells to
     * {@link #EMPTY_CELL}, starting at ({@code row}, {@code col}) in the given
     * orientation.
     */
    public static void removeWord(char[][] grid, String word, int row, int col, boolean vertical) {
        int wordLength = word.length();

        for (int offset = 0; offset < wordLength; offset++) {
            if (vertical) {
                grid[row + offset][col] = EMPTY_CELL;
            } else {
                grid[row][col + offset] = EMPTY_CELL;
            }
        }
    }

    /**
     * Attempts to fill {@code grid} with all words from {@code words} using
     * backtracking. Words may be placed vertically or horizontally.
     *
     * @return {@code true} if all words can be placed; {@code false} otherwise
     */
    public static boolean fillGrid(char[][] grid, Collection<String> words) {
        List<String> remainingWords = new ArrayList<>(words);
        return backtrack(grid, remainingWords);
    }

    /**
     * Backtracking driver: finds the next empty cell and tries to place each
     * remaining word there (in both orientations).
     */
    private static boolean backtrack(char[][] grid, List<String> remainingWords) {
        int rows = grid.length;
        int cols = grid[0].length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] != EMPTY_CELL) {
                    continue;
                }

                // Snapshot to avoid concurrent modification while iterating.
                List<String> snapshot = new ArrayList<>(remainingWords);
                for (String word : snapshot) {
                    if (tryPlaceWordInBothDirections(grid, remainingWords, row, col, word)) {
                        return true;
                    }
                }

                // No word fits in this empty cell; backtrack.
                return false;
            }
        }

        // No empty cells left; all words placed.
        return true;
    }

    /**
     * Tries to place {@code word} at ({@code row}, {@code col}) both
     * vertically and horizontally.
     */
    private static boolean tryPlaceWordInBothDirections(
            char[][] grid, List<String> remainingWords, int row, int col, String word) {

        return tryPlaceWord(grid, remainingWords, row, col, word, true)
                || tryPlaceWord(grid, remainingWords, row, col, word, false);
    }

    /**
     * Tries to place {@code word} at ({@code row}, {@code col}) in the given
     * orientation and continues backtracking from there.
     */
    private static boolean tryPlaceWord(
            char[][] grid, List<String> remainingWords, int row, int col, String word, boolean vertical) {

        if (!canPlaceWord(grid, word, row, col, vertical)) {
            return false;
        }

        placeWord(grid, word, row, col, vertical);
        remainingWords.remove(word);

        if (backtrack(grid, remainingWords)) {
            return true;
        }

        // Undo placement and restore state.
        remainingWords.add(word);
        removeWord(grid, word, row, col, vertical);
        return false;
    }
}