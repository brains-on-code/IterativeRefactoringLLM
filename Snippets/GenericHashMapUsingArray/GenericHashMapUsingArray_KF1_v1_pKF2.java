package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.LinkedList;

/**
 * A simple generic hash map implementation using separate chaining with linked lists.
 *
 * @param <K> the type of keys
 * @param <V> the type of values
 */
public class Class1<K, V> {

    /** Number of key-value pairs stored in the map. */
    private int size;

    /** Array of buckets, each bucket is a linked list of entries. */
    private LinkedList<Class2>[] buckets;

    /** Default initial capacity. */
    private static final int DEFAULT_CAPACITY = 16;

    /** Load factor threshold for resizing. */
    private static final float LOAD_FACTOR = 0.75f;

    /**
     * Constructs an empty map with default initial capacity.
     */
    public Class1() {
        initBuckets(DEFAULT_CAPACITY);
        size = 0;
    }

    /**
     * Initializes the bucket array with the given capacity.
     *
     * @param capacity the number of buckets
     */
    @SuppressWarnings("unchecked")
    private void initBuckets(int capacity) {
        buckets = new LinkedList[capacity];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     */
    public void method2(K key, V value) {
        int index = hash(key);
        LinkedList<Class2> bucket = buckets[index];

        for (Class2 entry : bucket) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }

        bucket.add(new Class2(key, value));
        size++;

        if ((float) size / buckets.length > LOAD_FACTOR) {
            resize();
        }
    }

    /**
     * Computes the bucket index for the given key.
     *
     * @param key the key
     * @return the bucket index
     */
    private int hash(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    /**
     * Resizes the bucket array to double its current length and rehashes all entries.
     */
    private void resize() {
        LinkedList<Class2>[] oldBuckets = buckets;
        initBuckets(oldBuckets.length * 2);
        this.size = 0;

        for (LinkedList<Class2> bucket : oldBuckets) {
            for (Class2 entry : bucket) {
                method2(entry.key, entry.value);
            }
        }
    }

    /**
     * Removes the mapping for a key from this map if it is present.
     *
     * @param key key whose mapping is to be removed from the map
     */
    public void method5(K key) {
        int index = hash(key);
        LinkedList<Class2> bucket = buckets[index];

        Class2 toRemove = null;
        for (Class2 entry : bucket) {
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
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings
     */
    public int method6() {
        return this.size;
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value mapped to the key, or {@code null} if none
     */
    public V method7(K key) {
        int index = hash(key);
        LinkedList<Class2> bucket = buckets[index];
        for (Class2 entry : bucket) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (LinkedList<Class2> bucket : buckets) {
            for (Class2 entry : bucket) {
                builder.append(entry.key);
                builder.append(" : ");
                builder.append(entry.value);
                builder.append(", ");
            }
        }
        if (builder.length() > 1) {
            builder.setLength(builder.length() - 2);
        }
        builder.append("}");
        return builder.toString();
    }

    /**
     * Returns {@code true} if this map contains a mapping for the specified key.
     *
     * @param key key whose presence in this map is to be tested
     * @return {@code true} if this map contains a mapping for the key
     */
    public boolean method9(K key) {
        return method7(key) != null;
    }

    /**
     * A key-value pair stored in the hash map.
     */
    public class Class2 {
        K key;
        V value;

        /**
         * Creates a new entry with the specified key and value.
         *
         * @param key   the key
         * @param value the value
         */
        public Class2(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}