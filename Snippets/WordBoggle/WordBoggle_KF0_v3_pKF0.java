package com.thealgorithms.puzzlesandgames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class WordBoggle {

    private WordBoggle() {}

    /**
     * O(nm * 8^s + ws) time where:
     * n = width of boggle board,
     * m = height of boggle board,
     * s = length of longest word in string array,
     * w = length of string array,
     * 8 is due to 8 explorable neighbours.
     *
     * O(nm + ws) space.
     */
    public static List<String> boggleBoard(char[][] board, String[] words) {
        Trie trie = buildTrie(words);

        Set<String> foundWords = new HashSet<>();
        int rows = board.length;
        int cols = board[0].length;
        boolean[][] visited = new boolean[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                exploreBoard(row, col, board, trie.root, visited, foundWords);
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

    private static void exploreBoard(
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

        char letter = board[row][col];
        TrieNode nextNode = currentNode.children.get(letter);
        if (nextNode == null) {
            return;
        }

        visited[row][col] = true;

        if (nextNode.isEndOfWord()) {
            foundWords.add(nextNode.word);
        }

        for (int[] neighbor : getNeighbors(row, col, board)) {
            int neighborRow = neighbor[0];
            int neighborCol = neighbor[1];
            exploreBoard(neighborRow, neighborCol, board, nextNode, visited, foundWords);
        }

        visited[row][col] = false;
    }

    private static List<int[]> getNeighbors(int row, int col, char[][] board) {
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

// Trie used to optimize string search
class TrieNode {
    final Map<Character, TrieNode> children = new HashMap<>();
    String word = "";

    boolean isEndOfWord() {
        return children.containsKey(Trie.END_SYMBOL);
    }
}

class Trie {

    static final char END_SYMBOL = '*';

    final TrieNode root = new TrieNode();

    public void add(String str) {
        TrieNode node = root;
        for (int i = 0; i < str.length(); i++) {
            char letter = str.charAt(i);
            node = node.children.computeIfAbsent(letter, k -> new TrieNode());
        }
        node.children.put(END_SYMBOL, null);
        node.word = str;
    }
}