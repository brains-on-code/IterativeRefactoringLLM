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

    private WordGridBacktracking() {
        // Prevent instantiation
    }

    /**
     * Checks whether a word can be placed in the grid starting at the given
     * position, either vertically or horizontally, without overlapping
     * non-empty cells or going out of bounds.
     *
     * @param grid      the character grid
     * @param word      the word to place
     * @param row       starting row index
     * @param col       starting column index
     * @param vertical  true to place vertically, false to place horizontally
     * @return true if the word can be placed, false otherwise
     */
    public static boolean canPlaceWord(char[][] grid, String word, int row, int col, boolean vertical) {
        int wordLength = word.length();

        if (vertical) {
            if (row + wordLength > grid.length) {
                return false;
            }
            for (int offset = 0; offset < wordLength; offset++) {
                if (grid[row + offset][col] != ' ') {
                    return false;
                }
            }
        } else {
            if (col + wordLength > grid[0].length) {
                return false;
            }
            for (int offset = 0; offset < wordLength; offset++) {
                if (grid[row][col + offset] != ' ') {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Places a word into the grid starting at the given position, either
     * vertically or horizontally. Assumes the placement is valid.
     *
     * @param grid      the character grid
     * @param word      the word to place
     * @param row       starting row index
     * @param col       starting column index
     * @param vertical  true to place vertically, false to place horizontally
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
     * Removes a previously placed word from the grid by resetting its cells
     * back to spaces (' '), starting at the given position, either vertically
     * or horizontally.
     *
     * @param grid      the character grid
     * @param word      the word to remove
     * @param row       starting row index
     * @param col       starting column index
     * @param vertical  true if the word was placed vertically, false if horizontally
     */
    public static void removeWord(char[][] grid, String word, int row, int col, boolean vertical) {
        int wordLength = word.length();

        for (int offset = 0; offset < wordLength; offset++) {
            if (vertical) {
                grid[row + offset][col] = ' ';
            } else {
                grid[row][col + offset] = ' ';
            }
        }
    }

    /**
     * Attempts to fill the grid with all words from the given collection using
     * backtracking. Words may be placed vertically or horizontally.
     *
     * @param grid   the character grid to fill
     * @param words  the collection of words to place
     * @return true if all words can be placed, false otherwise
     */
    public static boolean fillGrid(char[][] grid, Collection<String> words) {
        List<String> remainingWords = new ArrayList<>(words);
        return backtrack(grid, remainingWords);
    }

    private static boolean backtrack(char[][] grid, List<String> remainingWords) {
        int rows = grid.length;
        int cols = grid[0].length;

        // Find the next empty cell
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] != ' ') {
                    continue;
                }

                // Try placing each remaining word at this position
                List<String> snapshot = new ArrayList<>(remainingWords);
                for (String word : snapshot) {
                    for (boolean vertical : new boolean[] {true, false}) {
                        if (!canPlaceWord(grid, word, row, col, vertical)) {
                            continue;
                        }

                        placeWord(grid, word, row, col, vertical);
                        remainingWords.remove(word);

                        if (backtrack(grid, remainingWords)) {
                            return true;
                        }

                        remainingWords.add(word);
                        removeWord(grid, word, row, col, vertical);
                    }
                }

                // No word fits in this empty cell; backtrack
                return false;
            }
        }

        // No empty cells left; all words placed
        return true;
    }
}