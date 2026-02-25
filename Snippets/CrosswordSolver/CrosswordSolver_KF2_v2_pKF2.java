package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CrosswordSolver {

    private CrosswordSolver() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns true if {@code word} can be placed starting at ({@code row}, {@code col})
     * in the specified orientation without going out of bounds and only on empty cells.
     *
     * @param puzzle   crossword grid
     * @param word     word to place
     * @param row      starting row
     * @param col      starting column
     * @param vertical true for vertical placement, false for horizontal
     */
    public static boolean isValid(char[][] puzzle, String word, int row, int col, boolean vertical) {
        int wordLength = word.length();
        int maxRows = puzzle.length;
        int maxCols = puzzle[0].length;

        for (int i = 0; i < wordLength; i++) {
            int currentRow = vertical ? row + i : row;
            int currentCol = vertical ? col : col + i;

            if (currentRow >= maxRows || currentCol >= maxCols) {
                return false;
            }

            if (puzzle[currentRow][currentCol] != ' ') {
                return false;
            }
        }

        return true;
    }

    /**
     * Places {@code word} starting at ({@code row}, {@code col}) in the specified
     * orientation.
     *
     * @param puzzle   crossword grid
     * @param word     word to place
     * @param row      starting row
     * @param col      starting column
     * @param vertical true for vertical placement, false for horizontal
     */
    public static void placeWord(char[][] puzzle, String word, int row, int col, boolean vertical) {
        int wordLength = word.length();

        for (int i = 0; i < wordLength; i++) {
            int currentRow = vertical ? row + i : row;
            int currentCol = vertical ? col : col + i;

            puzzle[currentRow][currentCol] = word.charAt(i);
        }
    }

    /**
     * Removes {@code word} starting at ({@code row}, {@code col}) in the specified
     * orientation by resetting the affected cells to empty.
     *
     * @param puzzle   crossword grid
     * @param word     word to remove
     * @param row      starting row
     * @param col      starting column
     * @param vertical true for vertical placement, false for horizontal
     */
    public static void removeWord(char[][] puzzle, String word, int row, int col, boolean vertical) {
        int wordLength = word.length();

        for (int i = 0; i < wordLength; i++) {
            int currentRow = vertical ? row + i : row;
            int currentCol = vertical ? col : col + i;

            puzzle[currentRow][currentCol] = ' ';
        }
    }

    /**
     * Attempts to fill the crossword grid with all words from {@code words} using
     * backtracking.
     *
     * @param puzzle crossword grid
     * @param words  collection of words to place
     * @return true if all words can be placed, false otherwise
     */
    public static boolean solveCrossword(char[][] puzzle, Collection<String> words) {
        List<String> remainingWords = new ArrayList<>(words);
        int maxRows = puzzle.length;
        int maxCols = puzzle[0].length;

        for (int row = 0; row < maxRows; row++) {
            for (int col = 0; col < maxCols; col++) {
                if (puzzle[row][col] != ' ') {
                    continue;
                }

                List<String> snapshotWords = new ArrayList<>(remainingWords);
                for (String word : snapshotWords) {
                    if (tryPlaceWord(puzzle, remainingWords, word, row, col, true)) {
                        return true;
                    }
                    if (tryPlaceWord(puzzle, remainingWords, word, row, col, false)) {
                        return true;
                    }
                }

                // No word fits in this empty cell; backtrack
                return false;
            }
        }

        // No empty cells left; puzzle solved
        return true;
    }

    private static boolean tryPlaceWord(
        char[][] puzzle,
        List<String> remainingWords,
        String word,
        int row,
        int col,
        boolean vertical
    ) {
        if (!isValid(puzzle, word, row, col, vertical)) {
            return false;
        }

        placeWord(puzzle, word, row, col, vertical);
        remainingWords.remove(word);

        boolean solved = solveCrossword(puzzle, remainingWords);
        if (solved) {
            return true;
        }

        remainingWords.add(word);
        removeWord(puzzle, word, row, col, vertical);
        return false;
    }
}