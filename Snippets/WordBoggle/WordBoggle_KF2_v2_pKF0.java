package com.thealgorithms.puzzlesandgames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class WordBoggle {

    private WordBoggle() {
        // Utility class
    }

    public static List<String> boggleBoard(char[][] board, String[] words) {
        Trie trie = buildTrie(words);
        Set<String> foundWords = new HashSet<>();

        int rows = board.length;
        int cols = board[0].length;
        boolean[][] visited = new boolean[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                dfs(board, row, col, trie.root, visited, foundWords);
            }
        }

        return new ArrayList<>(foundWords);
    }

    private static Trie buildTrie(String[] words) {
        Trie trie = new Trie();
        for (String word : words) {
            trie.add(word);
        }
        return trie;
    }

    private static void dfs(
            char[][] board,
            int row,
            int col,
            TrieNode currentNode,
            boolean[][] visited,
            Set<String> foundWords
    ) {
        if (visited[row][col]) {
            return;
        }

        char letter = board[row][col];
        TrieNode nextNode = currentNode.children.get(letter);
        if (nextNode == null) {
            return;
        }

        visited[row][col] = true;

        if (nextNode.children.containsKey(Trie.END_SYMBOL)) {
            foundWords.add(nextNode.word);
        }

        for (int[] neighbor : getNeighbors(board, row, col)) {
            dfs(board, neighbor[0], neighbor[1], nextNode, visited, foundWords);
        }

        visited[row][col] = false;
    }

    private static List<int[]> getNeighbors(char[][] board, int row, int col) {
        List<int[]> neighbors = new ArrayList<>();
        int rows = board.length;
        int cols = board[0].length;

        for (int dRow = -1; dRow <= 1; dRow++) {
            for (int dCol = -1; dCol <= 1; dCol++) {
                if (dRow == 0 && dCol == 0) {
                    continue;
                }

                int newRow = row + dRow;
                int newCol = col + dCol;

                if (isInBounds(newRow, newCol, rows, cols)) {
                    neighbors.add(new int[] {newRow, newCol});
                }
            }
        }

        return neighbors;
    }

    private static boolean isInBounds(int row, int col, int rows, int cols) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }
}

class TrieNode {
    final Map<Character, TrieNode> children = new HashMap<>();
    String word = "";
}

class Trie {

    static final char END_SYMBOL = '*';
    final TrieNode root = new TrieNode();

    public void add(String word) {
        TrieNode currentNode = root;

        for (int index = 0; index < word.length(); index++) {
            char letter = word.charAt(index);
            currentNode.children.putIfAbsent(letter, new TrieNode());
            currentNode = currentNode.children.get(letter);
        }

        currentNode.children.put(END_SYMBOL, null);
        currentNode.word = word;
    }
}