package com.thealgorithms.datastructures.trees;

import java.util.HashMap;
import java.util.Map;

class TrieNode {
    final char value;
    final Map<Character, TrieNode> children;
    boolean isEndOfWord;

    TrieNode(char value) {
        this.value = value;
        this.children = new HashMap<>();
    }
}

public class Trie {
    private static final char ROOT_NODE_VALUE = '*';
    private final TrieNode root;

    public Trie() {
        this.root = new TrieNode(ROOT_NODE_VALUE);
    }

    public void insert(String word) {
        if (word == null || word.isEmpty()) {
            return;
        }

        TrieNode current = root;
        for (char character : word.toCharArray()) {
            current = current.children.computeIfAbsent(character, TrieNode::new);
        }
        current.isEndOfWord = true;
    }

    public boolean search(String word) {
        if (word == null || word.isEmpty()) {
            return false;
        }

        TrieNode node = findNode(word);
        return node != null && node.isEndOfWord;
    }

    public boolean delete(String word) {
        if (word == null || word.isEmpty()) {
            return false;
        }

        TrieNode node = findNode(word);
        if (node == null || !node.isEndOfWord) {
            return false;
        }

        node.isEndOfWord = false;
        return true;
    }

    public int countWords() {
        return countWordsFromNode(root);
    }

    public boolean startsWithPrefix(String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            return false;
        }
        return findNode(prefix) != null;
    }

    public int countWordsWithPrefix(String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            return 0;
        }

        TrieNode node = findNode(prefix);
        return node == null ? 0 : countWordsFromNode(node);
    }

    private TrieNode findNode(String sequence) {
        TrieNode current = root;
        for (char character : sequence.toCharArray()) {
            TrieNode next = current.children.get(character);
            if (next == null) {
                return null;
            }
            current = next;
        }
        return current;
    }

    private int countWordsFromNode(TrieNode node) {
        int count = node.isEndOfWord ? 1 : 0;
        for (TrieNode child : node.children.values()) {
            count += countWordsFromNode(child);
        }
        return count;
    }
}