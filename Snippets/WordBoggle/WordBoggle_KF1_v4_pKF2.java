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
            int neighborRow = neighbor[0];
            int neighborCol = neighbor[1];
            depthFirstSearch(neighborRow, neighborCol, board, nextNode, visited, foundWords);
        }

        visited[row][col] = false;
    }

    private static List<int[]> getNeighbors(int row, int col, char[][] board) {
        List<int[]> neighbors = new ArrayList<>();
        int maxRowIndex = board.length - 1;
        int maxColIndex = board[0].length - 1;

        boolean hasTop = row > 0;
        boolean hasBottom = row < maxRowIndex;
        boolean hasLeft = col > 0;
        boolean hasRight = col < maxColIndex;

        if (hasTop && hasLeft) {
            neighbors.add(new int[] {row - 1, col - 1});
        }
        if (hasTop && hasRight) {
            neighbors.add(new int[] {row - 1, col + 1});
        }
        if (hasBottom && hasRight) {
            neighbors.add(new int[] {row + 1, col + 1});
        }
        if (hasBottom && hasLeft) {
            neighbors.add(new int[] {row + 1, col - 1});
        }
        if (hasTop) {
            neighbors.add(new int[] {row - 1, col});
        }
        if (hasBottom) {
            neighbors.add(new int[] {row + 1, col});
        }
        if (hasLeft) {
            neighbors.add(new int[] {row, col - 1});
        }
        if (hasRight) {
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
        for (int index = 0; index < word.length(); index++) {
            char currentChar = word.charAt(index);
            current.children.putIfAbsent(currentChar, new TrieNode());
            current = current.children.get(currentChar);
        }
        current.children.put(END_SYMBOL, null);
        current.word = word;
    }
}