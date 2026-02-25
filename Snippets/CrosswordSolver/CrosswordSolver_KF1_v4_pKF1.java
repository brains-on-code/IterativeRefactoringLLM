package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CrosswordBacktracking {

    private CrosswordBacktracking() {
    }

    public static boolean canPlaceWord(
            char[][] board,
            String word,
            int startRow,
            int startCol,
            boolean isVertical
    ) {
        int wordLength = word.length();
        int rowCount = board.length;
        int colCount = board[0].length;

        for (int index = 0; index < wordLength; index++) {
            if (isVertical) {
                int currentRow = startRow + index;
                if (currentRow >= rowCount || board[currentRow][startCol] != ' ') {
                    return false;
                }
            } else {
                int currentCol = startCol + index;
                if (currentCol >= colCount || board[startRow][currentCol] != ' ') {
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
            int startCol,
            boolean isVertical
    ) {
        int wordLength = word.length();

        for (int index = 0; index < wordLength; index++) {
            char letter = word.charAt(index);
            if (isVertical) {
                board[startRow + index][startCol] = letter;
            } else {
                board[startRow][startCol + index] = letter;
            }
        }
    }

    public static void removeWord(
            char[][] board,
            String word,
            int startRow,
            int startCol,
            boolean isVertical
    ) {
        int wordLength = word.length();

        for (int index = 0; index < wordLength; index++) {
            if (isVertical) {
                board[startRow + index][startCol] = ' ';
            } else {
                board[startRow][startCol + index] = ' ';
            }
        }
    }

    public static boolean solveCrossword(char[][] board, Collection<String> words) {
        List<String> remainingWords = new ArrayList<>(words);
        int rowCount = board.length;
        int colCount = board[0].length;

        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                if (board[row][col] == ' ') {
                    for (String word : new ArrayList<>(remainingWords)) {
                        for (boolean isVertical : new boolean[] {true, false}) {
                            if (canPlaceWord(board, word, row, col, isVertical)) {
                                placeWord(board, word, row, col, isVertical);
                                remainingWords.remove(word);

                                if (solveCrossword(board, remainingWords)) {
                                    return true;
                                }

                                remainingWords.add(word);
                                removeWord(board, word, row, col, isVertical);
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