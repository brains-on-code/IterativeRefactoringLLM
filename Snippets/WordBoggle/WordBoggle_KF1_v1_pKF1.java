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
        boolean[][] visited = new boolean[board.length][board.length];

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                dfs(row, col, board, trie.root, visited, foundWords);
            }
        }
        return new ArrayList<>(foundWords);
    }

    private static void dfs(
        int row,
        int col,
        char[][] board,
        TrieNode node,
        boolean[][] visited,
        Set<String> foundWords
    ) {
        if (visited[row][col]) {
            return;
        }

        char currentChar = board[row][col];
        if (!node.children.containsKey(currentChar)) {
            return;
        }

        visited[row][col] = true;
        node = node.children.get(currentChar);

        if (node.children.containsKey(Trie.END_SYMBOL)) {
            foundWords.add(node.word);
        }

        List<int[]> neighbors = getNeighbors(row, col, board);
        for (int[] neighbor : neighbors) {
            dfs(neighbor[0], neighbor[1], board, node, visited, foundWords);
        }

        visited[row][col] = false;
    }

    private static List<int[]> getNeighbors(int row, int col, char[][] board) {
        List<int[]> neighbors = new ArrayList<>();
        int maxRow = board.length - 1;
        int maxCol = board[0].length - 1;

        if (row > 0 && col > 0) {
            neighbors.add(new int[] {row - 1, col - 1});
        }

        if (row > 0 && col < maxCol) {
            neighbors.add(new int[] {row - 1, col + 1});
        }

        if (row < maxRow && col < maxCol) {
            neighbors.add(new int[] {row + 1, col + 1});
        }

        if (row < maxRow && col > 0) {
            neighbors.add(new int[] {row + 1, col - 1});
        }

        if (row > 0) {
            neighbors.add(new int[] {row - 1, col});
        }

        if (row < maxRow) {
            neighbors.add(new int[] {row + 1, col});
        }

        if (col > 0) {
            neighbors.add(new int[] {row, col - 1});
        }

        if (col < maxCol) {
            neighbors.add(new int[] {row, col + 1});
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
        TrieNode current = this.root;
        for (int index = 0; index < word.length(); index++) {
            char currentChar = word.charAt(index);
            if (!current.children.containsKey(currentChar)) {
                TrieNode newNode = new TrieNode();
                current.children.put(currentChar, newNode);
            }
            current = current.children.get(currentChar);
        }
        current.children.put(this.endSymbol, null);
        current.word = word;
    }
}