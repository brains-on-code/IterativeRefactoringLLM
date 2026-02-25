package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A generic implementation of a hash map using an array list of linked lists for collision resolution.
 * This class allows storage of key-value pairs with average-case constant time complexity for insertion,
 * deletion, and retrieval operations.
 *
 * <p>
 * The hash map uses separate chaining to handle collisions. Each bucket in the hash map is represented
 * by a linked list that holds nodes containing key-value pairs. When multiple keys hash to the same index,
 * they are stored in the same linked list.
 * </p>
 *
 * <p>
 * The hash map automatically resizes itself when the load factor exceeds 0.5. The load factor is defined
 * as the ratio of the number of entries to the number of buckets. When resizing occurs, all existing entries
 * are rehashed and inserted into the new buckets.
 * </p>
 *
 * @param <K> the type of keys maintained by this hash map
 * @param <V> the type of mapped values
 */
public class GenericHashMapUsingArrayList<K, V> {

    private static final int DEFAULT_CAPACITY = 10;
    private static final float LOAD_FACTOR_THRESHOLD = 0.5f;

    private ArrayList<LinkedList<Node>> buckets;
    private int size;

    /**
     * Constructs a new empty hash map with an initial capacity of 10 buckets.
     */
    public GenericHashMapUsingArrayList() {
        initializeBuckets(DEFAULT_CAPACITY);
        size = 0;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     *
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
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
     * Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value associated with the specified key, or null if no mapping exists
     */
    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<Node> bucket = buckets.get(bucketIndex);

        Node node = findNodeInBucket(bucket, key);
        return node == null ? null : node.value;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     *
     * @param key the key whose mapping is to be removed from the map
     */
    public void remove(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<Node> bucket = buckets.get(bucketIndex);

        Node nodeToRemove = findNodeInBucket(bucket, key);
        if (nodeToRemove != null) {
            bucket.remove(nodeToRemove);
            size--;
        }
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     *
     * @param key the key whose presence in this map is to be tested
     * @return true if this map contains a mapping for the specified key
     */
    public boolean containsKey(K key) {
        return get(key) != null;
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
     * Returns a string representation of the map, containing all key-value pairs.
     *
     * @return a string representation of the map
     */
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
     * Resizes the hash map by doubling the number of buckets and rehashing existing entries.
     */
    private void rehash() {
        ArrayList<LinkedList<Node>> oldBuckets = buckets;
        initializeBuckets(oldBuckets.size() * 2);
        size = 0;

        for (LinkedList<Node> bucket : oldBuckets) {
            for (Node node : bucket) {
                put(node.key, node.value);
            }
        }
    }

    private void initializeBuckets(int capacity) {
        buckets = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            buckets.add(new LinkedList<>());
        }
    }

    private int getBucketIndex(K key) {
        return Math.abs(key.hashCode()) % buckets.size();
    }

    private float getLoadFactor() {
        return (float) size / buckets.size();
    }

    private Node findNodeInBucket(LinkedList<Node> bucket, K key) {
        for (Node node : bucket) {
            if (node.key.equals(key)) {
                return node;
            }
        }
        return null;
    }

    /**
     * A private inner class representing a key-value pair (node) in the hash map.
     */
    private class Node {
        K key;
        V value;

        /**
         * Constructs a new Node with the specified key and value.
         *
         * @param key   the key of the key-value pair
         * @param value the value of the key-value pair
         */
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}