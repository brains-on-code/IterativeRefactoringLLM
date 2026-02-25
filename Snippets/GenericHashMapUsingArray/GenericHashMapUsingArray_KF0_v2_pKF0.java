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
    private LinkedList<Node>[] buckets;

    public GenericHashMapUsingArray() {
        initializeBuckets(DEFAULT_INITIAL_CAPACITY);
        size = 0;
    }

    @SuppressWarnings("unchecked")
    private void initializeBuckets(int capacity) {
        buckets = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<Node> bucket = buckets[bucketIndex];

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

    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<Node> bucket = buckets[bucketIndex];

        Node node = findNodeInBucket(bucket, key);
        return node != null ? node.value : null;
    }

    public void remove(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<Node> bucket = buckets[bucketIndex];

        Node target = findNodeInBucket(bucket, key);
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

    private float getLoadFactor() {
        return (float) size / buckets.length;
    }

    private int getBucketIndex(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    private void rehash() {
        LinkedList<Node>[] oldBuckets = buckets;
        initializeBuckets(oldBuckets.length * 2);
        size = 0;

        for (LinkedList<Node> bucket : oldBuckets) {
            for (Node node : bucket) {
                put(node.key, node.value);
            }
        }
    }

    private Node findNodeInBucket(LinkedList<Node> bucket, K key) {
        for (Node node : bucket) {
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

        for (LinkedList<Node> bucket : buckets) {
            for (Node node : bucket) {
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

    private class Node {
        private final K key;
        private V value;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}