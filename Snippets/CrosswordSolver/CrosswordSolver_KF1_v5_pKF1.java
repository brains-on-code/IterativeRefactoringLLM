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
            int startColumn,
            boolean vertical
    ) {
        int wordLength = word.length();
        int rowCount = board.length;
        int columnCount = board[0].length;

        for (int offset = 0; offset < wordLength; offset++) {
            if (vertical) {
                int currentRow = startRow + offset;
                if (currentRow >= rowCount || board[currentRow][startColumn] != ' ') {
                    return false;
                }
            } else {
                int currentColumn = startColumn + offset;
                if (currentColumn >= columnCount || board[startRow][currentColumn] != ' ') {
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
            char currentChar = word.charAt(offset);
            if (vertical) {
                board[startRow + offset][startColumn] = currentChar;
            } else {
                board[startRow][startColumn + offset] = currentChar;
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
                board[startRow + offset][startColumn] = ' ';
            } else {
                board[startRow][startColumn + offset] = ' ';
            }
        }
    }

    public static boolean solveCrossword(char[][] board, Collection<String> words) {
        List<String> remainingWords = new ArrayList<>(words);
        int rowCount = board.length;
        int columnCount = board[0].length;

        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                if (board[rowIndex][columnIndex] == ' ') {
                    for (String word : new ArrayList<>(remainingWords)) {
                        for (boolean vertical : new boolean[] {true, false}) {
                            if (canPlaceWord(board, word, rowIndex, columnIndex, vertical)) {
                                placeWord(board, word, rowIndex, columnIndex, vertical);
                                remainingWords.remove(word);

                                if (solveCrossword(board, remainingWords)) {
                                    return true;
                                }

                                remainingWords.add(word);
                                removeWord(board, word, rowIndex, columnIndex, vertical);
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