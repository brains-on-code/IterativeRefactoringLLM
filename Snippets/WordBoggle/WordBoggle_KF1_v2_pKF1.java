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
        boolean[][] visited = new boolean[board.length][board[0].length];

        for (int rowIndex = 0; rowIndex < board.length; rowIndex++) {
            for (int colIndex = 0; colIndex < board[rowIndex].length; colIndex++) {
                depthFirstSearch(rowIndex, colIndex, board, trie.root, visited, foundWords);
            }
        }
        return new ArrayList<>(foundWords);
    }

    private static void depthFirstSearch(
        int rowIndex,
        int colIndex,
        char[][] board,
        TrieNode currentNode,
        boolean[][] visited,
        Set<String> foundWords
    ) {
        if (visited[rowIndex][colIndex]) {
            return;
        }

        char currentChar = board[rowIndex][colIndex];
        if (!currentNode.children.containsKey(currentChar)) {
            return;
        }

        visited[rowIndex][colIndex] = true;
        currentNode = currentNode.children.get(currentChar);

        if (currentNode.children.containsKey(Trie.END_SYMBOL)) {
            foundWords.add(currentNode.word);
        }

        List<int[]> neighborCoordinates = getNeighborCoordinates(rowIndex, colIndex, board);
        for (int[] neighbor : neighborCoordinates) {
            depthFirstSearch(neighbor[0], neighbor[1], board, currentNode, visited, foundWords);
        }

        visited[rowIndex][colIndex] = false;
    }

    private static List<int[]> getNeighborCoordinates(int rowIndex, int colIndex, char[][] board) {
        List<int[]> neighborCoordinates = new ArrayList<>();
        int lastRowIndex = board.length - 1;
        int lastColIndex = board[0].length - 1;

        if (rowIndex > 0 && colIndex > 0) {
            neighborCoordinates.add(new int[] {rowIndex - 1, colIndex - 1});
        }

        if (rowIndex > 0 && colIndex < lastColIndex) {
            neighborCoordinates.add(new int[] {rowIndex - 1, colIndex + 1});
        }

        if (rowIndex < lastRowIndex && colIndex < lastColIndex) {
            neighborCoordinates.add(new int[] {rowIndex + 1, colIndex + 1});
        }

        if (rowIndex < lastRowIndex && colIndex > 0) {
            neighborCoordinates.add(new int[] {rowIndex + 1, colIndex - 1});
        }

        if (rowIndex > 0) {
            neighborCoordinates.add(new int[] {rowIndex - 1, colIndex});
        }

        if (rowIndex < lastRowIndex) {
            neighborCoordinates.add(new int[] {rowIndex + 1, colIndex});
        }

        if (colIndex > 0) {
            neighborCoordinates.add(new int[] {rowIndex, colIndex - 1});
        }

        if (colIndex < lastColIndex) {
            neighborCoordinates.add(new int[] {rowIndex, colIndex + 1});
        }

        return neighborCoordinates;
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

    public void insert(String wordToInsert) {
        TrieNode currentNode = this.root;
        for (int charIndex = 0; charIndex < wordToInsert.length(); charIndex++) {
            char currentChar = wordToInsert.charAt(charIndex);
            if (!currentNode.children.containsKey(currentChar)) {
                TrieNode newNode = new TrieNode();
                currentNode.children.put(currentChar, newNode);
            }
            currentNode = currentNode.children.get(currentChar);
        }
        currentNode.children.put(this.endSymbol, null);
        currentNode.word = wordToInsert;
    }
}