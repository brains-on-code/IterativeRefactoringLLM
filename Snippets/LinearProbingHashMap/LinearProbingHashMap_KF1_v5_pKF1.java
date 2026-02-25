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
        this.keys = (Key[]) new Comparable[initialCapacity];
        this.values = (Value[]) new Object[initialCapacity];
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
        for (; keys[index] != null; index = nextIndex(index)) {
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

        for (int index = hash(key, capacity); keys[index] != null; index = nextIndex(index)) {
            if (key.equals(keys[index])) {
                return values[index];
            }
        }

        return null;
    }

    @Override
    public boolean remove(Key key) {
        if (key == null || !containsKey(key)) {
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
            Key keyToRehash = keys[index];
            Value valueToRehash = values[index];
            keys[index] = null;
            values[index] = null;
            size--;
            put(keyToRehash, valueToRehash);
            index = nextIndex(index);
        }

        size--;
        if (size > 0 && size <= capacity / 8) {
            resize(capacity / 2);
        }

        return true;
    }

    @Override
    public boolean containsKey(Key key) {
        return get(key) != null;
    }

    @Override
    int size() {
        return size;
    }

    @Override
    Iterable<Key> keys() {
        ArrayList<Key> collectedKeys = new ArrayList<>(size);
        for (int index = 0; index < capacity; index++) {
            if (keys[index] != null) {
                collectedKeys.add(keys[index]);
            }
        }

        collectedKeys.sort(Comparable::compareTo);
        return collectedKeys;
    }

    private int nextIndex(int currentIndex) {
        return (currentIndex + 1) % capacity;
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