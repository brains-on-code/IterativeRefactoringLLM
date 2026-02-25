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
        this.isEndOfWord = false;
    }
}

public class Trie {
    private static final char ROOT_NODE_VALUE = '*';
    private final TrieNode root;

    public Trie() {
        root = new TrieNode(ROOT_NODE_VALUE);
    }

    public void insert(String word) {
        TrieNode currentNode = root;
        for (char ch : word.toCharArray()) {
            currentNode = currentNode.children.computeIfAbsent(ch, TrieNode::new);
        }
        currentNode.isEndOfWord = true;
    }

    public boolean search(String word) {
        TrieNode node = traverse(word);
        return node != null && node.isEndOfWord;
    }

    public boolean delete(String word) {
        TrieNode node = traverse(word);
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
        return traverse(prefix) != null;
    }

    public int countWordsWithPrefix(String prefix) {
        TrieNode node = traverse(prefix);
        return node == null ? 0 : countWords(node);
    }

    private TrieNode traverse(String sequence) {
        TrieNode currentNode = root;
        for (char ch : sequence.toCharArray()) {
            TrieNode nextNode = currentNode.children.get(ch);
            if (nextNode == null) {
                return null;
            }
            currentNode = nextNode;
        }
        return currentNode;
    }

    private int countWords(TrieNode node) {
        int count = node.isEndOfWord ? 1 : 0;
        for (TrieNode child : node.children.values()) {
            count += countWords(child);
        }
        return count;
    }
}