package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility class for crossword-style backtracking placement.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks whether a word can be placed on the board starting at (row, col).
     *
     * @param board     the character grid
     * @param word      the word to place
     * @param row       starting row
     * @param col       starting column
     * @param vertical  true for vertical placement, false for horizontal
     * @return true if the word can be placed, false otherwise
     */
    public static boolean canPlaceWord(char[][] board, String word, int row, int col, boolean vertical) {
        for (int offset = 0; offset < word.length(); offset++) {
            if (vertical) {
                int currentRow = row + offset;
                if (currentRow >= board.length || board[currentRow][col] != ' ') {
                    return false;
                }
            } else {
                int currentCol = col + offset;
                if (currentCol >= board[0].length || board[row][currentCol] != ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Places a word on the board starting at (row, col).
     *
     * @param board     the character grid
     * @param word      the word to place
     * @param row       starting row
     * @param col       starting column
     * @param vertical  true for vertical placement, false for horizontal
     */
    public static void placeWord(char[][] board, String word, int row, int col, boolean vertical) {
        for (int offset = 0; offset < word.length(); offset++) {
            char ch = word.charAt(offset);
            if (vertical) {
                board[row + offset][col] = ch;
            } else {
                board[row][col + offset] = ch;
            }
        }
    }

    /**
     * Removes a word from the board starting at (row, col) by replacing with spaces.
     *
     * @param board     the character grid
     * @param word      the word to remove
     * @param row       starting row
     * @param col       starting column
     * @param vertical  true for vertical placement, false for horizontal
     */
    public static void removeWord(char[][] board, String word, int row, int col, boolean vertical) {
        for (int offset = 0; offset < word.length(); offset++) {
            if (vertical) {
                board[row + offset][col] = ' ';
            } else {
                board[row][col + offset] = ' ';
            }
        }
    }

    /**
     * Attempts to fill the board with all words using backtracking.
     *
     * @param board  the character grid
     * @param words  the collection of words to place
     * @return true if all words can be placed, false otherwise
     */
    public static boolean fillBoard(char[][] board, Collection<String> words) {
        List<String> remainingWords = new ArrayList<>(words);

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] == ' ') {
                    for (String word : new ArrayList<>(remainingWords)) {
                        for (boolean vertical : new boolean[] {true, false}) {
                            if (canPlaceWord(board, word, row, col, vertical)) {
                                placeWord(board, word, row, col, vertical);
                                remainingWords.remove(word);

                                if (fillBoard(board, remainingWords)) {
                                    return true;
                                }

                                remainingWords.add(word);
                                removeWord(board, word, row, col, vertical);
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