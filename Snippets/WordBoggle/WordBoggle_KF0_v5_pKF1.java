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
     * O(rows * cols * 8^s + w * s) time where:
     * rows = number of rows in the board,
     * cols = number of columns in the board,
     * s = length of the longest word,
     * w = number of words,
     * 8 is due to 8 explorable neighbours.
     * O(rows * cols + w * s) space.
     */
    public static List<String> boggleBoard(char[][] board, String[] words) {
        Trie trie = new Trie();
        for (String word : words) {
            trie.add(word);
        }

        Set<String> foundWords = new HashSet<>();
        boolean[][] visited = new boolean[board.length][board[0].length];

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                exploreBoard(row, col, board, trie.root, visited, foundWords);
            }
        }
        return new ArrayList<>(foundWords);
    }

    public static void exploreBoard(
        int row,
        int col,
        char[][] board,
        TrieNode trieNode,
        boolean[][] visited,
        Set<String> foundWords
    ) {
        if (visited[row][col]) {
            return;
        }

        char letter = board[row][col];
        if (!trieNode.children.containsKey(letter)) {
            return;
        }

        visited[row][col] = true;
        TrieNode nextNode = trieNode.children.get(letter);

        if (nextNode.children.containsKey('*')) {
            foundWords.add(nextNode.word);
        }

        List<int[]> neighbors = getNeighborCoordinates(row, col, board);
        for (int[] neighbor : neighbors) {
            int neighborRow = neighbor[0];
            int neighborCol = neighbor[1];
            exploreBoard(neighborRow, neighborCol, board, nextNode, visited, foundWords);
        }

        visited[row][col] = false;
    }

    public static List<int[]> getNeighborCoordinates(int row, int col, char[][] board) {
        List<int[]> neighbors = new ArrayList<>();
        int lastRow = board.length - 1;
        int lastCol = board[0].length - 1;

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

// Trie used to optimize string search
class TrieNode {

    Map<Character, TrieNode> children = new HashMap<>();
    String word = "";
}

class Trie {

    TrieNode root;
    char endSymbol;

    Trie() {
        this.root = new TrieNode();
        this.endSymbol = '*';
    }

    public void add(String word) {
        TrieNode currentNode = this.root;
        for (int index = 0; index < word.length(); index++) {
            char letter = word.charAt(index);
            if (!currentNode.children.containsKey(letter)) {
                TrieNode newNode = new TrieNode();
                currentNode.children.put(letter, newNode);
            }
            currentNode = currentNode.children.get(letter);
        }
        currentNode.children.put(this.endSymbol, null);
        currentNode.word = word;
    }
}