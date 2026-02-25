package com.thealgorithms.datastructures.caches;

import java.util.HashMap;
import java.util.Map;

/**
 * LFU (Least Frequently Used) cache.
 *
 * <p>Evicts the least frequently used item when capacity is reached.
 * {@code get} and {@code put} run in O(1) average time using:
 * <ul>
 *   <li>a hash map for key lookup</li>
 *   <li>a doubly linked list ordered by access frequency (ascending)</li>
 * </ul>
 *
 * @param <K> key type
 * @param <V> value type
 */
public class LFUCache<K, V> {

    /**
     * Doubly linked list node representing a cache entry.
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

    /** Head of the list: least frequently used entry. */
    private Node head;

    /** Tail of the list: most frequently used entry. */
    private Node tail;

    /** Key-to-node mapping for O(1) access. */
    private final Map<K, Node> cache;

    /** Maximum number of entries allowed in the cache. */
    private final int capacity;

    private static final int DEFAULT_CAPACITY = 100;

    /**
     * Creates an LFU cache with default capacity.
     */
    public LFUCache() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates an LFU cache with the given capacity.
     *
     * @param capacity maximum number of entries
     * @throws IllegalArgumentException if {@code capacity <= 0}
     */
    public LFUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        this.capacity = capacity;
        this.cache = new HashMap<>();
    }

    /**
     * Returns the value associated with {@code key}, or {@code null} if absent.
     * Accessing an entry increases its frequency and reorders it in the list.
     *
     * @param key key whose value is requested
     * @return associated value, or {@code null} if not present
     */
    public V get(K key) {
        Node node = cache.get(key);
        if (node == null) {
            return null;
        }
        incrementFrequency(node);
        return node.value;
    }

    /**
     * Inserts or updates a key-value pair.
     *
     * <ul>
     *   <li>If the key exists, its value is updated and its frequency increased.</li>
     *   <li>If the key does not exist and the cache is full, the least frequently
     *       used entry (head) is evicted.</li>
     * </ul>
     *
     * @param key key to insert or update
     * @param value value to associate with {@code key}
     */
    public void put(K key, V value) {
        Node existingNode = cache.get(key);
        if (existingNode != null) {
            existingNode.value = value;
            incrementFrequency(existingNode);
            return;
        }

        if (cache.size() >= capacity) {
            evictLeastFrequentlyUsed();
        }

        Node newNode = new Node(key, value, 1);
        insertByFrequency(newNode);
        cache.put(key, newNode);
    }

    /**
     * Increases the frequency of {@code node} and repositions it in the list.
     */
    private void incrementFrequency(Node node) {
        removeNode(node);
        node.frequency++;
        insertByFrequency(node);
    }

    /**
     * Evicts the least frequently used node (head) from the cache and list.
     */
    private void evictLeastFrequentlyUsed() {
        if (head == null) {
            return;
        }
        cache.remove(head.key);
        removeNode(head);
    }

    /**
     * Inserts {@code node} into the list ordered by frequency (ascending).
     * The head is the least frequently used node.
     */
    private void insertByFrequency(Node node) {
        if (head == null) {
            head = tail = node;
            return;
        }

        Node current = head;

        while (current != null && current.frequency <= node.frequency) {
            current = current.next;
        }

        if (current == null) {
            insertAtTail(node);
        } else {
            insertBefore(node, current);
        }
    }

    /**
     * Inserts {@code node} at the tail of the list.
     */
    private void insertAtTail(Node node) {
        tail.next = node;
        node.previous = tail;
        tail = node;
    }

    /**
     * Inserts {@code node} before {@code current} in the list.
     */
    private void insertBefore(Node node, Node current) {
        node.next = current;
        node.previous = current.previous;

        if (current.previous != null) {
            current.previous.next = node;
        } else {
            head = node;
        }

        current.previous = node;
    }

    /**
     * Removes {@code node} from the list and updates head/tail.
     */
    private void removeNode(Node node) {
        if (node.previous != null) {
            node.previous.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.previous = node.previous;
        } else {
            tail = node.previous;
        }

        node.previous = null;
        node.next = null;
    }
}