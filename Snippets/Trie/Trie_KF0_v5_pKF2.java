package com.thealgorithms.datastructures.trees;

import java.util.HashMap;
import java.util.Map;

/**
 * Node used in the {@link Trie} data structure.
 *
 * <p>Each node stores:
 * <ul>
 *   <li>a character value</li>
 *   <li>a mapping from characters to child nodes</li>
 *   <li>a flag indicating whether this node terminates a word</li>
 * </ul>
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
 * Trie (prefix tree) implementation.
 *
 * <p>Supports:
 * <ul>
 *   <li>inserting words</li>
 *   <li>searching for complete words</li>
 *   <li>deleting words</li>
 *   <li>counting all stored words</li>
 *   <li>checking whether a prefix exists</li>
 *   <li>counting words that share a given prefix</li>
 * </ul>
 *
 * <p>The root node does not correspond to any character in stored words.
 *
 * @author <a href="https://github.com/dheeraj92">Dheeraj Kumar Barnwal</a>
 * @author <a href="https://github.com/sailok">Sailok Chinta</a>
 */
public class Trie {
    private static final char ROOT_NODE_VALUE = '*';

    private final TrieNode root = new TrieNode(ROOT_NODE_VALUE);

    /**
     * Inserts a word into the trie.
     *
     * @param word word to insert
     */
    public void insert(String word) {
        TrieNode current = root;

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode next = current.children.get(ch);

            if (next == null) {
                next = new TrieNode(ch);
                current.children.put(ch, next);
            }

            current = next;
        }

        current.isEndOfWord = true;
    }

    /**
     * Checks whether the trie contains the given word.
     *
     * @param word word to search for
     * @return {@code true} if the word exists and terminates at a node,
     *         {@code false} otherwise
     */
    public boolean search(String word) {
        TrieNode node = findNode(word);
        return node != null && node.isEndOfWord;
    }

    /**
     * Deletes a word from the trie, if present.
     *
     * <p>This implementation only unmarks the end-of-word flag and does not
     * physically remove nodes, so shared prefixes remain intact and unused
     * nodes are left in place.
     *
     * @param word word to delete
     * @return {@code true} if the word was found and deleted,
     *         {@code false} if the word does not exist
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
     * @return number of words
     */
    public int countWords() {
        return countWords(root);
    }

    /**
     * Checks whether there exists at least one word that starts with
     * the given prefix.
     *
     * @param prefix prefix to check
     * @return {@code true} if the prefix exists, {@code false} otherwise
     */
    public boolean startsWithPrefix(String prefix) {
        return findNode(prefix) != null;
    }

    /**
     * Returns the number of words that start with the given prefix.
     *
     * @param prefix prefix to count words for
     * @return number of words starting with {@code prefix}
     */
    public int countWordsWithPrefix(String prefix) {
        TrieNode node = findNode(prefix);
        return node == null ? 0 : countWords(node);
    }

    /**
     * Traverses the trie following the characters of {@code key}.
     *
     * @param key word or prefix
     * @return node reached after following all characters,
     *         or {@code null} if any character is missing
     */
    private TrieNode findNode(String key) {
        TrieNode current = root;

        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            TrieNode next = current.children.get(ch);

            if (next == null) {
                return null;
            }

            current = next;
        }

        return current;
    }

    /**
     * Recursively counts words in the subtree rooted at {@code node}.
     *
     * @param node root of the subtree to count words in
     * @return number of complete words in the subtree
     */
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
}