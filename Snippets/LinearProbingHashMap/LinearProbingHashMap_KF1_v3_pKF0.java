package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.ArrayList;
import java.util.List;

/**
 * Linear probing hash map implementation.
 */
public class Class1<Key extends Comparable<Key>, Value> extends Map<Key, Value> {

    /** Default initial capacity. */
    private static final int DEFAULT_CAPACITY = 16;

    /** Load factor thresholds for resizing. */
    private static final double UPPER_LOAD_FACTOR = 0.5;
    private static final double LOWER_LOAD_FACTOR = 0.125;

    /** Current capacity of the hash table. */
    private int capacity;

    /** Array of keys. */
    private Key[] keys;

    /** Array of values. */
    private Value[] values;

    /** Number of key-value pairs stored. */
    private int size;

    public Class1() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public Class1(int initialCapacity) {
        this.capacity = initialCapacity;
        this.keys = (Key[]) new Comparable[initialCapacity];
        this.values = (Value[]) new Object[initialCapacity];
        this.size = 0;
    }

    @Override
    public boolean method1(Key key, Value value) {
        if (key == null) {
            return false;
        }

        if (needsExpansion()) {
            resize(capacity * 2);
        }

        int index = findSlotForInsert(key);
        if (keys[index] != null) {
            values[index] = value;
            return true;
        }

        keys[index] = key;
        values[index] = value;
        size++;
        return true;
    }

    @Override
    public Value method2(Key key) {
        if (key == null) {
            return null;
        }

        int index = findKeyIndex(key);
        return index == -1 ? null : values[index];
    }

    @Override
    public boolean method3(Key key) {
        if (key == null) {
            return false;
        }

        int index = findKeyIndex(key);
        if (index == -1) {
            return false;
        }

        keys[index] = null;
        values[index] = null;

        rehashClusterFrom(nextIndex(index));

        size--;
        if (needsShrink() && capacity > DEFAULT_CAPACITY) {
            resize(capacity / 2);
        }

        return true;
    }

    @Override
    public boolean method4(Key key) {
        return method2(key) != null;
    }

    @Override
    int method5() {
        return size;
    }

    @Override
    Iterable<Key> method6() {
        List<Key> keyList = new ArrayList<>(size);
        for (Key key : keys) {
            if (key != null) {
                keyList.add(key);
            }
        }
        keyList.sort(Comparable::compareTo);
        return keyList;
    }

    /** Returns the next index in the probe sequence. */
    private int nextIndex(int index) {
        return (index + 1) % capacity;
    }

    /** Resizes the hash table to the given new capacity. */
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

    /** Determines if the table needs to be expanded based on load factor. */
    private boolean needsExpansion() {
        return size > capacity * UPPER_LOAD_FACTOR;
    }

    /** Determines if the table should be shrunk based on load factor. */
    private boolean needsShrink() {
        return size > 0 && size <= capacity * LOWER_LOAD_FACTOR;
    }

    /** Finds the index of the given key, or -1 if not found. */
    private int findKeyIndex(Key key) {
        int index = hash(key, capacity);
        while (keys[index] != null) {
            if (key.equals(keys[index])) {
                return index;
            }
            index = nextIndex(index);
        }
        return -1;
    }

    /** Finds the index where the key should be inserted or updated. */
    private int findSlotForInsert(Key key) {
        int index = hash(key, capacity);
        while (keys[index] != null && !key.equals(keys[index])) {
            index = nextIndex(index);
        }
        return index;
    }

    /** Rehashes a cluster of keys starting from the given index. */
    private void rehashClusterFrom(int startIndex) {
        int index = startIndex;
        while (keys[index] != null) {
            Key rehashKey = keys[index];
            Value rehashValue = values[index];
            keys[index] = null;
            values[index] = null;
            size--;
            method1(rehashKey, rehashValue);
            index = nextIndex(index);
        }
    }
}