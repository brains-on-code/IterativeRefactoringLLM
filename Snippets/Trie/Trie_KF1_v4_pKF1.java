package com.thealgorithms.datastructures.trees;

import java.util.HashMap;
import java.util.Map;

class TrieNode {
    char value;
    Map<Character, TrieNode> children;
    boolean isEndOfWord;

    TrieNode(char value) {
        this.value = value;
        this.children = new HashMap<>();
        this.isEndOfWord = false;
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
            TrieNode nextNode = current.children.get(character);

            if (nextNode == null) {
                nextNode = new TrieNode(character);
                current.children.put(character, nextNode);
            }
            current = nextNode;
        }

        current.isEndOfWord = true;
    }

    public boolean search(String word) {
        TrieNode current = root;
        for (int position = 0; position < word.length(); position++) {
            char character = word.charAt(position);
            TrieNode nextNode = current.children.get(character);

            if (nextNode == null) {
                return false;
            }
            current = nextNode;
        }

        return current.isEndOfWord;
    }

    public boolean delete(String word) {
        TrieNode current = root;
        for (int position = 0; position < word.length(); position++) {
            char character = word.charAt(position);
            TrieNode nextNode = current.children.get(character);
            if (nextNode == null) {
                return false;
            }

            current = nextNode;
        }

        if (current.isEndOfWord) {
            current.isEndOfWord = false;
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
        if (node.isEndOfWord) {
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
            TrieNode nextNode = current.children.get(character);
            if (nextNode == null) {
                return false;
            }

            current = nextNode;
        }

        return true;
    }

    public int countWordsWithPrefix(String prefix) {
        TrieNode current = root;

        for (int position = 0; position < prefix.length(); position++) {
            char character = prefix.charAt(position);
            TrieNode nextNode = current.children.get(character);
            if (nextNode == null) {
                return 0;
            }

            current = nextNode;
        }

        return countWords(current);
    }
}