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
     * @param grid     The crossword puzzle represented as a 2D char array.
     * @param word     The word to be placed.
     * @param startRow The row index where the word might be placed.
     * @param startCol The column index where the word might be placed.
     * @param vertical If true, the word is placed vertically; otherwise, horizontally.
     * @return true if the word can be placed, false otherwise.
     */
    public static boolean canPlaceWord(char[][] grid, String word, int startRow, int startCol, boolean vertical) {
        int wordLength = word.length();

        for (int offset = 0; offset < wordLength; offset++) {
            if (vertical) {
                int currentRow = startRow + offset;
                if (currentRow >= grid.length || grid[currentRow][startCol] != ' ') {
                    return false;
                }
            } else {
                int currentCol = startCol + offset;
                if (currentCol >= grid[0].length || grid[startRow][currentCol] != ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Places a word at the specified position in the crossword.
     *
     * @param grid     The crossword puzzle represented as a 2D char array.
     * @param word     The word to be placed.
     * @param startRow The row index where the word will be placed.
     * @param startCol The column index where the word will be placed.
     * @param vertical If true, the word is placed vertically; otherwise, horizontally.
     */
    public static void placeWord(char[][] grid, String word, int startRow, int startCol, boolean vertical) {
        int wordLength = word.length();

        for (int offset = 0; offset < wordLength; offset++) {
            char currentChar = word.charAt(offset);
            if (vertical) {
                grid[startRow + offset][startCol] = currentChar;
            } else {
                grid[startRow][startCol + offset] = currentChar;
            }
        }
    }

    /**
     * Removes a word from the specified position in the crossword.
     *
     * @param grid     The crossword puzzle represented as a 2D char array.
     * @param word     The word to be removed.
     * @param startRow The row index where the word is placed.
     * @param startCol The column index where the word is placed.
     * @param vertical If true, the word was placed vertically; otherwise, horizontally.
     */
    public static void removeWord(char[][] grid, String word, int startRow, int startCol, boolean vertical) {
        int wordLength = word.length();

        for (int offset = 0; offset < wordLength; offset++) {
            if (vertical) {
                grid[startRow + offset][startCol] = ' ';
            } else {
                grid[startRow][startCol + offset] = ' ';
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
        int colCount = grid[0].length;

        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int colIndex = 0; colIndex < colCount; colIndex++) {
                if (grid[rowIndex][colIndex] == ' ') {
                    for (String word : new ArrayList<>(remainingWords)) {
                        for (boolean vertical : new boolean[] {true, false}) {
                            if (canPlaceWord(grid, word, rowIndex, colIndex, vertical)) {
                                placeWord(grid, word, rowIndex, colIndex, vertical);
                                remainingWords.remove(word);

                                if (solveCrossword(grid, remainingWords)) {
                                    return true;
                                }

                                remainingWords.add(word);
                                removeWord(grid, word, rowIndex, colIndex, vertical);
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