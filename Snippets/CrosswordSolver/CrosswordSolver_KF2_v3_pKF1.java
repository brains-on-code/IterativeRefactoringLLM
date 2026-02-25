package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CrosswordSolver {

    private CrosswordSolver() {
    }

    public static boolean canPlaceWord(char[][] board, String word, int startRow, int startColumn, boolean isVertical) {
        int wordLength = word.length();

        for (int index = 0; index < wordLength; index++) {
            if (isVertical) {
                int currentRow = startRow + index;
                if (currentRow >= board.length || board[currentRow][startColumn] != ' ') {
                    return false;
                }
            } else {
                int currentColumn = startColumn + index;
                if (currentColumn >= board[0].length || board[startRow][currentColumn] != ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public static void placeWord(char[][] board, String word, int startRow, int startColumn, boolean isVertical) {
        int wordLength = word.length();

        for (int index = 0; index < wordLength; index++) {
            char currentChar = word.charAt(index);
            if (isVertical) {
                board[startRow + index][startColumn] = currentChar;
            } else {
                board[startRow][startColumn + index] = currentChar;
            }
        }
    }

    public static void removeWord(char[][] board, String word, int startRow, int startColumn, boolean isVertical) {
        int wordLength = word.length();

        for (int index = 0; index < wordLength; index++) {
            if (isVertical) {
                board[startRow + index][startColumn] = ' ';
            } else {
                board[startRow][startColumn + index] = ' ';
            }
        }
    }

    public static boolean solveCrossword(char[][] board, Collection<String> words) {
        List<String> remainingWords = new ArrayList<>(words);

        for (int rowIndex = 0; rowIndex < board.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < board[0].length; columnIndex++) {
                if (board[rowIndex][columnIndex] == ' ') {
                    for (String word : new ArrayList<>(remainingWords)) {
                        for (boolean isVertical : new boolean[] {true, false}) {
                            if (canPlaceWord(board, word, rowIndex, columnIndex, isVertical)) {
                                placeWord(board, word, rowIndex, columnIndex, isVertical);
                                remainingWords.remove(word);

                                if (solveCrossword(board, remainingWords)) {
                                    return true;
                                }

                                remainingWords.add(word);
                                removeWord(board, word, rowIndex, columnIndex, isVertical);
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