package com.thealgorithms.puzzlesandgames;

import java.util.*;

public final class Class1 {

    private Class1() {
        // Utility class
    }

    /**
     * Finds all words from the given list that can be formed in the board.
     */
    public static List<String> method1(char[][] board, String[] words) {
        Trie trie = buildTrie(words);
        Set<String> foundWords = new HashSet<>();
        boolean[][] visited = new boolean[board.length][board[0].length];

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                searchFromCell(row, col, board, trie.root, visited, foundWords);
            }
        }

        return new ArrayList<>(foundWords);
    }

    private static Trie buildTrie(String[] words) {
        Trie trie = new Trie();
        for (String word : words) {
            trie.insert(word);
        }
        return trie;
    }

    private static void searchFromCell(
        int row,
        int col,
        char[][] board,
        TrieNode currentNode,
        boolean[][] visited,
        Set<String> foundWords
    ) {
        if (visited[row][col]) {
            return;
        }

        char currentChar = board[row][col];
        TrieNode nextNode = currentNode.children.get(currentChar);
        if (nextNode == null) {
            return;
        }

        visited[row][col] = true;

        if (nextNode.isEndOfWord) {
            foundWords.add(nextNode.word);
        }

        for (int[] neighbor : getNeighbors(row, col, board)) {
            int neighborRow = neighbor[0];
            int neighborCol = neighbor[1];
            searchFromCell(neighborRow, neighborCol, board, nextNode, visited, foundWords);
        }

        visited[row][col] = false;
    }

    private static List<int[]> getNeighbors(int row, int col, char[][] board) {
        List<int[]> neighbors = new ArrayList<>();
        int maxRow = board.length - 1;
        int maxCol = board[0].length - 1;

        // Diagonals
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

        // Straight lines
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
    final Map<Character, TrieNode> children = new HashMap<>();
    String word = "";
    boolean isEndOfWord = false;
}

class Trie {

    final TrieNode root;

    Trie() {
        this.root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode current = this.root;
        for (int index = 0; index < word.length(); index++) {
            char currentChar = word.charAt(index);
            current.children.putIfAbsent(currentChar, new TrieNode());
            current = current.children.get(currentChar);
        }
        current.isEndOfWord = true;
        current.word = word;
    }
}