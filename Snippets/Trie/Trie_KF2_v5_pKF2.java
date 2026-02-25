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

    /**
     * Inserts a word into the trie.
     *
     * @param word the word to insert; must not be {@code null}
     */
    public void insert(String word) {
        TrieNode current = root;

        for (char ch : word.toCharArray()) {
            current = current.children.computeIfAbsent(ch, TrieNode::new);
        }

        current.isEndOfWord = true;
    }

    /**
     * Checks whether a word exists in the trie as a complete word.
     *
     * @param word the word to search for; must not be {@code null}
     * @return {@code true} if the word exists and is marked as a complete word,
     *         {@code false} otherwise
     */
    public boolean search(String word) {
        TrieNode node = findNode(word);
        return node != null && node.isEndOfWord;
    }

    /**
     * Logically deletes a word from the trie by unmarking its end-of-word flag.
     * Nodes are not physically removed.
     *
     * @param word the word to delete; must not be {@code null}
     * @return {@code true} if the word existed and was unmarked,
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
     * Counts the total number of complete words stored in the trie.
     *
     * @return the number of complete words
     */
    public int countWords() {
        return countWords(root);
    }

    /**
     * Checks whether there exists at least one word in the trie that starts
     * with the given prefix.
     *
     * @param prefix the prefix to check; must not be {@code null}
     * @return {@code true} if at least one word starts with the prefix,
     *         {@code false} otherwise
     */
    public boolean startsWithPrefix(String prefix) {
        return findNode(prefix) != null;
    }

    /**
     * Counts how many words in the trie start with the given prefix.
     *
     * @param prefix the prefix to count words for; must not be {@code null}
     * @return the number of words that start with the prefix
     */
    public int countWordsWithPrefix(String prefix) {
        TrieNode node = findNode(prefix);
        return node == null ? 0 : countWords(node);
    }

    /**
     * Traverses the trie following the given sequence of characters (word or prefix).
     *
     * @param sequence the sequence to follow; must not be {@code null}
     * @return the node reached after following the sequence,
     *         or {@code null} if the sequence does not exist in the trie
     */
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

    /**
     * Recursively counts all complete words in the subtree rooted at the given node.
     *
     * @param node the root of the subtree; must not be {@code null}
     * @return the number of complete words in the subtree
     */
    private int countWords(TrieNode node) {
        int count = node.isEndOfWord ? 1 : 0;
        for (TrieNode child : node.children.values()) {
            count += countWords(child);
        }
        return count;
    }
}