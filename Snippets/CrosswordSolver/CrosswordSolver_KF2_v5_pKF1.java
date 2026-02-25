package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CrosswordSolver {

    private static final char EMPTY_CELL = ' ';

    private CrosswordSolver() {
    }

    public static boolean canPlaceWord(
            char[][] grid,
            String word,
            int startRow,
            int startCol,
            boolean isVertical
    ) {
        int wordLength = word.length();

        for (int index = 0; index < wordLength; index++) {
            if (isVertical) {
                int currentRow = startRow + index;
                if (currentRow >= grid.length || grid[currentRow][startCol] != EMPTY_CELL) {
                    return false;
                }
            } else {
                int currentCol = startCol + index;
                if (currentCol >= grid[0].length || grid[startRow][currentCol] != EMPTY_CELL) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void placeWord(
            char[][] grid,
            String word,
            int startRow,
            int startCol,
            boolean isVertical
    ) {
        int wordLength = word.length();

        for (int index = 0; index < wordLength; index++) {
            char currentChar = word.charAt(index);
            if (isVertical) {
                grid[startRow + index][startCol] = currentChar;
            } else {
                grid[startRow][startCol + index] = currentChar;
            }
        }
    }

    public static void removeWord(
            char[][] grid,
            String word,
            int startRow,
            int startCol,
            boolean isVertical
    ) {
        int wordLength = word.length();

        for (int index = 0; index < wordLength; index++) {
            if (isVertical) {
                grid[startRow + index][startCol] = EMPTY_CELL;
            } else {
                grid[startRow][startCol + index] = EMPTY_CELL;
            }
        }
    }

    public static boolean solveCrossword(char[][] grid, Collection<String> words) {
        List<String> remainingWords = new ArrayList<>(words);

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == EMPTY_CELL) {
                    for (String word : new ArrayList<>(remainingWords)) {
                        for (boolean isVertical : new boolean[] {true, false}) {
                            if (canPlaceWord(grid, word, row, col, isVertical)) {
                                placeWord(grid, word, row, col, isVertical);
                                remainingWords.remove(word);

                                if (solveCrossword(grid, remainingWords)) {
                                    return true;
                                }

                                remainingWords.add(word);
                                removeWord(grid, word, row, col, isVertical);
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