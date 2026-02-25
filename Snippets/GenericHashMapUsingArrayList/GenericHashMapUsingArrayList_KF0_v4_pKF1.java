package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A generic implementation of a hash map using an array list of linked lists for collision resolution.
 * This class allows storage of key-value pairs with average-case constant time complexity for insertion,
 * deletion, and retrieval operations.
 *
 * <p>
 * The hash map uses separate chaining to handle collisions. Each bucket in the hash map is represented
 * by a linked list that holds nodes containing key-value pairs. When multiple keys hash to the same index,
 * they are stored in the same linked list.
 * </p>
 *
 * <p>
 * The hash map automatically resizes itself when the load factor exceeds 0.5. The load factor is defined
 * as the ratio of the number of entries to the number of buckets. When resizing occurs, all existing entries
 * are rehashed and inserted into the new buckets.
 * </p>
 *
 * @param <K> the type of keys maintained by this hash map
 * @param <V> the type of mapped values
 */
public class GenericHashMapUsingArrayList<K, V> {

    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    private static final float DEFAULT_LOAD_FACTOR_THRESHOLD = 0.5f;

    /** List of buckets (each bucket is a linked list of entries). */
    private ArrayList<LinkedList<HashEntry>> buckets;
    /** Number of key-value pairs in the hash map. */
    private int size;

    /**
     * Constructs a new empty hash map with an initial capacity of 10 buckets.
     */
    public GenericHashMapUsingArrayList() {
        buckets = new ArrayList<>();
        for (int i = 0; i < DEFAULT_INITIAL_CAPACITY; i++) {
            buckets.add(new LinkedList<>());
        }
        size = 0;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     *
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     */
    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<HashEntry> bucket = buckets.get(bucketIndex);

        for (HashEntry entry : bucket) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }

        bucket.add(new HashEntry(key, value));
        size++;

        if (getLoadFactor() > DEFAULT_LOAD_FACTOR_THRESHOLD) {
            resize();
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value associated with the specified key, or null if no mapping exists
     */
    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<HashEntry> bucket = buckets.get(bucketIndex);

        for (HashEntry entry : bucket) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }

        return null;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     *
     * @param key the key whose mapping is to be removed from the map
     */
    public void remove(K key) {
        int bucketIndex = getBucketIndex(key);
        LinkedList<HashEntry> bucket = buckets.get(bucketIndex);

        HashEntry entryToRemove = null;
        for (HashEntry entry : bucket) {
            if (entry.key.equals(key)) {
                entryToRemove = entry;
                break;
            }
        }

        if (entryToRemove != null) {
            bucket.remove(entryToRemove);
            size--;
        }
    }

    /**
     * Returns true if this map contains a mapping for the specified key.
     *
     * @param key the key whose presence in this map is to be tested
     * @return true if this map contains a mapping for the specified key
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Returns the number of key-value pairs in this map.
     *
     * @return the number of key-value pairs
     */
    public int size() {
        return this.size;
    }

    /**
     * Returns a string representation of the map, containing all key-value pairs.
     *
     * @return a string representation of the map
     */
    @Override
    public String toString() {
        StringBuilder mapStringBuilder = new StringBuilder();
        mapStringBuilder.append("{");
        for (LinkedList<HashEntry> bucket : buckets) {
            for (HashEntry entry : bucket) {
                mapStringBuilder.append(entry.key);
                mapStringBuilder.append(" : ");
                mapStringBuilder.append(entry.value);
                mapStringBuilder.append(", ");
            }
        }
        // Remove trailing comma and space if there are any elements
        if (mapStringBuilder.length() > 1) {
            mapStringBuilder.setLength(mapStringBuilder.length() - 2);
        }
        mapStringBuilder.append("}");
        return mapStringBuilder.toString();
    }

    /**
     * Computes the bucket index for the given key.
     *
     * @param key the key for which to compute the bucket index
     * @return the bucket index
     */
    private int getBucketIndex(K key) {
        return Math.abs(key.hashCode() % buckets.size());
    }

    /**
     * Returns the current load factor of the hash map.
     *
     * @return the load factor
     */
    private float getLoadFactor() {
        return (float) size / buckets.size();
    }

    /**
     * Resizes the hash map by doubling the number of buckets and rehashing existing entries.
     */
    private void resize() {
        ArrayList<LinkedList<HashEntry>> oldBuckets = buckets;
        buckets = new ArrayList<>();
        size = 0;

        int newCapacity = oldBuckets.size() * 2;
        for (int i = 0; i < newCapacity; i++) {
            buckets.add(new LinkedList<>());
        }

        for (LinkedList<HashEntry> bucket : oldBuckets) {
            for (HashEntry entry : bucket) {
                put(entry.key, entry.value);
            }
        }
    }

    /**
     * A private inner class representing a key-value pair (entry) in the hash map.
     */
    private class HashEntry {
        K key;
        V value;

        /**
         * Constructs a new HashEntry with the specified key and value.
         *
         * @param key   the key of the key-value pair
         * @param value the value of the key-value pair
         */
        HashEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}