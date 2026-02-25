package com.thealgorithms.datastructures.caches;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple Least Frequently Used (LFU) cache implementation.
 *
 * <p>Items are stored in a doubly linked list ordered by access frequency in
 * descending order (most frequently used at the head, least at the tail).
 * When the cache reaches capacity, the tail node (least frequently used) is
 * evicted.
 *
 * <p>This implementation favors clarity over performance. Reordering by
 * frequency is O(n), so it is not optimized for large capacities.
 */
public class LFUCache<K, V> {

    private static final int DEFAULT_CAPACITY = 100;

    /** Backing map from key to node. */
    private final Map<K, Node> cache;

    /** Maximum number of entries allowed in the cache. */
    private final int capacity;

    /** Most frequently used node (head of the list). */
    private Node head;

    /** Least frequently used node (tail of the list). */
    private Node tail;

    /**
     * Node of the doubly linked list, storing key, value, and access frequency.
     */
    private class Node {
        private final K key;
        private V value;
        private int frequency;
        private Node previous;
        private Node next;

        Node(K key, V value, int frequency) {
            this.key = key;
            this.value = value;
            this.frequency = frequency;
        }
    }

    public LFUCache() {
        this(DEFAULT_CAPACITY);
    }

    public LFUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        this.capacity = capacity;
        this.cache = new HashMap<>();
    }

    /**
     * Returns the value associated with the given key, or {@code null} if the key
     * is not present. Accessing an entry increases its frequency.
     */
    public V get(K key) {
        Node node = cache.get(key);
        if (node == null) {
            return null;
        }
        increaseFrequency(node);
        return node.value;
    }

    /**
     * Inserts or updates the value for the given key.
     *
     * <p>If the key already exists, its value is updated and its frequency is
     * increased. If the key does not exist and the cache is at capacity, the
     * least frequently used entry is evicted.
     */
    public void put(K key, V value) {
        Node existingNode = cache.get(key);

        if (existingNode != null) {
            existingNode.value = value;
            increaseFrequency(existingNode);
            return;
        }

        if (cache.size() >= capacity) {
            evictLeastFrequentlyUsed();
        }

        Node newNode = new Node(key, value, 1);
        insertNodeByFrequency(newNode);
        cache.put(key, newNode);
    }

    /**
     * Increases the frequency of the given node and repositions it in the list
     * to maintain ordering by descending frequency.
     */
    private void increaseFrequency(Node node) {
        removeNode(node);
        node.frequency++;
        insertNodeByFrequency(node);
    }

    /**
     * Removes the least frequently used node (the tail) from both the list and
     * the backing map.
     */
    private void evictLeastFrequentlyUsed() {
        if (tail == null) {
            return;
        }
        cache.remove(tail.key);
        removeNode(tail);
    }

    /**
     * Inserts the node into the doubly linked list so that the list remains
     * sorted by frequency in descending order.
     */
    private void insertNodeByFrequency(Node node) {
        if (head == null) {
            head = node;
            tail = node;
            return;
        }

        Node current = head;

        // Find the first node with a strictly lower frequency than the new node.
        while (current != null && current.frequency >= node.frequency) {
            current = current.next;
        }

        // Insert at the end (least frequent position).
        if (current == null) {
            tail.next = node;
            node.previous = tail;
            tail = node;
            return;
        }

        // Insert before the current node.
        Node previous = current.previous;
        node.next = current;
        node.previous = previous;
        current.previous = node;

        if (previous != null) {
            previous.next = node;
        } else {
            // Inserting at the head.
            head = node;
        }
    }

    /**
     * Detaches the given node from the doubly linked list, updating head and tail
     * references as needed.
     */
    private void removeNode(Node node) {
        Node previous = node.previous;
        Node next = node.next;

        if (previous != null) {
            previous.next = next;
        } else {
            head = next;
        }

        if (next != null) {
            next.previous = previous;
        } else {
            tail = previous;
        }

        node.previous = null;
        node.next = null;
    }
}