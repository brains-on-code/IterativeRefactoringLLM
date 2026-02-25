package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.LinkedList;

public class GenericHashMapUsingArray<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR_THRESHOLD = 0.75f;

    private int size;
    private LinkedList<Node<K, V>>[] buckets;

    public GenericHashMapUsingArray() {
        initializeBuckets(DEFAULT_CAPACITY);
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
        LinkedList<Node<K, V>> bucket = buckets[bucketIndex];

        for (Node<K, V> node : bucket) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }

        bucket.add(new Node<>(key, value));
        size++;

        if (currentLoadFactor() > LOAD_FACTOR_THRESHOLD) {
            rehash();
        }
    }

    public V get(K key) {
        int bucketIndex = hash(key);
        LinkedList<Node<K, V>> bucket = buckets[bucketIndex];

        for (Node<K, V> node : bucket) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }

        return null;
    }

    public void remove(K key) {
        int bucketIndex = hash(key);
        LinkedList<Node<K, V>> bucket = buckets[bucketIndex];

        Node<K, V> target = null;
        for (Node<K, V> node : bucket) {
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

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public int size() {
        return size;
    }

    private float currentLoadFactor() {
        return (float) size / buckets.length;
    }

    private int hash(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    private void rehash() {
        LinkedList<Node<K, V>>[] oldBuckets = buckets;
        initializeBuckets(oldBuckets.length * 2);
        size = 0;

        for (LinkedList<Node<K, V>> bucket : oldBuckets) {
            for (Node<K, V> node : bucket) {
                put(node.key, node.value);
            }
        }
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