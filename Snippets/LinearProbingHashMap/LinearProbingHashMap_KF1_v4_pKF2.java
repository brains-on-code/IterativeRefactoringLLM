package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.ArrayList;

/**
 * A simple hash map implementation using linear probing.
 *
 * @param <Key>   the type of keys maintained by this map
 * @param <Value> the type of mapped values
 */
public class Class1<Key extends Comparable<Key>, Value> extends Map<Key, Value> {

    /** Current capacity of the hash table (array size). */
    private int capacity;

    /** Array storing keys. */
    private Key[] keys;

    /** Array storing values corresponding to keys. */
    private Value[] values;

    /** Number of key–value pairs currently stored. */
    private int size;

    /** Default initial capacity. */
    private static final int DEFAULT_CAPACITY = 16;

    /** Load factor threshold for resizing up (size > capacity / 2). */
    private static final double LOAD_FACTOR_UPPER = 0.5;

    /** Load factor threshold for resizing down (size <= capacity / 8). */
    private static final double LOAD_FACTOR_LOWER = 0.125;

    /** Constructs an empty map with default initial capacity. */
    public Class1() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructs an empty map with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the hash table
     */
    @SuppressWarnings("unchecked")
    public Class1(int initialCapacity) {
        this.capacity = initialCapacity;
        this.keys = (Key[]) new Comparable[initialCapacity];
        this.values = (Value[]) new Object[initialCapacity];
        this.size = 0;
    }

    /**
     * Inserts or updates a key–value pair in the map.
     *
     * @param key   the key to insert or update
     * @param value the value to associate with the key
     * @return {@code true} if the operation was successful, {@code false} if the key is {@code null}
     */
    @Override
    public boolean method1(Key key, Value value) {
        if (key == null) {
            return false;
        }

        if (shouldResizeUp()) {
            resize(2 * capacity);
        }

        int index = findSlotForInsert(key);
        if (keys[index] != null) {
            values[index] = value;
        } else {
            keys[index] = key;
            values[index] = value;
            size++;
        }

        return true;
    }

    /**
     * Retrieves the value associated with the specified key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value associated with the key, or {@code null} if the key is not present or is {@code null}
     */
    @Override
    public Value method2(Key key) {
        if (key == null) {
            return null;
        }

        int index = findSlotForSearch(key);
        return index == -1 ? null : values[index];
    }

    /**
     * Removes the key–value pair associated with the specified key.
     *
     * @param key the key to remove
     * @return {@code true} if the key was present and removed, {@code false} otherwise
     */
    @Override
    public boolean method3(Key key) {
        if (key == null || !method4(key)) {
            return false;
        }

        int index = findSlotForSearch(key);
        if (index == -1) {
            return false;
        }

        deleteEntryAt(index);
        if (shouldResizeDown()) {
            resize(capacity / 2);
        }

        return true;
    }

    /**
     * Checks whether the specified key is present in the map.
     *
     * @param key the key to check for presence
     * @return {@code true} if the key is present, {@code false} otherwise
     */
    @Override
    public boolean method4(Key key) {
        return method2(key) != null;
    }

    /**
     * Returns the number of key–value pairs in the map.
     *
     * @return the current size of the map
     */
    @Override
    int method5() {
        return size;
    }

    /**
     * Returns an iterable collection of all keys in the map, sorted in natural order.
     *
     * @return an iterable over the keys in the map
     */
    @Override
    Iterable<Key> method6() {
        ArrayList<Key> result = new ArrayList<>(size);
        for (int i = 0; i < capacity; i++) {
            if (keys[i] != null) {
                result.add(keys[i]);
            }
        }

        result.sort(Comparable::compareTo);
        return result;
    }

    /** Computes the next index in the table for linear probing. */
    private int nextIndex(int index) {
        return (index + 1) % capacity;
    }

    /** Indicates whether the table should be resized up. */
    private boolean shouldResizeUp() {
        return size > capacity * LOAD_FACTOR_UPPER;
    }

    /** Indicates whether the table should be resized down. */
    private boolean shouldResizeDown() {
        return size > 0 && size <= capacity * LOAD_FACTOR_LOWER;
    }

    /**
     * Finds the index at which the given key should be inserted or updated.
     * Returns either an empty slot or the slot containing the key.
     */
    private int findSlotForInsert(Key key) {
        int index = hash(key, capacity);
        while (keys[index] != null && !key.equals(keys[index])) {
            index = nextIndex(index);
        }
        return index;
    }

    /**
     * Finds the index of the given key for search operations.
     *
     * @return the index of the key, or -1 if not found
     */
    private int findSlotForSearch(Key key) {
        int index = hash(key, capacity);
        while (keys[index] != null) {
            if (key.equals(keys[index])) {
                return index;
            }
            index = nextIndex(index);
        }
        return -1;
    }

    /**
     * Deletes the entry at the given index and rehashes the subsequent cluster.
     */
    private void deleteEntryAt(int index) {
        keys[index] = null;
        values[index] = null;

        index = nextIndex(index);
        while (keys[index] != null) {
            Key rehashKey = keys[index];
            Value rehashValue = values[index];
            keys[index] = null;
            values[index] = null;
            size--;
            method1(rehashKey, rehashValue);
            index = nextIndex(index);
        }

        size--;
    }

    /**
     * Resizes the hash table to the given new capacity and rehashes all keys.
     *
     * @param newCapacity the new capacity of the hash table
     */
    private void resize(int newCapacity) {
        Class1<Key, Value> temp = new Class1<>(newCapacity);
        for (int i = 0; i < capacity; i++) {
            if (keys[i] != null) {
                temp.method1(keys[i], values[i]);
            }
        }

        this.keys = temp.keys;
        this.values = temp.values;
        this.capacity = newCapacity;
    }
}