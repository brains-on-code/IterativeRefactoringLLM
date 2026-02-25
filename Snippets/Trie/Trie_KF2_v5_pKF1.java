package com.thealgorithms.datastructures.trees;

import java.util.HashMap;
import java.util.Map;

class TrieNode {
    char character;
    Map<Character, TrieNode> children;
    boolean isEndOfWord;

    TrieNode(char character) {
        this.character = character;
        this.children = new HashMap<>();
        this.isEndOfWord = false;
    }
}

public class Trie {
    private static final char ROOT_NODE_CHARACTER = '*';

    private final TrieNode rootNode;

    public Trie() {
        rootNode = new TrieNode(ROOT_NODE_CHARACTER);
    }

    public void insert(String word) {
        TrieNode currentNode = rootNode;
        for (int index = 0; index < word.length(); index++) {
            char currentCharacter = word.charAt(index);
            TrieNode childNode = currentNode.children.get(currentCharacter);

            if (childNode == null) {
                childNode = new TrieNode(currentCharacter);
                currentNode.children.put(currentCharacter, childNode);
            }
            currentNode = childNode;
        }

        currentNode.isEndOfWord = true;
    }

    public boolean search(String word) {
        TrieNode currentNode = rootNode;
        for (int index = 0; index < word.length(); index++) {
            char currentCharacter = word.charAt(index);
            TrieNode childNode = currentNode.children.get(currentCharacter);

            if (childNode == null) {
                return false;
            }
            currentNode = childNode;
        }

        return currentNode.isEndOfWord;
    }

    public boolean delete(String word) {
        TrieNode currentNode = rootNode;
        for (int index = 0; index < word.length(); index++) {
            char currentCharacter = word.charAt(index);
            TrieNode childNode = currentNode.children.get(currentCharacter);
            if (childNode == null) {
                return false;
            }

            currentNode = childNode;
        }

        if (currentNode.isEndOfWord) {
            currentNode.isEndOfWord = false;
            return true;
        }

        return false;
    }

    public int countWords() {
        return countWords(rootNode);
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
        TrieNode currentNode = rootNode;

        for (int index = 0; index < prefix.length(); index++) {
            char currentCharacter = prefix.charAt(index);
            TrieNode childNode = currentNode.children.get(currentCharacter);
            if (childNode == null) {
                return false;
            }

            currentNode = childNode;
        }

        return true;
    }

    public int countWordsWithPrefix(String prefix) {
        TrieNode currentNode = rootNode;

        for (int index = 0; index < prefix.length(); index++) {
            char currentCharacter = prefix.charAt(index);
            TrieNode childNode = currentNode.children.get(currentCharacter);
            if (childNode == null) {
                return 0;
            }

            currentNode = childNode;
        }

        return countWords(currentNode);
    }
}