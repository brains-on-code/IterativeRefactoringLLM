package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CrosswordSolver {

    private static final char EMPTY_CELL = ' ';

    private CrosswordSolver() {
    }

    public static boolean canPlaceWord(
            char[][] board,
            String word,
            int startRow,
            int startColumn,
            boolean vertical
    ) {
        int wordLength = word.length();

        for (int offset = 0; offset < wordLength; offset++) {
            if (vertical) {
                int row = startRow + offset;
                if (row >= board.length || board[row][startColumn] != EMPTY_CELL) {
                    return false;
                }
            } else {
                int column = startColumn + offset;
                if (column >= board[0].length || board[startRow][column] != EMPTY_CELL) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void placeWord(
            char[][] board,
            String word,
            int startRow,
            int startColumn,
            boolean vertical
    ) {
        int wordLength = word.length();

        for (int offset = 0; offset < wordLength; offset++) {
            char letter = word.charAt(offset);
            if (vertical) {
                board[startRow + offset][startColumn] = letter;
            } else {
                board[startRow][startColumn + offset] = letter;
            }
        }
    }

    public static void removeWord(
            char[][] board,
            String word,
            int startRow,
            int startColumn,
            boolean vertical
    ) {
        int wordLength = word.length();

        for (int offset = 0; offset < wordLength; offset++) {
            if (vertical) {
                board[startRow + offset][startColumn] = EMPTY_CELL;
            } else {
                board[startRow][startColumn + offset] = EMPTY_CELL;
            }
        }
    }

    public static boolean solveCrossword(char[][] board, Collection<String> words) {
        List<String> remainingWords = new ArrayList<>(words);

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if (board[row][column] == EMPTY_CELL) {
                    for (String word : new ArrayList<>(remainingWords)) {
                        for (boolean vertical : new boolean[] {true, false}) {
                            if (canPlaceWord(board, word, row, column, vertical)) {
                                placeWord(board, word, row, column, vertical);
                                remainingWords.remove(word);

                                if (solveCrossword(board, remainingWords)) {
                                    return true;
                                }

                                remainingWords.add(word);
                                removeWord(board, word, row, column, vertical);
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