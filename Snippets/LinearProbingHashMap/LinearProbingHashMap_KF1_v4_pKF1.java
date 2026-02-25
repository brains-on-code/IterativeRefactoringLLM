package com.thealgorithms.datastructures.hashmap.hashing;

import java.util.ArrayList;

public class LinearProbingHashMap<Key extends Comparable<Key>, Value> extends Map<Key, Value> {
    private int tableCapacity;
    private Key[] keyTable;
    private Value[] valueTable;
    private int currentSize;

    public LinearProbingHashMap() {
        this(16);
    }

    @SuppressWarnings("unchecked")
    public LinearProbingHashMap(int initialCapacity) {
        this.tableCapacity = initialCapacity;
        this.keyTable = (Key[]) new Comparable[initialCapacity];
        this.valueTable = (Value[]) new Object[initialCapacity];
    }

    @Override
    public boolean put(Key key, Value value) {
        if (key == null) {
            return false;
        }

        if (currentSize > tableCapacity / 2) {
            resize(2 * tableCapacity);
        }

        int index = hash(key, tableCapacity);
        for (; keyTable[index] != null; index = nextIndex(index)) {
            if (key.equals(keyTable[index])) {
                valueTable[index] = value;
                return true;
            }
        }

        keyTable[index] = key;
        valueTable[index] = value;
        currentSize++;
        return true;
    }

    @Override
    public Value get(Key key) {
        if (key == null) {
            return null;
        }

        for (int index = hash(key, tableCapacity); keyTable[index] != null; index = nextIndex(index)) {
            if (key.equals(keyTable[index])) {
                return valueTable[index];
            }
        }

        return null;
    }

    @Override
    public boolean remove(Key key) {
        if (key == null || !containsKey(key)) {
            return false;
        }

        int index = hash(key, tableCapacity);
        while (!key.equals(keyTable[index])) {
            index = nextIndex(index);
        }

        keyTable[index] = null;
        valueTable[index] = null;

        index = nextIndex(index);
        while (keyTable[index] != null) {
            Key keyToRehash = keyTable[index];
            Value valueToRehash = valueTable[index];
            keyTable[index] = null;
            valueTable[index] = null;
            currentSize--;
            put(keyToRehash, valueToRehash);
            index = nextIndex(index);
        }

        currentSize--;
        if (currentSize > 0 && currentSize <= tableCapacity / 8) {
            resize(tableCapacity / 2);
        }

        return true;
    }

    @Override
    public boolean containsKey(Key key) {
        return get(key) != null;
    }

    @Override
    int size() {
        return currentSize;
    }

    @Override
    Iterable<Key> keys() {
        ArrayList<Key> collectedKeys = new ArrayList<>(currentSize);
        for (int index = 0; index < tableCapacity; index++) {
            if (keyTable[index] != null) {
                collectedKeys.add(keyTable[index]);
            }
        }

        collectedKeys.sort(Comparable::compareTo);
        return collectedKeys;
    }

    private int nextIndex(int currentIndex) {
        return (currentIndex + 1) % tableCapacity;
    }

    private void resize(int newCapacity) {
        LinearProbingHashMap<Key, Value> resizedMap = new LinearProbingHashMap<>(newCapacity);
        for (int index = 0; index < tableCapacity; index++) {
            if (keyTable[index] != null) {
                resizedMap.put(keyTable[index], valueTable[index]);
            }
        }

        this.keyTable = resizedMap.keyTable;
        this.valueTable = resizedMap.valueTable;
        this.tableCapacity = newCapacity;
    }
}