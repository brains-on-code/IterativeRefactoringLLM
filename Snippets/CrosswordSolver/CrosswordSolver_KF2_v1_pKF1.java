package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CrosswordSolver {

    private CrosswordSolver() {
    }

    public static boolean canPlaceWord(char[][] grid, String word, int startRow, int startCol, boolean isVertical) {
        int wordLength = word.length();

        for (int index = 0; index < wordLength; index++) {
            if (isVertical) {
                int currentRow = startRow + index;
                if (currentRow >= grid.length || grid[currentRow][startCol] != ' ') {
                    return false;
                }
            } else {
                int currentCol = startCol + index;
                if (currentCol >= grid[0].length || grid[startRow][currentCol] != ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public static void placeWord(char[][] grid, String word, int startRow, int startCol, boolean isVertical) {
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

    public static void removeWord(char[][] grid, String word, int startRow, int startCol, boolean isVertical) {
        int wordLength = word.length();

        for (int index = 0; index < wordLength; index++) {
            if (isVertical) {
                grid[startRow + index][startCol] = ' ';
            } else {
                grid[startRow][startCol + index] = ' ';
            }
        }
    }

    public static boolean solveCrossword(char[][] grid, Collection<String> words) {
        List<String> remainingWords = new ArrayList<>(words);

        for (int rowIndex = 0; rowIndex < grid.length; rowIndex++) {
            for (int colIndex = 0; colIndex < grid[0].length; colIndex++) {
                if (grid[rowIndex][colIndex] == ' ') {
                    for (String word : new ArrayList<>(remainingWords)) {
                        for (boolean isVertical : new boolean[] {true, false}) {
                            if (canPlaceWord(grid, word, rowIndex, colIndex, isVertical)) {
                                placeWord(grid, word, rowIndex, colIndex, isVertical);
                                remainingWords.remove(word);

                                if (solveCrossword(grid, remainingWords)) {
                                    return true;
                                }

                                remainingWords.add(word);
                                removeWord(grid, word, rowIndex, colIndex, isVertical);
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