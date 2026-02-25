package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.ArrayList;
import java.util.LinkedList;

public class Class1<K, V> {

    private static final int INITIAL_CAPACITY = 10;
    private static final float LOAD_FACTOR_THRESHOLD = 0.5f;

    private ArrayList<LinkedList<Entry>> buckets;
    private int size;

    public Class1() {
        buckets = new ArrayList<>();
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            buckets.add(new LinkedList<>());
        }
        size = 0;
    }

    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<Entry> bucket = buckets.get(bucketIndex);

        for (Entry entry : bucket) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }

        bucket.add(new Entry(key, value));
        size++;

        if ((float) size / buckets.size() > LOAD_FACTOR_THRESHOLD) {
            resize();
        }
    }

    private void resize() {
        ArrayList<LinkedList<Entry>> oldBuckets = buckets;
        buckets = new ArrayList<>();
        size = 0;

        for (int i = 0; i < oldBuckets.size() * 2; i++) {
            buckets.add(new LinkedList<>());
        }

        for (LinkedList<Entry> bucket : oldBuckets) {
            for (Entry entry : bucket) {
                put(entry.key, entry.value);
            }
        }
    }

    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<Entry> bucket = buckets.get(bucketIndex);

        for (Entry entry : bucket) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }

        return null;
    }

    public void remove(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<Entry> bucket = buckets.get(bucketIndex);

        Entry toRemove = null;
        for (Entry entry : bucket) {
            if (entry.key.equals(key)) {
                toRemove = entry;
                break;
            }
        }

        if (toRemove != null) {
            bucket.remove(toRemove);
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
        StringBuilder builder = new StringBuilder();
        builder.append("{");

        for (LinkedList<Entry> bucket : buckets) {
            for (Entry entry : bucket) {
                builder.append(entry.key)
                       .append(" : ")
                       .append(entry.value)
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

    private class Entry {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}