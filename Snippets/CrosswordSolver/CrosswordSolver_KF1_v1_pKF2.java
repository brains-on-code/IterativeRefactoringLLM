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
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
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
    public static boolean method1(char[][] grid, String word, int row, int col, boolean vertical) {
        for (int offset = 0; offset < word.length(); offset++) {
            if (vertical) {
                if (row + offset >= grid.length || grid[row + offset][col] != ' ') {
                    return false;
                }
            } else {
                if (col + offset >= grid[0].length || grid[row][col + offset] != ' ') {
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
    public static void method2(char[][] grid, String word, int row, int col, boolean vertical) {
        for (int offset = 0; offset < word.length(); offset++) {
            if (vertical) {
                grid[row + offset][col] = word.charAt(offset);
            } else {
                grid[row][col + offset] = word.charAt(offset);
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
    public static void method3(char[][] grid, String word, int row, int col, boolean vertical) {
        for (int offset = 0; offset < word.length(); offset++) {
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
    public static boolean method4(char[][] grid, Collection<String> words) {
        List<String> remainingWords = new ArrayList<>(words);

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == ' ') {
                    for (String word : new ArrayList<>(remainingWords)) {
                        for (boolean vertical : new boolean[] {true, false}) {
                            if (method1(grid, word, row, col, vertical)) {
                                method2(grid, word, row, col, vertical);
                                remainingWords.remove(word);

                                if (method4(grid, remainingWords)) {
                                    return true;
                                }

                                remainingWords.add(word);
                                method3(grid, word, row, col, vertical);
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}