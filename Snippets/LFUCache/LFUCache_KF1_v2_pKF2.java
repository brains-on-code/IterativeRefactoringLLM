package com.thealgorithms.datastructures.caches;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple LFU-style cache backed by a doubly linked list ordered by
 * access frequency in ascending order. When capacity is exceeded, the
 * least frequently used entry (at the head) is evicted.
 */
public class Class1<K, V> {

    /**
     * Node representing an entry in the cache.
     */
    private class Node {
        private final K key;
        private V value;
        private int frequency;
        private Node prev;
        private Node next;

        Node(K key, V value, int frequency) {
            this.key = key;
            this.value = value;
            this.frequency = frequency;
        }
    }

    /** Least frequently used node (head of the list). */
    private Node head;

    /** Most frequently used node (tail of the list). */
    private Node tail;

    /** Maps keys to their corresponding nodes for O(1) access. */
    private final Map<K, Node> cache;

    /** Maximum number of entries allowed in the cache. */
    private final int capacity;

    /** Default cache capacity. */
    private static final int DEFAULT_CAPACITY = 100;

    /**
     * Creates a cache with the default capacity.
     */
    public Class1() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates a cache with the specified capacity.
     *
     * @param capacity maximum number of entries
     * @throws IllegalArgumentException if capacity is not positive
     */
    public Class1(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        this.capacity = capacity;
        this.cache = new HashMap<>();
    }

    /**
     * Retrieves a value from the cache and increments its access frequency.
     *
     * @param key the key to look up
     * @return the associated value, or {@code null} if not present
     */
    public V method1(K key) {
        Node node = cache.get(key);
        if (node == null) {
            return null;
        }
        removeFromList(node);
        node.frequency++;
        insertByFrequency(node);
        return node.value;
    }

    /**
     * Inserts or updates a value in the cache. If the key already exists,
     * its value is updated and its frequency incremented. If the cache is
     * full, the least frequently used entry is evicted.
     *
     * @param key   the key to insert or update
     * @param value the value to associate with the key
     */
    public void method2(K key, V value) {
        Node node = cache.get(key);

        if (node != null) {
            node.value = value;
            node.frequency++;
            removeFromList(node);
            insertByFrequency(node);
            return;
        }

        if (cache.size() >= capacity) {
            cache.remove(head.key);
            removeFromList(head);
        }

        Node newNode = new Node(key, value, 1);
        insertByFrequency(newNode);
        cache.put(key, newNode);
    }

    /**
     * Inserts a node into the doubly linked list based on its frequency
     * (ascending order).
     *
     * @param node the node to insert
     */
    private void insertByFrequency(Node node) {
        if (head == null || tail == null) {
            head = node;
            tail = node;
            return;
        }

        Node current = head;

        while (current != null) {
            if (current.frequency > node.frequency) {
                // Insert before current
                node.next = current;
                node.prev = current.prev;

                if (current.prev != null) {
                    current.prev.next = node;
                } else {
                    head = node;
                }

                current.prev = node;
                return;
            }

            current = current.next;
        }

        // Insert at the end (most frequent)
        tail.next = node;
        node.prev = tail;
        node.next = null;
        tail = node;
    }

    /**
     * Removes a node from the doubly linked list.
     *
     * @param node the node to remove
     */
    private void removeFromList(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }

        node.prev = null;
        node.next = null;
    }
}