package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.ArrayList;

/**
 * Linear probing hash map implementation.
 */
public class Class1<Key extends Comparable<Key>, Value> extends Map<Key, Value> {

    /** Current capacity of the hash table. */
    private int capacity;

    /** Array of keys. */
    private Key[] keys;

    /** Array of values. */
    private Value[] values;

    /** Number of key-value pairs stored. */
    private int size;

    /** Default initial capacity. */
    private static final int DEFAULT_CAPACITY = 16;

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

        if (size > capacity / 2) {
            resize(2 * capacity);
        }

        int index = hash(key, capacity);
        while (keys[index] != null) {
            if (key.equals(keys[index])) {
                values[index] = value;
                return true;
            }
            index = nextIndex(index);
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

        for (int index = hash(key, capacity); keys[index] != null; index = nextIndex(index)) {
            if (key.equals(keys[index])) {
                return values[index];
            }
        }

        return null;
    }

    @Override
    public boolean method3(Key key) {
        if (key == null || !method4(key)) {
            return false;
        }

        int index = hash(key, capacity);
        while (!key.equals(keys[index])) {
            index = nextIndex(index);
        }

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
        if (size > 0 && size <= capacity / 8) {
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
        ArrayList<Key> keyList = new ArrayList<>(size);
        for (int i = 0; i < capacity; i++) {
            if (keys[i] != null) {
                keyList.add(keys[i]);
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
}