package com.thealgorithms.puzzlesandgames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Class1 {

    private Class1() {}

    public static List<String> method1(char[][] board, String[] words) {
        Class3 trie = new Class3();
        for (String word : words) {
            trie.method4(word);
        }

        Set<String> foundWords = new HashSet<>();
        boolean[][] visited = new boolean[board.length][board[0].length];

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                method2(row, col, board, trie.root, visited, foundWords);
            }
        }
        return new ArrayList<>(foundWords);
    }

    public static void method2(
            int row,
            int col,
            char[][] board,
            Class2 node,
            boolean[][] visited,
            Set<String> foundWords
    ) {
        if (visited[row][col]) {
            return;
        }

        char currentChar = board[row][col];
        if (!node.children.containsKey(currentChar)) {
            return;
        }

        visited[row][col] = true;
        node = node.children.get(currentChar);

        if (node.children.containsKey('*')) {
            foundWords.add(node.word);
        }

        List<int[]> neighbors = method3(row, col, board);
        for (int[] neighbor : neighbors) {
            method2(neighbor[0], neighbor[1], board, node, visited, foundWords);
        }

        visited[row][col] = false;
    }

    public static List<int[]> method3(int row, int col, char[][] board) {
        List<int[]> neighbors = new ArrayList<>();
        int maxRow = board.length - 1;
        int maxCol = board[0].length - 1;

        if (row > 0 && col > 0) {
            neighbors.add(new int[] {row - 1, col - 1});
        }
        if (row > 0 && col < maxCol) {
            neighbors.add(new int[] {row - 1, col + 1});
        }
        if (row < maxRow && col < maxCol) {
            neighbors.add(new int[] {row + 1, col + 1});
        }
        if (row < maxRow && col > 0) {
            neighbors.add(new int[] {row + 1, col - 1});
        }
        if (row > 0) {
            neighbors.add(new int[] {row - 1, col});
        }
        if (row < maxRow) {
            neighbors.add(new int[] {row + 1, col});
        }
        if (col > 0) {
            neighbors.add(new int[] {row, col - 1});
        }
        if (col < maxCol) {
            neighbors.add(new int[] {row, col + 1});
        }

        return neighbors;
    }
}

class Class2 {

    Map<Character, Class2> children = new HashMap<>();
    String word = "";
}

class Class3 {

    Class2 root;
    char endSymbol;

    Class3() {
        this.root = new Class2();
        this.endSymbol = '*';
    }

    public void method4(String word) {
        Class2 current = this.root;
        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            if (!current.children.containsKey(currentChar)) {
                Class2 newNode = new Class2();
                current.children.put(currentChar, newNode);
            }
            current = current.children.get(currentChar);
        }
        current.children.put(this.endSymbol, null);
        current.word = word;
    }
}