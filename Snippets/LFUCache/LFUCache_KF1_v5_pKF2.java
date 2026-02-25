package com.thealgorithms.datastructures.caches;

import java.util.HashMap;
import java.util.Map;

/**
 * Least Frequently Used (LFU) cache implemented with:
 * - a HashMap for O(1) key lookup
 * - a doubly linked list ordered by access frequency (ascending)
 *
 * When capacity is exceeded, the least frequently used entry (at the head)
 * is evicted.
 */
public class LfuCache<K, V> {

    /**
     * Doubly linked list node representing a cache entry.
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

    /** Key-to-node mapping for O(1) access. */
    private final Map<K, Node> cache;

    /** Maximum number of entries allowed in the cache. */
    private final int capacity;

    /** Default cache capacity. */
    private static final int DEFAULT_CAPACITY = 100;

    /**
     * Creates a cache with the default capacity.
     */
    public LfuCache() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates a cache with the specified capacity.
     *
     * @param capacity maximum number of entries
     * @throws IllegalArgumentException if capacity is not positive
     */
    public LfuCache(int capacity) {
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
    public V get(K key) {
        Node node = cache.get(key);
        if (node == null) {
            return null;
        }
        incrementFrequency(node);
        return node.value;
    }

    /**
     * Inserts or updates a value in the cache.
     * - If the key exists: update value and increment frequency.
     * - If the key does not exist:
     *   - evict least frequently used entry if at capacity
     *   - insert new entry with frequency 1
     *
     * @param key   the key to insert or update
     * @param value the value to associate with the key
     */
    public void put(K key, V value) {
        Node node = cache.get(key);

        if (node != null) {
            node.value = value;
            incrementFrequency(node);
            return;
        }

        if (cache.size() >= capacity) {
            evictLeastFrequent();
        }

        Node newNode = new Node(key, value, 1);
        insertByFrequency(newNode);
        cache.put(key, newNode);
    }

    /**
     * Increments a node's frequency and repositions it in the list.
     *
     * @param node the node to update
     */
    private void incrementFrequency(Node node) {
        removeFromList(node);
        node.frequency++;
        insertByFrequency(node);
    }

    /**
     * Evicts the least frequently used node (head of the list).
     */
    private void evictLeastFrequent() {
        if (head == null) {
            return;
        }
        cache.remove(head.key);
        removeFromList(head);
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
                insertBefore(current, node);
                return;
            }
            current = current.next;
        }

        appendToTail(node);
    }

    /**
     * Inserts {@code nodeToInsert} before {@code target} in the list.
     *
     * @param target      node before which {@code nodeToInsert} will be placed
     * @param nodeToInsert node to insert
     */
    private void insertBefore(Node target, Node nodeToInsert) {
        nodeToInsert.next = target;
        nodeToInsert.prev = target.prev;

        if (target.prev != null) {
            target.prev.next = nodeToInsert;
        } else {
            head = nodeToInsert;
        }

        target.prev = nodeToInsert;
    }

    /**
     * Appends {@code node} at the end of the list (as the most frequent).
     *
     * @param node node to append
     */
    private void appendToTail(Node node) {
        tail.next = node;
        node.prev = tail;
        node.next = null;
        tail = node;
    }

    /**
     * Removes a node from the doubly linked list and updates head/tail.
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