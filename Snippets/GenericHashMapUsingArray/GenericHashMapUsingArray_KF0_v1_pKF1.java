package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.LinkedList;

/**
 * A generic implementation of a hash map using an array of linked lists for collision resolution.
 * This class provides a way to store key-value pairs efficiently, allowing for average-case
 * constant time complexity for insertion, deletion, and retrieval operations.
 *
 * <p>
 * The hash map uses separate chaining for collision resolution. Each bucket in the hash map is a
 * linked list that stores nodes containing key-value pairs. When a collision occurs (i.e., when
 * two keys hash to the same index), the new key-value pair is simply added to the corresponding
 * linked list.
 * </p>
 *
 * <p>
 * The hash map automatically resizes itself when the load factor exceeds 0.75. The load factor is
 * defined as the ratio of the number of entries to the number of buckets. When resizing occurs,
 * all existing entries are rehashed and inserted into the new buckets.
 * </p>
 *
 * @param <K> the type of keys maintained by this hash map
 * @param <V> the type of mapped values
 */
public class GenericHashMapUsingArray<K, V> {

    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR_THRESHOLD = 0.75f;

    private int entryCount; // Total number of key-value pairs
    private LinkedList<EntryNode>[] bucketArray; // Array of linked lists (buckets) for storing entries

    /**
     * Constructs a new empty hash map with an initial capacity of 16.
     */
    public GenericHashMapUsingArray() {
        initializeBuckets(DEFAULT_INITIAL_CAPACITY);
        entryCount = 0;
    }

    /**
     * Initializes the buckets for the hash map with the specified number of buckets.
     *
     * @param bucketCount the number of buckets to initialize
     */
    @SuppressWarnings("unchecked")
    private void initializeBuckets(int bucketCount) {
        bucketArray = new LinkedList[bucketCount];
        for (int index = 0; index < bucketArray.length; index++) {
            bucketArray[index] = new LinkedList<>();
        }
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     *
     * @param key the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     */
    public void put(K key, V value) {
        int bucketIndex = computeBucketIndex(key);
        LinkedList<EntryNode> bucket = bucketArray[bucketIndex];

        for (EntryNode entryNode : bucket) {
            if (entryNode.key.equals(key)) {
                entryNode.value = value;
                return;
            }
        }

        bucket.add(new EntryNode(key, value));
        entryCount++;

        if ((float) entryCount / bucketArray.length > LOAD_FACTOR_THRESHOLD) {
            resizeAndRehash();
        }
    }

    /**
     * Returns the index of the bucket in which the key would be stored.
     *
     * @param key the key whose bucket index is to be computed
     * @return the bucket index
     */
    private int computeBucketIndex(K key) {
        return Math.floorMod(key.hashCode(), bucketArray.length);
    }

    /**
     * Rehashes the map by doubling the number of buckets and re-inserting all entries.
     */
    private void resizeAndRehash() {
        LinkedList<EntryNode>[] oldBucketArray = bucketArray;
        initializeBuckets(oldBucketArray.length * 2);
        this.entryCount = 0;

        for (LinkedList<EntryNode> bucket : oldBucketArray) {
            for (EntryNode entryNode : bucket) {
                put(entryNode.key, entryNode.value);
            }
        }
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     *
     * @param key the key whose mapping is to be removed from the map
     */
    public void remove(K key) {
        int bucketIndex = computeBucketIndex(key);
        LinkedList<EntryNode> bucket = bucketArray[bucketIndex];

        EntryNode nodeToRemove = null;
        for (EntryNode entryNode : bucket) {
            if (entryNode.key.equals(key)) {
                nodeToRemove = entryNode;
                break;
            }
        }

        if (nodeToRemove != null) {
            bucket.remove(nodeToRemove);
            entryCount--;
        }
    }

    /**
     * Returns the number of key-value pairs in this map.
     *
     * @return the number of key-value pairs
     */
    public int size() {
        return this.entryCount;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value associated with the specified key, or null if no mapping exists
     */
    public V get(K key) {
        int bucketIndex = computeBucketIndex(key);
        LinkedList<EntryNode> bucket = bucketArray[bucketIndex];

        for (EntryNode entryNode : bucket) {
            if (entryNode.key.equals(key)) {
                return entryNode.value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder mapStringBuilder = new StringBuilder();
        mapStringBuilder.append("{");
        for (LinkedList<EntryNode> bucket : bucketArray) {
            for (EntryNode entryNode : bucket) {
                mapStringBuilder.append(entryNode.key);
                mapStringBuilder.append(" : ");
                mapStringBuilder.append(entryNode.value);
                mapStringBuilder.append(", ");
            }
        }
        if (mapStringBuilder.length() > 1) {
            mapStringBuilder.setLength(mapStringBuilder.length() - 2);
        }
        mapStringBuilder.append("}");
        return mapStringBuilder.toString();
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
     * A private class representing a key-value pair (node) in the hash map.
     */
    public class EntryNode {
        K key;
        V value;

        /**
         * Constructs a new EntryNode with the specified key and value.
         *
         * @param key the key of the key-value pair
         * @param value the value of the key-value pair
         */
        public EntryNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}