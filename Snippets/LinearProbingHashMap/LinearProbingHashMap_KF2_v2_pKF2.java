package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.ArrayList;

public class LinearProbingHashMap<Key extends Comparable<Key>, Value> extends Map<Key, Value> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final int RESIZE_FACTOR = 2;
    private static final int LOAD_FACTOR_THRESHOLD_DIVISOR = 2;
    private static final int SHRINK_FACTOR_DIVISOR = 8;

    private int capacity;
    private Key[] keys;
    private Value[] values;
    private int size;

    public LinearProbingHashMap() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public LinearProbingHashMap(int initialCapacity) {
        this.capacity = initialCapacity;
        this.keys = (Key[]) new Comparable[initialCapacity];
        this.values = (Value[]) new Object[initialCapacity];
    }

    @Override
    public boolean put(Key key, Value value) {
        if (key == null) {
            return false;
        }

        if (size > capacity / LOAD_FACTOR_THRESHOLD_DIVISOR) {
            resize(capacity * RESIZE_FACTOR);
        }

        int index = hash(key, capacity);
        while (keys[index] != null) {
            if (key.equals(keys[index])) {
                values[index] = value;
                return true;
            }
            index = incrementIndex(index);
        }

        keys[index] = key;
        values[index] = value;
        size++;
        return true;
    }

    @Override
    public Value get(Key key) {
        if (key == null) {
            return null;
        }

        int index = hash(key, capacity);
        while (keys[index] != null) {
            if (key.equals(keys[index])) {
                return values[index];
            }
            index = incrementIndex(index);
        }

        return null;
    }

    @Override
    public boolean delete(Key key) {
        if (key == null || !contains(key)) {
            return false;
        }

        int index = hash(key, capacity);
        while (!key.equals(keys[index])) {
            index = incrementIndex(index);
        }

        keys[index] = null;
        values[index] = null;

        index = incrementIndex(index);
        while (keys[index] != null) {
            Key keyToRehash = keys[index];
            Value valueToRehash = values[index];
            keys[index] = null;
            values[index] = null;
            size--;
            put(keyToRehash, valueToRehash);
            index = incrementIndex(index);
        }

        size--;
        if (size > 0 && size <= capacity / SHRINK_FACTOR_DIVISOR) {
            resize(capacity / RESIZE_FACTOR);
        }

        return true;
    }

    @Override
    public boolean contains(Key key) {
        return get(key) != null;
    }

    @Override
    int size() {
        return size;
    }

    @Override
    Iterable<Key> keys() {
        ArrayList<Key> listOfKeys = new ArrayList<>(size);
        for (int i = 0; i < capacity; i++) {
            if (keys[i] != null) {
                listOfKeys.add(keys[i]);
            }
        }
        listOfKeys.sort(Comparable::compareTo);
        return listOfKeys;
    }

    private int incrementIndex(int index) {
        return (index + 1) % capacity;
    }

    private void resize(int newCapacity) {
        LinearProbingHashMap<Key, Value> resizedMap = new LinearProbingHashMap<>(newCapacity);
        for (int i = 0; i < capacity; i++) {
            if (keys[i] != null) {
                resizedMap.put(keys[i], values[i]);
            }
        }

        this.keys = resizedMap.keys;
        this.values = resizedMap.values;
        this.capacity = newCapacity;
    }
}