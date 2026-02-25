package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.LinkedList;
import java.util.Objects;

public class GenericHashMapUsingArray<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR_THRESHOLD = 0.75f;

    private int size;
    private LinkedList<Node<K, V>>[] buckets;

    public GenericHashMapUsingArray() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public GenericHashMapUsingArray(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be positive");
        }
        buckets = new LinkedList[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = new LinkedList<>();
        }
        size = 0;
    }

    public void put(K key, V value) {
        Objects.requireNonNull(key, "Key must not be null");

        int bucketIndex = hash(key);
        LinkedList<Node<K, V>> bucket = buckets[bucketIndex];

        Node<K, V> existingNode = findNodeInBucket(bucket, key);
        if (existingNode != null) {
            existingNode.value = value;
            return;
        }

        bucket.add(new Node<>(key, value));
        size++;

        if (needsRehashing()) {
            rehash();
        }
    }

    public V get(K key) {
        Objects.requireNonNull(key, "Key must not be null");

        int bucketIndex = hash(key);
        LinkedList<Node<K, V>> bucket = buckets[bucketIndex];

        Node<K, V> node = findNodeInBucket(bucket, key);
        return node != null ? node.value : null;
    }

    public V remove(K key) {
        Objects.requireNonNull(key, "Key must not be null");

        int bucketIndex = hash(key);
        LinkedList<Node<K, V>> bucket = buckets[bucketIndex];

        Node<K, V> targetNode = findNodeInBucket(bucket, key);
        if (targetNode != null) {
            bucket.remove(targetNode);
            size--;
            return targetNode.value;
        }
        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public int size() {
        return size;
    }

    private int hash(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    private boolean needsRehashing() {
        return (float) size / buckets.length > LOAD_FACTOR_THRESHOLD;
    }

    private void rehash() {
        LinkedList<Node<K, V>>[] oldBuckets = buckets;
        int newCapacity = oldBuckets.length * 2;

        @SuppressWarnings("unchecked")
        LinkedList<Node<K, V>>[] newBuckets = new LinkedList[newCapacity];
        for (int i = 0; i < newCapacity; i++) {
            newBuckets[i] = new LinkedList<>();
        }

        buckets = newBuckets;
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

        for (LinkedList<Node<K, V>> bucket : buckets) {
            for (Node<K, V> node : bucket) {
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

    private static class Node<K, V> {
        private final K key;
        private V value;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}