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

            if (isOutOfBounds(currentRow, currentCol, maxRows, maxCols)) {
                return false;
            }

            if (!isCellEmpty(puzzle, currentRow, currentCol)) {
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
                if (!isCellEmpty(puzzle, row, col)) {
                    continue;
                }

                if (!tryAllWordsAtCell(puzzle, remainingWords, row, col)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean tryAllWordsAtCell(char[][] puzzle, List<String> remainingWords, int row, int col) {
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

    private static boolean isOutOfBounds(int row, int col, int maxRows, int maxCols) {
        return row < 0 || col < 0 || row >= maxRows || col >= maxCols;
    }

    private static boolean isCellEmpty(char[][] puzzle, int row, int col) {
        return puzzle[row][col] == EMPTY_CELL;
    }
}