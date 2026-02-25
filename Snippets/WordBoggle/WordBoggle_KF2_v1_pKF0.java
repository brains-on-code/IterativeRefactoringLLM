package com.thealgorithms.puzzlesandgames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class WordBoggle {

    private WordBoggle() {
    }

    public static List<String> boggleBoard(char[][] board, String[] words) {
        Trie trie = new Trie();
        for (String word : words) {
            trie.add(word);
        }

        Set<String> foundWords = new HashSet<>();
        int rows = board.length;
        int cols = board[0].length;
        boolean[][] visited = new boolean[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                explore(row, col, board, trie.root, visited, foundWords);
            }
        }

        return new ArrayList<>(foundWords);
    }

    private static void explore(
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
        int rows = board.length;
        int cols = board[0].length;

        for (int dRow = -1; dRow <= 1; dRow++) {
            for (int dCol = -1; dCol <= 1; dCol++) {
                if (dRow == 0 && dCol == 0) {
                    continue;
                }
                int newRow = row + dRow;
                int newCol = col + dCol;

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                    neighbors.add(new int[] {newRow, newCol});
                }
            }
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