package com.thealgorithms.datastructures.caches;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@code LFUCache} class implements a Least Frequently Used (LFU) cache.
 * An LFU cache evicts the least frequently used item when the cache reaches its capacity.
 * It maintains a mapping of keys to nodes, where each node contains the key, its associated value,
 * and a frequency count that tracks how many times the item has been accessed. A doubly linked list
 * is used to efficiently manage the ordering of items based on their usage frequency.
 *
 * <p>This implementation is designed to provide O(1) time complexity for both the {@code get} and
 * {@code put} operations, which is achieved through the use of a hashmap for quick access and a
 * doubly linked list for maintaining the order of item frequencies.</p>
 *
 * <p>
 * Reference: <a href="https://en.wikipedia.org/wiki/Least_frequently_used">LFU Cache - Wikipedia</a>
 * </p>
 *
 * @param <K> The type of keys maintained by this cache.
 * @param <V> The type of mapped values.
 *
 * @author Akshay Dubey (https://github.com/itsAkshayDubey)
 */
public class LFUCache<K, V> {

    /**
     * The {@code Node} class represents an element in the LFU cache.
     * Each node contains a key, a value, and a frequency count.
     * It also has pointers to the previous and next nodes in the doubly linked list.
     */
    private class Node {
        private final K key;
        private V value;
        private int frequency;
        private Node previous;
        private Node next;

        /**
         * Constructs a new {@code Node} with the specified key, value, and frequency.
         *
         * @param key The key associated with this node.
         * @param value The value stored in this node.
         * @param frequency The frequency of usage of this node.
         */
        Node(K key, V value, int frequency) {
            this.key = key;
            this.value = value;
            this.frequency = frequency;
        }
    }

    private Node head;
    private Node tail;
    private final Map<K, Node> cache;
    private final int capacity;
    private static final int DEFAULT_CAPACITY = 100;

    /**
     * Constructs an LFU cache with the default capacity.
     */
    public LFUCache() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructs an LFU cache with the specified capacity.
     *
     * @param capacity The maximum number of items that the cache can hold.
     * @throws IllegalArgumentException if the specified capacity is less than or equal to zero.
     */
    public LFUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        this.capacity = capacity;
        this.cache = new HashMap<>();
    }

    /**
     * Retrieves the value associated with the given key from the cache.
     * If the key exists, the node's frequency is incremented, and the node is repositioned
     * in the linked list based on its updated frequency.
     *
     * @param key The key whose associated value is to be returned.
     * @return The value associated with the key, or {@code null} if the key is not present in the cache.
     */
    public V get(K key) {
        Node node = cache.get(key);
        if (node == null) {
            return null;
        }
        updateNodeFrequency(node);
        return node.value;
    }

    /**
     * Inserts or updates a key-value pair in the cache.
     * If the key already exists, the value is updated and its frequency is incremented.
     * If the cache is full, the least frequently used item is removed before inserting the new item.
     *
     * @param key The key associated with the value to be inserted or updated.
     * @param value The value to be inserted or updated.
     */
    public void put(K key, V value) {
        Node existingNode = cache.get(key);

        if (existingNode != null) {
            existingNode.value = value;
            updateNodeFrequency(existingNode);
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
     * Updates the frequency of a node and repositions it in the list.
     *
     * @param node The node whose frequency is to be updated.
     */
    private void updateNodeFrequency(Node node) {
        removeNode(node);
        node.frequency++;
        insertNodeByFrequency(node);
    }

    /**
     * Evicts the least frequently used node from the cache.
     */
    private void evictLeastFrequentlyUsed() {
        if (head == null) {
            return;
        }
        cache.remove(head.key);
        removeNode(head);
    }

    /**
     * Inserts a node into the linked list in the correct position based on its frequency.
     * The linked list is ordered by frequency, with the least frequently used node at the head.
     *
     * @param node The node to be inserted into the list.
     */
    private void insertNodeByFrequency(Node node) {
        if (head == null) {
            head = node;
            tail = node;
            return;
        }

        Node current = head;

        while (current != null && current.frequency <= node.frequency) {
            current = current.next;
        }

        if (current == head) {
            // Insert at the beginning
            node.next = head;
            head.previous = node;
            head = node;
        } else if (current == null) {
            // Insert at the end
            tail.next = node;
            node.previous = tail;
            tail = node;
        } else {
            // Insert in the middle
            Node prev = current.previous;
            node.next = current;
            node.previous = prev;
            prev.next = node;
            current.previous = node;
        }
    }

    /**
     * Removes a node from the doubly linked list.
     * This method ensures that the pointers of neighboring nodes are properly updated.
     *
     * @param node The node to be removed from the list.
     */
    private void removeNode(Node node) {
        Node prev = node.previous;
        Node next = node.next;

        if (prev != null) {
            prev.next = next;
        } else {
            head = next;
        }

        if (next != null) {
            next.previous = prev;
        } else {
            tail = prev;
        }

        node.previous = null;
        node.next = null;
    }
}