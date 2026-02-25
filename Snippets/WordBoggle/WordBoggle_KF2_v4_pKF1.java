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
        Trie trie = new Trie();
        for (String word : words) {
            trie.add(word);
        }

        Set<String> foundWords = new HashSet<>();
        boolean[][] visitedCells = new boolean[board.length][board[0].length];

        for (int rowIndex = 0; rowIndex < board.length; rowIndex++) {
            for (int colIndex = 0; colIndex < board[rowIndex].length; colIndex++) {
                exploreBoard(
                    rowIndex,
                    colIndex,
                    board,
                    trie.root,
                    visitedCells,
                    foundWords
                );
            }
        }
        return new ArrayList<>(foundWords);
    }

    public static void exploreBoard(
        int rowIndex,
        int colIndex,
        char[][] board,
        TrieNode currentTrieNode,
        boolean[][] visitedCells,
        Set<String> foundWords
    ) {
        if (visitedCells[rowIndex][colIndex]) {
            return;
        }

        char currentChar = board[rowIndex][colIndex];
        if (!currentTrieNode.children.containsKey(currentChar)) {
            return;
        }

        visitedCells[rowIndex][colIndex] = true;
        TrieNode nextTrieNode = currentTrieNode.children.get(currentChar);

        if (nextTrieNode.children.containsKey('*')) {
            foundWords.add(nextTrieNode.word);
        }

        List<int[]> neighborCoordinates = getNeighbors(rowIndex, colIndex, board);
        for (int[] neighborCoordinate : neighborCoordinates) {
            int neighborRowIndex = neighborCoordinate[0];
            int neighborColIndex = neighborCoordinate[1];
            exploreBoard(
                neighborRowIndex,
                neighborColIndex,
                board,
                nextTrieNode,
                visitedCells,
                foundWords
            );
        }

        visitedCells[rowIndex][colIndex] = false;
    }

    public static List<int[]> getNeighbors(int rowIndex, int colIndex, char[][] board) {
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