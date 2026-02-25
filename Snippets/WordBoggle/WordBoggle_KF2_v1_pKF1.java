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
        TrieNode currentNode,
        boolean[][] visited,
        Set<String> foundWords
    ) {
        if (visited[row][col]) {
            return;
        }

        char currentChar = board[row][col];
        if (!currentNode.children.containsKey(currentChar)) {
            return;
        }

        visited[row][col] = true;
        TrieNode nextNode = currentNode.children.get(currentChar);

        if (nextNode.children.containsKey('*')) {
            foundWords.add(nextNode.word);
        }

        List<int[]> neighbors = getNeighbors(row, col, board);
        for (int[] neighbor : neighbors) {
            int neighborRow = neighbor[0];
            int neighborCol = neighbor[1];
            exploreBoard(neighborRow, neighborCol, board, nextNode, visited, foundWords);
        }

        visited[row][col] = false;
    }

    public static List<int[]> getNeighbors(int row, int col, char[][] board) {
        List<int[]> neighbors = new ArrayList<>();
        int lastRowIndex = board.length - 1;
        int lastColIndex = board[0].length - 1;

        if (row > 0 && col > 0) {
            neighbors.add(new int[] {row - 1, col - 1});
        }

        if (row > 0 && col < lastColIndex) {
            neighbors.add(new int[] {row - 1, col + 1});
        }

        if (row < lastRowIndex && col < lastColIndex) {
            neighbors.add(new int[] {row + 1, col + 1});
        }

        if (row < lastRowIndex && col > 0) {
            neighbors.add(new int[] {row + 1, col - 1});
        }

        if (row > 0) {
            neighbors.add(new int[] {row - 1, col});
        }

        if (row < lastRowIndex) {
            neighbors.add(new int[] {row + 1, col});
        }

        if (col > 0) {
            neighbors.add(new int[] {row, col - 1});
        }

        if (col < lastColIndex) {
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

    TrieNode root;
    char endSymbol;

    Trie() {
        this.root = new TrieNode();
        this.endSymbol = '*';
    }

    public void add(String word) {
        TrieNode currentNode = this.root;
        for (int index = 0; index < word.length(); index++) {
            char currentChar = word.charAt(index);
            if (!currentNode.children.containsKey(currentChar)) {
                TrieNode newNode = new TrieNode();
                currentNode.children.put(currentChar, newNode);
            }
            currentNode = currentNode.children.get(currentChar);
        }
        currentNode.children.put(this.endSymbol, null);
        currentNode.word = word;
    }
}