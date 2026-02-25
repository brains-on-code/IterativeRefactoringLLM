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

        for (int rowIndex = 0; rowIndex < board.length; rowIndex++) {
            for (int colIndex = 0; colIndex < board[rowIndex].length; colIndex++) {
                exploreBoard(rowIndex, colIndex, board, trie.root, visited, foundWords);
            }
        }
        return new ArrayList<>(foundWords);
    }

    public static void exploreBoard(
        int rowIndex,
        int colIndex,
        char[][] board,
        TrieNode currentTrieNode,
        boolean[][] visited,
        Set<String> foundWords
    ) {
        if (visited[rowIndex][colIndex]) {
            return;
        }

        char currentChar = board[rowIndex][colIndex];
        if (!currentTrieNode.children.containsKey(currentChar)) {
            return;
        }

        visited[rowIndex][colIndex] = true;
        TrieNode nextTrieNode = currentTrieNode.children.get(currentChar);

        if (nextTrieNode.children.containsKey('*')) {
            foundWords.add(nextTrieNode.word);
        }

        List<int[]> neighborCoordinates = getNeighborCoordinates(rowIndex, colIndex, board);
        for (int[] neighbor : neighborCoordinates) {
            int neighborRowIndex = neighbor[0];
            int neighborColIndex = neighbor[1];
            exploreBoard(neighborRowIndex, neighborColIndex, board, nextTrieNode, visited, foundWords);
        }

        visited[rowIndex][colIndex] = false;
    }

    public static List<int[]> getNeighborCoordinates(int rowIndex, int colIndex, char[][] board) {
        List<int[]> neighborCoordinates = new ArrayList<>();
        int lastRowIndex = board.length - 1;
        int lastColIndex = board[0].length - 1;

        if (rowIndex > 0 && colIndex > 0) {
            neighborCoordinates.add(new int[] {rowIndex - 1, colIndex - 1});
        }

        if (rowIndex > 0 && colIndex < lastColIndex) {
            neighborCoordinates.add(new int[] {rowIndex - 1, colIndex + 1});
        }

        if (rowIndex < lastRowIndex && colIndex < lastColIndex) {
            neighborCoordinates.add(new int[] {rowIndex + 1, colIndex + 1});
        }

        if (rowIndex < lastRowIndex && colIndex > 0) {
            neighborCoordinates.add(new int[] {rowIndex + 1, colIndex - 1});
        }

        if (rowIndex > 0) {
            neighborCoordinates.add(new int[] {rowIndex - 1, colIndex});
        }

        if (rowIndex < lastRowIndex) {
            neighborCoordinates.add(new int[] {rowIndex + 1, colIndex});
        }

        if (colIndex > 0) {
            neighborCoordinates.add(new int[] {rowIndex, colIndex - 1});
        }

        if (colIndex < lastColIndex) {
            neighborCoordinates.add(new int[] {rowIndex, colIndex + 1});
        }

        return neighborCoordinates;
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

    public void add(String wordToAdd) {
        TrieNode currentNode = this.root;
        for (int charIndex = 0; charIndex < wordToAdd.length(); charIndex++) {
            char currentChar = wordToAdd.charAt(charIndex);
            if (!currentNode.children.containsKey(currentChar)) {
                TrieNode newNode = new TrieNode();
                currentNode.children.put(currentChar, newNode);
            }
            currentNode = currentNode.children.get(currentChar);
        }
        currentNode.children.put(this.endSymbol, null);
        currentNode.word = wordToAdd;
    }
}