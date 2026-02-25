package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public final class CrosswordSolver {
    private CrosswordSolver() {
    }


    public static boolean isValid(char[][] puzzle, String word, int row, int col, boolean vertical) {
        for (int i = 0; i < word.length(); i++) {
            if (vertical) {
                if (row + i >= puzzle.length || puzzle[row + i][col] != ' ') {
                    return false;
                }
            } else {
                if (col + i >= puzzle[0].length || puzzle[row][col + i] != ' ') {
                    return false;
                }
            }
        }
        return true;
    }


    public static void placeWord(char[][] puzzle, String word, int row, int col, boolean vertical) {
        for (int i = 0; i < word.length(); i++) {
            if (vertical) {
                puzzle[row + i][col] = word.charAt(i);
            } else {
                puzzle[row][col + i] = word.charAt(i);
            }
        }
    }


    public static void removeWord(char[][] puzzle, String word, int row, int col, boolean vertical) {
        for (int i = 0; i < word.length(); i++) {
            if (vertical) {
                puzzle[row + i][col] = ' ';
            } else {
                puzzle[row][col + i] = ' ';
            }
        }
    }


    public static boolean solveCrossword(char[][] puzzle, Collection<String> words) {
        List<String> remainingWords = new ArrayList<>(words);

        for (int row = 0; row < puzzle.length; row++) {
            for (int col = 0; col < puzzle[0].length; col++) {
                if (puzzle[row][col] == ' ') {
                    for (String word : new ArrayList<>(remainingWords)) {
                        for (boolean vertical : new boolean[] {true, false}) {
                            if (isValid(puzzle, word, row, col, vertical)) {
                                placeWord(puzzle, word, row, col, vertical);
                                remainingWords.remove(word);
                                if (solveCrossword(puzzle, remainingWords)) {
                                    return true;
                                }
                                remainingWords.add(word);
                                removeWord(puzzle, word, row, col, vertical);
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
