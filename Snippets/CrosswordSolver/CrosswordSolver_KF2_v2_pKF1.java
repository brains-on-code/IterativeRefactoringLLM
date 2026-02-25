package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CrosswordSolver {

    private CrosswordSolver() {
    }

    public static boolean canPlaceWord(char[][] grid, String word, int startRow, int startCol, boolean vertical) {
        int wordLength = word.length();

        for (int charIndex = 0; charIndex < wordLength; charIndex++) {
            if (vertical) {
                int currentRow = startRow + charIndex;
                if (currentRow >= grid.length || grid[currentRow][startCol] != ' ') {
                    return false;
                }
            } else {
                int currentCol = startCol + charIndex;
                if (currentCol >= grid[0].length || grid[startRow][currentCol] != ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public static void placeWord(char[][] grid, String word, int startRow, int startCol, boolean vertical) {
        int wordLength = word.length();

        for (int charIndex = 0; charIndex < wordLength; charIndex++) {
            char letter = word.charAt(charIndex);
            if (vertical) {
                grid[startRow + charIndex][startCol] = letter;
            } else {
                grid[startRow][startCol + charIndex] = letter;
            }
        }
    }

    public static void removeWord(char[][] grid, String word, int startRow, int startCol, boolean vertical) {
        int wordLength = word.length();

        for (int charIndex = 0; charIndex < wordLength; charIndex++) {
            if (vertical) {
                grid[startRow + charIndex][startCol] = ' ';
            } else {
                grid[startRow][startCol + charIndex] = ' ';
            }
        }
    }

    public static boolean solveCrossword(char[][] grid, Collection<String> words) {
        List<String> remainingWords = new ArrayList<>(words);

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == ' ') {
                    for (String word : new ArrayList<>(remainingWords)) {
                        for (boolean vertical : new boolean[] {true, false}) {
                            if (canPlaceWord(grid, word, row, col, vertical)) {
                                placeWord(grid, word, row, col, vertical);
                                remainingWords.remove(word);

                                if (solveCrossword(grid, remainingWords)) {
                                    return true;
                                }

                                remainingWords.add(word);
                                removeWord(grid, word, row, col, vertical);
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