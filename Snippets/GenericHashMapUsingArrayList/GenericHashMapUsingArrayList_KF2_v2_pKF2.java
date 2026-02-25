package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple generic hash map implementation backed by an {@link ArrayList} of
 * {@link LinkedList} buckets.
 *
 * @param <K> the type of keys
 * @param <V> the type of values
 */
public class GenericHashMapUsingArrayList<K, V> {

    private static final int INITIAL_CAPACITY = 10;
    private static final float LOAD_FACTOR_THRESHOLD = 0.5f;

    private List<LinkedList<Node>> buckets;
    private int size;

    public GenericHashMapUsingArrayList() {
        initializeBuckets(INITIAL_CAPACITY);
        this.size = 0;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the key already exists, its value is replaced.
     */
    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<Node> bucket = buckets.get(bucketIndex);

        Node existingNode = findNodeInBucket(bucket, key);
        if (existingNode != null) {
            existingNode.value = value;
            return;
        }

        bucket.add(new Node(key, value));
        size++;

        if (getLoadFactor() > LOAD_FACTOR_THRESHOLD) {
            rehash();
        }
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     */
    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<Node> bucket = buckets.get(bucketIndex);

        Node node = findNodeInBucket(bucket, key);
        return node != null ? node.value : null;
    }

    /**
     * Removes the mapping for a key from this map if it is present.
     */
    public void remove(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<Node> bucket = buckets.get(bucketIndex);

        Node target = findNodeInBucket(bucket, key);
        if (target != null) {
            bucket.remove(target);
            size--;
        }
    }

    /**
     * Returns {@code true} if this map contains a mapping for the specified key.
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    public int size() {
        return this.size;
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
     * Computes the index of the bucket for the given key.
     */
    private int getBucketIndex(K key) {
        return Math.abs(key.hashCode() % buckets.size());
    }

    /**
     * Returns the current load factor of the map.
     */
    private float getLoadFactor() {
        return (float) size / buckets.size();
    }

    /**
     * Rebuilds the internal bucket structure with increased capacity
     * and re-inserts all existing entries.
     */
    private void rehash() {
        List<LinkedList<Node>> oldBuckets = buckets;
        int newCapacity = oldBuckets.size() * 2;

        initializeBuckets(newCapacity);
        size = 0;

        for (LinkedList<Node> bucket : oldBuckets) {
            for (Node node : bucket) {
                put(node.key, node.value);
            }
        }
    }

    /**
     * Initializes the bucket list with the specified capacity.
     */
    private void initializeBuckets(int capacity) {
        buckets = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            buckets.add(new LinkedList<>());
        }
    }

    /**
     * Finds and returns the node with the specified key in the given bucket,
     * or {@code null} if no such node exists.
     */
    private Node findNodeInBucket(LinkedList<Node> bucket, K key) {
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                return node;
            }
        }
        return null;
    }

    private class Node {
        final K key;
        V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}