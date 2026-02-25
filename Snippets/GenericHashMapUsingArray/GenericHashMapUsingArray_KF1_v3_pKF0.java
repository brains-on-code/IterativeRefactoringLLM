package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.LinkedList;
import java.util.Objects;

/**
 * A simple generic hash map implementation using separate chaining with linked lists.
 */
public class SimpleHashMap<K, V> {

    private static final float LOAD_FACTOR_THRESHOLD = 0.75f;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    private int size;
    private LinkedList<Entry<K, V>>[] buckets;

    @SuppressWarnings("unchecked")
    public SimpleHashMap() {
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
        Objects.requireNonNull(key, "Key cannot be null");

        LinkedList<Entry<K, V>> bucket = getBucket(key);
        Entry<K, V> existingEntry = findEntryInBucket(bucket, key);

        if (existingEntry != null) {
            existingEntry.value = value;
            return;
        }

        bucket.add(new Entry<>(key, value));
        size++;
        resizeIfNeeded();
    }

    public V get(K key) {
        Objects.requireNonNull(key, "Key cannot be null");

        Entry<K, V> entry = findEntryInBucket(getBucket(key), key);
        return entry != null ? entry.value : null;
    }

    public void remove(K key) {
        Objects.requireNonNull(key, "Key cannot be null");

        LinkedList<Entry<K, V>> bucket = getBucket(key);
        Entry<K, V> entryToRemove = findEntryInBucket(bucket, key);

        if (entryToRemove != null) {
            bucket.remove(entryToRemove);
            size--;
        }
    }

    public boolean containsKey(K key) {
        Objects.requireNonNull(key, "Key cannot be null");
        return findEntryInBucket(getBucket(key), key) != null;
    }

    public int size() {
        return size;
    }

    private LinkedList<Entry<K, V>> getBucket(K key) {
        return buckets[getBucketIndex(key)];
    }

    private int getBucketIndex(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    private Entry<K, V> findEntryInBucket(LinkedList<Entry<K, V>> bucket, K key) {
        for (Entry<K, V> entry : bucket) {
            if (entry.key.equals(key)) {
                return entry;
            }
        }
        return null;
    }

    private void resizeIfNeeded() {
        float currentLoadFactor = (float) size / buckets.length;
        if (currentLoadFactor > LOAD_FACTOR_THRESHOLD) {
            resize();
        }
    }

    private void resize() {
        LinkedList<Entry<K, V>>[] oldBuckets = buckets;
        initializeBuckets(oldBuckets.length * 2);
        size = 0;

        for (LinkedList<Entry<K, V>> bucket : oldBuckets) {
            for (Entry<K, V> entry : bucket) {
                put(entry.key, entry.value);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{");

        for (LinkedList<Entry<K, V>> bucket : buckets) {
            for (Entry<K, V> entry : bucket) {
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

    public static class Entry<K, V> {
        final K key;
        V value;

        public Entry(K key, V value) {
            this.key = Objects.requireNonNull(key, "Key cannot be null");
            this.value = value;
        }
    }
}