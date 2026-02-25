package com.thealgorithms.datastructures.trees;

import java.util.HashMap;
import java.util.Map;

/**
 * Node used internally by {@link Trie}.
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
 * Trie (prefix tree) implementation for storing and querying strings.
 */
public class Trie {
    private static final char ROOT_CHAR = '*';
    private final TrieNode root = new TrieNode(ROOT_CHAR);

    /**
     * Inserts the given word into the trie.
     *
     * @param word the word to insert
     */
    public void insert(String word) {
        TrieNode current = root;
        for (int index = 0; index < word.length(); index++) {
            char ch = word.charAt(index);
            current = current.children.computeIfAbsent(ch, TrieNode::new);
        }
        current.isEndOfWord = true;
    }

    /**
     * Checks whether the given word exists in the trie.
     *
     * @param word the word to search for
     * @return {@code true} if the word exists, {@code false} otherwise
     */
    public boolean search(String word) {
        TrieNode node = findNode(word);
        return node != null && node.isEndOfWord;
    }

    /**
     * Deletes the given word from the trie, if it exists.
     * <p>
     * Note: This implementation only unmarks the end-of-word flag and does not
     * physically remove nodes that become unreachable.
     *
     * @param word the word to delete
     * @return {@code true} if the word existed and was deleted,
     *         {@code false} otherwise
     */
    public boolean delete(String word) {
        TrieNode node = findNode(word);
        if (node == null || !node.isEndOfWord) {
            return false;
        }
        node.isEndOfWord = false;
        return true;
    }

    /**
     * Returns the total number of words stored in the trie.
     *
     * @return the number of words in the trie
     */
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

    /**
     * Checks whether there is any word in the trie that starts with the given prefix.
     *
     * @param prefix the prefix to check
     * @return {@code true} if at least one word starts with the prefix,
     *         {@code false} otherwise
     */
    public boolean startsWith(String prefix) {
        return findNode(prefix) != null;
    }

    /**
     * Returns the number of words in the trie that start with the given prefix.
     *
     * @param prefix the prefix to count words for
     * @return the number of words starting with the given prefix
     */
    public int countWordsWithPrefix(String prefix) {
        TrieNode node = findNode(prefix);
        return node == null ? 0 : size(node);
    }

    /**
     * Returns the node corresponding to the last character of the given string,
     * or {@code null} if the string is not present as a path in the trie.
     *
     * @param str the string to traverse
     * @return the terminal node for the string, or {@code null} if not found
     */
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