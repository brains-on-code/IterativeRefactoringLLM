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
    private static final char ROOT_CHAR = '*';
    private final TrieNode root;

    public Trie() {
        root = new TrieNode(ROOT_CHAR);
    }

    public void insert(String word) {
        TrieNode current = root;
        for (int index = 0; index < word.length(); index++) {
            char ch = word.charAt(index);
            TrieNode child = current.children.get(ch);

            if (child == null) {
                child = new TrieNode(ch);
                current.children.put(ch, child);
            }
            current = child;
        }

        current.isEndOfWord = true;
    }

    public boolean search(String word) {
        TrieNode current = root;
        for (int index = 0; index < word.length(); index++) {
            char ch = word.charAt(index);
            TrieNode child = current.children.get(ch);

            if (child == null) {
                return false;
            }
            current = child;
        }

        return current.isEndOfWord;
    }

    public boolean delete(String word) {
        TrieNode current = root;
        for (int index = 0; index < word.length(); index++) {
            char ch = word.charAt(index);
            TrieNode child = current.children.get(ch);
            if (child == null) {
                return false;
            }

            current = child;
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

        int count = 0;
        if (node.isEndOfWord) {
            count++;
        }

        for (TrieNode child : node.children.values()) {
            count += countWords(child);
        }

        return count;
    }

    public boolean startsWith(String prefix) {
        TrieNode current = root;

        for (int index = 0; index < prefix.length(); index++) {
            char ch = prefix.charAt(index);
            TrieNode child = current.children.get(ch);
            if (child == null) {
                return false;
            }

            current = child;
        }

        return true;
    }

    public int countWordsWithPrefix(String prefix) {
        TrieNode current = root;

        for (int index = 0; index < prefix.length(); index++) {
            char ch = prefix.charAt(index);
            TrieNode child = current.children.get(ch);
            if (child == null) {
                return 0;
            }

            current = child;
        }

        return countWords(current);
    }
}