package com.thealgorithms.puzzlesandgames;

import java.util.*;

public final class WordBoggle {

    private WordBoggle() {
        // Utility class
    }

    public static List<String> boggleBoard(char[][] board, String[] words) {
        Trie trie = buildTrie(words);
        Set<String> foundWords = new HashSet<>();

        int rowCount = board.length;
        int colCount = board[0].length;
        boolean[][] visited = new boolean[rowCount][colCount];

        TrieNode root = trie.getRoot();
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                depthFirstSearch(board, row, col, root, visited, foundWords);
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
            int neighborRow = neighbor[0];
            int neighborCol = neighbor[1];
            depthFirstSearch(board, neighborRow, neighborCol, nextNode, visited, foundWords);
        }

        visited[row][col] = false;
    }

    private static List<int[]> getNeighbors(char[][] board, int row, int col) {
        List<int[]> neighbors = new ArrayList<>();
        int rowCount = board.length;
        int colCount = board[0].length;

        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int colOffset = -1; colOffset <= 1; colOffset++) {
                if (rowOffset == 0 && colOffset == 0) {
                    continue;
                }

                int newRow = row + rowOffset;
                int newCol = col + colOffset;

                if (isInBounds(newRow, newCol, rowCount, colCount)) {
                    neighbors.add(new int[] {newRow, newCol});
                }
            }
        }

        return neighbors;
    }

    private static boolean isInBounds(int row, int col, int rowCount, int colCount) {
        return row >= 0 && row < rowCount && col >= 0 && col < colCount;
    }
}

class TrieNode {
    private final Map<Character, TrieNode> children = new HashMap<>();
    private String word = "";

    public TrieNode getChild(char letter) {
        return children.get(letter);
    }

    public TrieNode getOrCreateChild(char letter) {
        return children.computeIfAbsent(letter, c -> new TrieNode());
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