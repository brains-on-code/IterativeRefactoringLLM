package com.thealgorithms.datastructures.trees;

import java.util.HashMap;
import java.util.Map;

/** Node of a Trie data structure. */
class TrieNode {
    final char value;
    final Map<Character, TrieNode> children = new HashMap<>();
    boolean isEndOfWord;

    TrieNode(char value) {
        this.value = value;
    }
}

/** Trie (prefix tree) implementation. */
public class Trie {
    private static final char ROOT_CHAR = '*';
    private final TrieNode root = new TrieNode(ROOT_CHAR);

    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieNode current = root;
        for (int index = 0; index < word.length(); index++) {
            char ch = word.charAt(index);
            current = current.children.computeIfAbsent(ch, TrieNode::new);
        }
        current.isEndOfWord = true;
    }

    /** Returns true if the word is in the trie. */
    public boolean search(String word) {
        TrieNode node = findNode(word);
        return node != null && node.isEndOfWord;
    }

    /**
     * Deletes a word from the trie.
     *
     * @return true if the word existed and was deleted, false otherwise
     */
    public boolean delete(String word) {
        TrieNode node = findNode(word);
        if (node == null || !node.isEndOfWord) {
            return false;
        }
        node.isEndOfWord = false;
        return true;
    }

    /** Returns the number of words stored in the trie. */
    public int size() {
        return size(root);
    }

    private int size(TrieNode node) {
        if (node == null) {
            return 0;
        }

        int count = node.isEndOfWord ? 1 : 0;
        for (TrieNode child : node.children.values()) {
            count += size(child);
        }
        return count;
    }

    /** Returns true if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        return findNode(prefix) != null;
    }

    /** Returns the number of words in the trie that start with the given prefix. */
    public int countWordsWithPrefix(String prefix) {
        TrieNode node = findNode(prefix);
        return node == null ? 0 : size(node);
    }

    /** Returns the node corresponding to the last character of the given string, or null if not found. */
    private TrieNode findNode(String str) {
        TrieNode current = root;
        for (int index = 0; index < str.length(); index++) {
            char ch = str.charAt(index);
            TrieNode child = current.children.get(ch);
            if (child == null) {
                return null;
            }
            current = child;
        }
        return current;
    }
}