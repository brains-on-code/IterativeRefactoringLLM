package com.thealgorithms.datastructures.trees;

import java.util.HashMap;
import java.util.Map;

class TrieNode {
    char value;
    Map<Character, TrieNode> children;
    boolean isWordEnd;

    TrieNode(char value) {
        this.value = value;
        this.children = new HashMap<>();
        this.isWordEnd = false;
    }
}

public class Trie {
    private static final char ROOT_VALUE = '*';

    private final TrieNode root;

    public Trie() {
        root = new TrieNode(ROOT_VALUE);
    }

    public void insert(String word) {
        TrieNode current = root;
        for (int position = 0; position < word.length(); position++) {
            char character = word.charAt(position);
            TrieNode child = current.children.get(character);

            if (child == null) {
                child = new TrieNode(character);
                current.children.put(character, child);
            }
            current = child;
        }

        current.isWordEnd = true;
    }

    public boolean search(String word) {
        TrieNode current = root;
        for (int position = 0; position < word.length(); position++) {
            char character = word.charAt(position);
            TrieNode child = current.children.get(character);

            if (child == null) {
                return false;
            }
            current = child;
        }

        return current.isWordEnd;
    }

    public boolean delete(String word) {
        TrieNode current = root;
        for (int position = 0; position < word.length(); position++) {
            char character = word.charAt(position);
            TrieNode child = current.children.get(character);
            if (child == null) {
                return false;
            }

            current = child;
        }

        if (current.isWordEnd) {
            current.isWordEnd = false;
            return true;
        }

        return false;
    }

    public int countWords() {
        return countWords(root);
    }

    private int countWords(TrieNode node) {
        if (node == null) {
            return 0;
        }

        int wordCount = 0;
        if (node.isWordEnd) {
            wordCount++;
        }

        for (TrieNode child : node.children.values()) {
            wordCount += countWords(child);
        }

        return wordCount;
    }

    public boolean startsWith(String prefix) {
        TrieNode current = root;

        for (int position = 0; position < prefix.length(); position++) {
            char character = prefix.charAt(position);
            TrieNode child = current.children.get(character);
            if (child == null) {
                return false;
            }

            current = child;
        }

        return true;
    }

    public int countWordsWithPrefix(String prefix) {
        TrieNode current = root;

        for (int position = 0; position < prefix.length(); position++) {
            char character = prefix.charAt(position);
            TrieNode child = current.children.get(character);
            if (child == null) {
                return 0;
            }

            current = child;
        }

        return countWords(current);
    }
}