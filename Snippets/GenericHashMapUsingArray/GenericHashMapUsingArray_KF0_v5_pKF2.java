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
        int bucketIndex = hash(key);
        LinkedList<Node> bucket = buckets[bucketIndex];

        for (Node node : bucket) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }

        bucket.add(new Node(key, value));
        size++;

        if (loadFactor() > LOAD_FACTOR_THRESHOLD) {
            rehash();
        }
    }

    private int hash(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    private float loadFactor() {
        return (float) size / buckets.length;
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

    public void remove(K key) {
        int bucketIndex = hash(key);
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

    public int size() {
        return size;
    }

    public V get(K key) {
        int bucketIndex = hash(key);
        LinkedList<Node> bucket = buckets[bucketIndex];

        for (Node node : bucket) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }

        return null;
    }

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

    private class Node {
        private final K key;
        private V value;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}