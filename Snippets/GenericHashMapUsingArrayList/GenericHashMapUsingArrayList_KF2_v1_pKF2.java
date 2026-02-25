package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.ArrayList;
import java.util.LinkedList;

public class GenericHashMapUsingArrayList<K, V> {

    private static final int INITIAL_CAPACITY = 10;
    private static final float LOAD_FACTOR_THRESHOLD = 0.5f;

    private ArrayList<LinkedList<Node>> buckets;
    private int size;

    public GenericHashMapUsingArrayList() {
        this.buckets = new ArrayList<>(INITIAL_CAPACITY);
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            buckets.add(new LinkedList<>());
        }
        this.size = 0;
    }

    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<Node> bucket = buckets.get(bucketIndex);

        for (Node node : bucket) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }

        bucket.add(new Node(key, value));
        size++;

        if (getLoadFactor() > LOAD_FACTOR_THRESHOLD) {
            rehash();
        }
    }

    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<Node> bucket = buckets.get(bucketIndex);

        for (Node node : bucket) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }

        return null;
    }

    public void remove(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<Node> bucket = buckets.get(bucketIndex);

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

    public boolean containsKey(K key) {
        return get(key) != null;
    }

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

    private int getBucketIndex(K key) {
        return Math.abs(key.hashCode() % buckets.size());
    }

    private float getLoadFactor() {
        return (float) size / buckets.size();
    }

    private void rehash() {
        ArrayList<LinkedList<Node>> oldBuckets = buckets;
        int newCapacity = oldBuckets.size() * 2;

        buckets = new ArrayList<>(newCapacity);
        size = 0;

        for (int i = 0; i < newCapacity; i++) {
            buckets.add(new LinkedList<>());
        }

        for (LinkedList<Node> bucket : oldBuckets) {
            for (Node node : bucket) {
                put(node.key, node.value);
            }
        }
    }

    private class Node {
        K key;
        V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}