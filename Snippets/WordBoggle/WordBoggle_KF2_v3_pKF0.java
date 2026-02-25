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
                depthFirstSearch(board, row, col, trie.getRoot(), visited, foundWords);
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

    private static void depthFirstSearch(
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
        TrieNode nextNode = currentNode.getChild(letter);
        if (nextNode == null) {
            return;
        }

        visited[row][col] = true;

        if (nextNode.isEndOfWord()) {
            foundWords.add(nextNode.getWord());
        }

        for (int[] neighbor : getNeighbors(board, row, col)) {
            depthFirstSearch(board, neighbor[0], neighbor[1], nextNode, visited, foundWords);
        }

        visited[row][col] = false;
    }

    private static List<int[]> getNeighbors(char[][] board, int row, int col) {
        List<int[]> neighbors = new ArrayList<>();
        int rows = board.length;
        int cols = board[0].length;

        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int colOffset = -1; colOffset <= 1; colOffset++) {
                if (rowOffset == 0 && colOffset == 0) {
                    continue;
                }

                int newRow = row + rowOffset;
                int newCol = col + colOffset;

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
    private final Map<Character, TrieNode> children = new HashMap<>();
    private String word = "";

    public TrieNode getChild(char letter) {
        return children.get(letter);
    }

    public void putChildIfAbsent(char letter) {
        children.putIfAbsent(letter, new TrieNode());
    }

    public TrieNode getOrCreateChild(char letter) {
        putChildIfAbsent(letter);
        return children.get(letter);
    }

    public void markAsEndOfWord(String word) {
        children.put(Trie.END_SYMBOL, null);
        this.word = word;
    }

    public boolean isEndOfWord() {
        return children.containsKey(Trie.END_SYMBOL);
    }

    public String getWord() {
        return word;
    }
}

class Trie {

    static final char END_SYMBOL = '*';
    private final TrieNode root = new TrieNode();

    public TrieNode getRoot() {
        return root;
    }

    public void add(String word) {
        TrieNode currentNode = root;

        for (int index = 0; index < word.length(); index++) {
            char letter = word.charAt(index);
            currentNode = currentNode.getOrCreateChild(letter);
        }

        currentNode.markAsEndOfWord(word);
    }
}