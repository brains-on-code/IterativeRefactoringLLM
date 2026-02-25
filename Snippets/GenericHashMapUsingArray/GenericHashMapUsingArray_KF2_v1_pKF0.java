package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.LinkedList;

public class GenericHashMapUsingArray<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR_THRESHOLD = 0.75f;

    private int size;
    private LinkedList<Node>[] buckets;

    public GenericHashMapUsingArray() {
        initializeBuckets(DEFAULT_CAPACITY);
        size = 0;
    }

    private void initializeBuckets(int capacity) {
        buckets = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    public void put(K key, V value) {
        int bucketIndex = hash(key);
        LinkedList<Node> bucket = buckets[bucketIndex];

        Node existingNode = findNodeInBucket(bucket, key);
        if (existingNode != null) {
            existingNode.value = value;
            return;
        }

        bucket.add(new Node(key, value));
        size++;

        if (needsRehashing()) {
            rehash();
        }
    }

    public V get(K key) {
        int bucketIndex = hash(key);
        LinkedList<Node> bucket = buckets[bucketIndex];

        Node node = findNodeInBucket(bucket, key);
        return node != null ? node.value : null;
    }

    public void remove(K key) {
        int bucketIndex = hash(key);
        LinkedList<Node> bucket = buckets[bucketIndex];

        Node targetNode = findNodeInBucket(bucket, key);
        if (targetNode != null) {
            bucket.remove(targetNode);
            size--;
        }
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

    public class Node {
        K key;
        V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}