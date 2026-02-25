package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CrosswordBacktracking {

    private CrosswordBacktracking() {
    }

    public static boolean canPlaceWord(
            char[][] grid,
            String word,
            int startRow,
            int startColumn,
            boolean vertical
    ) {
        int wordLength = word.length();
        int rowCount = grid.length;
        int columnCount = grid[0].length;

        for (int offset = 0; offset < wordLength; offset++) {
            if (vertical) {
                int currentRow = startRow + offset;
                if (currentRow >= rowCount || grid[currentRow][startColumn] != ' ') {
                    return false;
                }
            } else {
                int currentColumn = startColumn + offset;
                if (currentColumn >= columnCount || grid[startRow][currentColumn] != ' ') {
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
            int startColumn,
            boolean vertical
    ) {
        int wordLength = word.length();

        for (int offset = 0; offset < wordLength; offset++) {
            char letter = word.charAt(offset);
            if (vertical) {
                grid[startRow + offset][startColumn] = letter;
            } else {
                grid[startRow][startColumn + offset] = letter;
            }
        }
    }

    public static void removeWord(
            char[][] grid,
            String word,
            int startRow,
            int startColumn,
            boolean vertical
    ) {
        int wordLength = word.length();

        for (int offset = 0; offset < wordLength; offset++) {
            if (vertical) {
                grid[startRow + offset][startColumn] = ' ';
            } else {
                grid[startRow][startColumn + offset] = ' ';
            }
        }
    }

    public static boolean solveCrossword(char[][] grid, Collection<String> words) {
        List<String> remainingWords = new ArrayList<>(words);
        int rowCount = grid.length;
        int columnCount = grid[0].length;

        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                if (grid[row][column] == ' ') {
                    for (String word : new ArrayList<>(remainingWords)) {
                        for (boolean vertical : new boolean[] {true, false}) {
                            if (canPlaceWord(grid, word, row, column, vertical)) {
                                placeWord(grid, word, row, column, vertical);
                                remainingWords.remove(word);

                                if (solveCrossword(grid, remainingWords)) {
                                    return true;
                                }

                                remainingWords.add(word);
                                removeWord(grid, word, row, column, vertical);
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