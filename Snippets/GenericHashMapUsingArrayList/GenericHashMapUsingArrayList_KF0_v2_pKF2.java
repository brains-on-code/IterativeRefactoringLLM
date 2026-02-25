package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A generic hash map implementation using separate chaining with linked lists.
 *
 * <p>Provides average-case constant time for insertion, deletion, and lookup.</p>
 *
 * @param <K> the type of keys
 * @param <V> the type of values
 */
public class GenericHashMapUsingArrayList<K, V> {

    private static final int DEFAULT_CAPACITY = 10;
    private static final float LOAD_FACTOR_THRESHOLD = 0.5f;

    /** Buckets storing chains of nodes for separate chaining. */
    private ArrayList<LinkedList<Node>> buckets;

    /** Number of key-value pairs stored in the map. */
    private int size;

    /** Constructs an empty hash map with a default initial capacity. */
    public GenericHashMapUsingArrayList() {
        initializeBuckets(DEFAULT_CAPACITY);
        size = 0;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * Replaces the existing value if the key is already present.
     *
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     */
    public void put(K key, V value) {
        int index = bucketIndex(key);
        LinkedList<Node> bucket = buckets.get(index);

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
     * Returns the value mapped to the specified key, or {@code null} if no mapping exists.
     *
     * @param key the key whose associated value is to be returned
     * @return the value associated with the specified key, or {@code null} if none
     */
    public V get(K key) {
        int index = bucketIndex(key);
        LinkedList<Node> bucket = buckets.get(index);

        for (Node node : bucket) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }

        return null;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     *
     * @param key the key whose mapping is to be removed
     */
    public void remove(K key) {
        int index = bucketIndex(key);
        LinkedList<Node> bucket = buckets.get(index);

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
     * Returns {@code true} if this map contains a mapping for the specified key.
     *
     * @param key the key whose presence is to be tested
     * @return {@code true} if this map contains a mapping for the key
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /** Returns the number of key-value pairs in this map. */
    public int size() {
        return size;
    }

    /** Returns a string representation of the map containing all key-value pairs. */
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

    /** Computes the bucket index for the given key. */
    private int bucketIndex(K key) {
        return Math.abs(key.hashCode() % buckets.size());
    }

    /** Returns the current load factor of the map. */
    private float currentLoadFactor() {
        return (float) size / buckets.size();
    }

    /** Initializes the bucket list with the given capacity. */
    private void initializeBuckets(int capacity) {
        buckets = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            buckets.add(new LinkedList<>());
        }
    }

    /** Resizes the bucket array and rehashes all existing entries. */
    private void rehash() {
        ArrayList<LinkedList<Node>> oldBuckets = buckets;
        int newCapacity = oldBuckets.size() * 2;

        initializeBuckets(newCapacity);

        int oldSize = size;
        size = 0;

        for (LinkedList<Node> bucket : oldBuckets) {
            for (Node node : bucket) {
                put(node.key, node.value);
            }
        }

        size = oldSize;
    }

    /** Represents a single key-value pair stored in the map. */
    private class Node {
        final K key;
        V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}