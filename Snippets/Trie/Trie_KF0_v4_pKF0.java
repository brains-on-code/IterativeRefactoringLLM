package com.thealgorithms.datastructures.trees;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Trie Node that stores a character and pointers to its children.
 * Each node has a hashmap which can point to all possible characters.
 * Each node also has a boolean value to indicate if it is the end of a word.
 */
class TrieNode {
    final char value;
    final Map<Character, TrieNode> children = new HashMap<>();
    boolean isEndOfWord;

    TrieNode(char value) {
        this.value = value;
    }
}

/**
 * Trie Data structure implementation without any libraries.
 *
 * <p>The Trie (also known as a prefix tree) is a special tree-like data structure
 * that is used to store a dynamic set or associative array where the keys are
 * usually strings. It is highly efficient for prefix-based searches.</p>
 *
 * <p>This implementation supports basic Trie operations like insertion, search,
 * and deletion.</p>
 *
 * <p>Each node of the Trie represents a character and has child nodes for each
 * possible character.</p>
 */
public class Trie {
    private static final char ROOT_NODE_VALUE = '*';

    private final TrieNode root = new TrieNode(ROOT_NODE_VALUE);

    /**
     * Inserts a word into the Trie.
     *
     * @param word The word to be inserted into the Trie.
     */
    public void insert(String word) {
        if (!isValidInput(word)) {
            return;
        }

        TrieNode currentNode = root;
        for (char ch : word.toCharArray()) {
            currentNode = currentNode.children.computeIfAbsent(ch, TrieNode::new);
        }
        currentNode.isEndOfWord = true;
    }

    /**
     * Searches for a word in the Trie.
     *
     * @param word The word to search in the Trie.
     * @return true if the word exists in the Trie, false otherwise.
     */
    public boolean search(String word) {
        if (!isValidInput(word)) {
            return false;
        }

        TrieNode node = findNode(word);
        return node != null && node.isEndOfWord;
    }

    /**
     * Deletes a word from the Trie.
     *
     * @param word The word to be deleted from the Trie.
     * @return true if the word was found and deleted, false if it was not found.
     */
    public boolean delete(String word) {
        if (!isValidInput(word)) {
            return false;
        }

        TrieNode node = findNode(word);
        if (node == null || !node.isEndOfWord) {
            return false;
        }

        node.isEndOfWord = false;
        return true;
    }

    /**
     * Counts the number of words in the trie.
     *
     * @return count of words
     */
    public int countWords() {
        return countWords(root);
    }

    private int countWords(TrieNode node) {
        if (node == null) {
            return 0;
        }

        int count = node.isEndOfWord ? 1 : 0;
        for (TrieNode child : node.children.values()) {
            count += countWords(child);
        }
        return count;
    }

    /**
     * Check if the prefix exists in the trie.
     *
     * @param prefix the prefix to be checked in the Trie
     * @return true / false depending on the prefix if exists in the Trie
     */
    public boolean startsWithPrefix(String prefix) {
        if (!isValidInput(prefix)) {
            return false;
        }
        return findNode(prefix) != null;
    }

    /**
     * Count the number of words starting with the given prefix in the trie.
     *
     * @param prefix the prefix to be checked in the Trie
     * @return count of words
     */
    public int countWordsWithPrefix(String prefix) {
        if (!isValidInput(prefix)) {
            return 0;
        }

        TrieNode node = findNode(prefix);
        return node == null ? 0 : countWords(node);
    }

    /**
     * Helper method to traverse the trie and return the node corresponding
     * to the last character of the given key (word or prefix).
     *
     * @param key the word or prefix to traverse
     * @return the TrieNode corresponding to the last character, or null if not found
     */
    private TrieNode findNode(String key) {
        TrieNode currentNode = root;

        for (char ch : key.toCharArray()) {
            TrieNode nextNode = currentNode.children.get(ch);
            if (nextNode == null) {
                return null;
            }
            currentNode = nextNode;
        }

        return currentNode;
    }

    private boolean isValidInput(String value) {
        return value != null && !value.isEmpty();
    }
}