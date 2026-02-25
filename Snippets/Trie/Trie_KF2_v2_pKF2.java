package com.thealgorithms.datastructures.trees;

import java.util.HashMap;
import java.util.Map;

class TrieNode {
    final char value;
    final Map<Character, TrieNode> children = new HashMap<>();
    boolean isEndOfWord;

    TrieNode(char value) {
        this.value = value;
    }
}

public class Trie {
    private static final char ROOT_NODE_VALUE = '*';
    private final TrieNode root = new TrieNode(ROOT_NODE_VALUE);

    public void insert(String word) {
        TrieNode current = root;

        for (char ch : word.toCharArray()) {
            current = current.children.computeIfAbsent(ch, TrieNode::new);
        }

        current.isEndOfWord = true;
    }

    public boolean search(String word) {
        TrieNode node = findNode(word);
        return node != null && node.isEndOfWord;
    }

    public boolean delete(String word) {
        TrieNode node = findNode(word);
        if (node == null || !node.isEndOfWord) {
            return false;
        }
        node.isEndOfWord = false;
        return true;
    }

    public int countWords() {
        return countWords(root);
    }

    public boolean startsWithPrefix(String prefix) {
        return findNode(prefix) != null;
    }

    public int countWordsWithPrefix(String prefix) {
        TrieNode node = findNode(prefix);
        return node == null ? 0 : countWords(node);
    }

    private TrieNode findNode(String sequence) {
        TrieNode current = root;

        for (char ch : sequence.toCharArray()) {
            TrieNode next = current.children.get(ch);
            if (next == null) {
                return null;
            }
            current = next;
        }

        return current;
    }

    private int countWords(TrieNode node) {
        int count = node.isEndOfWord ? 1 : 0;
        for (TrieNode child : node.children.values()) {
            count += countWords(child);
        }
        return count;
    }
}