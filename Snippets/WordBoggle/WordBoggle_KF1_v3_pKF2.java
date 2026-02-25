package com.thealgorithms.puzzlesandgames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Class1 {

    private Class1() {}

    public static List<String> findWords(char[][] board, String[] words) {
        Trie trie = buildTrie(words);

        Set<String> foundWords = new HashSet<>();
        boolean[][] visited = new boolean[board.length][board[0].length];

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                depthFirstSearch(row, col, board, trie.root, visited, foundWords);
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

    private static void depthFirstSearch(
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
        TrieNode nextNode = node.children.get(currentChar);
        if (nextNode == null) {
            return;
        }

        visited[row][col] = true;

        if (nextNode.children.containsKey(Trie.END_SYMBOL)) {
            foundWords.add(nextNode.word);
        }

        for (int[] neighbor : getNeighbors(row, col, board)) {
            depthFirstSearch(neighbor[0], neighbor[1], board, nextNode, visited, foundWords);
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

    Trie() {
        this.root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode current = this.root;
        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            current.children.putIfAbsent(currentChar, new TrieNode());
            current = current.children.get(currentChar);
        }
        current.children.put(END_SYMBOL, null);
        current.word = word;
    }
}