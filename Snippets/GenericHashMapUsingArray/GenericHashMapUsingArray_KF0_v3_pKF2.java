package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.LinkedList;

/**
 * A generic hash map implementation using separate chaining with linked lists.
 *
 * <p>Provides average-case constant time complexity for insertion, deletion,
 * and retrieval operations.</p>
 *
 * @param <K> the type of keys maintained by this hash map
 * @param <V> the type of mapped values
 */
public class GenericHashMapUsingArray<K, V> {

    /** Default initial capacity of the bucket array. */
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    /** Load factor threshold that triggers a resize. */
    private static final float LOAD_FACTOR_THRESHOLD = 0.75f;

    /** Number of key-value pairs stored in the map. */
    private int size;

    /** Array of buckets; each bucket is a linked list of nodes. */
    private LinkedList<Node>[] buckets;

    /** Constructs an empty hash map with the default initial capacity. */
    public GenericHashMapUsingArray() {
        initBuckets(DEFAULT_INITIAL_CAPACITY);
        size = 0;
    }

    /**
     * Initializes the bucket array with the specified capacity.
     *
     * @param capacity the number of buckets to create
     */
    @SuppressWarnings("unchecked")
    private void initBuckets(int capacity) {
        buckets = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    /**
     * Associates the specified value with the specified key in this map.
     * Replaces the existing value if the key is already present.
     *
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     */
    public void put(K key, V value) {
        int bucketIndex = hashFunction(key);
        LinkedList<Node> bucket = buckets[bucketIndex];

        for (Node node : bucket) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }

        bucket.add(new Node(key, value));
        size++;

        if (currentLoadFactor() > LOAD_FACTOR_THRESHOLD) {
            rehash();
        }
    }

    /**
     * Computes the index of the bucket for the given key.
     *
     * @param key the key whose bucket index is to be computed
     * @return the bucket index
     */
    private int hashFunction(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    /**
     * Returns the current load factor of the map.
     *
     * @return the load factor (size / number of buckets)
     */
    private float currentLoadFactor() {
        return (float) size / buckets.length;
    }

    /**
     * Resizes the bucket array to twice its current size and re-inserts all entries.
     */
    private void rehash() {
        LinkedList<Node>[] oldBuckets = buckets;
        initBuckets(oldBuckets.length * 2);
        size = 0;

        for (LinkedList<Node> bucket : oldBuckets) {
            for (Node node : bucket) {
                put(node.key, node.value);
            }
        }
    }

    /**
     * Removes the mapping for the specified key from this map, if present.
     *
     * @param key the key whose mapping is to be removed
     */
    public void remove(K key) {
        int bucketIndex = hashFunction(key);
        LinkedList<Node> bucket = buckets[bucketIndex];

        Node target = null;
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                target = node;
                break;
            }
        }

        if (target != null) {
            bucket.remove(target);
            size--;
        }
    }

    /**
     * Returns the number of key-value pairs in this map.
     *
     * @return the number of key-value pairs
     */
    public int size() {
        return size;
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value associated with the specified key, or {@code null} if no mapping exists
     */
    public V get(K key) {
        int bucketIndex = hashFunction(key);
        LinkedList<Node> bucket = buckets[bucketIndex];

        for (Node node : bucket) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }

        return null;
    }

    /**
     * Returns {@code true} if this map contains a mapping for the specified key.
     *
     * @param key the key whose presence in this map is to be tested
     * @return {@code true} if this map contains a mapping for the specified key
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{");

        for (LinkedList<Node> bucket : buckets) {
            for (Node node : bucket) {
                builder.append(node.key)
                       .append(" : ")
                       .append(node.value)
                       .append(", ");
            }
        }

        if (builder.length() > 1) {
            builder.setLength(builder.length() - 2);
        }

        builder.append("}");
        return builder.toString();
    }

    /**
     * Represents a single key-value pair stored in the hash map.
     */
    public class Node {
        K key;
        V value;

        /**
         * Constructs a new node with the specified key and value.
         *
         * @param key   the key of the key-value pair
         * @param value the value of the key-value pair
         */
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}