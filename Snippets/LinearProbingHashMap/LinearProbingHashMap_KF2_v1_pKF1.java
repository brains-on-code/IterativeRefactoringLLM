package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.ArrayList;

public class LinearProbingHashMap<Key extends Comparable<Key>, Value> extends Map<Key, Value> {
    private int capacity;
    private Key[] keys;
    private Value[] values;
    private int size;

    public LinearProbingHashMap() {
        this(16);
    }

    @SuppressWarnings("unchecked")
    public LinearProbingHashMap(int initialCapacity) {
        this.capacity = initialCapacity;
        keys = (Key[]) new Comparable[initialCapacity];
        values = (Value[]) new Object[initialCapacity];
    }

    @Override
    public boolean put(Key key, Value value) {
        if (key == null) {
            return false;
        }

        if (size > capacity / 2) {
            resize(2 * capacity);
        }

        int index = hash(key, capacity);
        for (; keys[index] != null; index = incrementIndex(index)) {
            if (key.equals(keys[index])) {
                values[index] = value;
                return true;
            }
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

        for (int index = hash(key, capacity); keys[index] != null; index = incrementIndex(index)) {
            if (key.equals(keys[index])) {
                return values[index];
            }
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
        if (size > 0 && size <= capacity / 8) {
            resize(capacity / 2);
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
        ArrayList<Key> keyList = new ArrayList<>(size);
        for (int index = 0; index < capacity; index++) {
            if (keys[index] != null) {
                keyList.add(keys[index]);
            }
        }

        keyList.sort(Comparable::compareTo);
        return keyList;
    }

    private int incrementIndex(int index) {
        return (index + 1) % capacity;
    }

    private void resize(int newCapacity) {
        LinearProbingHashMap<Key, Value> resizedMap = new LinearProbingHashMap<>(newCapacity);
        for (int index = 0; index < capacity; index++) {
            if (keys[index] != null) {
                resizedMap.put(keys[index], values[index]);
            }
        }

        this.keys = resizedMap.keys;
        this.values = resizedMap.values;
        this.capacity = newCapacity;
    }
}