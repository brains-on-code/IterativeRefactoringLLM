package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class CrosswordSolver {

    private static final char EMPTY_CELL = ' ';

    private CrosswordSolver() {
        // Utility class; prevent instantiation
    }

    public static boolean isValid(char[][] puzzle, String word, int row, int col, boolean vertical) {
        int wordLength = word.length();
        int maxRows = puzzle.length;
        int maxCols = puzzle[0].length;

        for (int i = 0; i < wordLength; i++) {
            int currentRow = getRow(row, i, vertical);
            int currentCol = getCol(col, i, vertical);

            if (currentRow >= maxRows || currentCol >= maxCols) {
                return false;
            }

            if (puzzle[currentRow][currentCol] != EMPTY_CELL) {
                return false;
            }
        }

        return true;
    }

    public static void placeWord(char[][] puzzle, String word, int row, int col, boolean vertical) {
        int wordLength = word.length();

        for (int i = 0; i < wordLength; i++) {
            int currentRow = getRow(row, i, vertical);
            int currentCol = getCol(col, i, vertical);
            puzzle[currentRow][currentCol] = word.charAt(i);
        }
    }

    public static void removeWord(char[][] puzzle, String word, int row, int col, boolean vertical) {
        int wordLength = word.length();

        for (int i = 0; i < wordLength; i++) {
            int currentRow = getRow(row, i, vertical);
            int currentCol = getCol(col, i, vertical);
            puzzle[currentRow][currentCol] = EMPTY_CELL;
        }
    }

    public static boolean solveCrossword(char[][] puzzle, Collection<String> words) {
        List<String> remainingWords = new ArrayList<>(words);

        for (int row = 0; row < puzzle.length; row++) {
            for (int col = 0; col < puzzle[0].length; col++) {
                if (puzzle[row][col] != EMPTY_CELL) {
                    continue;
                }

                List<String> snapshot = new ArrayList<>(remainingWords);
                for (String word : snapshot) {
                    if (tryPlaceWord(puzzle, remainingWords, word, row, col, true)) {
                        return true;
                    }
                    if (tryPlaceWord(puzzle, remainingWords, word, row, col, false)) {
                        return true;
                    }
                }
                return false;
            }
        }

        return true;
    }

    private static boolean tryPlaceWord(
        char[][] puzzle,
        List<String> remainingWords,
        String word,
        int row,
        int col,
        boolean vertical
    ) {
        if (!isValid(puzzle, word, row, col, vertical)) {
            return false;
        }

        placeWord(puzzle, word, row, col, vertical);
        remainingWords.remove(word);

        boolean solved = solveCrossword(puzzle, remainingWords);
        if (solved) {
            return true;
        }

        remainingWords.add(word);
        removeWord(puzzle, word, row, col, vertical);
        return false;
    }

    private static int getRow(int baseRow, int offset, boolean vertical) {
        return vertical ? baseRow + offset : baseRow;
    }

    private static int getCol(int baseCol, int offset, boolean vertical) {
        return vertical ? baseCol : baseCol + offset;
    }
}