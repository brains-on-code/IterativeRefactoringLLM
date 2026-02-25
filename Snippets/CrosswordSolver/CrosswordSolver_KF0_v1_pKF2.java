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
     * Checks whether a word can be placed starting at (row, col).
     *
     * @param puzzle   crossword grid
     * @param word     word to place
     * @param row      starting row
     * @param col      starting column
     * @param vertical true for vertical placement, false for horizontal
     * @return true if placement is within bounds and only on empty cells
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
     * Places a word starting at (row, col).
     *
     * @param puzzle   crossword grid
     * @param word     word to place
     * @param row      starting row
     * @param col      starting column
     * @param vertical true for vertical placement, false for horizontal
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
     * Removes a word starting at (row, col) by resetting its cells to EMPTY_CELL.
     *
     * @param puzzle   crossword grid
     * @param word     word to remove
     * @param row      starting row
     * @param col      starting column
     * @param vertical true if the word was placed vertically, false if horizontally
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
     * Attempts to fill the crossword with all given words using backtracking.
     *
     * @param puzzle crossword grid
     * @param words  words to place
     * @return true if all words can be placed, false otherwise
     */
    public static boolean solveCrossword(char[][] puzzle, Collection<String> words) {
        List<String> remainingWords = new ArrayList<>(words);

        for (int row = 0; row < puzzle.length; row++) {
            for (int col = 0; col < puzzle[0].length; col++) {
                if (puzzle[row][col] != EMPTY_CELL) {
                    continue;
                }

                List<String> snapshot = new ArrayList<>(remainingWords);
                for (String word : snapshot) {
                    for (boolean vertical : new boolean[] {true, false}) {
                        if (!isValid(puzzle, word, row, col, vertical)) {
                            continue;
                        }

                        placeWord(puzzle, word, row, col, vertical);
                        remainingWords.remove(word);

                        if (solveCrossword(puzzle, remainingWords)) {
                            return true;
                        }

                        remainingWords.add(word);
                        removeWord(puzzle, word, row, col, vertical);
                    }
                }
                return false;
            }
        }
        return true;
    }
}