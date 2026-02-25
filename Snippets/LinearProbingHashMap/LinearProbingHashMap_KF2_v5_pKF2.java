package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.ArrayList;
import java.util.List;

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

        ensureCapacityBeforeInsert();

        int index = findSlotForKey(key);
        if (keys[index] != null && key.equals(keys[index])) {
            values[index] = value;
        } else {
            keys[index] = key;
            values[index] = value;
            size++;
        }

        return true;
    }

    @Override
    public Value get(Key key) {
        if (key == null) {
            return null;
        }

        int index = findExistingKeyIndex(key);
        return index == -1 ? null : values[index];
    }

    @Override
    public boolean delete(Key key) {
        if (key == null || !contains(key)) {
            return false;
        }

        int index = findExistingKeyIndex(key);
        if (index == -1) {
            return false;
        }

        removeEntryAtIndex(index);
        rehashClusterStartingFrom(incrementIndex(index));
        size--;

        shrinkIfNeeded();
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
        List<Key> listOfKeys = new ArrayList<>(size);
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

    private void ensureCapacityBeforeInsert() {
        if (size > capacity / LOAD_FACTOR_THRESHOLD_DIVISOR) {
            resize(capacity * RESIZE_FACTOR);
        }
    }

    private void shrinkIfNeeded() {
        if (size > 0 && size <= capacity / SHRINK_FACTOR_DIVISOR) {
            resize(capacity / RESIZE_FACTOR);
        }
    }

    private int findSlotForKey(Key key) {
        int index = hash(key, capacity);
        while (keys[index] != null && !key.equals(keys[index])) {
            index = incrementIndex(index);
        }
        return index;
    }

    private int findExistingKeyIndex(Key key) {
        int index = hash(key, capacity);
        while (keys[index] != null) {
            if (key.equals(keys[index])) {
                return index;
            }
            index = incrementIndex(index);
        }
        return -1;
    }

    private void removeEntryAtIndex(int index) {
        keys[index] = null;
        values[index] = null;
    }

    private void rehashClusterStartingFrom(int startIndex) {
        int index = startIndex;
        while (keys[index] != null) {
            Key keyToRehash = keys[index];
            Value valueToRehash = values[index];

            keys[index] = null;
            values[index] = null;
            size--;

            put(keyToRehash, valueToRehash);
            index = incrementIndex(index);
        }
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