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
    private LinkedList<Entry>[] buckets;

    /** Default initial capacity. */
    private static final int DEFAULT_CAPACITY = 16;

    /** Load factor threshold for resizing. */
    private static final float LOAD_FACTOR = 0.75f;

    /** Constructs an empty map with default initial capacity. */
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
    public void put(K key, V value) {
        int index = hash(key);
        LinkedList<Entry> bucket = buckets[index];

        for (Entry entry : bucket) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }

        bucket.add(new Entry(key, value));
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

    /** Resizes the bucket array to double its current length and rehashes all entries. */
    private void resize() {
        LinkedList<Entry>[] oldBuckets = buckets;
        initBuckets(oldBuckets.length * 2);
        this.size = 0;

        for (LinkedList<Entry> bucket : oldBuckets) {
            for (Entry entry : bucket) {
                put(entry.key, entry.value);
            }
        }
    }

    /**
     * Removes the mapping for a key from this map if it is present.
     *
     * @param key key whose mapping is to be removed from the map
     */
    public void remove(K key) {
        int index = hash(key);
        LinkedList<Entry> bucket = buckets[index];

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
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings
     */
    public int size() {
        return this.size;
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value mapped to the key, or {@code null} if none
     */
    public V get(K key) {
        int index = hash(key);
        LinkedList<Entry> bucket = buckets[index];
        for (Entry entry : bucket) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    /**
     * Returns a string representation of this map in the form:
     * {key1 : value1, key2 : value2, ...}
     *
     * @return a string representation of the map
     */
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

    /**
     * Returns {@code true} if this map contains a mapping for the specified key.
     *
     * @param key key whose presence in this map is to be tested
     * @return {@code true} if this map contains a mapping for the key
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /** A key-value pair stored in the hash map. */
    public class Entry {
        K key;
        V value;

        /**
         * Creates a new entry with the specified key and value.
         *
         * @param key   the key
         * @param value the value
         */
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}