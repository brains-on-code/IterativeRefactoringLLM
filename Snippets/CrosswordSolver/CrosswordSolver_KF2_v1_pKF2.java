package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CrosswordSolver {

    private CrosswordSolver() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if a word can be placed starting at (row, col) in the given direction
     * without going out of bounds and only on empty cells (' ').
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
     * Places a word starting at (row, col) in the given direction.
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
     * Removes a word starting at (row, col) in the given direction by resetting
     * the cells to empty (' ').
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
     * Attempts to solve the crossword puzzle by placing all words from the
     * collection into the puzzle using backtracking.
     *
     * @return true if the puzzle can be completely filled with the given words,
     *         false otherwise
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