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
        if (isNullOrEmpty(word)) {
            return;
        }

        TrieNode currentNode = root;
        for (char character : word.toCharArray()) {
            currentNode = currentNode.children.computeIfAbsent(character, TrieNode::new);
        }
        currentNode.isEndOfWord = true;
    }

    public boolean search(String word) {
        if (isNullOrEmpty(word)) {
            return false;
        }

        TrieNode node = findNode(word);
        return node != null && node.isEndOfWord;
    }

    public boolean delete(String word) {
        if (isNullOrEmpty(word)) {
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
        if (isNullOrEmpty(prefix)) {
            return false;
        }
        return findNode(prefix) != null;
    }

    public int countWordsWithPrefix(String prefix) {
        if (isNullOrEmpty(prefix)) {
            return 0;
        }

        TrieNode node = findNode(prefix);
        return node == null ? 0 : countWordsFromNode(node);
    }

    private TrieNode findNode(String sequence) {
        TrieNode currentNode = root;
        for (char character : sequence.toCharArray()) {
            TrieNode nextNode = currentNode.children.get(character);
            if (nextNode == null) {
                return null;
            }
            currentNode = nextNode;
        }
        return currentNode;
    }

    private int countWordsFromNode(TrieNode node) {
        int count = node.isEndOfWord ? 1 : 0;
        for (TrieNode child : node.children.values()) {
            count += countWordsFromNode(child);
        }
        return count;
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }
}