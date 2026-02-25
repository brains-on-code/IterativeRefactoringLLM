package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A simple generic hash map implementation using separate chaining.
 *
 * @param <K> key type
 * @param <V> value type
 */
public class Class1<K, V> {

    /** Buckets storing the entries (separate chaining). */
    private ArrayList<LinkedList<Entry>> buckets;

    /** Number of key-value pairs stored in the map. */
    private int size;

    /** Default initial capacity. */
    private static final int DEFAULT_CAPACITY = 10;

    /** Load factor threshold for resizing. */
    private static final float LOAD_FACTOR_THRESHOLD = 0.5f;

    /** Constructs an empty map with default capacity. */
    public Class1() {
        buckets = new ArrayList<>(DEFAULT_CAPACITY);
        for (int i = 0; i < DEFAULT_CAPACITY; i++) {
            buckets.add(new LinkedList<>());
        }
        size = 0;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     */
    public void method1(K key, V value) {
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

        if (getLoadFactor() > LOAD_FACTOR_THRESHOLD) {
            method2();
        }
    }

    /** Resizes the bucket array and rehashes all existing entries. */
    private void method2() {
        ArrayList<LinkedList<Entry>> oldBuckets = buckets;
        int newCapacity = oldBuckets.size() * 2;

        buckets = new ArrayList<>(newCapacity);
        for (int i = 0; i < newCapacity; i++) {
            buckets.add(new LinkedList<>());
        }

        size = 0;

        for (LinkedList<Entry> bucket : oldBuckets) {
            for (Entry entry : bucket) {
                method1(entry.key, entry.value);
            }
        }
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value mapped to the key, or {@code null} if none
     */
    public V method3(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<Entry> bucket = buckets.get(bucketIndex);

        for (Entry entry : bucket) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    /**
     * Removes the mapping for a key from this map if it is present.
     *
     * @param key key whose mapping is to be removed from the map
     */
    public void method4(K key) {
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

    /**
     * Returns {@code true} if this map contains a mapping for the specified key.
     *
     * @param key key whose presence in this map is to be tested
     * @return {@code true} if this map contains a mapping for the key
     */
    public boolean method5(K key) {
        return method3(key) != null;
    }

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of entries in the map
     */
    public int method6() {
        return this.size;
    }

    /**
     * Returns a string representation of this map.
     *
     * @return a string in the form {@code {key1 : value1, key2 : value2, ...}}
     */
    @Override
    public String method7() {
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

    /** Computes the bucket index for the given key. */
    private int getBucketIndex(K key) {
        return Math.abs(key.hashCode() % buckets.size());
    }

    /** Returns the current load factor of the map. */
    private float getLoadFactor() {
        return (float) size / buckets.size();
    }

    /** Simple key-value pair entry used in the buckets. */
    private class Entry {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}