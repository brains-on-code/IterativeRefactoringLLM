package com.thealgorithms.datastructures.trees;

import java.util.HashMap;

/** Node of a Trie data structure. */
class TrieNode {
    char value;
    HashMap<Character, TrieNode> children;
    boolean isEndOfWord;

    TrieNode(char value) {
        this.value = value;
        this.children = new HashMap<>();
        this.isEndOfWord = false;
    }
}

/** Trie (prefix tree) implementation. */
public class Trie {
    private static final char ROOT_CHAR = '*';
    private final TrieNode root;

    public Trie() {
        root = new TrieNode(ROOT_CHAR);
    }

    /** Inserts a word into the trie. */
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

    /** Returns true if the word is in the trie. */
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

    /**
     * Deletes a word from the trie.
     *
     * @return true if the word existed and was deleted, false otherwise
     */
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

    /** Returns the number of words stored in the trie. */
    public int size() {
        return size(root);
    }

    private int size(TrieNode node) {
        if (node == null) {
            return 0;
        }

        int count = 0;
        if (node.isEndOfWord) {
            count++;
        }

        for (TrieNode child : node.children.values()) {
            count += size(child);
        }

        return count;
    }

    /** Returns true if there is any word in the trie that starts with the given prefix. */
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

    /** Returns the number of words in the trie that start with the given prefix. */
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

        return size(current);
    }
}