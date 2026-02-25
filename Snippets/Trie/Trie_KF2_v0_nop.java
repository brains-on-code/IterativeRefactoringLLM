package com.thealgorithms.datastructures.trees;

import java.util.HashMap;


class TrieNode {
    char value;
    HashMap<Character, TrieNode> child;
    boolean end;


    TrieNode(char value) {
        this.value = value;
        this.child = new HashMap<>();
        this.end = false;
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
        for (int i = 0; i < word.length(); i++) {
            TrieNode node = currentNode.child.getOrDefault(word.charAt(i), null);

            if (node == null) {
                node = new TrieNode(word.charAt(i));
                currentNode.child.put(word.charAt(i), node);
            }
            currentNode = node;
        }

        currentNode.end = true;
    }


    public boolean search(String word) {
        TrieNode currentNode = root;
        for (int i = 0; i < word.length(); i++) {
            TrieNode node = currentNode.child.getOrDefault(word.charAt(i), null);

            if (node == null) {
                return false;
            }
            currentNode = node;
        }

        return currentNode.end;
    }


    public boolean delete(String word) {
        TrieNode currentNode = root;
        for (int i = 0; i < word.length(); i++) {
            TrieNode node = currentNode.child.getOrDefault(word.charAt(i), null);
            if (node == null) {
                return false;
            }

            currentNode = node;
        }

        if (currentNode.end) {
            currentNode.end = false;
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
        if (node.end) {
            count++;
        }

        for (TrieNode child : node.child.values()) {
            count += countWords(child);
        }

        return count;
    }


    public boolean startsWithPrefix(String prefix) {
        TrieNode currentNode = root;

        for (int i = 0; i < prefix.length(); i++) {
            TrieNode node = currentNode.child.getOrDefault(prefix.charAt(i), null);
            if (node == null) {
                return false;
            }

            currentNode = node;
        }

        return true;
    }


    public int countWordsWithPrefix(String prefix) {
        TrieNode currentNode = root;

        for (int i = 0; i < prefix.length(); i++) {
            TrieNode node = currentNode.child.getOrDefault(prefix.charAt(i), null);
            if (node == null) {
                return 0;
            }

            currentNode = node;
        }

        return countWords(currentNode);
    }
}
