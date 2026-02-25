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

    private static final char EMPTY_CELL = ' ';

    private CrosswordSolver() {
        // Utility class
    }

    /**
     * Checks if a word can be placed at the specified position in the crossword.
     *
     * @param puzzle   The crossword puzzle represented as a 2D char array.
     * @param word     The word to be placed.
     * @param row      The row index where the word might be placed.
     * @param col      The column index where the word might be placed.
     * @param vertical If true, the word is placed vertically; otherwise, horizontally.
     * @return true if the word can be placed, false otherwise.
     */
    public static boolean isValid(char[][] puzzle, String word, int row, int col, boolean vertical) {
        int wordLength = word.length();

        if (vertical) {
            if (!fitsVertically(puzzle, row, wordLength)) {
                return false;
            }
            for (int i = 0; i < wordLength; i++) {
                if (puzzle[row + i][col] != EMPTY_CELL) {
                    return false;
                }
            }
        } else {
            if (!fitsHorizontally(puzzle, col, wordLength)) {
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

    private static boolean fitsVertically(char[][] puzzle, int row, int wordLength) {
        return row + wordLength <= puzzle.length;
    }

    private static boolean fitsHorizontally(char[][] puzzle, int col, int wordLength) {
        return col + wordLength <= puzzle[0].length;
    }

    /**
     * Places a word at the specified position in the crossword.
     *
     * @param puzzle   The crossword puzzle represented as a 2D char array.
     * @param word     The word to be placed.
     * @param row      The row index where the word will be placed.
     * @param col      The column index where the word will be placed.
     * @param vertical If true, the word is placed vertically; otherwise, horizontally.
     */
    public static void placeWord(char[][] puzzle, String word, int row, int col, boolean vertical) {
        int wordLength = word.length();

        for (int i = 0; i < wordLength; i++) {
            char currentChar = word.charAt(i);
            if (vertical) {
                puzzle[row + i][col] = currentChar;
            } else {
                puzzle[row][col + i] = currentChar;
            }
        }
    }

    /**
     * Removes a word from the specified position in the crossword.
     *
     * @param puzzle   The crossword puzzle represented as a 2D char array.
     * @param word     The word to be removed.
     * @param row      The row index where the word is placed.
     * @param col      The column index where the word is placed.
     * @param vertical If true, the word was placed vertically; otherwise, horizontally.
     */
    public static void removeWord(char[][] puzzle, String word, int row, int col, boolean vertical) {
        int wordLength = word.length();

        for (int i = 0; i < wordLength; i++) {
            if (vertical) {
                puzzle[row + i][col] = EMPTY_CELL;
            } else {
                puzzle[row][col + i] = EMPTY_CELL;
            }
        }
    }

    /**
     * Solves the crossword puzzle using backtracking.
     *
     * @param puzzle The crossword puzzle represented as a 2D char array.
     * @param words  The list of words to be placed.
     * @return true if the crossword is solved, false otherwise.
     */
    public static boolean solveCrossword(char[][] puzzle, Collection<String> words) {
        List<String> remainingWords = new ArrayList<>(words);

        for (int row = 0; row < puzzle.length; row++) {
            for (int col = 0; col < puzzle[0].length; col++) {
                if (puzzle[row][col] == EMPTY_CELL) {
                    return tryPlaceWordsAtCell(puzzle, remainingWords, row, col);
                }
            }
        }

        return true;
    }

    private static boolean tryPlaceWordsAtCell(char[][] puzzle, List<String> remainingWords, int row, int col) {
        List<String> snapshot = new ArrayList<>(remainingWords);

        for (String word : snapshot) {
            if (tryPlaceWord(puzzle, remainingWords, word, row, col, true)) {
                return true;
            }
            if (tryPlaceWord(puzzle, remainingWords, word, row, col, false)) {
                return true;
            }
        }

        return false;
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