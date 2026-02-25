package com.thealgorithms.puzzlesandgames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class WordSearch {

    private WordSearch() {}

    /**
     * Finds all words from the given list that can be formed in the board.
     */
    public static List<String> findWords(char[][] board, String[] words) {
        Trie trie = new Trie();
        for (String word : words) {
            trie.insert(word);
        }

        Set<String> foundWords = new HashSet<>();
        boolean[][] visitedCells = new boolean[board.length][board[0].length];

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[row].length; column++) {
                depthFirstSearch(row, column, board, trie.root, visitedCells, foundWords);
            }
        }
        return new ArrayList<>(foundWords);
    }

    private static void depthFirstSearch(
        int row,
        int column,
        char[][] board,
        TrieNode currentNode,
        boolean[][] visitedCells,
        Set<String> foundWords
    ) {
        if (visitedCells[row][column]) {
            return;
        }

        char currentCharacter = board[row][column];
        if (!currentNode.children.containsKey(currentCharacter)) {
            return;
        }

        visitedCells[row][column] = true;
        currentNode = currentNode.children.get(currentCharacter);

        if (currentNode.children.containsKey(Trie.END_SYMBOL)) {
            foundWords.add(currentNode.word);
        }

        List<int[]> neighborPositions = getNeighborPositions(row, column, board);
        for (int[] neighbor : neighborPositions) {
            depthFirstSearch(neighbor[0], neighbor[1], board, currentNode, visitedCells, foundWords);
        }

        visitedCells[row][column] = false;
    }

    private static List<int[]> getNeighborPositions(int row, int column, char[][] board) {
        List<int[]> neighbors = new ArrayList<>();
        int lastRowIndex = board.length - 1;
        int lastColumnIndex = board[0].length - 1;

        if (row > 0 && column > 0) {
            neighbors.add(new int[] {row - 1, column - 1});
        }

        if (row > 0 && column < lastColumnIndex) {
            neighbors.add(new int[] {row - 1, column + 1});
        }

        if (row < lastRowIndex && column < lastColumnIndex) {
            neighbors.add(new int[] {row + 1, column + 1});
        }

        if (row < lastRowIndex && column > 0) {
            neighbors.add(new int[] {row + 1, column - 1});
        }

        if (row > 0) {
            neighbors.add(new int[] {row - 1, column});
        }

        if (row < lastRowIndex) {
            neighbors.add(new int[] {row + 1, column});
        }

        if (column > 0) {
            neighbors.add(new int[] {row, column - 1});
        }

        if (column < lastColumnIndex) {
            neighbors.add(new int[] {row, column + 1});
        }

        return neighbors;
    }
}

class TrieNode {

    Map<Character, TrieNode> children = new HashMap<>();
    String word = "";
}

class Trie {

    static final char END_SYMBOL = '*';

    TrieNode root;
    char endSymbol;

    Trie() {
        this.root = new TrieNode();
        this.endSymbol = END_SYMBOL;
    }

    public void insert(String word) {
        TrieNode currentNode = this.root;
        for (int index = 0; index < word.length(); index++) {
            char currentCharacter = word.charAt(index);
            if (!currentNode.children.containsKey(currentCharacter)) {
                TrieNode newNode = new TrieNode();
                currentNode.children.put(currentCharacter, newNode);
            }
            currentNode = currentNode.children.get(currentCharacter);
        }
        currentNode.children.put(this.endSymbol, null);
        currentNode.word = word;
    }
}