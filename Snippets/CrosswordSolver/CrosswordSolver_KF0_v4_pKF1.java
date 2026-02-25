package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A class to solve a crossword puzzle using backtracking.
 * Example:
 * Input:
 *  puzzle = {
 *      {' ', ' ', ' '},
 *      {' ', ' ', ' '},
 *      {' ', ' ', ' '}
 *  }
 *  words = List.of("cat", "dog")
 *
 * Output:
 *  {
 *      {'c', 'a', 't'},
 *      {' ', ' ', ' '},
 *      {'d', 'o', 'g'}
 *  }
 */
public final class CrosswordSolver {

    private CrosswordSolver() {
    }

    /**
     * Checks if a word can be placed at the specified position in the crossword.
     *
     * @param grid       The crossword puzzle represented as a 2D char array.
     * @param word       The word to be placed.
     * @param startRow   The row index where the word might be placed.
     * @param startCol   The column index where the word might be placed.
     * @param isVertical If true, the word is placed vertically; otherwise, horizontally.
     * @return true if the word can be placed, false otherwise.
     */
    public static boolean canPlaceWord(
        char[][] grid,
        String word,
        int startRow,
        int startCol,
        boolean isVertical
    ) {
        int wordLength = word.length();

        for (int index = 0; index < wordLength; index++) {
            if (isVertical) {
                int row = startRow + index;
                if (row >= grid.length || grid[row][startCol] != ' ') {
                    return false;
                }
            } else {
                int col = startCol + index;
                if (col >= grid[0].length || grid[startRow][col] != ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Places a word at the specified position in the crossword.
     *
     * @param grid       The crossword puzzle represented as a 2D char array.
     * @param word       The word to be placed.
     * @param startRow   The row index where the word will be placed.
     * @param startCol   The column index where the word will be placed.
     * @param isVertical If true, the word is placed vertically; otherwise, horizontally.
     */
    public static void placeWord(
        char[][] grid,
        String word,
        int startRow,
        int startCol,
        boolean isVertical
    ) {
        int wordLength = word.length();

        for (int index = 0; index < wordLength; index++) {
            char letter = word.charAt(index);
            if (isVertical) {
                grid[startRow + index][startCol] = letter;
            } else {
                grid[startRow][startCol + index] = letter;
            }
        }
    }

    /**
     * Removes a word from the specified position in the crossword.
     *
     * @param grid       The crossword puzzle represented as a 2D char array.
     * @param word       The word to be removed.
     * @param startRow   The row index where the word is placed.
     * @param startCol   The column index where the word is placed.
     * @param isVertical If true, the word was placed vertically; otherwise, horizontally.
     */
    public static void removeWord(
        char[][] grid,
        String word,
        int startRow,
        int startCol,
        boolean isVertical
    ) {
        int wordLength = word.length();

        for (int index = 0; index < wordLength; index++) {
            if (isVertical) {
                grid[startRow + index][startCol] = ' ';
            } else {
                grid[startRow][startCol + index] = ' ';
            }
        }
    }

    /**
     * Solves the crossword puzzle using backtracking.
     *
     * @param grid  The crossword puzzle represented as a 2D char array.
     * @param words The list of words to be placed.
     * @return true if the crossword is solved, false otherwise.
     */
    public static boolean solveCrossword(char[][] grid, Collection<String> words) {
        List<String> remainingWords = new ArrayList<>(words);

        int rowCount = grid.length;
        int columnCount = grid[0].length;

        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                if (grid[row][col] == ' ') {
                    for (String word : new ArrayList<>(remainingWords)) {
                        for (boolean isVertical : new boolean[] {true, false}) {
                            if (canPlaceWord(grid, word, row, col, isVertical)) {
                                placeWord(grid, word, row, col, isVertical);
                                remainingWords.remove(word);

                                if (solveCrossword(grid, remainingWords)) {
                                    return true;
                                }

                                remainingWords.add(word);
                                removeWord(grid, word, row, col, isVertical);
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