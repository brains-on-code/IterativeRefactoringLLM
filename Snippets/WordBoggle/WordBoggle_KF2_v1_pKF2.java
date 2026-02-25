package com.thealgorithms.puzzlesandgames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class WordBoggle {

    private WordBoggle() {}

    public static List<String> boggleBoard(char[][] board, String[] words) {
        Trie trie = new Trie();
        for (String word : words) {
            trie.add(word);
        }

        Set<String> foundWords = new HashSet<>();
        boolean[][] visited = new boolean[board.length][board[0].length];

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                explore(row, col, board, trie.root, visited, foundWords);
            }
        }

        return new ArrayList<>(foundWords);
    }

    private static void explore(
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

        char letter = board[row][col];
        TrieNode nextNode = node.children.get(letter);
        if (nextNode == null) {
            return;
        }

        visited[row][col] = true;

        if (nextNode.children.containsKey(Trie.END_SYMBOL)) {
            foundWords.add(nextNode.word);
        }

        for (int[] neighbor : getNeighbors(row, col, board)) {
            explore(neighbor[0], neighbor[1], board, nextNode, visited, foundWords);
        }

        visited[row][col] = false;
    }

    private static List<int[]> getNeighbors(int row, int col, char[][] board) {
        List<int[]> neighbors = new ArrayList<>();
        int lastRow = board.length - 1;
        int lastCol = board[0].length - 1;

        // Diagonals
        if (row > 0 && col > 0) {
            neighbors.add(new int[] {row - 1, col - 1});
        }
        if (row > 0 && col < lastCol) {
            neighbors.add(new int[] {row - 1, col + 1});
        }
        if (row < lastRow && col < lastCol) {
            neighbors.add(new int[] {row + 1, col + 1});
        }
        if (row < lastRow && col > 0) {
            neighbors.add(new int[] {row + 1, col - 1});
        }

        // Vertical and horizontal
        if (row > 0) {
            neighbors.add(new int[] {row - 1, col});
        }
        if (row < lastRow) {
            neighbors.add(new int[] {row + 1, col});
        }
        if (col > 0) {
            neighbors.add(new int[] {row, col - 1});
        }
        if (col < lastCol) {
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

    TrieNode root = new TrieNode();

    public void add(String word) {
        TrieNode current = root;

        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            current.children.putIfAbsent(letter, new TrieNode());
            current = current.children.get(letter);
        }

        current.children.put(END_SYMBOL, null);
        current.word = word;
    }
}