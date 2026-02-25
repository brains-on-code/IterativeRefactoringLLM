package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CrosswordBacktracking {

    private CrosswordBacktracking() {
    }

    public static boolean canPlaceWord(char[][] board, String word, int startRow, int startCol, boolean isVertical) {
        int wordLength = word.length();
        int boardHeight = board.length;
        int boardWidth = board[0].length;

        for (int index = 0; index < wordLength; index++) {
            if (isVertical) {
                int currentRow = startRow + index;
                if (currentRow >= boardHeight || board[currentRow][startCol] != ' ') {
                    return false;
                }
            } else {
                int currentCol = startCol + index;
                if (currentCol >= boardWidth || board[startRow][currentCol] != ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public static void placeWord(char[][] board, String word, int startRow, int startCol, boolean isVertical) {
        int wordLength = word.length();

        for (int index = 0; index < wordLength; index++) {
            char currentChar = word.charAt(index);
            if (isVertical) {
                board[startRow + index][startCol] = currentChar;
            } else {
                board[startRow][startCol + index] = currentChar;
            }
        }
    }

    public static void removeWord(char[][] board, String word, int startRow, int startCol, boolean isVertical) {
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
        int boardHeight = board.length;
        int boardWidth = board[0].length;

        for (int row = 0; row < boardHeight; row++) {
            for (int col = 0; col < boardWidth; col++) {
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