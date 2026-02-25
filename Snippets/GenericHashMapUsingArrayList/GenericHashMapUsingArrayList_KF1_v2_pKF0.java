package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Class1<K, V> {

    private static final int INITIAL_CAPACITY = 10;
    private static final float LOAD_FACTOR_THRESHOLD = 0.5f;

    private List<LinkedList<Entry>> buckets;
    private int size;

    public Class1() {
        initializeBuckets(INITIAL_CAPACITY);
        size = 0;
    }

    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<Entry> bucket = buckets.get(bucketIndex);

        Entry existingEntry = findEntryInBucket(bucket, key);
        if (existingEntry != null) {
            existingEntry.value = value;
            return;
        }

        bucket.add(new Entry(key, value));
        size++;

        if (getLoadFactor() > LOAD_FACTOR_THRESHOLD) {
            resize();
        }
    }

    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<Entry> bucket = buckets.get(bucketIndex);

        Entry entry = findEntryInBucket(bucket, key);
        return entry != null ? entry.value : null;
    }

    public void remove(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<Entry> bucket = buckets.get(bucketIndex);

        Entry entryToRemove = findEntryInBucket(bucket, key);
        if (entryToRemove != null) {
            bucket.remove(entryToRemove);
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

    private void resize() {
        List<LinkedList<Entry>> oldBuckets = buckets;
        initializeBuckets(oldBuckets.size() * 2);
        size = 0;

        for (LinkedList<Entry> bucket : oldBuckets) {
            for (Entry entry : bucket) {
                put(entry.key, entry.value);
            }
        }
    }

    private void initializeBuckets(int capacity) {
        buckets = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            buckets.add(new LinkedList<>());
        }
    }

    private float getLoadFactor() {
        return (float) size / buckets.size();
    }

    private int getBucketIndex(K key) {
        return Math.abs(key.hashCode() % buckets.size());
    }

    private Entry findEntryInBucket(LinkedList<Entry> bucket, K key) {
        for (Entry entry : bucket) {
            if (entry.key.equals(key)) {
                return entry;
            }
        }
        return null;
    }

    private class Entry {
        private final K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}