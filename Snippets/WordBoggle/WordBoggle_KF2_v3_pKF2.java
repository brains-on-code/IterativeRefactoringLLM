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
        Trie trie = buildTrie(words);
        Set<String> foundWords = new HashSet<>();
        boolean[][] visited = new boolean[board.length][board[0].length];

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                dfs(row, col, board, trie.root, visited, foundWords);
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
            dfs(neighbor[0], neighbor[1], board, nextNode, visited, foundWords);
        }

        visited[row][col] = false;
    }

    private static List<int[]> getNeighbors(int row, int col, char[][] board) {
        List<int[]> neighbors = new ArrayList<>();
        int lastRow = board.length - 1;
        int lastCol = board[0].length - 1;

        boolean hasTop = row > 0;
        boolean hasBottom = row < lastRow;
        boolean hasLeft = col > 0;
        boolean hasRight = col < lastCol;

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