package com.thealgorithms.datastructures.trees;

import java.util.HashMap;

class TrieNode {
    char character;
    HashMap<Character, TrieNode> children;
    boolean isEndOfWord;

    TrieNode(char character) {
        this.character = character;
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
        for (int index = 0; index < word.length(); index++) {
            char currentChar = word.charAt(index);
            TrieNode nextNode = currentNode.children.getOrDefault(currentChar, null);

            if (nextNode == null) {
                nextNode = new TrieNode(currentChar);
                currentNode.children.put(currentChar, nextNode);
            }
            currentNode = nextNode;
        }

        currentNode.isEndOfWord = true;
    }

    public boolean search(String word) {
        TrieNode currentNode = root;
        for (int index = 0; index < word.length(); index++) {
            char currentChar = word.charAt(index);
            TrieNode nextNode = currentNode.children.getOrDefault(currentChar, null);

            if (nextNode == null) {
                return false;
            }
            currentNode = nextNode;
        }

        return currentNode.isEndOfWord;
    }

    public boolean delete(String word) {
        TrieNode currentNode = root;
        for (int index = 0; index < word.length(); index++) {
            char currentChar = word.charAt(index);
            TrieNode nextNode = currentNode.children.getOrDefault(currentChar, null);
            if (nextNode == null) {
                return false;
            }

            currentNode = nextNode;
        }

        if (currentNode.isEndOfWord) {
            currentNode.isEndOfWord = false;
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

        for (TrieNode childNode : node.children.values()) {
            wordCount += countWords(childNode);
        }

        return wordCount;
    }

    public boolean startsWithPrefix(String prefix) {
        TrieNode currentNode = root;

        for (int index = 0; index < prefix.length(); index++) {
            char currentChar = prefix.charAt(index);
            TrieNode nextNode = currentNode.children.getOrDefault(currentChar, null);
            if (nextNode == null) {
                return false;
            }

            currentNode = nextNode;
        }

        return true;
    }

    public int countWordsWithPrefix(String prefix) {
        TrieNode currentNode = root;

        for (int index = 0; index < prefix.length(); index++) {
            char currentChar = prefix.charAt(index);
            TrieNode nextNode = currentNode.children.getOrDefault(currentChar, null);
            if (nextNode == null) {
                return 0;
            }

            currentNode = nextNode;
        }

        return countWords(currentNode);
    }
}