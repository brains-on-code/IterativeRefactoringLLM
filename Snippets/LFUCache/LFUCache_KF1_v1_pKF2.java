package com.thealgorithms.datastructures.caches;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple frequency-based cache (LFU-style) with a doubly linked list
 * ordered by access count (ascending). When capacity is exceeded, the
 * least frequently used entry is evicted.
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

    /** Head of the frequency-ordered list (least frequently used). */
    private Node head;

    /** Tail of the frequency-ordered list (most frequently used). */
    private Node tail;

    /** Map from key to node for O(1) access. */
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
        node.frequency += 1;
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
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            node.value = value;
            node.frequency += 1;
            removeFromList(node);
            insertByFrequency(node);
        } else {
            if (cache.size() >= capacity) {
                cache.remove(this.head.key);
                removeFromList(head);
            }
            Node node = new Node(key, value, 1);
            insertByFrequency(node);
            cache.put(key, node);
        }
    }

    /**
     * Inserts a node into the doubly linked list based on its frequency
     * (ascending order).
     *
     * @param node the node to insert
     */
    private void insertByFrequency(Node node) {
        if (tail != null && head != null) {
            Node current = this.head;
            while (current != null) {
                if (current.frequency > node.frequency) {
                    if (current == head) {
                        node.next = current;
                        current.prev = node;
                        this.head = node;
                        break;
                    } else {
                        node.next = current;
                        node.prev = current.prev;
                        current.prev.next = node;
                        current.prev = node;
                        break;
                    }
                } else {
                    current = current.next;
                    if (current == null) {
                        tail.next = node;
                        node.prev = tail;
                        node.next = null;
                        tail = node;
                        break;
                    }
                }
            }
        } else {
            tail = node;
            head = tail;
        }
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
            this.head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            this.tail = node.prev;
        }
    }
}