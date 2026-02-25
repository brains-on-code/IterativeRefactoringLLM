package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility class for crossword-style backtracking placement.
 */
public final class Class1 {

    private static final char EMPTY_CELL = ' ';

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
        int wordLength = word.length();
        int rowLimit = board.length;
        int colLimit = board[0].length;

        if (vertical) {
            if (row + wordLength > rowLimit) {
                return false;
            }
            for (int offset = 0; offset < wordLength; offset++) {
                if (board[row + offset][col] != EMPTY_CELL) {
                    return false;
                }
            }
        } else {
            if (col + wordLength > colLimit) {
                return false;
            }
            for (int offset = 0; offset < wordLength; offset++) {
                if (board[row][col + offset] != EMPTY_CELL) {
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
        forEachLetter(word, (offset, ch) -> {
            int targetRow = getTargetRow(row, offset, vertical);
            int targetCol = getTargetCol(col, offset, vertical);
            board[targetRow][targetCol] = ch;
        });
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
        forEachLetter(word, (offset, ch) -> {
            int targetRow = getTargetRow(row, offset, vertical);
            int targetCol = getTargetCol(col, offset, vertical);
            board[targetRow][targetCol] = EMPTY_CELL;
        });
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
        return fillBoardRecursive(board, remainingWords);
    }

    private static boolean fillBoardRecursive(char[][] board, List<String> remainingWords) {
        int rows = board.length;
        int cols = board[0].length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (board[row][col] == EMPTY_CELL) {
                    return tryPlaceWordsAtCell(board, remainingWords, row, col);
                }
            }
        }

        // No empty cells left; board is successfully filled
        return true;
    }

    private static boolean tryPlaceWordsAtCell(char[][] board, List<String> remainingWords, int row, int col) {
        List<String> snapshot = new ArrayList<>(remainingWords);

        for (String word : snapshot) {
            if (tryPlaceWordInBothDirections(board, remainingWords, row, col, word)) {
                return true;
            }
        }

        return false;
    }

    private static boolean tryPlaceWordInBothDirections(
        char[][] board,
        List<String> remainingWords,
        int row,
        int col,
        String word
    ) {
        return tryPlaceWord(board, remainingWords, row, col, word, true)
            || tryPlaceWord(board, remainingWords, row, col, word, false);
    }

    private static boolean tryPlaceWord(
        char[][] board,
        List<String> remainingWords,
        int row,
        int col,
        String word,
        boolean vertical
    ) {
        if (!canPlaceWord(board, word, row, col, vertical)) {
            return false;
        }

        placeWord(board, word, row, col, vertical);
        remainingWords.remove(word);

        boolean solved = fillBoardRecursive(board, remainingWords);
        if (solved) {
            return true;
        }

        remainingWords.add(word);
        removeWord(board, word, row, col, vertical);
        return false;
    }

    private static int getTargetRow(int baseRow, int offset, boolean vertical) {
        return vertical ? baseRow + offset : baseRow;
    }

    private static int getTargetCol(int baseCol, int offset, boolean vertical) {
        return vertical ? baseCol : baseCol + offset;
    }

    @FunctionalInterface
    private interface LetterConsumer {
        void accept(int offset, char ch);
    }

    private static void forEachLetter(String word, LetterConsumer consumer) {
        int length = word.length();
        for (int offset = 0; offset < length; offset++) {
            consumer.accept(offset, word.charAt(offset));
        }
    }
}