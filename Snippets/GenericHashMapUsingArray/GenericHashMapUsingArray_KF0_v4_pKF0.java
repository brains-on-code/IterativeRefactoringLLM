package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.LinkedList;

/**
 * A generic implementation of a hash map using an array of linked lists for collision resolution.
 *
 * @param <K> the type of keys maintained by this hash map
 * @param <V> the type of mapped values
 */
public class GenericHashMapUsingArray<K, V> {

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR_THRESHOLD = 0.75f;

    private int size;
    private LinkedList<Node<K, V>>[] buckets;

    public GenericHashMapUsingArray() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public GenericHashMapUsingArray(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be positive");
        }
        buckets = new LinkedList[initialCapacity];
        initializeBuckets();
        size = 0;
    }

    private void initializeBuckets() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    public void put(K key, V value) {
        LinkedList<Node<K, V>> bucket = getBucket(key);
        Node<K, V> existingNode = findNodeInBucket(bucket, key);

        if (existingNode != null) {
            existingNode.value = value;
            return;
        }

        bucket.add(new Node<>(key, value));
        size++;

        if (getLoadFactor() > LOAD_FACTOR_THRESHOLD) {
            rehash();
        }
    }

    public V get(K key) {
        Node<K, V> node = findNodeInBucket(getBucket(key), key);
        return node != null ? node.value : null;
    }

    public void remove(K key) {
        LinkedList<Node<K, V>> bucket = getBucket(key);
        Node<K, V> target = findNodeInBucket(bucket, key);

        if (target != null) {
            bucket.remove(target);
            size--;
        }
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public int size() {
        return size;
    }

    private LinkedList<Node<K, V>> getBucket(K key) {
        return buckets[getBucketIndex(key)];
    }

    private float getLoadFactor() {
        return (float) size / buckets.length;
    }

    private int getBucketIndex(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    private void rehash() {
        LinkedList<Node<K, V>>[] oldBuckets = buckets;
        @SuppressWarnings("unchecked")
        LinkedList<Node<K, V>>[] newBuckets = new LinkedList[oldBuckets.length * 2];
        buckets = newBuckets;
        initializeBuckets();
        size = 0;

        for (LinkedList<Node<K, V>> bucket : oldBuckets) {
            for (Node<K, V> node : bucket) {
                put(node.key, node.value);
            }
        }
    }

    private Node<K, V> findNodeInBucket(LinkedList<Node<K, V>> bucket, K key) {
        for (Node<K, V> node : bucket) {
            if (node.key.equals(key)) {
                return node;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{");
        boolean firstEntry = true;

        for (LinkedList<Node<K, V>> bucket : buckets) {
            for (Node<K, V> node : bucket) {
                if (!firstEntry) {
                    builder.append(", ");
                }
                builder.append(node.key).append(" : ").append(node.value);
                firstEntry = false;
            }
        }

        builder.append("}");
        return builder.toString();
    }

    private static class Node<K, V> {
        private final K key;
        private V value;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}